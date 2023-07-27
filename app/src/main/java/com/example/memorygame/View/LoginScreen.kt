package com.example.memorygame.View


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.example.memorygame.ui.theme.BackGround
import com.example.memorygame.ui.theme.MemoryGameTheme
import com.example.memorygame.ui.theme.how_to_play
import com.example.memorygame.ui.theme.how_to_play_text
import com.example.memorygame.ui.theme.play
import kotlinx.coroutines.delay

@Composable
fun LoginScreen(navController: NavController) {
    LoginScreenGenerate(navController = navController)
}


@Composable
fun LoginScreenGenerate(navController: NavController) {
    var howToPlayedClicked by remember{ mutableStateOf(false) }
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
            howToPlayedClicked=!howToPlayedClicked
        }) {
            Text(text = how_to_play)
        }
        if (howToPlayedClicked){
            HowToPlayAlert {
                howToPlayedClicked=false
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HowToPlayAlert(onClick:()->Unit){
    Dialog(onDismissRequest = onClick, properties = DialogProperties(), content = {
        Card() {
            Text(modifier = Modifier.padding(15.dp),text = how_to_play_text )
        }
    })

}



@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    LoginScreenGenerate(navController = NavController(LocalContext.current))
}