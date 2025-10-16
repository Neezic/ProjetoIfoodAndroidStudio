package com.example.projetoifoodandroidstudio.ui.theme.endereco

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projetoifoodandroidstudio.data.local.AppDatabase
import com.example.projetoifoodandroidstudio.data.local.Endereco
import com.example.projetoifoodandroidstudio.data.local.repository.EnderecoRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class EnderecoViewModel(private val repository: EnderecoRepository): ViewModel() {

    val enderecos: StateFlow<List<Endereco>> = repository.getAll()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )
    var enderecoSendoEditado by mutableStateOf<Endereco?>(null)
        private set
    var logradouro by mutableStateOf("")
        private set
    var numero by mutableStateOf("")
        private set
    var bairro by mutableStateOf("")
        private set
    var cidade by mutableStateOf("")
        private set
    var complemento by mutableStateOf("")
        private set
    var apelido by mutableStateOf("")
        private set

    fun onEditDialogOpened(endereco: Endereco){
        enderecoSendoEditado = endereco
        logradouro = endereco.logradouro
        numero = endereco.numero
        bairro = endereco.bairro
        cidade = endereco.cidade
        complemento = endereco.complemento
        apelido = endereco.apelido
    }
    fun onAddDialogOpened(){
        enderecoSendoEditado = null
        logradouro = ""
        numero = ""
        bairro = ""
        cidade = ""
        complemento = ""
        apelido = ""
    }
    fun onDialogDismissed(){
        onAddDialogOpened()
    }
    fun onLogradouroChange(newLogradouro: String) {logradouro = newLogradouro}
    fun onNumeroChange(newNumero: String) {numero = newNumero}
    fun onBairroChange(newBairro: String) {bairro = newBairro}
    fun onCidadeChange(newCidade: String) {cidade = newCidade }
    fun onComplementoChange(newComplemento: String) {complemento = newComplemento}
    fun onApelidoChange(newApelido: String) {apelido = newApelido}
    fun saveEndereco(){
        if(logradouro.isBlank() || numero.isBlank() || bairro.isBlank() || cidade.isBlank() || complemento.isBlank() || apelido.isBlank()){
            return
        }
        val endereco = Endereco (
            id = enderecoSendoEditado?.id ?: 0,
            logradouro = logradouro,
            numero = numero,
            bairro = bairro,
            cidade = cidade,
            complemento = complemento,
            apelido = apelido
        )
        viewModelScope.launch {
            if(enderecoSendoEditado == null){
                repository.insert(endereco)
            } else {
                repository.update(endereco)
            }
        }
        onDialogDismissed()
    }
    fun deleteEndereco(endereco: Endereco){
        viewModelScope.launch{
            repository.delete(endereco)
        }
    }
}