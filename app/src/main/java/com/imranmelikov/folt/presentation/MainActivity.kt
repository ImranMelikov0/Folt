package com.imranmelikov.folt.presentation

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.imranmelikov.folt.R
import com.imranmelikov.folt.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var bottomNav:BottomNavigationView
    private lateinit var navController:NavController
    private lateinit var binding:ActivityMainBinding
    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        bottomNav = binding.bottomNav
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        bottomNav.setupWithNavController(navController)
//        NavigationUI.setupWithNavController(bottomNav,navController)
    }
    fun hideBottomNav(){
        binding.bottomNav.visibility=View.GONE
    }
    fun showBottomNav(){
        binding.bottomNav.visibility=View.VISIBLE
    }
}