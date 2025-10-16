package com.example.projetoifoodandroidstudio.ui.theme.pesquisa

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

private val todasAsCategoria = listOf(
    "Promoções", "Super Restaurantes", "Açaí", "Doces & Bolos",
    "Salgados", "Pastel", "Marmita", "Japonesa", "Pizza", "Hambúrguer"
)

class SearchViewModel: ViewModel(){
    var textoBusca by mutableStateOf("")
        private set
    var categoriasFiltradas by mutableStateOf(todasAsCategoria)
        private set
    fun onTextoBuscaChange(novoTexto:String){
        textoBusca = novoTexto

        if(novoTexto.isBlank()){
            categoriasFiltradas = todasAsCategoria
        } else {
            categoriasFiltradas = todasAsCategoria.filter{
                categoria -> categoria.contains(novoTexto, ignoreCase = true)
            }
        }
    }
}