package com.example.projetoifoodandroidstudio.ui.theme.endereco

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.projetoifoodandroidstudio.data.local.Endereco
import com.example.projetoifoodandroidstudio.ui.theme.IfoodRed

@Composable
fun EnderecoItem(
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
            Text(text = endereco.apelido, style = MaterialTheme.typography.titleMedium)
            Text(
                text = "${endereco.logradouro}, ${endereco.numero}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "${endereco.bairro}, ${endereco.cidade}",
                style = MaterialTheme.typography.bodySmall
            )
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaEnderecos(
    modifier: Modifier = Modifier,
    viewModel: EnderecoViewModel = viewModel()
) {
    val endereco by viewModel.enderecos.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    if (showDialog) {
        EnderecoDialog(
            viewModel = viewModel,
            onDismiss = {
                showDialog = false
                viewModel.onDialogDismissed()
            }
        )
    }
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Meus Endereços") })
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onAddDialogOpened()
                    showDialog = true
                },
                containerColor = IfoodRed
            ) {
                Icon(Icons.Default.Add, contentDescription = "adicionar Endereço")
            }
        }
    ) { padding ->
        if (endereco.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("Nenhum Endereço cadastrado.")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {
                items(endereco) { endereco ->
                    EnderecoItem(
                        endereco = endereco,
                        onEdit = {
                            viewModel.onEditDialogOpened(endereco)
                            showDialog = true
                        },
                        onDelete = { viewModel.deleteEndereco(endereco) }
                    )
                    Divider()
                }

            }
        }
    }
}

@Composable
fun EnderecoDialog(
    viewModel: EnderecoViewModel,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (viewModel.enderecoSendoEditado == null) "Adicionar Endereço" else "Editar Endereço") },
        text = {
            Column {
                TextField(
                    value = viewModel.logradouro,
                    onValueChange = viewModel::onLogradouroChange,
                    label = { Text("Logradouro") })
                TextField(
                    value = viewModel.numero,
                    onValueChange = viewModel::onNumeroChange,
                    label = { Text("Numero") })
                TextField(
                    value = viewModel.bairro,
                    onValueChange = viewModel::onBairroChange,
                    label = { Text("Bairro") })
                TextField(
                    value = viewModel.cidade,
                    onValueChange = viewModel::onCidadeChange,
                    label = { Text("Cidade") })
                TextField(
                    value = viewModel.complemento,
                    onValueChange = viewModel::onComplementoChange,
                    label = { Text("Complemento") })
                TextField(
                    value = viewModel.apelido,
                    onValueChange = viewModel::onApelidoChange,
                    label = { Text("Apelido") })
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    viewModel.saveEndereco()
                    onDismiss()
                },
                colors = ButtonDefaults.buttonColors(containerColor = IfoodRed)
            ) {
                Text("Salvar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}