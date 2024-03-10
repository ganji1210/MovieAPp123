package com.example.movieappmad24

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.movieappmad24.ui.theme.MovieAppMAD24Theme
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState

data class Movie(
    val title: String,
    val imageUrl: String,
    val Director: String,
    val Genre: String
    // Include other properties here, such as director, year, etc.
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieApp()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieApp() {
    val movies = listOf(
        Movie(
            title = "300",
            imageUrl = "https://bilder.fernsehserien.de/epg/4438/pu4_190623_0010_768852e9_300_b-w-450.jpg.jpg",
            Director = "Zack Snyder",
            Genre = "Action, Drama, Fantasy"
        ),
        Movie(
            title = "Avengers",
            imageUrl = "https://de.web.img3.acsta.net/r_654_368/newsv7/22/10/13/13/49/3252041.jpg",
            Director = "Joss Wheldon",
            Genre = "Science Fiction"
        ),
        // Add more movies here...
    )

    val scrollState = rememberScrollState()

    MovieAppMAD24Theme {
        Scaffold(
            topBar = { MovieTopAppBar() },
            bottomBar = { MovieBottomAppBar() }
        ) { innerPadding ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                color = MaterialTheme.colorScheme.background
            ) {
                Column(modifier = Modifier.verticalScroll(scrollState)) {
                    movies.forEach { movie ->
                        ExpandableMovieCard(movie = movie)
                    }
                }
            }
        }
    }
}




@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MovieTopAppBar() {
    CenterAlignedTopAppBar(
        title = { Text(text = "Movie App") }
    )
}

@Composable
fun MovieBottomAppBar() {
    val selectedTab = remember { mutableStateOf("home") }

    NavigationBar {
        NavigationBarItem(
            icon = { Icon(imageVector = Icons.Filled.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = selectedTab.value == "home",
            onClick = { selectedTab.value = "home" }
        )
        NavigationBarItem(
            icon = { Icon(imageVector = Icons.Filled.Star, contentDescription = "Watchlist") },
            label = { Text("Watchlist") },
            selected = selectedTab.value == "watchlist",
            onClick = { selectedTab.value = "watchlist" }
        )
    }
}

@Composable
fun ExpandableMovieCard(movie: Movie) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .animateContentSize()
    ) {
        Column {
            MovieImage(imageUrl = movie.imageUrl)
            Text(text = movie.title, style = MaterialTheme.typography.headlineMedium)
            IconButton(onClick = { expanded = !expanded }) {
                Icon(
                    imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                    contentDescription = if (expanded) "Less" else "More"
                )
            }
            AnimatedVisibility(visible = expanded) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Director: ${movie.Director}", style = MaterialTheme.typography.bodyLarge)
                    Text("Genre: ${movie.Genre}", style = MaterialTheme.typography.bodyLarge)
                    // Add more details here
                }
            }
        }
    }
}



@Composable
fun MovieImage(imageUrl: String) {
    AsyncImage(
        model = imageUrl,
        contentDescription = "Movie Poster",
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    )
}
