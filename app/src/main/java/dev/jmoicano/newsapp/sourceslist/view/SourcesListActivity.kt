package dev.jmoicano.newsapp.sourceslist.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
    Text(text = sourceResponse.name)
}
