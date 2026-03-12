package com.example.myapplication

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.myapplication.ui.CheckRequestScreen
import com.example.myapplication.ui.PersonSelectScreen
import com.example.myapplication.ui.RailsWebScreen
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // カメラの実行時 permission を要求
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                1
            )
        }

        val prefs = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val savedPersonId = prefs.getLong("selected_person_id", -1L)

        setContent {
            MyApplicationTheme {
                var selectedPersonId by remember {
                    mutableStateOf(
                        if (savedPersonId != -1L) savedPersonId else null
                    )
                }

                var showWeb by remember { mutableStateOf(false) }

                if (showWeb) {
                    RailsWebScreen(
                        url = "https://railsgirls-psq6.onrender.com/",
                    )
                } else if (selectedPersonId == null) {
                    PersonSelectScreen(
                        onPersonSelected = { personId ->
                            prefs.edit()
                                .putLong("selected_person_id", personId)
                                .apply()

                            selectedPersonId = personId
                        },
                        onOpenWeb = {
                            showWeb = true
                        }
                    )
                } else {
                    CheckRequestScreen(
                        personId = selectedPersonId!!,
                        onAdminLongPress = {
                            showWeb = true
                        }
                    )
                }
            }
        }
    }
}