package com.example.projetoifoodandroidstudio.banco

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class LoginViewModelFactory(private val usuarioDAO: UsuarioDAO) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(usuarioDAO) as T
        }
        throw IllegalArgumentException("Classe ViewModel desconhecida")
    }
}
