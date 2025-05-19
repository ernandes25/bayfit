package com.baysoftware.bayfit.settings.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.baysoftware.bayfit.home.composable.SettingsScreen



class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SettingsScreen(
                onButtonRestTime = {
                    val intent =
                        Intent(this, SettingsTimerCountTypeActivity::class.java)
                    startActivity(intent)
                }
            )
        }
    }
}
