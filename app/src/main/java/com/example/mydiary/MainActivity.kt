package com.example.mydiary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.example.mydiary.core.data.repository.MongoDB
import com.example.mydiary.navigation.Screens
import com.example.mydiary.navigation.SetupNavGraph
import com.example.mydiary.ui.theme.MydiaryTheme
import com.example.mydiary.utils.Constants.APP_ID
import com.google.firebase.FirebaseApp
import io.realm.kotlin.mongodb.App

class MainActivity : ComponentActivity() {
    private var keepSplashOpened = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().setKeepOnScreenCondition { keepSplashOpened }
        WindowCompat.setDecorFitsSystemWindows(window, false)
        FirebaseApp.initializeApp(this)
        MongoDB.configureTheRealm()
        setContent {
            MydiaryTheme {
                val navController = rememberNavController()
                SetupNavGraph(
                    startDestination = getStartDestination(),
                    navController = navController,
                    onDataLoaded = {
                        keepSplashOpened = false
                    }
                )
            }
        }
    }
}

private fun getStartDestination(): String {
    val user = App.create(APP_ID).currentUser
    return if (user != null && user.loggedIn) Screens.Home.route
    else Screens.Authentication.route
}