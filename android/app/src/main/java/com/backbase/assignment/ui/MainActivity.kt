package com.backbase.assignment.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.backbase.assignment.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTheme(R.style.AppTheme)

        setContentView(R.layout.activity_main)

        val nestedNavHostFragment =
            supportFragmentManager.findFragmentById(R.id.movie_nav_container) as NavHostFragment

        val navController = nestedNavHostFragment.navController

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)

        bottomNavigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { controller, destination, arguments ->

            if (isTopLevelDestination(destination.id)) {
                bottomNavigationView.visibility = View.VISIBLE
                return@addOnDestinationChangedListener
            }

            bottomNavigationView.visibility = View.GONE
        }

    }

    private fun isTopLevelDestination(destination : Int): Boolean {
        val topLevelDestinations = getTopLevelDestinations()

        if (topLevelDestinations.contains(destination)) {
            return true
        }

        return false
    }

    private fun getTopLevelDestinations () : Set<Int>{
        return setOf(
            R.id.playingNowFragment,
            R.id.mostPopularFragment
        )
    }

}
