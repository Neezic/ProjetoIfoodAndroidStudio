package com.example.projetoifoodandroidstudio.ui.theme.login

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projetoifoodandroidstudio.data.local.Usuario
import com.example.projetoifoodandroidstudio.data.local.repository.UsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: UsuarioRepository) : ViewModel() {

    // Campos observáveis
    var nome by mutableStateOf("")
        private set
    var email by mutableStateOf("")
        private set
    var senha by mutableStateOf("")
        private set
    var mensagemErro by mutableStateOf<String?>(null)
        private set

    // Estado de sucesso no login
    private val _loginSucess = MutableStateFlow(false)
    val loginSucess = _loginSucess.asStateFlow()

    // Usuário logado observado em tempo real
    private val _usuarioLogado = mutableStateOf<Usuario?>(null)
    val usuarioLogado: State<Usuario?> get() = _usuarioLogado

    // ----- Manipulação de campos -----
    fun onNomeChange(novoNome: String) { nome = novoNome; mensagemErro = null }
    fun onEmailChange(novoEmail: String) { email = novoEmail; mensagemErro = null }
    fun onSenhaChange(novaSenha: String) { senha = novaSenha; mensagemErro = null }

    // ----- Função de Login -----
    fun fazerLogin() {
        viewModelScope.launch {
            val usuarioExistente = repository.buscarPorEmail(email).first() // coleta o flow

            when {
                usuarioExistente == null -> mensagemErro = "Usuário não encontrado. Crie uma conta."
                usuarioExistente.senha != senha -> mensagemErro = "Senha incorreta."
                else -> {
                    _usuarioLogado.value = usuarioExistente
                    _loginSucess.value = true
                    mensagemErro = null
                }
            }
        }
    }

    // ----- Função de Cadastro -----
    fun criarConta() {
        viewModelScope.launch {
            if (nome.isBlank() || email.isBlank() || senha.isBlank()) {
                mensagemErro = "Preencha todos os campos."
                return@launch
            }

            val usuarioExistente = repository.buscarPorEmail(email).first()
            if (usuarioExistente != null) {
                mensagemErro = "Usuário já existe."
            } else {
                val novoUsuario = Usuario(nome = nome, email = email, senha = senha)
                repository.inserir(novoUsuario)
                _usuarioLogado.value = novoUsuario
                _loginSucess.value = true
                mensagemErro = null
            }
        }
    }

    // ----- Atualizar Perfil -----
    fun atualizarUsuario(usuario: Usuario) {
        viewModelScope.launch {
            repository.atualizar(usuario)
            _usuarioLogado.value = usuario
        }
    }

    // ----- Deletar Usuário -----
    fun deletarUsuario(usuario: Usuario) {
        viewModelScope.launch {
            repository.deletar(usuario)
            _usuarioLogado.value = null
        }
    }

    // ----- Limpar e resetar -----
    fun onLoginHandled() { _loginSucess.value = false }

    fun limparCampos() {
        nome = ""
        email = ""
        senha = ""
        mensagemErro = null
    }
}
