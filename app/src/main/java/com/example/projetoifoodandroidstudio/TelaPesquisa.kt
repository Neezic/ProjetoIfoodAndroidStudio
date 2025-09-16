import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import com.example.projetoifoodandroidstudio.R


class TelaBuscaActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                TelaBusca(modifier = Modifier)
            }
        }
    }
}

@Preview
@Composable
fun TelaBusca(modifier: Modifier = Modifier) {

    val textoBusca = remember { androidx.compose.runtime.mutableStateOf("") }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
    ) {

        item {
            OutlinedTextField(
                value = textoBusca.value,
                onValueChange = { textoBusca.value = it },
                leadingIcon = {
                    IconButton(onClick = {
                        println("Buscando por: ${textoBusca.value}")
                    }) {
                        Icon(Icons.Outlined.Search, contentDescription = "Buscar")
                    }
                },
                placeholder = { Text("O que vai pedir hoje?") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )
        }

        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .padding(horizontal = 10.dp)
                    .background(Color(0xFFD32F2F), RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Cupons de até R$30!",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        item {
            Spacer(Modifier.height(10.dp))
            Text(
                "Alguém buscando cupons?",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(start = 10.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CupomItem("até R$12", Color(0xFF2E7D32))
                CupomItem("a partir de R$5", Color(0xFFD32F2F))
                CupomItem("a partir de R$10", Color(0xFFD32F2F))
                CupomItem("a partir de R$20", Color(0xFFD32F2F))
            }
        }

        item {
            Spacer(Modifier.height(25.dp))
            Text(
                "Categorias",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(start = 10.dp)
            )
        }

        items(
            listOf(
                "Pastel" to Color(0xFFF57C00),
                "Salgados" to Color(0xFFD32F2F),
                "Promoções" to Color(0xFFFF80AB),
                "Super Restaurantes" to Color(0xFFC2185B),
                "Açaí" to Color(0xFF7B1FA2),
                "Doces & Bolos" to Color(0xFFFF7043)
            ).chunked(2)
        ) { linha ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 5.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                linha.forEach { (titulo, cor) ->
                    val imagem = when (titulo.lowercase()) {
                        "açaí" -> R.drawable.acai
                        "doces & bolos" -> R.drawable.docesebolo
                        "pastel" -> R.drawable.pastel
                        "promoções" -> R.drawable.promocao
                        "salgados" -> R.drawable.salgados
                        "super restaurantes" -> R.drawable.superrestaurantes
                        else -> R.drawable.ic_launcher_background
                    }
                    CategoriaItem(
                        titulo = titulo,
                        cor = cor,
                        imagemRes = imagem,
                        modifier = Modifier.weight(1f)
                    )
                }
                if (linha.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}


@Composable
fun CupomItem(texto: String, cor: Color) {
    Box(
        modifier = Modifier
            .width(90.dp)
            .height(50.dp)
            .background(cor, RoundedCornerShape(8.dp))
            .clickable { },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = texto,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
    }
}

@Composable
fun CategoriaItem(
    titulo: String,
    cor: Color,
    imagemRes: Int,  // novo parâmetro para a imagem
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(90.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(cor),
            contentAlignment = Alignment.Center
        ) {

            Image(
                painter = painterResource(id = imagemRes),
                contentDescription = titulo,
                modifier = Modifier.fillMaxSize()
            )
            Text(
                text = titulo,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


@Composable
fun BottomBar() {
    NavigationBar {
        NavigationBarItem(
            selected = true,
            onClick = { },
            icon = { Icon(Icons.Default.Home, contentDescription = "Início") },
            label = { Text("Início") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { },
            icon = { Icon(Icons.Outlined.Search, contentDescription = "Busca") },
            label = { Text("Busca") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { },
            icon = { Icon(Icons.Outlined.KeyboardArrowUp, contentDescription = "Hits") },
            label = { Text("Hits") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { },
            icon = { Icon(Icons.Outlined.ShoppingCart, contentDescription = "Pedidos") },
            label = { Text("Pedidos") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { },
            icon = { Icon(Icons.Default.Person, contentDescription = "Perfil") },
            label = { Text("Perfil") }
        )
    }
}