package com.baysoftware.bayfit.settings.viewmodel

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