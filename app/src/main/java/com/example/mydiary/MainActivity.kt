package com.example.mydiary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.example.mydiary.navigation.Screens
import com.example.mydiary.navigation.SetupNavGraph
import com.example.mydiary.ui.theme.MydiaryTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            MydiaryTheme {
                ApplyNavController()
            }
        }
    }
}

@Composable
private fun ApplyNavController() {
    val navController = rememberNavController()
    SetupNavGraph(startDestination = Screens.Authentication.route, navController = navController)
}