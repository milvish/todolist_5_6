package ru.ulizina.todolist_bottom.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.ulizina.todolist_bottom.data.entity.AllTaskAction

@Database(entities = [AllTaskAction::class], version = 1)
abstract class AllTaskDatabase : RoomDatabase() {

    abstract fun allTaskDao(): AllTaskDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AllTaskDatabase? = null

        fun getDatabase(context: Context,scope: CoroutineScope): AllTaskDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AllTaskDatabase::class.java,
                    "all_tasks_database"
                )
                    .addCallback(AllTaskDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                return instance
//                instance
            }
        }
        private class AllTaskDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            /**
             * Override the onCreate method to populate the database.
             */
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                // If you want to keep the data through app restarts,
                // comment out the following line.
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database.allTaskDao())
                    }
                }
            }
        }

        suspend fun populateDatabase(allTaskDao: AllTaskDao) {
            // Start the app with a clean database every time.
            // Not needed if you only populate on creation.

            for(i in 1 until 15){
                allTaskDao.insert(AllTaskAction(
                    task="Task $i",
                    description = "Info about task $i",
                    doneStatus = false,
                    importantStatus = false))
            }
            for(i in 1 until 15) {
                if (i % 2 == 0) {
                    allTaskDao.insert(
                        AllTaskAction(
                            task = "Task $i",
                            description = "Info about task $i",
                            doneStatus = true,
                            importantStatus = true
                        )
                    )
                }
            }



        }
    }
}

