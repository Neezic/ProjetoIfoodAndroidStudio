package com.example.projetoifoodandroidstudio.ui.theme.endereco

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.projetoifoodandroidstudio.data.local.AppDatabase
import com.example.projetoifoodandroidstudio.data.local.repository.EnderecoRepository

class ViewModelFactoryEnd (private val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>) : T {
        if (modelClass.isAssignableFrom(EnderecoViewModel::class.java)) {
            val dao = AppDatabase.getDatabase(application).enderecoDAO()
            val repository = EnderecoRepository(dao)
            return EnderecoViewModel(repository) as T
        }
        throw IllegalArgumentException("Classe ViewModel Desconhecida")
    }
}