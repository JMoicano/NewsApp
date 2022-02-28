package dev.jmoicano.newsapp.sources.view

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.jmoicano.newsapp.data.Result
import dev.jmoicano.newsapp.sources.data.SourcesRepository
import dev.jmoicano.newsapp.sources.data.models.SourceResponse
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SourcesViewModel @Inject constructor(private val sourcesRepository: SourcesRepository) :
    ViewModel() {

    private val mutableSourcesList = mutableStateOf<Result<List<SourceResponse>>>(Result.None)
    val sourcesList: State<Result<List<SourceResponse>>> = mutableSourcesList

    fun fetchSourcesList() {
        viewModelScope.launch {
            sourcesRepository.fetchSourcesList().collect {
                mutableSourcesList.value = it
            }
        }
    }

}