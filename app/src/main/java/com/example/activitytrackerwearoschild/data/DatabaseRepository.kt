package com.example.activitytrackerwearoschild.data

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.provider.Settings
import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database
import com.google.firebase.ktx.Firebase
import java.util.Date
import java.util.Locale

class DatabaseRepository(context: Context) {
    private val database =
        com.google.firebase.Firebase.database("https://activitytracker-8d328-default-rtdb.europe-west1.firebasedatabase.app")
    private val deviceId =
        Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    private val user = Firebase.auth.currentUser
    fun sendQuickMessage(message: String) {
        val databaseRef = database.getReference("messages")
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val time = dateFormat.format(Date())
        val messageData = HashMap<String, Any>()
        //messageData["deviceId"] = deviceId
        messageData["message"] = message
        messageData["time"] = time

        Log.d("RTB", "$database, $databaseRef")
        if (user != null) {
            databaseRef.child(deviceId).setValue(messageData)
                .addOnSuccessListener {
                    // Data was successfully written to the database
                    Log.d("Database", "Success")
                }
                .addOnFailureListener { e ->
                    // Handle any errors
                    Log.d("Database", "Error: ${e.message}")
                    e.printStackTrace()
                }
        } else {
            Log.d("Database", "Not authenticated")
        }
    }
}