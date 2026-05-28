package com.example.module6android.task6.presentation.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun Task6LoginScreen(
    onLoginSuccess: (token: String) -> Unit,
    viewModel: Task6LoginViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.checkSavedToken(onLoginSuccess)
    }

    LaunchedEffect(state) {
        if (state is Task6LoginState.Success) {
            onLoginSuccess((state as Task6LoginState.Success).token)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Nobel Prize API", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(32.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Имя пользователя") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Пароль") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(24.dp))

        Button(
            onClick = { viewModel.login(username, password) },
            enabled = state !is Task6LoginState.Loading,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Войти")
        }

        if (state is Task6LoginState.Loading) {
            Spacer(Modifier.height(16.dp))
            CircularProgressIndicator()
        }

        if (state is Task6LoginState.Error) {
            Spacer(Modifier.height(16.dp))
            Text(
                (state as Task6LoginState.Error).message,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}
