package com.example.beerapp.presenter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.beerapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class FragmentsContainer : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewGroup = inflater.inflate(R.layout.fragments_container, container, false)
        val bottomNav: BottomNavigationView = viewGroup.findViewById(R.id.bottom_navigation_view)
        val navHostFragment = childFragmentManager.findFragmentById(R.id.nestedNavHostFragment) as NavHostFragment
        NavigationUI.setupWithNavController(bottomNav, navHostFragment.navController)
        return viewGroup
    }
}