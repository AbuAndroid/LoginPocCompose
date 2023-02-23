package com.example.loginpoccompose.loginui


import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.coroutines.launch


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen() {
    //HomeText()

    val sheetState = rememberBottomSheetState(
        initialValue = BottomSheetValue.Collapsed
    )

    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = sheetState
    )

    val scope = rememberCoroutineScope()

    val context = LocalContext.current
    val openDialog = remember { mutableStateOf(false) }

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentAlignment = Alignment.Center,
            ) {
                Text(text = "Bottom sheet", fontSize = 50.sp)
            }
        },
        sheetPeekHeight = 0.dp,
        sheetBackgroundColor = Color.LightGray
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column() {
                    ClickableText(text = AnnotatedString("Bottom sheet"), onClick = {
                        scope.launch {
                            if(sheetState.isCollapsed){
                                sheetState.expand()
                            }else{
                                sheetState.collapse()
                            }
                        }
                    })

                    Button(onClick = {
                       openDialog.value = !openDialog.value
                    }) {
                        Text(text = "Open Alert")
                    }
                }
            }

            if(openDialog.value){
                AlertDialog(
                    onDismissRequest = { openDialog.value=false },
                    title = {
                        Text(text = "Alert Dialog", color = Color.Black)
                    },
                    text = {
                        Text(text = "Hello friends, This is Our Alert Dialog..", color = Color.Black)
                    },
                    confirmButton = {
                        Button(onClick = {
                            openDialog.value = false
                        }) {
                            Text(text = "Confirm", color = Color.White)
                        }
                    },

                    dismissButton = {
                        Button(onClick = {
                            openDialog.value = false
                        }) {
                            Text(text = "Cancel", color = Color.White)
                        }
                    }
                )
            }
        }
    }
}


