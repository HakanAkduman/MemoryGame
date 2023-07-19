package com.example.memorygame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.memorygame.View.GameScreen
import com.example.memorygame.View.LevelScreen
import com.example.memorygame.View.LoginScreen
import com.example.memorygame.ViewModel.LevelScreenViewModel
import com.example.memorygame.ViewModel.LoginScreenViewModel
import com.example.memorygame.ui.theme.MemoryGameTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            MemoryGameTheme {
                Column(modifier = Modifier.fillMaxSize()) {
                    val navController= rememberNavController()
                    NavHost(navController =navController , startDestination ="LoginScreen" ){
                        composable("LoginScreen"){
                            LoginScreen(navController = navController)
                        }
                        composable("GameScreen/{level}", arguments = listOf(
                            navArgument("level"){
                                type= NavType.IntType
                            }
                        )){
                            val level=it.arguments!!.getInt("level")
                            GameScreen(navController = navController, level = level)
                        }
                        composable("LevelScreen"){
                            LevelScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {

}