package com.example.projetoifoodandroidstudio

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

object AppDestinations{
    const val Login = "TelaLogin"
    const val Tela_Principal = "MainActivity"
    const val Promocoes = "TelaPromocoes"
    const val Perfil  = "TelaPerfil"
    const val Pesquisa = "TelaPesquisa"
}

@Composable
fun AppNavigation(){
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    if(currentRoute != AppDestinations.Login) {
        Scaffold(
            bottomBar = {
                BarraDeNavegacaoInferior(navController = navController)
            }
        ) { padding ->
            AppNavHost(navController = navController, modifier = Modifier.padding(padding))
        }
    } else {
        AppNavHost(navController = navController, modifier = Modifier)
    }
}

@Composable
fun AppNavHost(
    navController: androidx.navigation.NavHostController,
    modifier: Modifier
) {
    NavHost(
        navController = navController,
        startDestination = AppDestinations.Login, // 2. Login é a nova tela inicial
        modifier = modifier
    ) {
        composable(AppDestinations.Login) {
            TelaLogin(
                onLoginSucess = {
                    navController.navigate(AppDestinations.Tela_Principal){
                        popUpTo(AppDestinations.Login){
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(AppDestinations.Tela_Principal) {
            TelaPrincipal(modifier = Modifier)
        }
        composable(AppDestinations.Promocoes) {
            TeladePromocoes(modifier = Modifier)
        }
        composable(AppDestinations.Perfil) {
            TelaPerfil(modifier = Modifier)
        }
        composable(AppDestinations.Pesquisa) {
            TelaBusca(modifier = Modifier)
        }
    }
}