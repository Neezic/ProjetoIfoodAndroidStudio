package com.example.projetoifoodandroidstudio

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

object AppDestinations{
    const val LOGIN = "TelaLogin"
    const val TELA_PRINCIPAL = "MainActivity"
    const val PROMOCOES = "TelaPromocoes"
    const val PERFIL  = "TelaPerfil"
    const val PESQUISA = "TelaPesquisa"
    const val ENDERECOS = "TelaEnderecos"
}

@Composable
fun AppNavigation(){
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    if(currentRoute != AppDestinations.LOGIN) {
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
        startDestination = AppDestinations.LOGIN, // 2. Login é a nova tela inicial
        modifier = modifier
    ) {
        composable(AppDestinations.LOGIN) {
            TelaLogin(
                onLoginSucess = {
                    navController.navigate(AppDestinations.TELA_PRINCIPAL){
                        popUpTo(AppDestinations.LOGIN){
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(AppDestinations.TELA_PRINCIPAL) {
            TelaPrincipal(modifier = Modifier)
        }
        composable(AppDestinations.PROMOCOES) {
            TeladePromocoes(modifier = Modifier)
        }
        composable(AppDestinations.PERFIL) {
            TelaPerfil(
                modifier = Modifier,
                navController = navController
            )
        }
        composable(AppDestinations.ENDERECOS){
            TelaEnderecos(
                modifier = Modifier,
                viewModel = viewModel()
            )
        }
        composable(AppDestinations.PESQUISA) {
            TelaBusca(modifier = Modifier)
        }
    }
}