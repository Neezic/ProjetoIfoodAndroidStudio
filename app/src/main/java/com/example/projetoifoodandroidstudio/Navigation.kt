package com.example.projetoifoodandroidstudio

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

object AppDestinations{
    const val Tela_Principal = "MainActivity"
    const val Promocoes = "TelaPromocoes"
    const val Perfil  = "TelaPerfil"
    const val Pesquisa = "TelaPesquisa"
}

@Composable
fun AppNavigation(){
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BarraDeNavegacaoInferior(navController = navController)
        }
    ) {
        padding ->
        NavHost(
            navController = navController,
            startDestination = AppDestinations.Tela_Principal,
            modifier = Modifier.padding(padding)
        ){
            composable(AppDestinations.Tela_Principal){
                TelaPrincipal(modifier = Modifier)
            }
            composable(AppDestinations.Promocoes) {
                TeladePromocoes(navController = navController, modifier = Modifier)
            }

            composable(AppDestinations.Perfil){
                TelaPerfil(modifier = Modifier)
            }
            composable(AppDestinations.Pesquisa){
                TelaBusca(modifier = Modifier)
            }
        }

    }



}