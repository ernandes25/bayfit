package com.baysoftware.bayfit.settings.view.composable

// Removidos: getValue, mutableStateOf, remember, setValue (se não usados para outros estados internos)
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.baysoftware.bayfit.R
import com.baysoftware.bayfit.settings.viewmodel.SettingsTimerCountTypeIntent
import com.baysoftware.bayfit.settings.viewmodel.SettingsTimerCountTypeViewState
import com.baysoftware.bayfit.settings.viewmodel.TimerTypeOption
import com.baysoftware.bayfit.ui.theme.Archivo

@Composable
fun SettingsTimerCountTypeScreen(
    viewState: SettingsTimerCountTypeViewState, // Define o estado da tela
    onIntent: (SettingsTimerCountTypeIntent) -> Unit, // Callback para enviar intents ao ViewModel
) {
    Column(
        modifier = Modifier
            .background(color = Color.Black)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxWidth(.55f), // Ajusta a largura para 60% da tela,
            horizontalAlignment = Alignment.Start,
        ) {
            // Opção "LIVRE"
            Row(
                modifier = Modifier
                    .selectable(
                        selected = (viewState.selectedOption == TimerTypeOption.FREE),
                        onClick = { onIntent(SettingsTimerCountTypeIntent.SetTimerCountType(TimerTypeOption.FREE)) }, // Chama o callback para que o estado seja atualizado externamente
                        role = Role.RadioButton
                    )
                    .padding(vertical = 8.dp),

                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
            ) {
                RadioButton(
                    selected = (viewState.selectedOption == TimerTypeOption.FREE),
                    onClick = null, // onClick é tratado pelo Row.selectable
                    colors = RadioButtonDefaults.colors(
                        selectedColor = Color.White,
                        unselectedColor = Color.Gray
                    )
                )
                Text(
                    text = stringResource(id = R.string.free).uppercase(),
                    fontFamily = Archivo,
                    color = Color.White,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            // Opção "TEMPO"
            Row(
                modifier = Modifier
                    .selectable(
                        selected = (viewState.selectedOption == TimerTypeOption.TIME),
                        onClick = { onIntent(SettingsTimerCountTypeIntent.SetTimerCountType(TimerTypeOption.TIME)) }, // Chama o callback para que o estado seja atualizado externamente
                        role = Role.RadioButton
                    )
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (viewState.selectedOption == TimerTypeOption.TIME),
                    onClick = null, // onClick é tratado pelo Row.selectable
                    colors = RadioButtonDefaults.colors(
                        selectedColor = Color.White,
                        unselectedColor = Color.Gray
                    )
                )
                Text(
                    text = stringResource(id = R.string.time).uppercase(),
                    fontFamily = Archivo,
                    color = Color.White,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }

        val context = LocalContext.current
        // Botão "OK"
        Button(
            onClick = { onIntent(SettingsTimerCountTypeIntent.SaveTimerConfiguration(context)) }, // Mantém o callback original para o botão OK
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            ),
            shape = RectangleShape,
            contentPadding = PaddingValues(0.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.rectangle_green),
                    contentDescription = stringResource(id = R.string.button_ok),
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )
                Text(
                    fontFamily = Archivo,
                    text = stringResource(id = R.string.button_ok).uppercase(),
                    color = Color.White,
                    fontSize = 40.sp
                )
            }
        }
    }
}

class SettingsTimerCountTypeScreenPreviews {

    @Preview(name = "Square Screen Preview", showSystemUi = true, device = "id:wearos_rect")
    @Composable
    fun SettingsTimerCountTypeScreenPreviewFreeSelected() {
        SettingsTimerCountTypeScreen(
            viewState = SettingsTimerCountTypeViewState(
                selectedOption = TimerTypeOption.FREE
            ), // Exemplo com "LIVRE" selecionado
            onIntent = {}
        )
    }

    @Preview(name = "Square Screen Preview", showSystemUi = true, device = "id:wearos_rect")
    @Composable
    fun SettingsTimerCountTypeScreenPreviewTimeSelected() {
        SettingsTimerCountTypeScreen(
            viewState = SettingsTimerCountTypeViewState(
                selectedOption = TimerTypeOption.TIME
            ), // Exemplo com "LIVRE" selecionado
            onIntent = {}
        )
    }

    @Preview(name = "Square Screen Preview", showSystemUi = true, device = "id:wearos_rect")
    @Composable
    fun SettingsTimerCountTypeScreenPreviewNoneSelected() {
        SettingsTimerCountTypeScreen(
            viewState = SettingsTimerCountTypeViewState(
                selectedOption = TimerTypeOption.NONE
            ), // Exemplo com "LIVRE" selecionado
            onIntent = {}
        )
    }

    @Preview(name = "Square Screen Preview", showSystemUi = true, device = "id:wearos_small_round")
    @Composable
    fun SettingsTimerCountTypeScreenPreviewNoneSmallRound() {
        SettingsTimerCountTypeScreen(
            viewState = SettingsTimerCountTypeViewState(
                selectedOption = TimerTypeOption.NONE
            ), // Exemplo com "LIVRE" selecionado
            onIntent = {}
        )
    }

    @Preview(name = "Square Screen Preview", showSystemUi = true, device = "id:wearos_large_round")
    @Composable
    fun SettingsTimerCountTypeScreenPreviewNoneLargeRound() {
        SettingsTimerCountTypeScreen(
            viewState = SettingsTimerCountTypeViewState(
                selectedOption = TimerTypeOption.NONE
            ), // Exemplo com "LIVRE" selecionado
            onIntent = {}
        )
    }
}