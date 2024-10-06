package com.example.presentation.screens.search

import androidx.lifecycle.viewModelScope
import com.example.domain.Resource
import com.example.domain.models.repo.RepoModel
import com.example.domain.models.repo.params.DownloadRepoParamModel
import com.example.domain.models.repo.params.SearchRepoParamModel
import com.example.domain.usecases.DownloadRepoUseCase
import com.example.domain.usecases.GetUserReposUseCase
import com.example.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getUserReposUseCase: GetUserReposUseCase,
    private val downloadRepoUseCase: DownloadRepoUseCase
) : BaseViewModel<SearchIntent, SearchState, SearchEffect>(
    initialState = SearchState()
) {

    override fun handleIntent(intent: SearchIntent) {
        when (intent) {
            SearchIntent.Search -> search()
            is SearchIntent.UpdateSearchQuery -> updateState { it.copy(query = intent.query) }
            is SearchIntent.DownloadRepo -> downloadRepo(intent.id)
            is SearchIntent.OpenDownloadedReposScreen -> openDownloadedReposScreen()
            is SearchIntent.OpenRepoInBrowser -> openRepoInBrowser(intent.repo)
        }
    }

    private fun search() {
        if (currentState.isDownloading) return
        if (currentState.isLoading) return
        if (currentState.query.isBlank()) return
        val searchParamModel = SearchRepoParamModel(currentState.query)
        getUserReposUseCase(searchParamModel)
            .flowOn(Dispatchers.IO)
            .onEach { resource ->
                when (resource) {
                    Resource.Loading -> updateState { it.copy(isLoading = true) }
                    is Resource.Error -> sendEffect(SearchEffect.ShowErrorToast)
                    is Resource.Success -> updateState { it.copy(repos = resource.model) }
                }
            }
            .onCompletion { updateState { it.copy(isLoading = false) } }
            .flowOn(Dispatchers.Main)
            .launchIn(viewModelScope)

    }

    private fun downloadRepo(id: Long) {
        if (currentState.isDownloading) return
        val repo = currentState.repos.find { it.id == id } ?: return
        val model = DownloadRepoParamModel(repo.ownerName, repo.name, repo.id)
        downloadRepoUseCase(model)
            .flowOn(Dispatchers.IO)
            .onEach { resource ->
                when (resource) {
                    Resource.Loading -> updateState { it.copy(isDownloading = true) }
                    is Resource.Error -> sendEffect(SearchEffect.ShowErrorToast)
                    is Resource.Success -> Unit
                }
            }
            .onCompletion { updateState { it.copy(isDownloading = false) } }
            .flowOn(Dispatchers.Main)
            .launchIn(viewModelScope)
    }

    private fun openDownloadedReposScreen() {
        if (currentState.isDownloading) return
        viewModelScope.launch {
            sendEffect(SearchEffect.NavigateToDownloadsScreen)
        }
    }

    private fun openRepoInBrowser(repo: RepoModel) {
        viewModelScope.launch {
            sendEffect(SearchEffect.OpenRepoInBrowser(repo))
        }
    }

}