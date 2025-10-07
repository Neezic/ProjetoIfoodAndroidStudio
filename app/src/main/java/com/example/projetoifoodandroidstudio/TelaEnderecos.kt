package com.example.projetoifoodandroidstudio

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Delete
import com.example.projetoifoodandroidstudio.ui.theme.IfoodRed

@Composable
fun EnderecoItem (
    endereco: Endereco,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = endereco.apelido,style = MaterialTheme.typography.titleMedium)
            Text(text = "${endereco.logradouro}, ${endereco.numero}",style = MaterialTheme.typography.bodyMedium)
            Text(text = "${endereco.bairro}, ${endereco.cidade}", style = MaterialTheme.typography.bodySmall)
        }
        Row {
            IconButton(onClick = onEdit) {
                Icon(Icons.Default.Edit, contentDescription = "Editar")
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Deletar", tint = IfoodRed)
            }
        }
    }
}
@Composable
fun TelaEnderecos(
    modifier: Modifier = Modifier,
    viewModel: EnderecoViewModel = viewModel()
) {
    val endereco by viewModel.enderecos.collectAsState()

Scaffold(
    floatingActionButton = {
        FloatingActionButton(
            onClick = { },
            containerColor = IfoodRed
        ) {
            Icon(Icons.Default.Add,contentDescription = "adicionar Endereço")
        }
    }
) { padding ->
    LazyColumn (
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(16.dp)
    ){
        items(endereco) { endereco ->
            EnderecoItem(
                endereco = endereco,
                onEdit = {viewModel.updateEndereco(endereco)},
                onDelete = {viewModel.deleteEndereco(endereco)}
            )
            Divider()
        }
    }
}
}