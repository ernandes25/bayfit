package com.baysoftware.bayfit.settings.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.lifecycleScope
import com.baysoftware.bayfit.home.composable.TimerTypeOption
import com.baysoftware.bayfit.home.composable.ActivitySettingsTimerCountTypeScreen
import com.baysoftware.bayfit.preferences.UserManager
import kotlinx.coroutines.launch


class SettingsTimerCountTypeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            // Estado para controlar a opção selecionada na UI
            var selectedOptionState by remember { mutableStateOf(TimerTypeOption.NONE) }

            // LaunchedEffect para carregar o valor inicial do UserManager uma vez
            LaunchedEffect(key1 = Unit) {
                val currentMode =
                    UserManager.getInstance().readTimerMode(this@SettingsTimerCountTypeActivity)
                selectedOptionState = when (currentMode) {
                    UserManager.TimerMode.FREE -> TimerTypeOption.FREE
                    UserManager.TimerMode.PREDEFINED -> TimerTypeOption.TIME
                    else -> TimerTypeOption.NONE // Ou UserManager.TimerMode.UNDEFINED
                }
            }

            ActivitySettingsTimerCountTypeScreen(
                selectedOption = selectedOptionState,
                onRadioButton1Select = { // Usuário selecionou "LIVRE"
                    selectedOptionState = TimerTypeOption.FREE
                    // A lógica de salvar será feita no botão OK conforme o código original,
                    // mas você poderia salvar imediatamente se quisesse:
                    // lifecycleScope.launch {
                    //     UserManager.getInstance().saveTimerMode(this@SettingsTimerCountTypeActivity, UserManager.TimerMode.FREE)
                    // }
                },
                onRadioButton2Select = { // Usuário selecionou "PREDEFINIDO/TEMPO"
                    selectedOptionState = TimerTypeOption.TIME
                    // lifecycleScope.launch {
                    //     UserManager.getInstance().saveTimerMode(this@SettingsTimerCountTypeActivity, UserManager.TimerMode.PREDEFINED)
                    // }
                },
                onButtonOk = {
                    lifecycleScope.launch {
                        val modeToSave = when (selectedOptionState) {
                            TimerTypeOption.FREE -> UserManager.TimerMode.FREE
                            TimerTypeOption.TIME -> UserManager.TimerMode.PREDEFINED
                            TimerTypeOption.NONE -> UserManager.TimerMode.UNDEFINED // ou não salvar nada e mostrar Toast
                        }

                        if (modeToSave == UserManager.TimerMode.UNDEFINED) {
                            Toast.makeText(
                                this@SettingsTimerCountTypeActivity,
                                "Por favor, selecione uma opção",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            UserManager.getInstance()
                                .saveTimerMode(this@SettingsTimerCountTypeActivity, modeToSave)

                            if (modeToSave == UserManager.TimerMode.FREE) {
                                finish() // Finaliza a atividade atual
                            } else if (modeToSave == UserManager.TimerMode.PREDEFINED) {
                                val intent = Intent(
                                    this@SettingsTimerCountTypeActivity,
                                    SettingsTimerSetterActivity::class.java // Navega para a tela de definir tempo
                                )
                                startActivity(intent)
                                finish() // Finaliza a atividade atual
                            }
                        }
                    }
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