package ru.ulizina.todolist_bottom

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import ru.ulizina.todolist_bottom.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_all_task, R.id.navigation_important_tasks, R.id.navigation_done_tasks
            )
        )
        //setupActionBarWithNavController(navController, appBarConfiguration)
        //navView.setupWithNavController(navController)
        //val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        //bottomNav?.setupWithNavController(navController)
        //supportFragmentManager.beginTransaction().apply {
         //   replace()
           // commit()


        @Suppress("DEPRECATION")
        navView?.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_all_task -> {
                    navController.navigate(R.id.nav_all_task)
                    true
                }
                R.id.navigation_important_tasks -> {
                    navController.navigate(R.id.nav_important_tasks)
                    true
                }
                R.id.navigation_done_tasks ->{
                    navController.navigate(R.id.nav_done_tasks)
                    true
                }
                else -> false
            }
        }

    }
/*
    private fun setupBottomNavMenu(navController: NavController) {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        bottomNav?.setupWithNavController(navController)
    }

 */
}