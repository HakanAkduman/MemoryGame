package com.example.memorygame.View

import android.os.CountDownTimer
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.AnimationState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
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
import kotlin.concurrent.timer

@Composable
fun GameScreen(level:Int,navController: NavController, viewModel: GameScreenViewModel=remember{ GameScreenViewModel() }){
    val context= LocalContext.current
    viewModel.getAllCards(context,4+2*level)
    GameScreenGenerate(navController = navController, edgeNumber = 4+2*level, viewModel = viewModel)
}
@OptIn(DelicateCoroutinesApi::class)
@Composable
fun GameScreenGenerate(navController: NavController, edgeNumber: Int, viewModel: GameScreenViewModel) {
    BackHandler() {
        navController.navigate("LevelScreen")
    }

    val scope=rememberCoroutineScope()
    var point by remember{ mutableStateOf(100) }



    val seenList by remember{
        mutableStateOf(mutableListOf<Card>())
    }
    var finished by remember {
        mutableStateOf(false)
    }
    var knownCardList by remember{ mutableStateOf(mutableListOf<Card>()) }
    val cardList by viewModel.cardlist.observeAsState(listOf())
    var gameStarted by remember {
        mutableStateOf(false)
    }



    var progress by remember { mutableStateOf(1f) }
    var timerRunning by remember { mutableStateOf(false) }

    if (!gameStarted&&finished){
        GameFinishedDialog(
            text = "Congrats you have $point points ",
            navController = navController,
            edgeNumber = edgeNumber
        )

    }


    if (gameStarted&&!timerRunning&&!finished){
        timerRunning = true
        progress = 1f
        startTimer(onTicked ={
            progress=it
        } ) {
            if(gameStarted){

                gameStarted=false
                finished=true

                Log.e("Message","Timer bitti")
            }else{

                gameStarted=false



            }

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
                            point-=5
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
                                    point+=50
                                    seenList.forEach {
                                        it.knew=true
                                        knownCardList.add(it)
                                    }
                                    seenList.clear()
                                    if (knownCardList.size==edgeNumber*edgeNumber){
                                        //süreden önce bitirmişse
                                        gameStarted=false
                                        finished=true



                                    }



                                } else {
                                    Log.e("game","kartlar farklı")
                                    scope.launch {
                                        delay(600)
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

@Composable
fun GameFinishedDialog(text:String,navController: NavController,edgeNumber: Int){


    Dialog(
        onDismissRequest = {

        },
        DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false),
        content ={
            Card() {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(text =text , modifier = Modifier.padding(bottom = 10.dp))
                    Button(onClick = {navController.navigate("GameScreen/${(edgeNumber-4)/2}")}) {
                        Text(text = "Replay")
                    }
                }
            }
        }
    )
}

fun startTimer(onTicked:(f:Float) -> Unit,onFinished: () -> Unit) {
    val duration = 60000L // 40 saniye

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



    var animStarted by remember{ mutableStateOf(false) }
    val animationOfColumn= animateFloatAsState(targetValue =if(animStarted)0.3f else 1f , animationSpec = tween(200), finishedListener = {
        animStarted=false
    })


Column(modifier=modifier, verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){

        Card(modifier = Modifier.fillMaxHeight().fillMaxWidth(animationOfColumn.value), onClick = {


            if(!card.knew){
                viewModel.setSeen(card,!card.seen)
                animStarted=true
                onClick(card.seen)
                
            }

        },
            colors = CardDefaults.cardColors(contentColor = Color(R.color.card_bg),
                containerColor =  Color(R.color.card_bg),
                disabledContainerColor =Color(R.color.card_bg),
                disabledContentColor = Color(R.color.card_bg)) ) {
            Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                if (!animStarted&&card.seen){
                    Image(modifier= Modifier
                        .fillMaxSize(),
                        painter = painterResource(id = card.photoUrl),
                        contentDescription ="Game card",
                        contentScale = ContentScale.FillBounds)
                }
            }




        }

}

}
@Preview(showBackground = true)
@Composable
fun GamePreview() {
    GameScreenGenerate(navController = NavController(LocalContext.current),4, GameScreenViewModel())
}