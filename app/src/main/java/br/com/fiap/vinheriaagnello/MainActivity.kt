package br.com.fiap.vinheriaagnello

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.com.fiap.vinheriaagnello.ui.screens.WineFormScreen
import br.com.fiap.vinheriaagnello.ui.screens.WineListScreen
import br.com.fiap.vinheriaagnello.ui.theme.VinheriaAgnelloTheme
import br.com.fiap.vinheriaagnello.viewmodel.WineViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VinheriaAgnelloTheme {
                VinheriaApp()
            }
        }
    }
}

@Composable
fun VinheriaApp() {
    val navController = rememberNavController()
    val viewModel: WineViewModel = viewModel()

    NavHost(navController = navController, startDestination = "list") {
        composable("list") {
            WineListScreen(
                viewModel = viewModel,
                onAddWine = { navController.navigate("form/-1") },
                onEditWine = { id -> navController.navigate("form/$id") }
            )
        }
        composable(
            route = "form/{wineId}",
            arguments = listOf(navArgument("wineId") { type = NavType.LongType })
        ) { backStackEntry ->
            val wineId = backStackEntry.arguments?.getLong("wineId")
            WineFormScreen(
                viewModel = viewModel,
                wineId = wineId,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
