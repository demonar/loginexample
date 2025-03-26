package com.example.loginapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.loginapp.ui.theme.LoginAppTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LoginAppTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text("Login Screen") }
                        )
                    },
                    modifier = Modifier.fillMaxSize()
                    ) { innerPadding ->
                    Login(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Login(modifier: Modifier = Modifier) {
    var user by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var onLogin by remember {  mutableStateOf( false) }
    var loginState by remember { mutableStateOf("Not logged in") }
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            MyRow {
                Text("User:")
                TextField(user, onValueChange = { user = it })
            }
            MyRow {
                Text("Password:")
                TextField(
                    password,
                    onValueChange = { password = it },
                    visualTransformation = PasswordVisualTransformation()
                )
            }
            MyRow {
                Button(
                    onClick = { onLogin = !onLogin },
                    enabled = !onLogin
                ) {
                    if (onLogin) {
                        CircularProgressIndicator()
                    } else {
                        Text("Login")
                    }
                }
                Text(loginState)
            }
            LaunchedEffect(onLogin) {
                if (onLogin) {
                    loginState = "Logging in..."
                    launch {
                        loginState = when {
                            user == "admin" && password == "admin" -> {
                                doLogin(true)
                            }

                            else -> {
                                doLogin(false)
                            }
                        }
                        onLogin = false
                    }
                }
            }
        }
    }
}

@Composable
fun MyRow(content: @Composable () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        content()
    }
}

suspend fun doLogin(loggedIn: Boolean): String {
    delay(2000)
    return if (loggedIn) {
        "Logged in"
    } else {
        "Wrong user or password"
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LoginAppTheme {
        Login()
    }
}