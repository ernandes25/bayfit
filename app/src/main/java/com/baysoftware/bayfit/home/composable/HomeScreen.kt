package com.baysoftware.bayfit.home.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.baysoftware.bayfit.R
import com.baysoftware.bayfit.ui.theme.Archivo

@Composable
fun HomeScreen(
    onStartClicked: () -> Unit,
    onHistoryClicked: () -> Unit,
    onSettingsClicked: () -> Unit,
) {
    Column(
        modifier = Modifier
            .background(color = Color.Black)
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Botão "Play"
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = Color.White
            ),
            modifier = Modifier
                .width(80.dp)
                .height(80.dp),
            content = {
                Image(
                    painter = painterResource(id = R.drawable.ic_play_new),
                    contentDescription = stringResource(id = R.string.button_description_start),
                    modifier = Modifier.background(color = Color.Transparent)
                )
            },
            contentPadding = PaddingValues(0.dp),
            onClick = { onStartClicked() }
        )

        // Text "Iniciar"
        Text(
            modifier = Modifier.padding(vertical = 16.dp),
            fontFamily = Archivo,
            fontSize = 48.sp,
            color = Color.White,
            text = stringResource(id = R.string.button_start).uppercase()
        )

        // Botão de "Configurações"
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Gray,
                contentColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            content = {
                Text(
                    fontFamily = Archivo,
                    text = stringResource(id = R.string.button_config).uppercase()
                )
            },
            onClick = { onSettingsClicked() }
        )

        // Botão de "Histórico"
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Gray,
                contentColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            content = {
                Text(
                    fontFamily = Archivo,
                    text = stringResource(id = R.string.button_history).uppercase()
                )
              },
            onClick = { onHistoryClicked() }
        )
    }
}

class HomeScreenPreviews {

    @Preview(name = "Default Screen Preview", showSystemUi = true, device = "id:wearos_large_round")
    @Composable
    fun DefaultPreview() {
        HomeScreen(
            onStartClicked = {},
            onHistoryClicked = {},
            onSettingsClicked = {}
        )
    }

    @Preview(name = "Large Screen Preview", showSystemUi = true, device = "id:wearos_large_round")
    @Composable
    fun LargePreview() {
        HomeScreen(
            onStartClicked = {},
            onHistoryClicked = {},
            onSettingsClicked = {}
        )
    }

    @Preview(name = "Square Screen Preview", showSystemUi = true, device = "id:wearos_rect")
    @Composable
    fun SquarePreview() {
        HomeScreen(
            onStartClicked = {},
            onHistoryClicked = {},
            onSettingsClicked = {}
        )
    }
}
