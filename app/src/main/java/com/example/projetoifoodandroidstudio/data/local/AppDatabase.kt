package com.example.projetoifoodandroidstudio.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Endereco::class, Usuario::class], version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun enderecoDAO(): EnderecoDAO
    abstract fun usuarioDAO(): UsuarioDAO

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "ifood_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
