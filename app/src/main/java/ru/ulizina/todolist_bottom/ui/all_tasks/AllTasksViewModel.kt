package ru.ulizina.todolist_bottom.ui.all_tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import ru.ulizina.todolist_bottom.data.database.AllTaskDao
import ru.ulizina.todolist_bottom.data.entity.AllTaskAction
import kotlinx.coroutines.launch

class AllTasksViewModel(private val allTaskDao: AllTaskDao)  : ViewModel(){
    val allTasks = allTaskDao.getAllTasks().asLiveData()
    val importantTasks = allTaskDao.getImportantTasks().asLiveData()
    val doneTasks = allTaskDao.getDoneTasks().asLiveData()
    fun addNewTask(task: String, description: String, doneStatus: Boolean, importantStatus: Boolean) =
        viewModelScope.launch { allTaskDao.insert(AllTaskAction(
            task = task,
            description = description,
            doneStatus = doneStatus,
            importantStatus = importantStatus))
        }


    fun retrieveAction(id: Int) = allTaskDao.getItem(id).asLiveData()

    fun updateTaskDoneStatus(task: AllTaskAction){
        val newTask = task.copy(doneStatus = !task.doneStatus)
        viewModelScope.launch {
            allTaskDao.updateReady(newTask)
        }
    }
    fun updateTaskImportantStatus(task: AllTaskAction){
        val newTask = task.copy(importantStatus = !task.importantStatus)
        viewModelScope.launch {
            allTaskDao.updateReady(newTask)
        }
    }

    fun deleteTask(allTaskAction: AllTaskAction) = viewModelScope.launch {
        allTaskDao.delete(allTaskAction)
    }




    class AllTaskViewModelFactory(private val allTaskDao: AllTaskDao): ViewModelProvider.Factory{
        override fun <T: ViewModel> create (modelClass: Class<T>):T{
            if (modelClass.isAssignableFrom(AllTasksViewModel::class.java)){
                @Suppress("UNCHECKED_CAST")
                return AllTasksViewModel(allTaskDao) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }

    }
}