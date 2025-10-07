package com.example.projetoifoodandroidstudio

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginViewModel : ViewModel(){

    var email by mutableStateOf("")
        private set
    var senha by mutableStateOf("")
        private set
    var mensagemErro by mutableStateOf<String?>(null)
        private set
    private val _loginSucess = MutableStateFlow<Boolean>(false)
    val loginSucess = _loginSucess.asStateFlow()

    fun onEmailChange(novoEmail: String){
        email = novoEmail
        mensagemErro = null
    }
    fun onSenhaChange(novaSenha: String){
        senha = novaSenha
        mensagemErro = null
    }


    fun fazerLogin(){
        if(email.isNotBlank() && senha.isNotBlank()){
            _loginSucess.value = true
            mensagemErro = null
        } else{
            mensagemErro = "Por favor, preencha todos os campos."
        }
    }
    fun onLoginHandled(){
        _loginSucess.value = false
    }

}