package com.example.presentation.screens.downloads

import androidx.lifecycle.viewModelScope
import com.example.domain.Resource
import com.example.domain.usecases.GetDownloadedReposUseCase
import com.example.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DownloadsViewModel @Inject constructor(
    private val getDownloadedReposUseCase: GetDownloadedReposUseCase
): BaseViewModel<DownloadsIntent, DownloadsState, DownloadsEffect>(
    initialState = DownloadsState()
) {

    override fun handleIntent(intent: DownloadsIntent) {
        when (intent) {
            DownloadsIntent.GetDownloadedRepos -> getDownloadedRepos()
        }
    }

    private fun getDownloadedRepos() {
        getDownloadedReposUseCase()
            .flowOn(Dispatchers.IO)
            .onEach { resource ->
                when (resource) {
                    Resource.Loading -> updateState { it.copy(isLoading = true) }
                    is Resource.Error -> sendEffect(DownloadsEffect.ShowErrorToast)
                    is Resource.Success -> updateState { it.copy(downloadedRepos = resource.model) }
                }
            }
            .onCompletion { updateState { it.copy(isLoading = false) } }
            .flowOn(Dispatchers.Main)
            .launchIn(viewModelScope)
    }

}