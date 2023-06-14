package ru.ulizina.todolist_bottom.ui.add_task

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import ru.ulizina.todolist_bottom.AllTaskApplication
import ru.ulizina.todolist_bottom.R
import ru.ulizina.todolist_bottom.databinding.FragmentAddTaskBinding
import ru.ulizina.todolist_bottom.databinding.FragmentAllTasksBinding
import ru.ulizina.todolist_bottom.ui.all_tasks.ActivityViewModelsDelegate
import ru.ulizina.todolist_bottom.ui.all_tasks.AllTasksViewModel

class AddTaskFragment : Fragment() {

    companion object {
        fun newInstance() = AddTaskFragment()
    }

    private var viewModel: AllTasksViewModel by ActivityViewModelsDelegate(
        AllTasksViewModel::class.java,
        { AllTasksViewModel.AllTaskViewModelFactory((activity?.application as AllTaskApplication).database.allTaskDao()) }
    )
    private var binding: FragmentAddTaskBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        return binding!!.root    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val button = view.findViewById<Button>(R.id.buttonAddTask)
        button?.setOnClickListener {
            val nameTask = binding?.taskNameAdd?.editText?.text.toString()
            println("name $nameTask")
            val descriptionTask = binding?.textInputLayout?.editText?.text.toString()
            println(descriptionTask)

            if (nameTask == "" )
                Toast.makeText(context, "Please, enter what do you want to do", Toast.LENGTH_SHORT).show()
            else{
                viewModel.addNewTask(nameTask, descriptionTask,false, true)
                findNavController().navigate(R.id.nav_all_task, null)
            }
            println(viewModel.allTasks.value)
        }
    }

}