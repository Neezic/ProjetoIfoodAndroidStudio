package com.example.projetoifoodandroidstudio

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
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
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.navigation.NavHostController
import com.example.projetoifoodandroidstudio.ui.theme.IfoodGrey
import com.example.projetoifoodandroidstudio.ui.theme.ProjetoIfoodAndroidStudioTheme

data class ProfileItem(val title: String)

@Composable
fun TelaPerfil(modifier: Modifier = Modifier, navController: NavHostController){
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

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            item { ProfileHeader() }
            items(profileItem) { item -> ProfileListItem(item = item,
                onItemClick = {
                    if (item.title == "Endereços") {
                        navController.navigate(AppDestinations.ENDERECOS)
                    }
                }) }
        }
    }


@Composable
fun ProfileHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.fotoperfil),
            contentDescription = "Foto de perfil",
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = "Johnson Pinto da Silva",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Text(
                text = "Ver meu Clube",
                color = IfoodGrey,
                fontSize = 14.sp,
            )
        }
    }
}

@Composable
fun ProfileListItem(
    item: ProfileItem,
    onItemClick: () -> Unit
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable (onClick = onItemClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = item.title)
        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = "Navegar",
            tint = Color.LightGray
        )
    }
    Divider(color = Color(0xFFF0F0F0), thickness = 1.dp)
}

@Preview(showBackground = true, name = "Tela de Perfil")
@Composable
fun PreviewProfileScreen(navController: NavHostController) {
    ProjetoIfoodAndroidStudioTheme {
        TelaPerfil(navController = navController)
    }
}
