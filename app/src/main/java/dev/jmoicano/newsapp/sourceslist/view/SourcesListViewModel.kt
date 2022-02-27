package dev.jmoicano.newsapp.sourceslist.view

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.jmoicano.newsapp.data.Result
import dev.jmoicano.newsapp.sourceslist.data.SourcesListRepository
import dev.jmoicano.newsapp.sourceslist.data.models.SourceResponse
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SourcesListViewModel @Inject constructor(private val sourcesListRepository: SourcesListRepository) :
    ViewModel() {

    private val mutableSourcesList = mutableStateOf<Result<List<SourceResponse>>>(Result.None)
    val sourcesList: State<Result<List<SourceResponse>>> = mutableSourcesList

    fun fetchSourcesList() {
        viewModelScope.launch {
            sourcesListRepository.fetchSourcesList().collect {
                mutableSourcesList.value = it
            }
        }
    }

}