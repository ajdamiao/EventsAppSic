package com.example.appevents

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.appevents.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    init {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarInclude.toolbar)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.mainContent) as NavHostFragment
        navController = navHostFragment.navController

        setUpNavigation()
    }

    private fun setUpNavigation() {
        navController.addOnDestinationChangedListener { _: NavController, nd: NavDestination, _: Bundle? ->
            supportActionBar?.setDisplayHomeAsUpEnabled(nd.id != R.id.homeFragment)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, binding.drawerLayout)
    }

    fun setToolbarTitle(title: String) {
        binding.toolbarInclude.toolbarTitle.text = title
    }
}