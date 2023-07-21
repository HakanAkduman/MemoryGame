package com.example.memorygame.View

import android.os.CountDownTimer
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.memorygame.ui.theme.BackGround
import com.example.memorygame.ui.theme.getPhotos
import kotlinx.coroutines.delay

@Composable
fun GameScreen(level:Int,navController: NavController){
    GameScreenGenerate(navController = navController, edgeNumber = 4+2*level)
}
@Composable
fun GameScreenGenerate(navController: NavController, edgeNumber: Int) {
    val cardList= getPhotos(LocalContext.current,edgeNumber)
    var gameStarted by remember {
        mutableStateOf(false)
    }


    var progress by remember { mutableStateOf(1f) }
    var timerRunning by remember { mutableStateOf(false) }
    if (gameStarted&&!timerRunning){
        timerRunning = true
        progress = 1f
        startTimer(onTicked ={
            progress=it
        } ) {
            timerRunning = false
            Log.e("Message","Timer bitti")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackGround),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {




        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier.size(height = 20.dp, width = 250.dp),
            color = Color.Blue,
            trackColor = Color.DarkGray
        )

        Spacer(modifier = Modifier.height(16.dp))


    }
}

fun startTimer(onTicked:(f:Float) -> Unit,onFinished: () -> Unit) {
    val duration = 40000L // 40 saniye

    object : CountDownTimer(duration, 100L) {
        override fun onTick(millisUntilFinished: Long) {
            val progressValue = millisUntilFinished.toFloat() / duration
            onTicked(progressValue)
        }

        override fun onFinish() {
            onFinished()
        }
    }.start()
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameCard(id:Int,onClick:()->Unit){
    Card(onClick =onClick ) {
        Image(painter = painterResource(id = id), contentDescription ="Game card" )

    }
}

@Preview(showBackground = true)
@Composable
fun GamePreview() {
    GameScreenGenerate(navController = NavController(LocalContext.current),4)
}