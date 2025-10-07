package com.example.projetoifoodandroidstudio

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class EnderecoViewModel (application:Application): AndroidViewModel(application) {

    private val dao = AppDatabase.getDatabase(application).enderecoDAO()

    val enderecos: StateFlow<List<Endereco>> = dao.getAll()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )
    fun addEndereco(endereco: Endereco) {
        viewModelScope.launch {
            dao.insert(endereco)
        }
    }
    fun updateEndereco(endereco: Endereco) {
        viewModelScope.launch{
            dao.update(endereco)
        }
    }
    fun deleteEndereco(endereco: Endereco){
        viewModelScope.launch{
            dao.delete(endereco)
        }
    }
}