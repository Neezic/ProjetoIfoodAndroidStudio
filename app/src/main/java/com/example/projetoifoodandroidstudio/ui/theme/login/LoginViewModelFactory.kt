package com.example.projetoifoodandroidstudio.ui.theme.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.projetoifoodandroidstudio.data.local.UsuarioDAO

class LoginViewModelFactory(private val usuarioDAO: UsuarioDAO) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(usuarioDAO) as T
        }
        throw IllegalArgumentException("Classe ViewModel desconhecida")
    }
}
