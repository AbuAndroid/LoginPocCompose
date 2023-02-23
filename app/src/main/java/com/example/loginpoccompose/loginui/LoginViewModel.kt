package com.example.loginpoccompose.loginui

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class LoginViewModel() : ViewModel() {

    var isNavigateHome = mutableStateOf(false)
        private set

    fun onUsernameChange(userName:String){
        viewModelState.update { it.copy(userName = userName) }
    }

    fun onUserPasswordChanged(userPassword:String){
        viewModelState.update { it.copy(userPassword=userPassword) }
    }

    fun validateUser(navController: NavHostController) {
        if(viewModelState.value.userName?.isEmpty() == true || viewModelState.value.userPassword?.isEmpty()==true){
            viewModelState.update {
                it.copy(errorMessage = "Please Enter Your Username and Password!!", isLoading = false)
            }
        }
        else{
            viewModelState.update {
                it.copy(errorMessage = "",isLoading = true)
            }
            viewModelScope.launch {
                delay(5000)
                navController.navigate("Home_screen")

            }
        }
    }

    fun resetErrorMessage() {
        viewModelState.update {
            it.copy(errorMessage = "")
        }
    }

    private val viewModelState = MutableStateFlow(
        LoginViewModelState(isLoading = false)
    )
    val uiState = viewModelState.map { it.toUiState() }
        .stateIn(viewModelScope, SharingStarted.Eagerly, viewModelState.value.toUiState())
}

data class LoginViewModelState(
    val isLoading: Boolean = false,
    val userName: String? = "",
    val userPassword: String? = "",
    val lastUpdate:Long = System.currentTimeMillis(),
    var errorMessage:String = "",
    val isNavigateHome : Boolean = false
) {

    fun toUiState(): LoginViewModelUi {
        return LoginViewModelUi(
            isLoading = isLoading,
            userName = userName,
            userPassword = userPassword,
            lastUpdate = lastUpdate,
            errorMessage = errorMessage,
        )
    }
}

data class LoginViewModelUi(
    val isLoading: Boolean?,
    val userName: String?,
    val userPassword: String?,
    val lastUpdate : Long?,
    val errorMessage:String?,
)