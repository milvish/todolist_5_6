package ru.ulizina.todolist_bottom.ui.all_tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import ru.ulizina.todolist_bottom.AllTaskApplication
import ru.ulizina.todolist_bottom.R
import ru.ulizina.todolist_bottom.adapter.TasksAdapter
import ru.ulizina.todolist_bottom.data.entity.AllTaskAction
import ru.ulizina.todolist_bottom.databinding.FragmentAllTasksBinding
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty


class AllTasksFragment : Fragment() {

    companion object {
        fun newInstance() = AllTasksFragment()
    }

    private var viewModel: AllTasksViewModel by ActivityViewModelsDelegate(
        AllTasksViewModel::class.java,
        { AllTasksViewModel.AllTaskViewModelFactory((activity?.application as AllTaskApplication).database.allTaskDao()) }
    )
    private var binding: FragmentAllTasksBinding? = null


    ///


    data class Test(
        val onTaskClick: (index: Int) -> Unit,
        val onDoneBoxClick: (allTaskAction: AllTaskAction) -> Unit,
        val onDeleteButtonClick: (allTaskAction: AllTaskAction) -> Unit,
        val onImportantBoxClick: (allTaskAction: AllTaskAction) ->Unit,
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        val taskId = arguments?.getInt("taskId")
        binding = FragmentAllTasksBinding.inflate(inflater, container, false)
        return binding!!.root
    }


    private fun clickOnTaskHandler(id: Int){
        findNavController().navigate(R.id.action_navigation_all_task_to_taskInfoFragment)
    }

    private fun clickOnDoneBoxHandler(allTaskAction: AllTaskAction){
        viewModel.updateTaskDoneStatus(allTaskAction)
    }

    private fun clickOnDeleteHandler(allTaskAction: AllTaskAction){
        viewModel.deleteTask(allTaskAction)
    }

    private fun clickOnImportantBoxHandler(allTaskAction: AllTaskAction){
        viewModel.updateTaskImportantStatus(allTaskAction)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val addImageButton = view.findViewById<ImageButton>(R.id.add_image_button)
        addImageButton?.setOnClickListener {
          findNavController().navigate(R.id.action_navigation_all_task_to_addTaskFragment, null)
        }


        val recyclerView = binding!!.recyclerView
        val adapter = TasksAdapter(Test(
            {clickOnTaskHandler(it)},
            {clickOnDoneBoxHandler(it)},
            {clickOnDeleteHandler(it)},
            {clickOnImportantBoxHandler(it)},
        ))
        recyclerView.adapter = adapter

        viewModel.allTasks.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }


            // recyclerView?.setHasFixedSize(true)

    }



}

class ActivityViewModelsDelegate<VM : ViewModel>(
    private val viewModelClass: Class<VM>,
    private val factory: () -> ViewModelProvider.Factory
) : ReadWriteProperty<Fragment, VM> {

    private var viewModel: VM? = null

    override fun getValue(thisRef: Fragment, property: KProperty<*>): VM {
        val vm = viewModel
        if (vm != null) {
            return vm
        }
        val value = thisRef.requireActivity().let { activity ->
            ViewModelProvider(activity.viewModelStore, factory.invoke())[viewModelClass]
        }
        viewModel = value
        return value
    }

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: VM) {
        viewModel = value
    }
}