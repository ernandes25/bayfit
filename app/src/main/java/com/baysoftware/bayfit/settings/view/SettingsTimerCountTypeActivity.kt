package com.baysoftware.bayfit.settings.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.baysoftware.bayfit.databinding.ActivitySettingsTimerCountTypeBinding
import com.baysoftware.bayfit.history.view.HistoryListActivity
import com.baysoftware.bayfit.home.composable.ActivitySettingsTimerCountTypeScreen
import com.baysoftware.bayfit.home.composable.SettingsScreen
import com.baysoftware.bayfit.preferences.UserManager
import com.baysoftware.bayfit.running.view.RunningTimerActivity
import kotlinx.coroutines.launch

class SettingsTimerCountTypeActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ActivitySettingsTimerCountTypeScreen(
                onRadioButton1Select = {
                    val intent = Intent(this, SettingsTimerCountTypeActivity::class.java)
                    startActivity(intent)
                },
                onRadioButton2Select = {
                    val intent = Intent(this, SettingsTimerCountTypeActivity::class.java)
                    startActivity(intent)
                },
                onButtonOk = {
                    val intent = Intent(this, SettingsTimerCountTypeActivity::class.java)
                    startActivity(intent)
                }
            )
        }



    }
}



//    private fun setupListeners() {
//        lifecycleScope.launch {
//            val restType =
//                UserManager.getInstance().readTimerMode(this@SettingsTimerCountTypeActivity)
//
//            if (restType == UserManager.TimerMode.FREE) {
//                binding.radioButton1.isChecked = true
//            } else if (restType == UserManager.TimerMode.PREDEFINED) {
//                binding.radioButton2.isChecked = true
//            }
//        }
//
//        binding.buttonOkcountType.setOnClickListener {
//            lifecycleScope.launch {
//
//                val selectedButton = if (binding.radioButton1.isChecked) {
//                    finish()
//                    UserManager.TimerMode.FREE
//                } else if (binding.radioButton2.isChecked) {
//                    val intent = Intent(
//                        this@SettingsTimerCountTypeActivity,
//                        SettingsTimerSetterActivity::class.java
//                    )
//                    startActivity(intent)
//                    finish() // Finaliza a atividade atual
//                    UserManager.TimerMode.PREDEFINED
//                } else {
//                    Toast.makeText(
//                        this@SettingsTimerCountTypeActivity,
//                        "Por favor, selecione uma opção",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    UserManager.TimerMode.UNDEFINED
//                }
//                UserManager.getInstance()
//                    .saveTimerMode(this@SettingsTimerCountTypeActivity, selectedButton)
//            }
//        }
//    }
//}