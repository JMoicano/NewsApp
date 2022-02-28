package dev.jmoicano.newsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
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
            val screenState = remember(ScreenState.SOURCES) {
                mutableStateOf(ScreenState.SOURCES)}
            NewsAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopBar(navController = navController, screenState)
                    }) {
                    NavigationComponent(navController, screenState)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NavigationComponent(navController: NavHostController, screenState: MutableState<ScreenState>) {
    NavHost(
        navController = navController,
        startDestination = Screen.Sources.route
    ) {
        composable(route = Screen.Sources.route) {
            screenState.value = ScreenState.SOURCES
            SourcesList(navController = navController)
        }
        composable(route = Screen.Articles.route) { backStackEntry ->
            val sourceId = backStackEntry.arguments?.getString(SOURCE_ID).orEmpty()
            screenState.value = ScreenState.ARTICLES
            ArticlesList(sourceId = sourceId)
        }
    }
}

@Composable
fun TopBar(navController: NavHostController, screenState: State<ScreenState>) {
    TopAppBar(
        title = {
            Text(text = stringResource(id = R.string.app_name))
        },
        navigationIcon = {
            if (screenState.value == ScreenState.ARTICLES) {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(R.string.navigate_back)
                    )
                }
            }
        }
    )
}

sealed class Screen(val route: String) {
    object Sources : Screen("sources")
    object Articles : Screen("{$SOURCE_ID}/articles") {
        fun createRoute(sourceId: String) = "$sourceId/articles"
    }
}

enum class ScreenState{
    SOURCES,
    ARTICLES
}

const val SOURCE_ID = "sourceId"