package com.example.projetoifoodandroidstudio.banco

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class LoginViewModel(private val usuarioDAO: UsuarioDAO) : ViewModel() {

    var nome by mutableStateOf("")
        private set
    var email by mutableStateOf("")
        private set
    var senha by mutableStateOf("")
        private set
    var mensagemErro by mutableStateOf<String?>(null)
        private set

    private val _loginSucess = MutableStateFlow(false)
    val loginSucess = _loginSucess.asStateFlow()

    // Usuário logado armazenado no ViewModel
    private var _usuarioLogado = mutableStateOf<Usuario?>(null)
    val usuarioLogado: State<Usuario?> get() = _usuarioLogado

    fun onNomeChange(novoNome: String) { nome = novoNome; mensagemErro = null }
    fun onEmailChange(novoEmail: String) { email = novoEmail; mensagemErro = null }
    fun onSenhaChange(novaSenha: String) { senha = novaSenha; mensagemErro = null }

    fun fazerLogin() {
        viewModelScope.launch {
            val usuarioExistente = usuarioDAO.buscarPorEmail(email).first()
            if (usuarioExistente == null) {
                mensagemErro = "Usuário não encontrado. Crie uma conta."
            } else if (usuarioExistente.senha != senha) {
                mensagemErro = "Senha incorreta."
            } else {
                _usuarioLogado.value = usuarioExistente
                _loginSucess.value = true
                mensagemErro = null
            }
        }
    }

    fun criarConta() {
        viewModelScope.launch {
            if (nome.isBlank() || email.isBlank() || senha.isBlank()) {
                mensagemErro = "Preencha todos os campos."
                return@launch
            }

            val usuarioExistente = usuarioDAO.buscarPorEmail(email).first()
            if (usuarioExistente != null) {
                mensagemErro = "Usuário já existe."
            } else {
                val novoUsuario = Usuario(nome = nome, email = email, senha = senha)
                usuarioDAO.inserir(novoUsuario)
                _usuarioLogado.value = novoUsuario
                _loginSucess.value = true
                mensagemErro = null
            }
        }
    }

    fun atualizarUsuario(usuario: Usuario) {
        viewModelScope.launch {
            usuarioDAO.atualizar(usuario)
            _usuarioLogado.value = usuario // Atualiza usuário logado
        }
    }

    fun deletarUsuario(usuario: Usuario) {
        viewModelScope.launch {
            usuarioDAO.deletar(usuario)
            _usuarioLogado.value = null
        }
    }

    fun onLoginHandled() {
        _loginSucess.value = false
    }

    fun limparCampos() {
        nome = ""
        email = ""
        senha = ""
        mensagemErro = null
    }
}
