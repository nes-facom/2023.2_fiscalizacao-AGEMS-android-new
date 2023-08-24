package com.ufms.nes.features.registration.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CardTravel
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ufms.nes.R
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun RegistrationScreen(
    modifier: Modifier = Modifier,
    viewModel: RegistrationViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier.fillMaxWidth()
    ) { paddingValues ->
        RegistrationContent(
            uiState = uiState,
            onEvent = viewModel::onEvent,
            modifier = Modifier.padding(paddingValues)
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationContent(
    uiState: RegistrationUiState,
    onEvent: (RegistrationEvent) -> Unit,
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
            text = stringResource(id = R.string.registerScreenTitle),
            style = MaterialTheme.typography.headlineMedium
        )

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = uiState.nome.orEmpty(),
            onValueChange = {
                onEvent(RegistrationEvent.EnteredEmail(it))
            },
            placeholder = {
                Text(text = stringResource(id = R.string.nome))
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Person, contentDescription = null)
            }
        )

        Spacer(modifier = Modifier.height(height = 30.dp))

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = uiState.email.orEmpty(),
            onValueChange = {
                onEvent(RegistrationEvent.EnteredEmail(it))
            },
            placeholder = {
                Text(text = stringResource(id = R.string.email))
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Email, contentDescription = null)
            }
        )

        Spacer(modifier = Modifier.height(height = 30.dp))

        var expanded by remember { mutableStateOf(false) }
        val cargos = listOf<String>("Analista de regulação", "Assessor jurídico", "Assessor técnico", "Técnico")
        var selectedCargo by remember { mutableStateOf(cargos[0]) }

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            TextField(
                modifier = Modifier.menuAnchor().fillMaxWidth(),
                readOnly = true,
                value = selectedCargo,
                onValueChange = {},
                label = { Text(stringResource(id = R.string.role)) },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded
                    )
                },
                leadingIcon = { Icon(imageVector = Icons.Default.CardTravel, contentDescription = null) },
                colors = ExposedDropdownMenuDefaults.textFieldColors()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                },
            ) {
                cargos.forEach { selectionOption ->
                    DropdownMenuItem(
                        {
                            Text(selectionOption)
                        },
                        onClick = {
                            selectedCargo = selectionOption
                            expanded = false
                        }
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.height(height = 30.dp))

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = uiState.password.orEmpty(),
            onValueChange = {
                onEvent(RegistrationEvent.EnteredPassword(it))
            },
            placeholder = {
                Text(text = stringResource(id = R.string.password))
            },
            leadingIcon = { Icon(imageVector = Icons.Default.Key, contentDescription = null) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        )

        Spacer(modifier = Modifier.height(30.dp))

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = uiState.passwordConfirmation.orEmpty(),
            onValueChange = {
                onEvent(RegistrationEvent.EnteredPassword(it))
            },
            placeholder = {
                Text(text = stringResource(id = R.string.passwordConfirmation))
            },
            leadingIcon = { Icon(imageVector = Icons.Default.Key, contentDescription = null) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        )

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
                Text(text = stringResource(id = R.string.returnToLoginScreen))
            }

            Button(
                onClick = { onEvent(RegistrationEvent.LoginEnter) },
            ) {
                Text(text = stringResource(id = R.string.register))
            }
        }

    }
}
