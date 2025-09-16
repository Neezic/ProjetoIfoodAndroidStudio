package com.example.projetoifoodandroidstudio

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.*
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.projetoifoodandroidstudio.ui.theme.ProjetoIfoodAndroidStudioTheme

data class Categoria(val nome: String)
data class ItemComida(val nome: String, val preco: String, val entregaInfo: String)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProjetoIfoodAndroidStudioTheme {
               AppNavigation()
            }
        }
    }
}

@Composable
fun TelaPrincipal(modifier: Modifier = Modifier) {
    val categorias = listOf(
        Categoria("Restaurantes"), Categoria("Mercados"), Categoria("Farmácias"),
        Categoria("Shopping"), Categoria("Promoções"), Categoria("Bebidas"),
        Categoria("Cupons"), Categoria("Ofertas")
    )
    val itensComida = listOf(
        ItemComida("X-Salada", "R$ 11,75", "42-52 min • Grátis"),
        ItemComida("Sanduíche Americano", "R$ 7,50", "42-52 min • Grátis")
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color.White)
    ) {
        BarraSuperior()
        GridDeCategorias(categorias)
        BannerPromocional()
        SecaoEntregaGratis(itensComida)
    }
}

@Composable
fun BarraSuperior() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text("Av. Francisco Kruger, 5280", fontWeight = FontWeight.Bold)
       Icon (
           imageVector = Icons.Outlined.Notifications,
           contentDescription = "Notificações"
       )
    }
}

@Composable
fun GridDeCategorias(categorias: List<Categoria>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        categorias.take(4).forEach { categoria ->
            ItemDeCategoria(categoria)
        }
    }
}

@Composable
fun ItemDeCategoria(categoria: Categoria) {
    val icon: ImageVector = when(categoria.nome){
        "Restaurantes" -> Icons.Default.LocalDining
        "Mercados" -> Icons.Default.LocalGroceryStore
        "Fármacias" -> Icons.Default.LocalPharmacy
        "Shopping" -> Icons.Default.Storefront
        else -> Icons.Default.Storefront

    }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFFF0F0F0)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = categoria.nome,
                tint = Color.DarkGray
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = categoria.nome, fontSize = 12.sp, color = Color.Gray)
    }
}

@Composable
fun BannerPromocional() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Red),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                "Tudo por R$0,99",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun SecaoEntregaGratis(itens: List<ItemComida>) {
    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Tudo com entrega grátis", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text("Ver mais", fontWeight = FontWeight.Bold, color = Color.Red)
        }
        Spacer(modifier = Modifier.height(16.dp))
        itens.forEach { item ->
            LinhaDeItemDeComida(item)
        }
    }
}

@Composable
fun LinhaDeItemDeComida(item: ItemComida) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.LocalDining,
            contentDescription = "Imagem de Prato",
            modifier = Modifier
                .size(70.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.LightGray)
                .padding(8.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(item.nome, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(item.preco, color = Color.Gray, fontSize = 14.sp)
            Text(item.entregaInfo, color = Color.Gray, fontSize = 14.sp)
        }
    }
}

@Composable
fun BarraDeNavegacaoInferior(navController: NavController) {
    NavigationBar (
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        NavigationBarItem(
            currentRoute == AppDestinations.Tela_Principal,
            onClick = {
                navController.navigate(AppDestinations.Tela_Principal){
                    launchSingleTop = true
                }
            },
            icon = { Icon(Icons.Default.Home, contentDescription = "Início") },
            label = { Text("Início") }
        )
        NavigationBarItem(
            currentRoute == AppDestinations.Pesquisa,
            onClick = {
                navController.navigate(AppDestinations.Pesquisa) {
                    launchSingleTop = true
                }
            },
            icon = { Icon(Icons.Outlined.Search, contentDescription = "Busca") },
            label = { Text("Busca") }
        )
        NavigationBarItem(
            currentRoute == AppDestinations.Promocoes,
            onClick = {
                navController.navigate(AppDestinations.Promocoes) {
                    launchSingleTop = true
                }},
            icon = { Icon(Icons.Outlined.KeyboardArrowUp, contentDescription = "Promoções") },
            label = { Text("Promoções") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { },
            icon = { Icon(Icons.Outlined.ShoppingCart, contentDescription = "Pedidos") },
            label = { Text("Pedidos") }
        )
        NavigationBarItem(
            currentRoute == AppDestinations.Perfil,
            onClick = {
                navController.navigate(AppDestinations.Perfil) {
                launchSingleTop = true
            }},
            icon = { Icon(Icons.Default.Person, contentDescription = "Perfil") },
            label = { Text("Perfil") }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ProjetoIfoodAndroidStudioTheme {
        AppNavigation()
    }
}
