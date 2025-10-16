package com.example.projetoifoodandroidstudio.data.local.repository

import com.example.projetoifoodandroidstudio.data.local.Endereco
import com.example.projetoifoodandroidstudio.data.local.EnderecoDAO
import kotlinx.coroutines.flow.Flow

class EnderecoRepository (private val enderecoDAO: EnderecoDAO) {


    fun getAll(): Flow<List<Endereco>> {
        return enderecoDAO.getAll()
    }

    suspend fun insert(endereco: Endereco) {
        enderecoDAO.insert(endereco)
    }

    suspend fun update(endereco: Endereco) {

    }
}