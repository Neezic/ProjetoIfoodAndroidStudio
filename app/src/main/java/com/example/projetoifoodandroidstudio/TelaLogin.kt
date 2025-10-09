package com.example.projetoifoodandroidstudio

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.projetoifoodandroidstudio.banco.LoginViewModel

@Composable
fun TelaLogin(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel,
    onLoginSucess: () -> Unit
) {
    val loginSuccess by viewModel.loginSucess.collectAsStateWithLifecycle()
    val context = LocalContext.current

    if (loginSuccess) {
        LaunchedEffect(Unit) {
            onLoginSucess()
            viewModel.onLoginHandled()
            viewModel.limparCampos()
        }
    }

    Surface(modifier = modifier.fillMaxSize(), color = Color.White) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Bem-Vindo!", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))

            // Nome
            OutlinedTextField(
                value = viewModel.nome,
                onValueChange = { viewModel.onNomeChange(it) },
                label = { Text("Nome") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Email
            OutlinedTextField(
                value = viewModel.email,
                onValueChange = { viewModel.onEmailChange(it) },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Senha
            OutlinedTextField(
                value = viewModel.senha,
                onValueChange = { viewModel.onSenhaChange(it) },
                label = { Text("Senha") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation()
            )

            if (viewModel.mensagemErro != null) {
                LaunchedEffect(viewModel.mensagemErro) {
                    Toast.makeText(context, viewModel.mensagemErro, Toast.LENGTH_SHORT).show()
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    viewModel.mensagemErro!!,
                    color = Color.Red,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Botão Login
            Button(
                onClick = { viewModel.fazerLogin() },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
            ) {
                Text("Entrar", color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botão Criar Conta
            OutlinedButton(
                onClick = { viewModel.criarConta() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Criar Conta")
            }
        }
    }
}
