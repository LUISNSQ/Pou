package com.example.pouxml

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

// Ecrã de Login e Registo
@Composable
fun LoginScreenView(nav: NavController, loginVm: LoginViewModel, vm: PouViewModel) {
    val context = LocalContext.current
    var isLoginMode by remember { mutableStateOf(true) } // Alterna entre ecrã de Login e Registo

    // Layout principal com imagem de fundo
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.cenario1),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        // Escurecimento de fundo
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.4f))
        )

            // Formulario
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Título
            Text(
                text = if (isLoginMode) "BEM-VINDO" else "CRIAR CONTA",
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Cartão que contém os campos de input
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.9f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    // Campo do nome de utilizador (Apenas visível no registo)
                    if (!isLoginMode) {
                        OutlinedTextField(
                            value = loginVm.user.value,
                            onValueChange = { loginVm.user.value = it },
                            label = { Text("Nome de Utilizador") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    // Campo de Email
                    OutlinedTextField(
                        value = loginVm.email.value,
                        onValueChange = { loginVm.email.value = it },
                        label = { Text("Email") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // Campo de Password, com censura
                    OutlinedTextField(
                        value = loginVm.password.value,
                        onValueChange = { loginVm.password.value = it },
                        label = { Text("Password") },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // --- BOTÃO DE AÇÃO PRINCIPAL ---
                    Button(
                        onClick = {
                            if (isLoginMode) {
                                loginVm.login(
                                    onSucessed = {
                                        vm.carregarDadosFb() // Carrega os dados do Pou após login
                                        nav.navigate("home")
                                    },
                                    onError = { Toast.makeText(context, it, Toast.LENGTH_SHORT).show() }
                                )
                            } else {
                                loginVm.register(
                                    onSucesso = { isLoginMode = true },
                                    onError = { Toast.makeText(context, it, Toast.LENGTH_SHORT).show() }
                                )
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(if (isLoginMode) "ENTRAR" else "REGISTAR")
                    }

                    // Botão para alternar entre Login e Registo
                    TextButton(
                        onClick = { isLoginMode = !isLoginMode },
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text(
                            if (isLoginMode) "Não tem conta? Registe-se" 
                            else "Já tem conta? Faça Login",
                            color = Color.DarkGray
                        )
                    }
                }
            }
        }
    }
}
