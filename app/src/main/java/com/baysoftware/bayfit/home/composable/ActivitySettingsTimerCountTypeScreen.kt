package com.baysoftware.bayfit.home.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
// Removidos: getValue, mutableStateOf, remember, setValue (se não usados para outros estados internos)
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.baysoftware.bayfit.R
import com.baysoftware.bayfit.ui.theme.Archivo

// Enum para indicar qual opção está selecionada (a ser passado como parâmetro)
// Você pode definir este enum em um local mais global se for usado por múltiplas classes.
enum class TimerTypeOption {
    FREE, TIME, NONE // Adicionada a opção NONE para quando nada está selecionado
}

@Composable
fun ActivitySettingsTimerCountTypeScreen(
    selectedOption: TimerTypeOption, // Novo parâmetro: qual opção está atualmente selecionada
    onButtonOk: () -> Unit,
    onRadioButton1Select: () -> Unit, // Renomeado para clareza: indica intenção de selecionar
    onRadioButton2Select: () -> Unit  // Renomeado para clareza: indica intenção de selecionar
) {
    Column(
        modifier = Modifier
            .background(color = Color.Black)
            .fillMaxSize()
            .padding(top = 82.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Opção "LIVRE"
        Row(
            modifier = Modifier
                .selectable(
                    selected = (selectedOption == TimerTypeOption.FREE),
                    onClick = onRadioButton1Select, // Chama o callback para que o estado seja atualizado externamente
                    role = Role.RadioButton
                )
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = (selectedOption == TimerTypeOption.FREE),
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
                fontSize = 40.sp,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        // Opção "TEMPO"
        Row(
            modifier = Modifier
                .selectable(
                    selected = (selectedOption == TimerTypeOption.TIME),
                    onClick = onRadioButton2Select, // Chama o callback para que o estado seja atualizado externamente
                    role = Role.RadioButton
                )
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = (selectedOption == TimerTypeOption.TIME),
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
                fontSize = 40.sp,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        // Botão "OK"
        Button(
            onClick = onButtonOk, // Mantém o callback original para o botão OK
            modifier = Modifier
                .width(250.dp)
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


@Preview
@Composable
fun ActivitySettingsTimerCountTypeScreenPreviewFreeSelected() {
    ActivitySettingsTimerCountTypeScreen(
        selectedOption = TimerTypeOption.FREE, // Exemplo com "LIVRE" selecionado
        onButtonOk = {},
        onRadioButton1Select = {},
        onRadioButton2Select = {}
    )
}

@Preview
@Composable
fun ActivitySettingsTimerCountTypeScreenPreviewTimeSelected() {
    ActivitySettingsTimerCountTypeScreen(
        selectedOption = TimerTypeOption.TIME, // Exemplo com "TEMPO" selecionado
        onButtonOk = {},
        onRadioButton1Select = {},
        onRadioButton2Select = {}
    )
}

@Preview
@Composable
fun ActivitySettingsTimerCountTypeScreenPreviewNoneSelected() {
    ActivitySettingsTimerCountTypeScreen(
        selectedOption = TimerTypeOption.NONE, // Exemplo com nenhum selecionado
        onButtonOk = {},
        onRadioButton1Select = {},
        onRadioButton2Select = {}
    )
}