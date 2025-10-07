package com.example.projetoifoodandroidstudio

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "endereços")
data class Endereco(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val logradouro: String,
    val numero: String,
    val bairro: String,
    val cidade: String,
    val complemento: String,
    val apelido: String
)