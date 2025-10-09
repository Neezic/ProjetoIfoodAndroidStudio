package com.example.projetoifoodandroidstudio

import androidx.compose.foundation.layout.padding
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
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.projetoifoodandroidstudio.banco.AppDatabase
import com.example.projetoifoodandroidstudio.banco.LoginViewModel
import com.example.projetoifoodandroidstudio.banco.LoginViewModelFactory
import com.example.projetoifoodandroidstudio.banco.UsuarioDAO

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
    val loginViewModel: LoginViewModel = viewModel(
        factory = LoginViewModelFactory(usuarioDAO)
    )

    if (currentRoute != AppDestinations.LOGIN) {
        Scaffold(bottomBar = { BarraDeNavegacaoInferior(navController = navController) }) { padding ->
            AppNavHost(
                navController = navController,
                modifier = Modifier.padding(padding),
                usuarioDAO = usuarioDAO,
                loginViewModel = loginViewModel
            )
        }
    } else {
        AppNavHost(
            navController = navController,
            modifier = Modifier,
            usuarioDAO = usuarioDAO,
            loginViewModel = loginViewModel
        )
    }
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier,
    usuarioDAO: UsuarioDAO,
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
            val usuario = loginViewModel.usuarioLogado.value
            if (usuario != null) {
                TelaPerfil(
                    usuarioDAO = usuarioDAO,
                    usuario = usuario,
                    onLogout = {
                        loginViewModel.limparCampos()
                        navController.navigate(AppDestinations.LOGIN) {
                            popUpTo(AppDestinations.TELA_PRINCIPAL) { inclusive = true }
                        }
                    },
                    onEditarPerfil = { email ->
                        navController.navigate("${AppDestinations.EDITAR_PERFIL}/$email")
                    },
                    onEnderecos = {
                        navController.navigate(AppDestinations.ENDERECOS)
                    }
                )
            } else {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Carregando usuário...", color = Color.Gray)
                }
            }
        }

        // Tela Editar Perfil
        composable(
            route = "${AppDestinations.EDITAR_PERFIL}/{email}",
            arguments = listOf(navArgument("email") { type = NavType.StringType })
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            TelaEditarPerfil(
                usuarioDAO = usuarioDAO,
                usuarioEmail = email,
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
        composable(AppDestinations.ENDERECOS) {
            TelaEnderecos()
        }
    }
}
