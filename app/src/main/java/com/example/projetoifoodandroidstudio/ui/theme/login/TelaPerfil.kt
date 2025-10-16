package com.example.projetoifoodandroidstudio.ui.theme.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import com.example.projetoifoodandroidstudio.R
import com.example.projetoifoodandroidstudio.data.local.Usuario
import com.example.projetoifoodandroidstudio.ui.theme.IfoodGrey

data class ProfileItem(val title: String, val onClick: () -> Unit = {})

@Composable
fun TelaPerfil(
    viewModel: LoginViewModel,
    onLogout: () -> Unit,
    onEditarPerfil: (String) -> Unit,
    onEnderecos: () -> Unit,
    modifier: Modifier = Modifier
) {
    val usuario by viewModel.usuarioLogado
    val usuarioLocal = usuario // Truque para smart cast

    if (usuarioLocal == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Carregando usuário...", color = Color.Gray)
        }
        return
    }

    val profileItems = listOf(
        ProfileItem("Comunidade iFood"),
        ProfileItem("Código de entrega"),
        ProfileItem("Fidelidade"),
        ProfileItem("Favoritos"),
        ProfileItem("Doações"),
        ProfileItem("Endereços") { onEnderecos() },
        ProfileItem("Ajuda"),
        ProfileItem("Configurações"),
        ProfileItem("Segurança"),
        ProfileItem("Sugerir restaurantes"),
        ProfileItem("Editar Perfil") { onEditarPerfil(usuarioLocal.email) },
        ProfileItem("Sair") {
            viewModel.limparCampos()
            onLogout()
        }
    )

    Scaffold { paddingValues ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
        ) {
            item { ProfileHeader(usuarioLocal) }
            items(profileItems) { item ->
                ProfileListItem(item)
            }
        }
    }
}

@Composable
fun ProfileHeader(usuario: Usuario) {
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
            Text(usuario.nome, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)
            Text("Ver meu Clube", color = IfoodGrey)
        }
    }
}

@Composable
fun ProfileListItem(item: ProfileItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = item.onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(item.title)
        Icon(Icons.Filled.ArrowForward, contentDescription = "Ir para", tint = Color.LightGray)
    }
    Divider(color = Color(0xFFF0F0F0), thickness = 1.dp)
}
