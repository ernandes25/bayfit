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
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.baysoftware.bayfit.R
import com.baysoftware.bayfit.ui.theme.Archivo



@Composable
fun SettingsScreen(
    onButtonRestTime: () -> Unit,

    ) {


    Column(
        modifier = Modifier
            .background(color = Color.Black)
            .fillMaxSize()
            .padding(top = 82.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Gray,
                contentColor = Color.White
            ),

            modifier = Modifier

                .padding(16.dp)
                .width(160.dp)
                .height(40.dp),

            content = {
                Text(
                    fontFamily = Archivo,
                    text = stringResource(id = R.string.time_rest_setting).uppercase()
                )

            },

            onClick = { onButtonRestTime() }
        )
    }
}

@Preview
@Composable
fun SettingsScreenPreview() {
    SettingsScreen(onButtonRestTime = {})

}