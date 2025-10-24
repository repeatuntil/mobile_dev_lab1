package com.example.messenger.ui

import android.util.Log
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.content.Context
import com.example.messenger.databinding.FragmentSettingsBinding
import android.content.SharedPreferences
import com.example.messenger.R

class SettingsFragment: Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    private lateinit var prefs: SharedPreferences
    private val tag: String = "Settings"

    companion object {
        private const val prefs_name = "app_settings"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefs = requireContext().getSharedPreferences(prefs_name, Context.MODE_PRIVATE)
        Log.d(tag, "onCreate: Fragment создан")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d(tag, "onCreateView: Создание View для Fragment")
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(tag, "onViewCreated: View создан")

        applyChanges()
    }

    override fun onStart() {
        super.onStart()
        Log.d(tag, "onStart: Fragment запущен")
    }

    private fun applyChanges() {
        binding.notificationsSwitch.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("notifications", isChecked).apply()
        }

        binding.themeRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            val theme = when (checkedId) {
                R.id.theme_light -> "light"
                R.id.theme_dark -> "dark"
                else -> "system"
            }
            prefs.edit().putString("theme", theme).apply()
        }

        binding.resetSettingsButton.setOnClickListener {
            resetSettings()
        }
    }

    private fun loadSettings() {
        binding.notificationsSwitch.isChecked = prefs.getBoolean("notifications", true)

        when (prefs.getString("theme", "system")) {
            "light" -> binding.themeLight.isChecked = true
            "dark" -> binding.themeDark.isChecked = true
            else -> binding.themeSystem.isChecked = true
        }
    }

    private fun resetSettings() {
        with(prefs.edit()) {
            putBoolean("notifications", true)
            putString("theme", "system")
            apply()
        }
        loadSettings()
    }
}
