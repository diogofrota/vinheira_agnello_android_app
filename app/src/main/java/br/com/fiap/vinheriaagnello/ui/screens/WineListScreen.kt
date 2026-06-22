package br.com.fiap.vinheriaagnello.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.vinheriaagnello.model.Wine
import br.com.fiap.vinheriaagnello.viewmodel.WineViewModel

import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WineListScreen(
    viewModel: WineViewModel,
    onAddWine: () -> Unit,
    onEditWine: (Long) -> Unit
) {
    val wines by viewModel.allWines.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Vinheria Agnello - Estoque", color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddWine,
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Adicionar Vinho")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            if (wines.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Nenhum vinho cadastrado.", style = MaterialTheme.typography.bodyLarge)
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(wines) { wine ->
                        WineItem(
                            wine = wine,
                            onEdit = { onEditWine(wine.id) },
                            onDelete = { viewModel.deleteWine(wine) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun WineItem(
    wine: Wine,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(2f)) {
                Text(wine.name, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = MaterialTheme.colorScheme.primary)
                Text("R$ ${String.format(Locale.getDefault(), "%.2f", wine.price)}", fontSize = 14.sp, color = Color.Gray)
            }
            Column(modifier = Modifier.weight(1.5f)) {
                Text(wine.type, fontSize = 14.sp, fontWeight = FontWeight.Medium)
                Text("Qtd: ${wine.quantity}", fontSize = 12.sp, color = Color.DarkGray)
            }
            Row(horizontalArrangement = Arrangement.End) {
                IconButton(onClick = onEdit) {
                    Icon(Icons.Default.Edit, contentDescription = "Editar", tint = MaterialTheme.colorScheme.secondary)
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, contentDescription = "Excluir", tint = Color(0xFFB03A2E))
                }
            }
        }
    }
}
