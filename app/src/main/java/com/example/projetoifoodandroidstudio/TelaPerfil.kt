package com.example.projetoifoodandroidstudio

import android.provider.ContactsContract.Profile
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
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
import com.example.projetoifoodandroidstudio.ui.theme.ProjetoIfoodAndroidStudioTheme

data class ProfileItem(val title: String)

@Composable
fun ProfileScreen(modifier: Modifier = Modifier){
    val profileItem = listOf(
        ProfileItem("Comunidade iFood"),
        ProfileItem("Código de entrega"),
        ProfileItem("Fidelidade"),
        ProfileItem("Favoritos"),
        ProfileItem("Doações"),
        ProfileItem("Endereços"),
        ProfileItem("Ajuda"),
        ProfileItem("Configurações"),
        ProfileItem("Segurança"),
        ProfileItem("Sugerir restaurantes")
    )
    Scaffold (
        bottomBar = { BarraDeNavegacaoInferior() }
    ){
        paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
        ) {
            item{
                ProfileHeader()
            }
            items(profileItem){
                item -> ProfileListItem(item)
            }
        }
    }
}

@Composable
fun ProfileHeader(){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(Color(0xFFE0E0E0))
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column{
            Text(
                text = "Johnson Pinto da Silva",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Text(
                text = "Ver meu Clube",
                color = Color.Gray,
                fontSize = 14.sp,
            )
        }
    }
}

@Composable
fun ProfileListItem(item:ProfileItem){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = item.title)
        Box(
            modifier = Modifier
                .size(16.dp)
                .background(Color.LightGray)
        )
    }
    Divider(color = Color(0xFFF0F0F0), thickness = 1.dp)
}

@Preview(showBackground = true, name = "Tela de Perfil")
@Composable
fun PreviewProfileScreen() {
    ProjetoIfoodAndroidStudioTheme {
        ProfileScreen()
    }
}