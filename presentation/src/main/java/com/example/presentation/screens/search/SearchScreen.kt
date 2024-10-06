package com.example.presentation.screens.search

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.presentation.R
import com.example.presentation.screens.search.utils.openRepoInBrowser
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    navigateToDownloadsScreen: () -> Unit
) {

    val state = viewModel.state.collectAsState()
    val uriHandler = LocalUriHandler.current
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                SearchEffect.NavigateToDownloadsScreen -> navigateToDownloadsScreen()
                SearchEffect.ShowErrorToast -> {
                    Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT).show()
                }
                is SearchEffect.OpenRepoInBrowser -> openRepoInBrowser(uriHandler, effect.repo)
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Button(
                onClick = {
                    viewModel.handleIntent(SearchIntent.OpenDownloadedReposScreen)
                },
                shape = AbsoluteRoundedCornerShape(4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 6.dp),
            ) {
                Text(text = stringResource(R.string.downloaded_repos))
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                TextField(
                    value = state.value.query,
                    onValueChange = {
                        viewModel.handleIntent(SearchIntent.UpdateSearchQuery(it))
                    },
                    maxLines = 1,
                    modifier = Modifier.weight(5F)
                )
                Button(
                    onClick = {
                        viewModel.handleIntent(SearchIntent.Search)
                    },
                    shape = AbsoluteRoundedCornerShape(4.dp),
                    modifier = Modifier
                        .weight(2F)
                        .height(48.dp)
                        .padding(start = 6.dp),
                ) {
                    if (state.value.isLoading) {
                        CircularProgressIndicator(
                            color = Color.Cyan
                        )
                    } else {
                        Text(text = stringResource(R.string.search))
                    }
                }
            }
            LazyColumn {
                items(
                    items = state.value.repos,
                    key = { it.id }
                ) { item ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(start = 8.dp)
                    ) {
                        Text(
                            text = "${item.ownerName} / ${item.name} - ${item.id}",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier
                                .weight(8F)
                                .padding(vertical = 6.dp)
                                .clickable {
                                    viewModel.handleIntent(SearchIntent.OpenRepoInBrowser(item))
                                }
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.ic_download),
                            contentDescription = null,
                            modifier = Modifier
                                .weight(2F)
                                .clickable {
                                    viewModel.handleIntent(SearchIntent.DownloadRepo(item.id))
                                }
                        )
                    }
                }
            }
        }

        if (state.value.isDownloading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }

    }
}