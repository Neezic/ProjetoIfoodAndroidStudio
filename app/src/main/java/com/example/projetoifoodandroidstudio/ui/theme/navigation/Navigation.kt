package com.example.projetoifoodandroidstudio.ui.theme.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.projetoifoodandroidstudio.BarraDeNavegacaoInferior
import com.example.projetoifoodandroidstudio.TelaPrincipal
import com.example.projetoifoodandroidstudio.data.local.AppDatabase
import com.example.projetoifoodandroidstudio.data.local.repository.UsuarioRepository
import com.example.projetoifoodandroidstudio.ui.theme.endereco.TelaEnderecos
import com.example.projetoifoodandroidstudio.ui.theme.login.*
import com.example.projetoifoodandroidstudio.ui.theme.pesquisa.TelaBusca
import com.example.projetoifoodandroidstudio.ui.theme.promocoes.TeladePromocoes

object AppDestinations {
    const val LOGIN = "TelaLogin"
    const val TELA_PRINCIPAL = "TelaPrincipal"
    const val PROMOCOES = "TelaPromocoes"
    const val PERFIL = "TelaPerfil"
    const val PESQUISA = "TelaPesquisa"
    const val EDITAR_PERFIL = "EditarPerfil"
    const val ENDERECOS = "TelaEnderecos"
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val context = LocalContext.current
    val usuarioDAO = AppDatabase.getDatabase(context).usuarioDAO()
    val repository = UsuarioRepository(usuarioDAO)

    val loginViewModel: LoginViewModel = viewModel(
        factory = LoginViewModelFactory(repository)
    )

    if (currentRoute != AppDestinations.LOGIN) {
        Scaffold(
            bottomBar = { BarraDeNavegacaoInferior(navController = navController) }
        ) { padding ->
            AppNavHost(
                navController = navController,
                modifier = Modifier.padding(padding),
                loginViewModel = loginViewModel
            )
        }
    } else {
        AppNavHost(
            navController = navController,
            modifier = Modifier,
            loginViewModel = loginViewModel
        )
    }
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier,
    loginViewModel: LoginViewModel
) {
    NavHost(
        navController = navController,
        startDestination = AppDestinations.LOGIN,
        modifier = modifier
    ) {
        // Login
        composable(AppDestinations.LOGIN) {
            TelaLogin(
                viewModel = loginViewModel,
                onLoginSucess = {
                    navController.navigate(AppDestinations.TELA_PRINCIPAL) {
                        popUpTo(AppDestinations.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        // Tela Principal
        composable(AppDestinations.TELA_PRINCIPAL) {
            TelaPrincipal(modifier = Modifier)
        }

        // Promoções
        composable(AppDestinations.PROMOCOES) {
            TeladePromocoes(modifier = Modifier)
        }

        // Perfil
        composable(AppDestinations.PERFIL) {
            TelaPerfil(
                viewModel = loginViewModel,
                onLogout = {
                    loginViewModel.limparCampos()
                    navController.navigate(AppDestinations.LOGIN) {
                        popUpTo(AppDestinations.TELA_PRINCIPAL) { inclusive = true }
                    }
                },
                onEditarPerfil = { navController.navigate(AppDestinations.EDITAR_PERFIL) },
                onEnderecos = {
                    navController.navigate(AppDestinations.ENDERECOS)
                }
            )
        }

        // Tela Editar Perfil
        composable(AppDestinations.EDITAR_PERFIL) {
            TelaEditarPerfil(
                loginViewModel = loginViewModel,
                onUsuarioAtualizado = { navController.popBackStack() },
                onUsuarioDeletado = {
                    loginViewModel.limparCampos()
                    navController.navigate(AppDestinations.LOGIN) {
                        popUpTo(AppDestinations.PERFIL) { inclusive = true }
                    }
                }
            )
        }

        // Pesquisa
        composable(AppDestinations.PESQUISA) {
            TelaBusca(modifier = Modifier)
        }

        // Endereços
        composable(AppDestinations.ENDERECOS) {
            TelaEnderecos()
        }
    }
}
