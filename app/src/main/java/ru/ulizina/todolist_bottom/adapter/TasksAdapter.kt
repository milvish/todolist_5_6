package ru.ulizina.todolist_bottom.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.graphics.green
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.ulizina.todolist_bottom.data.entity.AllTaskAction
import ru.ulizina.todolist_bottom.databinding.ListItemBinding
import ru.ulizina.todolist_bottom.ui.all_tasks.AllTasksFragment
import ru.ulizina.todolist_bottom.ui.important_tasks.ImportantTasksFragment

class TasksAdapter(private val allTaskCallBacks: AllTasksFragment.Test): ListAdapter<AllTaskAction, TasksAdapter.AllTaskViewHolder>(DiffCallback) {
    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<AllTaskAction>() {
            override fun areItemsTheSame(oldItem: AllTaskAction, newItem: AllTaskAction): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: AllTaskAction, newItem: AllTaskAction): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllTaskViewHolder {
        return AllTaskViewHolder(ListItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ))
    }


    override fun onBindViewHolder(holder: AllTaskViewHolder, position: Int) {
        val taskItem = getItem(position)
        holder.textView.text = taskItem.task
        if (taskItem.doneStatus) holder.textView.paintFlags = holder.textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        else holder.textView.paintFlags = holder.textView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()

        holder.doneBox.isChecked = taskItem.doneStatus
        holder.importantBox.isChecked = taskItem.importantStatus

        holder.textView.setOnClickListener{ allTaskCallBacks.onTaskClick(taskItem.id) }
        holder.doneBox.setOnClickListener{ allTaskCallBacks.onDoneBoxClick(taskItem) }
        holder.deleteButton.setOnClickListener { allTaskCallBacks.onDeleteButtonClick(taskItem) }
        holder.importantBox.setOnClickListener{allTaskCallBacks.onImportantBoxClick(taskItem)}
    }
/*
    class ItemViewHolder(private val view: View): RecyclerView.ViewHolder(view){
        val doneBox: CheckBox = view.findViewById(R.id.done_Box)
        val textView: TextView = view.findViewById(R.id.task_recycler_info)
        val deleteButton: ImageButton = view.findViewById(R.id.delete_button) as ImageButton
        val importantBox: CheckBox = view.findViewById(R.id.important_Box)

 */
    class AllTaskViewHolder(binding: ListItemBinding): RecyclerView.ViewHolder(binding.root) {
        val textView:TextView = binding.taskRecyclerInfo
        val doneBox: CheckBox = binding.doneBox
        val deleteButton: ImageButton = binding.deleteButton
        val importantBox: CheckBox = binding.importantBox
    }
}