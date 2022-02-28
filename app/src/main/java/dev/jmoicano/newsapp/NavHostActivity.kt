package dev.jmoicano.newsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.jmoicano.newsapp.articles.view.ArticlesList
import dev.jmoicano.newsapp.sources.view.SourcesList
import dev.jmoicano.newsapp.ui.theme.NewsAppTheme

@AndroidEntryPoint
class NavHostActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NewsAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    NavigationComponent(navController)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NavigationComponent(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Sources.route
    ) {
        composable(route = Screen.Sources.route) {
            SourcesList(navController = navController)
        }
        composable(route = Screen.Articles.route) { backStackEntry ->
            val sourceId = backStackEntry.arguments?.getString(SOURCE_ID).orEmpty()
            ArticlesList(sourceId = sourceId)
        }
    }
}

sealed class Screen(val route: String) {
    object Sources: Screen("sources")
    object Articles: Screen("{$SOURCE_ID}/articles") {
        fun createRoute(sourceId: String) = "$sourceId/articles"
    }
}

const val SOURCE_ID = "sourceId"