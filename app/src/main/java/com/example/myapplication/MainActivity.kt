package com.example.myapplication

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import com.example.myapplication.ui.CheckRequestScreen
import com.example.myapplication.ui.PersonSelectScreen
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 保存された personId を取得
        val prefs = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val savedPersonId = prefs.getLong("selected_person_id", -1L)

        setContent {
            MyApplicationTheme {

                var selectedPersonId by remember {
                    mutableStateOf(
                        if (savedPersonId != -1L) savedPersonId else null
                    )
                }

                if (selectedPersonId == null) {

                    // 人選択画面
                    PersonSelectScreen(
                        onPersonSelected = { personId ->
                            prefs.edit()
                                .putLong("selected_person_id", personId)
                                .apply()

                            selectedPersonId = personId
                        }
                    )

                } else {

                    // 服薬確認画面
                    CheckRequestScreen(
                        personId = selectedPersonId!!
                    )

                }
            }
        }
    }
}