package com.example.memorygame.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController

@Composable
fun GameScreen(level:Int,navController: NavController){
    GameScreenGenerate(navController = navController, edgeNumber = 4+2*level)
}
@Composable
fun GameScreenGenerate(navController: NavController,edgeNumber:Int){
    Box(modifier = Modifier.fillMaxSize()) {

    }
    
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