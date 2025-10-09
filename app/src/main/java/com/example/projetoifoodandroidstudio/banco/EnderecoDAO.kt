package com.example.projetoifoodandroidstudio.banco

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface EnderecoDAO {
    @Insert
    suspend fun insert(endereco: Endereco)

    @Query("SELECT * FROM endereços ORDER BY apelido ASC")
    fun getAll(): Flow<List<Endereco>>

    @Update
    suspend fun update(endereco: Endereco)

    @Delete
    suspend fun delete(endereco: Endereco)
}
