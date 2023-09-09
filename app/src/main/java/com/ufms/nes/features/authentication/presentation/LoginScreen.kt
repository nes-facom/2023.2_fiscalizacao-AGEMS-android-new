package com.ufms.nes.features.authentication.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Key
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.navigation.compose.rememberNavController
import com.ufms.nes.R
import com.ufms.nes.core.ui.components.PasswordTextFieldComponent
import com.ufms.nes.features.registration.presentation.registrationNavigationRoute

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onLoginSuccess: () -> Unit,
    onRegistrationButtonClick: () -> Unit,
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
            onRegistrationButtonClick = onRegistrationButtonClick,
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
    onRegistrationButtonClick: () -> Unit,
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
            text = stringResource(id = R.string.login),
            modifier = Modifier
                .fillMaxWidth()
                .heightIn()
                .padding(30.dp),
            style = TextStyle(
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Normal
            ),
            textAlign = TextAlign.Center
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = uiState.email.orEmpty(),
            onValueChange = {
                onEvent(LoginEvent.EnteredEmail(it))
            },
            placeholder = {
                Text(text = stringResource(id = R.string.email))
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

        Button(
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = Color.Gray
            )
        ) {
            Text(text = stringResource(id = R.string.forget_password))
        }

        Spacer(modifier = Modifier.weight(1f))

        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            onClick = { onRegistrationButtonClick() },
        ) {
            Text(text = stringResource(id = R.string.register))
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            onClick = { onEvent(LoginEvent.LoginEnter) },
        ) {
            Text(text = stringResource(id = R.string.enter))
        }

    }
}
