package com.example.activitytrackerwearoschild.presentation.screens.home

import androidx.lifecycle.ViewModel
import com.example.activitytrackerwearoschild.data.DatabaseRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val databaseRepository: DatabaseRepository) : ViewModel() {
    fun sendQuickMessage(message: String) {
        CoroutineScope(Dispatchers.IO).launch {
            databaseRepository.sendQuickMessage(message)
        }
    }
}