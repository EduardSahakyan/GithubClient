package com.example.presentation.screens.downloads

import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.presentation.R
import kotlinx.coroutines.flow.collectLatest

@Composable
fun DownloadsScreen(
    viewModel: DownloadsViewModel = hiltViewModel()
) {

    val state = viewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.handleIntent(DownloadsIntent.GetDownloadedRepos)
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                DownloadsEffect.ShowErrorToast -> {
                    Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    LazyColumn {
        items(
            items = state.value.downloadedRepos,
            key = { it.id }
        ) { item ->
            Text(
                text = "${item.ownerName} / ${item.name} - ${item.id}",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(vertical = 6.dp, horizontal = 8.dp)
            )
        }
    }

}