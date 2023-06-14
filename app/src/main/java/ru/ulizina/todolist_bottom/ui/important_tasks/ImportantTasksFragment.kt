package ru.ulizina.todolist_bottom.ui.important_tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import ru.ulizina.todolist_bottom.AllTaskApplication
import ru.ulizina.todolist_bottom.R
import ru.ulizina.todolist_bottom.adapter.TasksAdapter
import ru.ulizina.todolist_bottom.data.entity.AllTaskAction
import ru.ulizina.todolist_bottom.databinding.FragmentAllTasksBinding
import ru.ulizina.todolist_bottom.databinding.FragmentImportantTasksBinding
import ru.ulizina.todolist_bottom.ui.all_tasks.ActivityViewModelsDelegate
import ru.ulizina.todolist_bottom.ui.all_tasks.AllTasksFragment
import ru.ulizina.todolist_bottom.ui.all_tasks.AllTasksViewModel

class ImportantTasksFragment : Fragment() {

    companion object {
        fun newInstance() = ImportantTasksFragment()
    }

    private var viewModel: AllTasksViewModel by ActivityViewModelsDelegate(
        AllTasksViewModel::class.java,
        { AllTasksViewModel.AllTaskViewModelFactory((activity?.application as AllTaskApplication).database.allTaskDao()) }
    )
    private var binding: FragmentImportantTasksBinding? = null
    private lateinit var recyclerView: RecyclerView

    data class Test(
        val onTaskClick: (index: Int) -> Unit,
        val onDoneBoxClick: (allTaskAction: AllTaskAction) -> Unit,
        val onDeleteButtonClick: (allTaskAction: AllTaskAction) -> Unit,
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentImportantTasksBinding.inflate(inflater, container, false)
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
            findNavController().navigate(R.id.action_importantTasksFragment_to_addTaskFragment, null)
        }


        val recyclerView = binding!!.recyclerView
        val adapter = TasksAdapter(
            AllTasksFragment.Test(
            {clickOnTaskHandler(it)},
            {clickOnDoneBoxHandler(it)},
            {clickOnDeleteHandler(it)},
                {clickOnImportantBoxHandler(it)},
        )
        )
        recyclerView.adapter = adapter

        viewModel.importantTasks.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }
    }
/*
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ImportantTasksViewModel::class.java)
        // TODO: Use the ViewModel
    }

 */

}