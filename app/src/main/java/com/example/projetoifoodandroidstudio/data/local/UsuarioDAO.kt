package com.example.projetoifoodandroidstudio.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UsuarioDAO {
    @Insert
    suspend fun inserir(usuario: Usuario)

    @Query("SELECT * FROM usuarios WHERE email = :email LIMIT 1")
    fun buscarPorEmail(email: String): Flow<Usuario?>

    @Update
    suspend fun atualizar(usuario: Usuario)

    @Delete
    suspend fun deletar(usuario: Usuario)

    @Query("SELECT * FROM usuarios ORDER BY nome ASC")
    fun listarTodos(): Flow<List<Usuario>>
}
