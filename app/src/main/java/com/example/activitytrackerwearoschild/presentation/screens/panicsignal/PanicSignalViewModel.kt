package com.example.activitytrackerwearoschild.presentation.screens.panicsignal

import androidx.lifecycle.ViewModel
import com.example.activitytrackerwearoschild.data.DatabaseRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PanicSignalViewModel(private val databaseRepository: DatabaseRepository) : ViewModel() {
    fun sendPanicSignal() {
        CoroutineScope(Dispatchers.IO).launch {
            databaseRepository.sendSOS()
            //databaseRepository.sendQuickMessage(message)
        }
    }
}