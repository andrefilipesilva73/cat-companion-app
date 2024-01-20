package com.catcompanion.app.view

// Import necessary components from the Jetpack Compose library
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(pagingData.itemCount) { index ->
            val breed = pagingData[index] as Breed

            Card(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .heightIn(0.dp, 256.dp),
                onClick = {
                    // Use the navigation controller to navigate to the "breedDetailScreen/{breedId}" destination
                    navController.navigate("breedDetailScreen/${breed.id}") {
                        // Configure the navigation behavior
                        launchSingleTop = true // Launch as a single top-level destination
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true // Save the state of the popped destinations
                        }
                    }
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
                    AsyncImage(
                        model = breed.imageUrl,
                        contentDescription = breed.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .width(100.dp)
                            .height(100.dp)
                            .clip(RoundedCornerShape(100.dp)),
                    )
                    Column (modifier = Modifier
                        .weight(1f)
                        .padding(12.dp)) {
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
                           Column (
                               horizontalAlignment = Alignment.CenterHorizontally,
                               verticalArrangement = Arrangement.Center
                           ) {
                               Text(text = stringResource(id = R.string.infinite_scroll_error))
                               // Retry button
                               Button(
                                   onClick = {  breedViewModel.retry() },
                                   modifier = Modifier.padding(top = 16.dp) )
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
}
