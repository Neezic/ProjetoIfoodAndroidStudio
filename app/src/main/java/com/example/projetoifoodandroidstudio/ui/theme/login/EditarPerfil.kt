package com.example.projetoifoodandroidstudio.ui.theme.login

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.projetoifoodandroidstudio.data.local.UsuarioDAO
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.first

@Composable
fun TelaEditarPerfil(
    usuarioDAO: UsuarioDAO,
    usuarioEmail: String,
    onUsuarioAtualizado: () -> Unit,
    onUsuarioDeletado: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope() // CoroutineScope para launch

    var nome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var mensagemErro by remember { mutableStateOf<String?>(null) }

    // Buscar dados do usuário ao carregar a tela
    LaunchedEffect(usuarioEmail) {
        usuarioDAO.buscarPorEmail(usuarioEmail).collect { usuario ->
            usuario?.let {
                nome = it.nome
                email = it.email
                senha = it.senha
            }
        }
    }

    Column(
        modifier = Modifier
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

        Button(
            onClick = {
                scope.launch {
                    if (nome.isBlank() || email.isBlank() || senha.isBlank()) {
                        mensagemErro = "Preencha todos os campos"
                        return@launch
                    }

                    val usuarioExistente = usuarioDAO.buscarPorEmail(usuarioEmail).first()
                    if (usuarioExistente != null) {
                        val usuarioAtualizado = usuarioExistente.copy(
                            nome = nome,
                            email = email,
                            senha = senha
                        )
                        usuarioDAO.atualizar(usuarioAtualizado)
                        Toast.makeText(context, "Perfil atualizado!", Toast.LENGTH_SHORT).show()
                        onUsuarioAtualizado()
                    } else {
                        mensagemErro = "Usuário não encontrado"
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Salvar Alterações")
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = {
                scope.launch {
                    val usuarioExistente = usuarioDAO.buscarPorEmail(usuarioEmail).first()
                    if (usuarioExistente != null) {
                        usuarioDAO.deletar(usuarioExistente)
                        Toast.makeText(context, "Conta deletada!", Toast.LENGTH_SHORT).show()
                        onUsuarioDeletado()
                    } else {
                        mensagemErro = "Usuário não encontrado"
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Excluir Conta", color = MaterialTheme.colorScheme.error)
        }
    }
}
