package com.example.projetoifoodandroidstudio.ui.theme.login

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun TelaEditarPerfil(
    loginViewModel: LoginViewModel,
    onUsuarioAtualizado: () -> Unit,
    onUsuarioDeletado: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val usuario by loginViewModel.usuarioLogado

    if (usuario == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Carregando usuário...", color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f))
        }
        return
    }

    // Variáveis locais não-nulas para evitar Smart Cast
    var nome by remember { mutableStateOf(usuario!!.nome) }
    var email by remember { mutableStateOf(usuario!!.email) }
    var senha by remember { mutableStateOf(usuario!!.senha) }
    var mensagemErro by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = nome,
            onValueChange = { nome = it },
            label = { Text("Nome") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = senha,
            onValueChange = { senha = it },
            label = { Text("Senha") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(16.dp))

        if (mensagemErro != null) {
            Text(
                text = mensagemErro!!,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        // Botão salvar alterações
        Button(
            onClick = {
                scope.launch {
                    if (nome.isBlank() || email.isBlank() || senha.isBlank()) {
                        mensagemErro = "Preencha todos os campos"
                        return@launch
                    }

                    val usuarioAtualizado = usuario!!.copy(
                        nome = nome,
                        email = email,
                        senha = senha
                    )
                    loginViewModel.atualizarUsuario(usuarioAtualizado)
                    Toast.makeText(context, "Perfil atualizado!", Toast.LENGTH_SHORT).show()
                    onUsuarioAtualizado()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Salvar Alterações")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botão excluir conta
        OutlinedButton(
            onClick = {
                scope.launch {
                    loginViewModel.deletarUsuario(usuario!!)
                    Toast.makeText(context, "Conta deletada!", Toast.LENGTH_SHORT).show()
                    onUsuarioDeletado()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Excluir Conta", color = MaterialTheme.colorScheme.error)
        }
    }
}
