package dev.jmoicano.newsapp.sources.view

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import dev.jmoicano.newsapp.R
import dev.jmoicano.newsapp.Screen
import dev.jmoicano.newsapp.data.Result
import dev.jmoicano.newsapp.sources.data.models.SourceResponse

@ExperimentalMaterialApi
@Composable
fun SourcesList(
    navController: NavHostController
) {
    val sourcesViewModel = hiltViewModel<SourcesViewModel>()

    val result = sourcesViewModel.sourcesList.value
    when (result) {
        is Result.Success -> {
            LazyColumn(content = {
                result.response.forEach { response ->
                    item {
                        SourceView(sourceResponse = response) {
                            navController.navigate(Screen.Articles.createRoute(response.id))
                        }
                    }
                }
            })
        }
        is Result.Error -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = stringResource(id = R.string.source_list_error_message))
                TextButton(onClick = { sourcesViewModel.fetchSourcesList() }) {
                    Text(text = stringResource(id = R.string.source_list_retry))
                }
            }
        }
        Result.Loading -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(alignment = Alignment.Center))
            }
        }
        Result.None -> {
            sourcesViewModel.fetchSourcesList()
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun SourceView(
    sourceResponse: SourceResponse,
    onSourceClicked: (source: SourceResponse) -> Unit
) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        border = BorderStroke(1.dp, MaterialTheme.colors.onSurface),
        onClick = {
            onSourceClicked(sourceResponse)
        }
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(text = sourceResponse.name, style = MaterialTheme.typography.h4)
            Text(text = sourceResponse.description, style = MaterialTheme.typography.body2)
            TextButton(onClick = {
                val intent =
                    Intent(Intent.ACTION_VIEW).apply { data = Uri.parse(sourceResponse.url) }
                context.startActivity(intent)
            }) {
                Text(text = sourceResponse.url, style = MaterialTheme.typography.caption)
            }
        }
    }
}
