package com.baysoftware.bayfit

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.baysoftware.bayfit.history.view.HistoryListActivity
import com.baysoftware.bayfit.home.composable.HomeScreen
import com.baysoftware.bayfit.running.view.RunningTimerActivity
import com.baysoftware.bayfit.settings.view.SettingsActivity

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeScreen(
                onStartClicked = {
                    val intent = Intent(this, RunningTimerActivity::class.java)
                    startActivity(intent)
                },
                onHistoryClicked = {
                    val intent = Intent(this, HistoryListActivity::class.java)
                    startActivity(intent)
                },
                onSettingsClicked = {
                    val intent = Intent(this, SettingsActivity::class.java)
                    startActivity(intent)
                }
            )
        }
    }
}