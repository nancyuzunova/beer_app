package com.example.beerapp.presenter

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Switch
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import coil.transform.CircleCropTransformation
import com.example.beerapp.R
import com.example.beerapp.databinding.FragmentSettingsBinding
import com.example.beerapp.ui.LoginActivity
import com.example.domain.controller.SettingsViewModel
import java.util.*

class SettingsFragment : Fragment() {

    private var notifications: Switch? = null
    private var fromFavourites: Switch? = null
    private var darkMode: Switch? = null

    private val viewModel by lazy {
        ViewModelProvider(this).get(SettingsViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val binding = FragmentSettingsBinding.inflate(inflater)
        binding.viewModel = viewModel

        val user = viewModel.getUserInfo()
        binding?.username?.text = (user?.givenName + " " + user?.familyName)
        binding?.email?.text = user?.email
        loadAvatar(binding?.profilePicture!!, user?.photoUrl)

        notifications = binding.notificationsSwitch
        fromFavourites = binding.favSwitch
        darkMode = binding.darkModeSwitch

        notifications?.isChecked = viewModel.notifications
        fromFavourites?.isChecked = viewModel.fromFavourites
        darkMode?.isChecked = viewModel.darkMode

        setLocale(Locale.getDefault().language)

        binding.logout.setOnClickListener {
            viewModel.signOut(context) {
                val intent = Intent(context, LoginActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
        }
        darkMode?.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
        return binding.root
    }

    override fun onPause() {
        saveSettings()
        super.onPause()
    }

    private fun setLocale(language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val resources = context?.resources
        val config = Configuration(resources?.configuration)
        config.setLocale(locale)
        context?.createConfigurationContext(config)
    }

    private fun saveSettings() {
        viewModel.saveSettings(notifications?.isChecked, fromFavourites?.isChecked, darkMode?.isChecked)
    }

    private fun loadAvatar(profilePicture: ImageView, imageUrl: Uri?) {
        if(imageUrl == null) {
            profilePicture.load(R.drawable.user){
                transformations(CircleCropTransformation())
            }
        } else {
            profilePicture.load(imageUrl){
                transformations(CircleCropTransformation())
            }
        }
    }
}
