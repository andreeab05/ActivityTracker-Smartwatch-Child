package com.example.activitytrackerwearoschild.presentation.screens.quickmessages

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.activitytrackerwearoschild.data.DatabaseRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QuickMessageViewModel(private val databaseRepository: DatabaseRepository) : ViewModel() {
    var messagePreview = mutableStateOf("Message Preview")
        private set

    fun updateMessagePreview(message: String) {
        messagePreview.value = message
    }

    fun sendQuickMessage(message: String) {
        if (message != "Message Preview") {
            CoroutineScope(Dispatchers.IO).launch {
                databaseRepository.sendQuickMessage(message)
            }
        }
    }
}