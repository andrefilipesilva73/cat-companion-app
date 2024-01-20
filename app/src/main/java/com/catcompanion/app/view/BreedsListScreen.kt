package com.catcompanion.app.view

// Import necessary components from the Jetpack Compose library
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.BrokenImage
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.catcompanion.app.R
import com.catcompanion.app.model.Breed
import com.catcompanion.app.repository.BreedRepository
import com.catcompanion.app.viewmodel.BreedViewModel
import androidx.compose.foundation.lazy.items

private fun navigateToBreedDetail(navController: NavHostController, breed: Breed) {
    // Use the navigation controller to navigate to the "breedDetailScreen/{breedId}" destination
    navController.navigate("breedDetailScreen/${breed.id}") {
        // Configure the navigation behavior
        launchSingleTop = true // Launch as a single top-level destination
        popUpTo(navController.graph.startDestinationId) {
            saveState = true // Save the state of the popped destinations
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BreedCard(navController: NavHostController, breed: Breed) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .heightIn(0.dp, 256.dp),
        onClick = {
            navigateToBreedDetail(navController, breed)
        }
    ) {
        // Inside the Card composable, define the content of the card
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (breed.imageUrl != "") {
                AsyncImage(
                    model = breed.imageUrl,
                    contentDescription = breed.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(100.dp)
                        .height(100.dp)
                        .clip(RoundedCornerShape(100.dp)),
                )
            } else {
                Column(
                    modifier = Modifier
                        .width(100.dp)
                        .height(100.dp)
                        .clip(RoundedCornerShape(100.dp))
                        .border(
                            BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                            CircleShape
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(Icons.Outlined.BrokenImage, contentDescription = null)
                }
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(12.dp)
            ) {
                Text(text = breed.name, fontWeight = FontWeight.Bold)
                Text(text = breed.temperament)
            }
            OutlinedIconButton(
                onClick = { /* do something */ },
                modifier = Modifier.size(50.dp), // avoid the oval shape
                shape = CircleShape,
                border = BorderStroke(0.dp, MaterialTheme.colorScheme.surface),
                colors = IconButtonDefaults.iconButtonColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Icon(Icons.Outlined.StarBorder, contentDescription = "Favorite")
            }
        }
    }
}

@Composable
fun SearchLine(navController: NavHostController, breed: Breed) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .clickable(onClick = {
                navigateToBreedDetail(navController, breed)
            }),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (breed.imageUrl != "") {
            AsyncImage(
                model = breed.imageUrl,
                contentDescription = breed.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(32.dp)
                    .height(32.dp)
                    .clip(RoundedCornerShape(32.dp)),
            )
        } else {
            Column(
                modifier = Modifier
                    .width(32.dp)
                    .height(32.dp)
                    .clip(RoundedCornerShape(32.dp))
                    .border(
                        BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                        CircleShape
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(Icons.Outlined.BrokenImage, contentDescription = null)
            }
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(12.dp)
        ) {
            Text(text = breed.name)
        }
    }
}

// Declare a composable function that represents the UI for the BreedsListScreen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BreedsListScreen(navController: NavHostController) {
    // Create View model
    val breedViewModel = remember { BreedViewModel(BreedRepository()) }

    // Collecting states from ViewModel
    val pagingData = breedViewModel.breeds.collectAsLazyPagingItems()
    val searchText by breedViewModel.searchText.collectAsState()
    val isSearching by breedViewModel.isSearching.collectAsState()
    val searchResultsList by breedViewModel.searchResultsList.collectAsState()

    // Build
    Scaffold(
        topBar = {
            // SearchBar goes here
            SearchBar(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 0.dp)
                    .padding(bottom = 16.dp)
                    .fillMaxWidth(),
                query = searchText,
                onQueryChange = breedViewModel::onQueryChange,
                onSearch = breedViewModel::onSearch,
                active = isSearching,
                onActiveChange = { breedViewModel.onToogleSearch() },
                leadingIcon = {
                    Icon(Icons.Filled.Search, contentDescription = stringResource(id = R.string.search_breeds_by_name),)
                },
                trailingIcon = {
                    if (isSearching || searchText.isNotEmpty()) {
                        Icon(
                            imageVector = Icons.Filled.Clear,
                            contentDescription = null,
                            modifier = Modifier
                                .padding(4.dp)
                                .clickable {
                                    breedViewModel.onToogleSearch()
                                }
                        )
                    }
                },
                placeholder = {
                    Text(text = stringResource(id = R.string.search_breeds_by_name))
                }
            ) {
                LazyColumn {
                    items(searchResultsList) { breed ->
                        SearchLine(navController, breed)
                    }
                }
            }
        },
    ) { innerPadding ->
        Surface(modifier = Modifier.fillMaxSize(), // Set the size to fill the maximum available space
            color = MaterialTheme.colorScheme.surfaceColorAtElevation(NavigationBarDefaults.Elevation) // Set the background color from the MaterialTheme
        ) {
            // Eval if search is active
            if (searchText.isEmpty()){
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(pagingData.itemCount) { index ->
                        BreedCard(navController = navController, pagingData[index] as Breed)
                    }
                    pagingData.apply {
                        when {
                            loadState.refresh is LoadState.Loading || loadState.append is LoadState.Loading -> {
                                item {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(top = 16.dp),
                                        contentAlignment = Alignment.Center,
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
                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.Center
                                        ) {
                                            Text(text = stringResource(id = R.string.infinite_scroll_error))
                                            // Retry button
                                            Button(
                                                onClick = { breedViewModel.retry() },
                                                modifier = Modifier.padding(top = 16.dp)
                                            )
                                            {
                                                Text(text = stringResource(id = R.string.infinite_scroll_try_again))
                                            }
                                        }
                                    }
                                }
                            }

                            loadState.refresh is LoadState.NotLoading -> {
                            }
                        }
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(searchResultsList) { breed ->
                        BreedCard(navController = navController, breed)
                    }
                }
            }

        }
    }
}
