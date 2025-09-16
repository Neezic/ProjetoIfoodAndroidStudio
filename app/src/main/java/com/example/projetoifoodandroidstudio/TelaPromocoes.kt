package com.example.projetoifoodandroidstudio

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.projetoifoodandroidstudio.ui.theme.ProjetoIfoodAndroidStudioTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeladePromocoes(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("PROMOÇÕES", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    Box(
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .size(24.dp)
                            .background(Color.Gray)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },

    ) { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .verticalScroll(rememberScrollState())
        ) {
            CardDescontos()
            BannerPromocionalGrande()
            SecaoCupom30Reais()
            SecaoRestaurantesComCupom()
        }
    }
}



@Composable
fun CardDescontos() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Ganhe descontos ao fazer pedidos", fontWeight = FontWeight.Bold)
            Text("Compre a partir de R$0,99", fontSize = 14.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(8.dp))
            Text("10 dias para resgatar", fontSize = 12.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                (1..5).forEach { _ ->
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Color.Gray)
                    )
                }
            }
        }
    }
}

@Composable
fun BannerPromocionalGrande() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Red),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "Tudo com até 50% OFF",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }
    }
}

@Composable
fun SecaoCupom30Reais() {
    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Cupom de até R$ 30", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text("Ver Mais", color = Color.Red, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(Color.DarkGray)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text("Deck 583 - ... ", fontWeight = FontWeight.SemiBold)
                Text("Dog, Burger...", color = Color.Gray, fontSize = 14.sp)
            }
        }
    }
}

@Composable
fun SecaoRestaurantesComCupom() {
    Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Restaurantes com Cupom", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text("Ver mais", color = Color.Red, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            items(5) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTelaDePromocoes() {
    val navController = rememberNavController()
    ProjetoIfoodAndroidStudioTheme {
        TeladePromocoes(navController = navController)
    }
}

