package com.example.loginpoccompose.loginui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun LoginRouter(
    loginViewModel: LoginViewModel
) {
    val uiState by loginViewModel.uiState.collectAsState()
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "Login_screen") {
        composable("Login_screen") {
            LoginScreen(
                isLoading = uiState.isLoading,
                userName = uiState.userName,
                userPassword = uiState.userPassword.toString(),
                onUserNameChanged = { loginViewModel.onUsernameChange(it) },
                onUserPasswordChanged = { loginViewModel.onUserPasswordChanged(it) },
                navController = navController,
                onSubmit = { loginViewModel.validateUser(navController) },
                onRemoveErrorMessage = { loginViewModel.resetErrorMessage() },
                errorMessage = uiState.errorMessage,
            )
        }

        composable("Home_screen") {
            HomeScreen()
        }
    }
}