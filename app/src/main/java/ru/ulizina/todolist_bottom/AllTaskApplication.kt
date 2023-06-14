package ru.ulizina.todolist_bottom

import android.app.Application
import ru.ulizina.todolist_bottom.data.database.AllTaskDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class AllTaskApplication:Application() {
    private val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { AllTaskDatabase.getDatabase(this, applicationScope) }
}