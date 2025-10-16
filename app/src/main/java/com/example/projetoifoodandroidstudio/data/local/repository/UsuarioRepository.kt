package com.example.projetoifoodandroidstudio.data.local.repository

import com.example.projetoifoodandroidstudio.data.local.Usuario
import com.example.projetoifoodandroidstudio.data.local.UsuarioDAO
import kotlinx.coroutines.flow.Flow

class UsuarioRepository(private val usuarioDAO: UsuarioDAO) {

    // Buscar usuário por e-mail
    fun buscarPorEmail(email: String): Flow<Usuario?> {
        return usuarioDAO.buscarPorEmail(email)
    }

    // Inserir novo usuário
    suspend fun inserir(usuario: Usuario) {
        usuarioDAO.inserir(usuario)
    }

    // Atualizar usuário
    suspend fun atualizar(usuario: Usuario) {
        usuarioDAO.atualizar(usuario)
    }

    // Deletar usuário
    suspend fun deletar(usuario: Usuario) {
        usuarioDAO.deletar(usuario)
    }
}
