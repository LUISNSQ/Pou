package com.example.pouxml

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun LoginScreenView(
    navController: NavController,
    loginVm: LoginViewModel,
    pouVm: PouViewModel
) {
    var mensagemErro by remember { mutableStateOf("") }
    var aProcessar by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Campo Username
        TextField(
            value = loginVm.user.value,
            onValueChange = { loginVm.user.value = it },
            label = { Text("Nome de Utilizador") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Campo Email
        TextField(
            value = loginVm.email.value,
            onValueChange = { loginVm.email.value = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Campo Password (Aceita qualquer passe)
        TextField(
            value = loginVm.password.value,
            onValueChange = { loginVm.password.value = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        if (mensagemErro.isNotEmpty()) {
            Text(text = mensagemErro, color = Color.Red, modifier = Modifier.padding(top = 8.dp))
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (aProcessar) {
            CircularProgressIndicator()
        } else {
            // BOTÃO ÚNICO: Tenta entrar, se não existir cria conta e entra
            Button(
                onClick = {
                    aProcessar = true
                    loginVm.login(
                        onSucessed = {
                            pouVm.carregarDadosFb()
                            navController.navigate("home")
                        },
                        onError = {
                            // Se falhar o login, tenta registar automaticamente
                            loginVm.register(
                                onSucesso = {
                                    pouVm.carregarDadosFb()
                                    navController.navigate("home")
                                },
                                onError = {
                                    mensagemErro = "Erro ao entrar. Tenta outro email."
                                    aProcessar = false
                                }
                            )
                        }
                    )
                },
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Text("ENTRAR")
            }
        }
    }
}
