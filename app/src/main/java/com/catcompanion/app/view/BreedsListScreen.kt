package com.catcompanion.app.view

// Import necessary components from the Jetpack Compose library
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.catcompanion.app.viewmodel.BreedViewModel
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.paging.LoadState
import com.catcompanion.app.repository.BreedRepository
import androidx.paging.compose.collectAsLazyPagingItems
import com.catcompanion.app.R


// Declare a composable function that represents the UI for the BreedsListScreen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BreedsListScreen(navController: NavHostController) {
    // Create View model
    val breedViewModel = remember { BreedViewModel(BreedRepository()) }

    // Get paging data
    val pagingData = breedViewModel.breeds.collectAsLazyPagingItems()

    // Build
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(pagingData.itemCount) { breed ->
            Card(
                modifier = Modifier.padding(horizontal = 16.dp),
                onClick = {
                    // Use the navigation controller to navigate to the "breedDetailScreen/{breedId}" destination
                    navController.navigate("breedDetailScreen/${pagingData[breed]?.id}") {
                        // Configure the navigation behavior
                        launchSingleTop = true // Launch as a single top-level destination
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true // Save the state of the popped destinations
                        }
                    }
                }
            ) {
                // Inside the Card composable, define the content of the card
                Text(text = pagingData[breed]?.name.toString())
            }
        }
        pagingData.apply {
            when {
                loadState.refresh is LoadState.Loading || loadState.append is LoadState.Loading -> {
                    item {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }

                    }
                }

                loadState.refresh is LoadState.Error || loadState.append is LoadState.Error -> {
                    item {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = stringResource(id = R.string.infinite_scroll_error),)
                        }
                    }
                }

                loadState.refresh is LoadState.NotLoading -> {
                }
            }
        }
    }
}
