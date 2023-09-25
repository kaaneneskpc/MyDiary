package com.example.mydiary.navigation

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.mydiary.R
import com.example.mydiary.core.data.repository.MongoDB
import com.example.mydiary.presentation.components.DisplayAlertDialog
import com.example.mydiary.presentation.screens.auth.AuthenticationScreen
import com.example.mydiary.presentation.screens.auth.AuthenticationViewModel
import com.example.mydiary.presentation.screens.home.HomeScreen
import com.example.mydiary.utils.Constants.APP_ID
import com.example.mydiary.utils.Constants.WRITE_SCREEN_ARGUMENT_KEY
import com.stevdzasan.messagebar.rememberMessageBarState
import com.stevdzasan.onetap.rememberOneTapSignInState
import io.realm.kotlin.mongodb.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

@Composable
fun SetupNavGraph(startDestination: String, navController: NavHostController) {
    NavHost(
        startDestination = startDestination,
        navController = navController
    ) {
        authenticationRoute(
            navigateToHome = {
                navController.popBackStack()
                navController.navigate(Screens.Home.route)
            }
        )
        homeRoute(
            navigateToWrite = {
                navController.navigate(Screens.Write.route)
            },
            navigateToAuth = {
                navController.popBackStack()
                navController.navigate(Screens.Authentication.route)
            }
        )
        writeRoute()
    }
}

fun NavGraphBuilder.authenticationRoute(
    navigateToHome: () -> Unit
) {
    composable(route = Screens.Authentication.route) {
        val authViewModel: AuthenticationViewModel = viewModel()
        val loadingState by authViewModel.loadingState
        val authenticated by authViewModel.authenticated
        val oneTapState = rememberOneTapSignInState()
        val messageBarState = rememberMessageBarState()
        AuthenticationScreen(
            authenticated = authenticated,
            loadingState = loadingState,
            oneTapState = oneTapState,
            messageBarState = messageBarState,
            onButtonClicked = {
                oneTapState.open()
                authViewModel.setLoading(true)
            },
            onTokenReceived = { tokenId ->
                authViewModel.signInWithMongoAtlas(
                    tokenId = tokenId,
                    onSuccess = {
                        messageBarState.addSuccess("Successfully Authenticated!")
                        authViewModel.setLoading(false)
                    },
                    onError = {
                        messageBarState.addError(it)
                        authViewModel.setLoading(false)
                    }
                )
            },
            onDialogDismissed = { message ->
                messageBarState.addError(Exception(message))
                authViewModel.setLoading(false)
            },
            navigateToHome = navigateToHome
        )
    }
}

fun NavGraphBuilder.homeRoute(
    navigateToWrite: () -> Unit,
    navigateToAuth: () -> Unit
) {
    composable(route = Screens.Home.route) {

        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        var signOutDialogOpened by remember { mutableStateOf(false) }
        val scope = rememberCoroutineScope()

        HomeScreen(
            drawerState = drawerState,
            onMenuClicked = {
                scope.launch {
                    drawerState.open()
                }
            },
            onSignOutClicked = { signOutDialogOpened = true },
            onDeleteAllClicked = {},
            navigateToWrite = navigateToWrite
        )
        
        LaunchedEffect(key1 = Unit) {
            MongoDB.configureTheRealm()
        }

        DisplayAlertDialog(
            title = stringResource(R.string.sign_out_text),
            message = stringResource(R.string.sign_out_confirm_text),
            dialogOpened = signOutDialogOpened,
            onDialogClosed = { signOutDialogOpened = false },
            onYesClicked = {
                scope.launch(Dispatchers.IO) {
                    val user = App.create(APP_ID).currentUser
                    user?.let {
                        it.logOut()
                        withContext(Dispatchers.Main) {
                            navigateToAuth()
                        }
                    }
                }
            }
        )
    }

}

fun NavGraphBuilder.writeRoute() {
    composable(
        route = Screens.Write.route,
        arguments = listOf(navArgument(name = WRITE_SCREEN_ARGUMENT_KEY) {
            type = NavType.StringType
            nullable = true
            defaultValue = null
        })
    ) {

    }
}