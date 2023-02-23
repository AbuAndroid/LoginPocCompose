package com.example.loginpoccompose.loginui

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleObserver
import androidx.navigation.NavHostController
import com.example.loginpoccompose.R
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        isLoading = false,
        userName = "",
        userPassword = "",
        onUserNameChanged = {},
        onUserPasswordChanged = {},
        onSubmit = {},
        navController = NavHostController(context = LocalContext.current),
        onRemoveErrorMessage = {},
        errorMessage = ""
    )
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalPermissionsApi::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun LoginScreen(
    isLoading: Boolean?,
    userName: String?,
    userPassword: String,
    onUserNameChanged: (String) -> Unit,
    onUserPasswordChanged: (String) -> Unit,
    navController: NavHostController,
    onSubmit: () -> Unit,
    onRemoveErrorMessage: () -> Unit,
    errorMessage: String?
) {
    val passwordVisible = rememberSaveable { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
//    val permissionState = rememberMultiplePermissionsState(
//        permissions = listOf(
//            Manifest.permission.CAMERA,
//            Manifest.permission.READ_MEDIA_AUDIO,
//            Manifest.permission.ACCESS_COARSE_LOCATION,
//            Manifest.permission.ACCESS_FINE_LOCATION
//        )
//    )
//
//    val lifecycleOwner = LocalLifecycleOwner.current
//    DisposableEffect(key1 = lifecycleOwner, effect = {
//        val observer = LifecycleEventObserver { _, event ->
//            if (event == Lifecycle.Event.ON_RESUME) {
//                permissionState.launchMultiplePermissionRequest()
//            }
//        }
//        lifecycleOwner.lifecycle.addObserver(observer)
//
//        onDispose {
//            lifecycleOwner.lifecycle.removeObserver(observer)
//        }
//    })
    Scaffold(
        bottomBar = {
            BottomBar() //permissionState
        },
        scaffoldState = scaffoldState,
        backgroundColor = Color.Blue
    ) {
        if (errorMessage?.isNotEmpty() == true) {
            scope.launch {
                scaffoldState.snackbarHostState.showSnackbar(
                    errorMessage.toString()
                )
                onRemoveErrorMessage()
            }
        }
        Box(
            modifier = Modifier.fillMaxSize()
        ) {

            Image(
                painter = painterResource(id = R.drawable.ic_bg_splash),
                contentDescription = "content",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds
            )

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Image(
                    painter = painterResource(id = R.drawable.ic_logo),
                    contentDescription = "ClassLogo",
                )

                Card(
                    modifier = Modifier
                        .padding(30.dp),
                    elevation = 10.dp,
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {

                        Text(
                            text = "Welcome",
                            fontSize = 25.sp,
                            modifier = Modifier.padding(vertical = 20.dp)
                        )

                        EnterUserName(userName, onUserNameChanged)

                        EnterUserPassword(userPassword, onUserPasswordChanged, passwordVisible)

                        Button(
                            onClick = {
                                onSubmit()
                            },
                            modifier = Modifier.padding(vertical = 20.dp)
                        ) {
                            Text(text = "LOGIN")
                        }
                        if (isLoading == true) {
                            CircularProgressIndicator(
                                color = Color.Green,
                                strokeWidth = 5.dp,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun EnterUserName(
    userName: String?,
    onUserNameChanged: (String) -> Unit
) {
    OutlinedTextField(
        value = userName.toString(),
        onValueChange = {
            onUserNameChanged(it)
        },
        singleLine = true,
        leadingIcon = {
            Icon(Icons.Filled.Person, contentDescription = "User Icon")
        },
        label = { Text(text = "Enter Your Name :") },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text
        )
    )
}

@Composable
fun EnterUserPassword(
    userPassword: String,
    onUserPasswordChanged: (String) -> Unit,
    passwordVisible: MutableState<Boolean>
) {
    OutlinedTextField(
        value = userPassword,
        onValueChange = {
            onUserPasswordChanged(it)
        },
        leadingIcon = {
            Icon(Icons.Filled.Lock, contentDescription = "Lock Icon")
        },
        label = { Text(text = "Enter Your Password") },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType =
            KeyboardType.Text
        ),
        trailingIcon = {
            val visibilityImage = if (passwordVisible.value) {
                Icons.Filled.Visibility
            } else {
                Icons.Filled.VisibilityOff
            }
            val description = if (passwordVisible.value) "Hide password" else "Show password"

            IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                Icon(imageVector = visibilityImage, contentDescription = description)
            }
        },
        visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation()
    )
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalPermissionsApi::class)
@Composable
fun BottomBar() {  //permissionState: MultiplePermissionsState
    val selectedIndex = remember { mutableStateOf(0) }
    val context = LocalContext.current
    val listItem = arrayOf("English", "Arabic")
    val selectedItem = remember { mutableStateOf(listItem[0]) }
    var expanded = remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp)
    ) {

        ExposedDropdownMenuBox(expanded = expanded.value, onExpandedChange = {
            expanded.value = !expanded.value
        }) {
            TextField(
                value = selectedItem.value,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded.value) },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.White,
                    disabledTextColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    backgroundColor = Color.Transparent
                ),
                modifier = Modifier.fillMaxWidth(.4f)
            )

            ExposedDropdownMenu(
                expanded = expanded.value,
                onDismissRequest = { expanded.value = false }) {
                listItem.forEach { selectedOption ->
                    DropdownMenuItem(onClick = {
                        selectedItem.value = selectedOption
                        expanded.value = false
                    }) {
                        Text(text = selectedOption)
                    }
                }
            }
        }

        ClickableText(text = AnnotatedString("Forget Password?"),
            style = TextStyle(
                color = Color.White
            ),
            onClick = {
                //      permissionState.launchMultiplePermissionRequest()
//                permissionState.permissions.forEach { permissionState ->
//
//                    when (permissionState.permission) {
//                        Manifest.permission.CAMERA -> {
//                            when {
//                                permissionState.hasPermission -> {
//                                    Toast.makeText(
//                                        context,
//                                        "Camera Permission Granted",
//                                        Toast.LENGTH_SHORT
//                                    ).show()
//                                }
//
//                                permissionState.shouldShowRationale -> {
//                                    Toast.makeText(
//                                        context,
//                                        "Camera Permission Deneied",
//                                        Toast.LENGTH_SHORT
//                                    ).show()
//                                }
//                            }
//                        }
//
//                        Manifest.permission.READ_MEDIA_AUDIO -> {
//                            when {
//                                permissionState.hasPermission -> {
//                                    Toast.makeText(
//                                        context,
//                                        "Audio Permission Granted",
//                                        Toast.LENGTH_SHORT
//                                    ).show()
//                                }
//                                permissionState.shouldShowRationale -> {
//                                    Toast.makeText(
//                                        context,
//                                        "Audio Permission Granted",
//                                        Toast.LENGTH_SHORT
//                                    ).show()
//                                }
//                            }
//                        }
//
//                        Manifest.permission.ACCESS_FINE_LOCATION -> {
//                            when {
//                                permissionState.hasPermission -> {
//                                    Toast.makeText(
//                                        context,
//                                        "Location permission Granted",
//                                        Toast.LENGTH_SHORT
//                                    ).show()
//                                }
//                                permissionState.shouldShowRationale -> {
//                                    Toast.makeText(
//                                        context,
//                                        "Location permission Denied",
//                                        Toast.LENGTH_SHORT
//                                    ).show()
//                                }
//                            }
//                        }
//                    }
                }
                //  Toast.makeText(context, "Working", Toast.LENGTH_SHORT).show()
            )
    }
}