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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.memorygame.Model.Card
import com.example.memorygame.R
import com.example.memorygame.ViewModel.GameScreenViewModel
import com.example.memorygame.ui.theme.BackGround
import com.example.memorygame.ui.theme.getPhotos
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.internal.notifyAll

@Composable
fun GameScreen(level:Int,navController: NavController, viewModel: GameScreenViewModel=remember{ GameScreenViewModel() }){
    val context= LocalContext.current
    viewModel.getAllCards(context,4+2*level)
    GameScreenGenerate(navController = navController, edgeNumber = 4+2*level, viewModel = viewModel)
}
@OptIn(DelicateCoroutinesApi::class)
@Composable
fun GameScreenGenerate(navController: NavController, edgeNumber: Int, viewModel: GameScreenViewModel) {

    val scope=rememberCoroutineScope()
    val seenList by remember{
        mutableStateOf(mutableListOf<Card>())
    }
    var temp by remember {
        mutableStateOf(true)
    }
    val cardList by viewModel.cardlist.observeAsState(listOf())
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

        LazyColumn(){
            items(cardList){
                LazyRow(){

                    items(it){card->

                        GameCard(modifier = Modifier
                            .size((384 / edgeNumber).dp)
                            .padding(3.dp)
                            , card =  card,
                            viewModel = viewModel)
                        {
                            if (!gameStarted){
                                gameStarted=true

                            }
                            if(it){
                                seenList.add(card)
                            }else{
                                seenList.remove(card)
                            }
                            if (seenList.size == 2) {

                                if (seenList[0].photoUrl == seenList[1].photoUrl) {
                                    // Eşleşme durumu
                                    Log.e("game","kartlar eşleşiyor")
                                    seenList.forEach { it.knew=true }
                                    seenList.clear()
                                } else {
                                    Log.e("game","kartlar farklı")
                                    scope.launch {
                                        delay(500)
                                        seenList.forEach { viewModel.setSeen(it,false) }
                                        seenList.clear()
                                    }


                                }

                            }




                        }
                    }
                }


            }
        }



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
fun GameCard(modifier: Modifier, card: Card,viewModel: GameScreenViewModel, onClick:(Boolean)->Unit){

    Card(modifier = modifier, onClick = {


        if(!card.knew){
            viewModel.setSeen(card,!card.seen)

            onClick(card.seen)
        }

    },
        colors = CardDefaults.cardColors(contentColor = Color(R.color.card_bg),
            containerColor = Color(R.color.card_bg),
            disabledContainerColor =Color(R.color.card_bg),
            disabledContentColor = Color(R.color.card_bg)) ) {

        if (card.seen){
            Image(modifier=Modifier.fillMaxSize(),
                painter = painterResource(id = card.photoUrl),
                contentDescription ="Game card",
                contentScale = ContentScale.FillBounds)
        }


    }
}

@Preview(showBackground = true)
@Composable
fun GamePreview() {
    GameScreenGenerate(navController = NavController(LocalContext.current),4, GameScreenViewModel())
}