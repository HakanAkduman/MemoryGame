package com.example.memorygame.View

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.memorygame.ui.theme.BackGround
import com.example.memorygame.ui.theme._4
import com.example.memorygame.ui.theme._6
import com.example.memorygame.ui.theme._8
import com.example.memorygame.ui.theme.how_to_play
import com.example.memorygame.ui.theme.play
import kotlinx.coroutines.delay

@Composable
fun LevelScreen(navController: NavController){
    LevelScreenGenerate(navController = navController)
}

@Composable
fun LevelScreenGenerate(navController: NavController){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackGround),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card() {

        }
        Button(onClick = {

        }) {
            Text(_4)
        }
        Button(onClick = {

        }) {
            Text(_6)
        }
        Button(onClick = {

        }) {
            Text(_8)
        }
    }

}

@Preview(showBackground = true)
@Composable
fun LevelPreview() {
    LevelScreenGenerate(navController = NavController(LocalContext.current))
}