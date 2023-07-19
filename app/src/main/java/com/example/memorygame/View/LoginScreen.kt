package com.example.memorygame.View

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.memorygame.ui.theme.BackGround
import com.example.memorygame.ui.theme.MemoryGameTheme
import com.example.memorygame.ui.theme.how_to_play
import com.example.memorygame.ui.theme.play
import kotlinx.coroutines.delay

@Composable
fun LoginScreen(navController: NavController) {
    LoginScreenGenerate(navController = navController)
}

@Composable
fun LoginScreenGenerate(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackGround),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            navController.navigate("LevelScreen")
        }) {
            Text(text = play)
        }
        Button(onClick = {

        }) {
            Text(text = "How to Play")
        }
    }
}



@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    LoginScreenGenerate(navController = NavController(LocalContext.current))
}