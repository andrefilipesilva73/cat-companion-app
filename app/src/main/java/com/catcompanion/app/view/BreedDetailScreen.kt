package com.catcompanion.app.view

// Import necessary components from the Jetpack Compose library
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.BrokenImage
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.catcompanion.app.repository.BreedRepository
import com.catcompanion.app.viewmodel.BreedDetailViewModel

// Declare a composable function that represents the UI for the BreedDetailScreen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BreedDetailScreen(navController: NavHostController, breedId: String) {
    // Create View model
    val breedDetailViewModel = remember { BreedDetailViewModel(BreedRepository()) }

    // Observe individualBreed StateFlow
    val breed by breedDetailViewModel.selectedBreed.collectAsState()
    val isLoading by breedDetailViewModel.isLoadingSelectedBreed.collectAsState()

    // Fetch individual breed by ID when the composable is first launched
    LaunchedEffect(breedId) {
        breedDetailViewModel.fetchBreedById(breedId)
    }

    // Define Scroll behavior
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    // Content goes here
    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            // TopAppBar goes here
            MediumTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(
                        NavigationBarDefaults.Elevation),
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    if (!isLoading && breed != null){
                        Text(
                            breed!!.name,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                },
                actions = {
                    if (!isLoading && breed != null) {
                        IconButton(onClick = { /* TODO */ }) {
                            Icon(
                                imageVector = Icons.Outlined.StarOutline,
                                contentDescription = null
                            )
                        }
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
    ) { innerPadding ->
        Surface(modifier = Modifier.fillMaxSize(), // Set the size to fill the maximum available space
            color = MaterialTheme.colorScheme.surfaceColorAtElevation(NavigationBarDefaults.Elevation) // Set the background color from the MaterialTheme
        ) {
            // Check loading
            if (isLoading) {
                // Is loading
                Box(
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            } else if (breed == null) {
                // Error
                // TODO
            } else {
                // List details
                Column (modifier = Modifier.padding(innerPadding)) {
                    if (breed!!.imageUrl != "") {
                        AsyncImage(
                            model = breed!!.imageUrl,
                            contentDescription = breed!!.name,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(4/3f)
                        )
                    } else {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(4/3f)
                                .background(Color.LightGray),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(Icons.Outlined.BrokenImage, contentDescription = null)
                        }
                    }
                }
            }

        }
    }
}
