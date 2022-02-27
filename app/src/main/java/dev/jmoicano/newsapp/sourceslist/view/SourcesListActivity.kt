package dev.jmoicano.newsapp.sourceslist.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dagger.hilt.android.AndroidEntryPoint
import dev.jmoicano.newsapp.R
import dev.jmoicano.newsapp.data.Result
import dev.jmoicano.newsapp.sourceslist.data.models.SourceResponse
import dev.jmoicano.newsapp.ui.theme.NewsAppTheme


@ExperimentalMaterialApi
@AndroidEntryPoint
class SourcesListActivity : ComponentActivity() {

    private val viewModel by viewModels<SourcesListViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsAppTheme {
                // A surface container using the 'background' color from the theme
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar {
                            Text(text = stringResource(id = R.string.app_name))
                        }
                    }
                ) {
                    SourcesList(result = viewModel.sourcesList.value)
                    {
                        TODO("Add navigation")
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchSourcesList()
    }
}

@ExperimentalMaterialApi
@Composable
fun SourcesList(
    result: Result<List<SourceResponse>>,
    onSourceClicked: (source: SourceResponse) -> Unit
) {
    when (result) {
        is Result.Success -> {
            LazyColumn(content = {
                result.response.forEach { response ->
                    item {
                        SourceView(sourceResponse = response, onSourceClicked = onSourceClicked)
                    }
                }
            })
        }
        is Result.Error -> {

        }
        Result.Loading -> {

        }
        Result.None -> {

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
