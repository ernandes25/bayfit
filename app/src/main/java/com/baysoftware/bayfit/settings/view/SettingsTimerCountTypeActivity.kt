package com.baysoftware.bayfit.settings.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.lifecycleScope
import com.baysoftware.bayfit.R
import com.baysoftware.bayfit.settings.view.composable.SettingsTimerCountTypeScreen
import com.baysoftware.bayfit.settings.viewmodel.SettingsTimerCountTypeCommand
import com.baysoftware.bayfit.settings.viewmodel.SettingsTimerCountTypeIntent.LoadSetting
import com.baysoftware.bayfit.settings.viewmodel.SettingsTimerCountTypeViewModel
import kotlinx.coroutines.launch

class SettingsTimerCountTypeActivity : AppCompatActivity() {

    private val viewModel: SettingsTimerCountTypeViewModel by viewModels {
        SettingsTimerCountTypeViewModel.Factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Isto diz ao view model: "carregue a configuração atual pra mim"
        viewModel.onIntent(LoadSetting(this))

        // Isto diz ao compose: "renderize a tela SettingsTimerCountTypeScreen, configure o estado e
        // o que vai acontecer quando o usuário interagir com a tela" (no caso, chamar o
        // viewmodel.onIntent())
        setContent {
            SettingsTimerCountTypeScreen(
                viewState = viewModel.viewState.collectAsState().value
            ) {
                viewModel.onIntent(it)
            }
        }

        // Isto diz ao view model: "me avise quando algum comando for emitido, para que eu possa
        // lidar com ele"
        lifecycleScope.launch {
            viewModel.command.collect { command ->
                handleCommand(command)
            }
        }
    }

    private fun handleCommand(command: SettingsTimerCountTypeCommand) {
        when (command) {
            is SettingsTimerCountTypeCommand.ShowSelectionError -> {
                Toast.makeText(
                    this@SettingsTimerCountTypeActivity,
                    R.string.time_setting_error_message,
                    Toast.LENGTH_SHORT
                ).show()
            }
            is SettingsTimerCountTypeCommand.NavigateToTimerSetter -> {
                val intent = Intent(
                    this@SettingsTimerCountTypeActivity,
                    SettingsTimerSetterActivity::class.java
                )
                startActivity(intent)
                finish()
            }
            is SettingsTimerCountTypeCommand.ShowSaveConfirmation -> {
                Toast.makeText(
                    this@SettingsTimerCountTypeActivity,
                    R.string.time_setting_saved_message,
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }
}