package com.ufms.nes.features.authentication.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Key
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ufms.nes.R
import com.ufms.nes.core.ui.components.PasswordTextFieldComponent

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onLoginSuccess: () -> Unit,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    loginViewModel: LoginViewModel = hiltViewModel()
) {

    val uiState by loginViewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        LoginContent(
            uiState = uiState,
            onEvent = loginViewModel::onEvent,
            modifier = Modifier.padding(paddingValues)
        )

        LaunchedEffect(uiState.userLogged) {
            if (uiState.userLogged) {
                onLoginSuccess()
            }
        }

        uiState.userMessage?.let { message ->
            val snackbarText = message//stringResource(id = message)
            LaunchedEffect(snackbarHostState, loginViewModel, message, snackbarText) {
                snackbarHostState.showSnackbar(snackbarText)
                loginViewModel.snackbarMessageShown()
            }
        }

    }

}

@Composable
fun LoginContent(
    uiState: LoginUiState,
    onEvent: (LoginEvent) -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            modifier = Modifier
                .width(150.dp)
                .height(150.dp),
            painter = painterResource(id = R.drawable.app_logo),
            contentDescription = null
        )

        Text(
            modifier = Modifier.padding(30.dp),
            text = stringResource(id = R.string.login),
            style = MaterialTheme.typography.headlineMedium
        )

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = uiState.email.orEmpty(),
            onValueChange = {
                onEvent(LoginEvent.EnteredEmail(it))
            },
            placeholder = {
                Text(text = stringResource(id = R.string.login))
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Email, contentDescription = null)
            }
        )

        Spacer(modifier = Modifier.height(height = 30.dp))

        PasswordTextFieldComponent(
            labelValue = stringResource(id = R.string.password),
            onTextSelected = { onEvent(LoginEvent.EnteredPassword(it)) },
            errorStatus = true,
            leadingIcon = Icons.Default.Key
        )

//        Button(
//            onClick = { /*TODO*/ },
//            colors = ButtonDefaults.buttonColors(
//                containerColor = Color.Transparent,
//                contentColor = Color.Gray
//            )
//        ) {
//            Text(text = stringResource(id = R.string.forget_password))
//        }

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            OutlinedButton(
                onClick = { /*TODO() - Navegar para tela de cadastro */ },
            ) {
                Text(text = stringResource(id = R.string.register))
            }

            Button(
                onClick = { onEvent(LoginEvent.LoginEnter) },
            ) {
                Text(text = stringResource(id = R.string.enter))
            }
        }

    }
}
