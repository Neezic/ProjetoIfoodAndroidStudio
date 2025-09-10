package com.example.clonedoifood_

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.clonedoifood_.ui.theme.CloneDoIfood_Theme


data class Categoria(val nome: String)
data class ItemComida(val nome: String, val preco: String, val entregaInfo: String)


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CloneDoIfood_Theme {
                Scaffold(
                    bottomBar = { BarraDeNavegacaoInferior() }
                ) { padding ->
                    TelaPrincipal(modifier = Modifier.padding(padding))

                }

            }
        }
    }
}

@Composable
fun TelaPrincipal(modifier: Modifier = Modifier) {
    // --- Dados de Exemplo para a Tela ---
    val categorias = listOf(
        Categoria("Restaurantes"), Categoria("Mercados"), Categoria("Farmácias"),
        Categoria("Shopping"), Categoria("Promoções"), Categoria("Bebidas"),
        Categoria("Cupons"), Categoria("Ofertas")
    )
    val itensComida = listOf(
        ItemComida("X-Salada", "R$ 11,75", "42-52 min • Grátis"),
        ItemComida("Sanduíche Americano", "R$ 7,50", "42-52 min • Grátis")
    )

    // A Column organiza os itens verticalmente. O verticalScroll permite rolar a tela.
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
    // Row organiza os itens horizontalmente.
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween // Espaça os itens
    ) {
        Text("Av. Francisco Kruger, 5280", fontWeight = FontWeight.Bold)
        // Caixa cinza no lugar do ícone de notificação
        Box(modifier = Modifier.size(24.dp).background(Color.LightGray))
    }
}

@Composable
fun GridDeCategorias(categorias: List<Categoria>) {
    // Usamos um FlowRow para que os itens quebrem a linha automaticamente.
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        // Mostra apenas as 4 primeiras categorias para simplificar
        categorias.take(4).forEach { categoria ->
            ItemDeCategoria(categoria)
        }
    }
}

@Composable
fun ItemDeCategoria(categoria: Categoria) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFFF0F0F0)) // Um cinza bem claro
        )
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
        colors = CardDefaults.cardColors(
            containerColor = Color.Red
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
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
        // Cria uma linha para cada item de comida
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
        // Caixa cinza no lugar da imagem da comida
        Box(
            modifier = Modifier
                .size(70.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.LightGray)
        )
        Spacer(modifier = Modifier.width(16.dp))
        // Coluna com as informações do item
        Column {
            Text(item.nome, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(4.dp))
            Text(item.preco, color = Color.Gray, fontSize = 14.sp)
            Text(item.entregaInfo, color = Color.Gray, fontSize = 14.sp)
        }
    }
}

@Composable
fun BarraDeNavegacaoInferior() {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface
    )
    {
        // Itens da barra de navegação, sem ícones
        NavigationBarItem(
            selected = true,
            onClick = { /* Não faz nada */ },
            label = { Text("Início") },
            icon = { /* Icone vazio */ Box(Modifier.size(24.dp)) },
            colors = NavigationBarItemDefaults.colors(
                selectedTextColor = Color.Red,
                unselectedTextColor = Color.Gray
            )
        )
        NavigationBarItem(
            selected = false,
            onClick = { /* Não faz nada */ },
            label = { Text("Busca") },
            icon = { /* Icone vazio */ Box(Modifier.size(24.dp)) },
            colors = NavigationBarItemDefaults.colors(
                selectedTextColor = Color.Red,
                unselectedTextColor = Color.Gray
            )
        )
        NavigationBarItem(
            selected = false,
            onClick = { /* Não faz nada */ },
            label = { Text("Pedidos") },
            icon = { /* Icone vazio */ Box(Modifier.size(24.dp)) },
            colors = NavigationBarItemDefaults.colors(
                selectedTextColor = Color.Red,
                unselectedTextColor = Color.Gray
            )
        )
        NavigationBarItem(
            selected = false,
            onClick = { /* Não faz nada */ },
            label = { Text("Perfil") },
            icon = { /* Icone vazio */ Box(Modifier.size(24.dp)) },
            colors = NavigationBarItemDefaults.colors(
                selectedTextColor = Color.Red,
                unselectedTextColor = Color.Gray
            )
        )
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CloneDoIfood_Theme {
        Scaffold(
            bottomBar = { BarraDeNavegacaoInferior() }
        ) { padding ->
            TelaPrincipal(modifier = Modifier.padding(padding))
        }
    }
}
