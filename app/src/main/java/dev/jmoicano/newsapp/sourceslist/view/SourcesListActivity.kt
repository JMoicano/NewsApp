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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import dagger.hilt.android.AndroidEntryPoint
import dev.jmoicano.newsapp.data.Result
import dev.jmoicano.newsapp.sourceslist.data.models.SourceResponse
import dev.jmoicano.newsapp.ui.theme.NewsAppTheme

@AndroidEntryPoint
class SourcesListActivity : ComponentActivity() {

    private val viewModel by viewModels<SourcesListViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    SourcesList(result = viewModel.sourcesList.value)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchSourcesList()
    }
}

@Composable
fun SourcesList(result: Result<List<SourceResponse>>) {
    when (result) {
        is Result.Success -> {
            LazyColumn(content = {
                result.response.forEach { response ->
                    item {
                        SourceView(sourceResponse = response)
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

@Composable
fun SourceView(sourceResponse: SourceResponse) {
    val context = LocalContext.current
    Card(
        modifier = Modifier.padding(10.dp),
        border = BorderStroke(1.dp, MaterialTheme.colors.onSurface)
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
