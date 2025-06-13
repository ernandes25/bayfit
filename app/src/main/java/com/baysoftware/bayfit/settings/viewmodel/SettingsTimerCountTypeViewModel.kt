package com.baysoftware.bayfit.settings.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.baysoftware.bayfit.preferences.UserManager
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class SettingsTimerCountTypeViewModel() : ViewModel() {

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                SettingsTimerCountTypeViewModel()
            }
        }
    }

    private val _viewState = MutableStateFlow(SettingsTimerCountTypeViewState())
    val viewState = _viewState.asStateFlow()

    private val _intent = MutableSharedFlow<SettingsTimerCountTypeIntent>()
    val intent = _intent.asSharedFlow()

    private val _command: Channel<SettingsTimerCountTypeCommand> = Channel()
    val command = _command.receiveAsFlow()

    /**
     * Construtor padrão para o ViewModel.
     * Inicializa o fluxo de intenções e observa as mudanças de estado.
     */
    init {
        viewModelScope.launch {
            intent.collect {
                handleIntent(it)
            }
        }
    }

    /**
     * Método que chamamos para enviar intenções ao ViewModel.
     */
    fun onIntent(intent: SettingsTimerCountTypeIntent) =
        viewModelScope.launch { _intent.emit(intent) }

    /**
     * Método que chamamos para enviar comandos ("side effects" ou "efeitos colaterais" -- diálogos,
     * navegação -- ao ViewModel.
     */
    private fun sendCommand(command: () -> SettingsTimerCountTypeCommand) {
        viewModelScope.launch {
            _command.send(command())
        }
    }

    /**
     * Método para lidar ("handle") com os intents chamados.
     */
    private fun handleIntent(intent: SettingsTimerCountTypeIntent) {
        when (intent) {
            is SettingsTimerCountTypeIntent.LoadSetting -> loadTimerConfiguration(intent.context)
            is SettingsTimerCountTypeIntent.SetTimerCountType -> setTimerConfiguration(intent.type)
            is SettingsTimerCountTypeIntent.SaveTimerConfiguration ->
                saveTimerConfiguration(intent.context, _viewState.value.selectedOption)
        }
    }

    private fun loadTimerConfiguration(context: Context) {
        viewModelScope.launch {
            val currentMode = UserManager.getInstance().readTimerMode(context)
            val selectedOption = when (currentMode) {
                UserManager.TimerMode.FREE -> TimerTypeOption.FREE
                UserManager.TimerMode.PREDEFINED -> TimerTypeOption.TIME
                else -> TimerTypeOption.NONE
            }
            _viewState.value = _viewState.value.copy(selectedOption = selectedOption)
        }
    }

    private fun setTimerConfiguration(optionToSave: TimerTypeOption) {
        viewModelScope.launch {
            _viewState.value = _viewState.value.copy(selectedOption = optionToSave)
        }
    }

    private fun saveTimerConfiguration(context: Context, selectedOption: TimerTypeOption) {
        viewModelScope.launch {
            val modeToSave = when (selectedOption) {
                TimerTypeOption.FREE -> UserManager.TimerMode.FREE
                TimerTypeOption.TIME -> UserManager.TimerMode.PREDEFINED
                TimerTypeOption.NONE -> UserManager.TimerMode.UNDEFINED
            }

            if (modeToSave == UserManager.TimerMode.UNDEFINED) {
                sendCommand { SettingsTimerCountTypeCommand.ShowSelectionError }
            } else {
                UserManager.getInstance().saveTimerMode(context, modeToSave)

                if (modeToSave == UserManager.TimerMode.FREE) {
                    sendCommand { SettingsTimerCountTypeCommand.ShowSaveConfirmation }
                } else {
                    sendCommand { SettingsTimerCountTypeCommand.NavigateToTimerSetter }
                }
            }
        }
    }

}

/**
 * Classe de Intent > ações que o ViewModel pode receber.
 */
sealed class SettingsTimerCountTypeIntent {
    data class LoadSetting(val context: Context) : SettingsTimerCountTypeIntent()
    data class SetTimerCountType(val type: TimerTypeOption) : SettingsTimerCountTypeIntent()
    data class SaveTimerConfiguration(val context: Context) : SettingsTimerCountTypeIntent()
}

/**
 * Classe de Commands > ações que o ViewModel pode emitir para a UI.
 */
sealed class SettingsTimerCountTypeCommand {
    data object ShowSelectionError : SettingsTimerCountTypeCommand()
    data object NavigateToTimerSetter : SettingsTimerCountTypeCommand()
    data object ShowSaveConfirmation : SettingsTimerCountTypeCommand()
}

/**
 * Classe de ViewState > estado que o ViewModel mantém e que a UI observa.
 */
data class SettingsTimerCountTypeViewState(
    val selectedOption: TimerTypeOption = TimerTypeOption.NONE
)

// Enum para indicar qual opção está selecionada (a ser passado como parâmetro)
// Você pode definir este enum em um local mais global se for usado por múltiplas classes.
enum class TimerTypeOption {
    FREE, // Sem limite de tempo
    TIME, // Com limite de tempo
    NONE // Nada selecionado
}