package ru.ulizina.todolist_bottom.ui.change_task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.ulizina.todolist_bottom.R

class ChangeTaskFragment : Fragment() {

    companion object {
        fun newInstance() = ChangeTaskFragment()
    }

    private lateinit var viewModel: ChangeTaskViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_change_task, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ChangeTaskViewModel::class.java)
        // TODO: Use the ViewModel
    }

}