package br.com.fiap.vinheriaagnello.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import br.com.fiap.vinheriaagnello.model.Wine
import br.com.fiap.vinheriaagnello.viewmodel.WineViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WineFormScreen(
    viewModel: WineViewModel,
    wineId: Long? = null,
    onNavigateBack: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }

    LaunchedEffect(wineId) {
        if (wineId != null && wineId != -1L) {
            val wine = viewModel.getWineById(wineId)
            wine?.let {
                name = it.name
                type = it.type
                price = it.price.toString()
                quantity = it.quantity.toString()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (wineId == null || wineId == -1L) "Novo Vinho" else "Editar Vinho", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nome do Vinho") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = type,
                onValueChange = { type = it },
                label = { Text("Tipo (ex: Tinto, Branco, Rose)") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = price,
                onValueChange = { price = it },
                label = { Text("Valor (R$)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )
            OutlinedTextField(
                value = quantity,
                onValueChange = { quantity = it },
                label = { Text("Quantidade em Estoque") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Button(
                onClick = {
                    val winePrice = price.toDoubleOrNull() ?: 0.0
                    val wineQuantity = quantity.toIntOrNull() ?: 0
                    if (name.isNotBlank() && type.isNotBlank()) {
                        val wine = Wine(
                            id = if (wineId != null && wineId != -1L) wineId else 0,
                            name = name,
                            type = type,
                            price = winePrice,
                            quantity = wineQuantity
                        )
                        if (wine.id == 0L) {
                            viewModel.insertWine(wine)
                        } else {
                            viewModel.updateWine(wine)
                        }
                        onNavigateBack()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text("Salvar", color = Color.White)
            }
        }
    }
}
