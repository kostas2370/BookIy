package com.example.bookindexer

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bookindexer.Model.FavouriteViewModel
import com.example.bookindexer.Model.HomeViewModel
import com.example.bookindexer.api.RetrofitInstance
import com.example.bookindexer.data.FavouriteRepositoryImpl
import com.example.bookindexer.data.HomeRepositoryImpl
import com.example.bookindexer.data.SessionManager
import com.example.bookindexer.screens.Bar
import com.example.bookindexer.screens.FavouritesScreen
import com.example.bookindexer.screens.HomeScreen
import com.example.bookindexer.screens.SearchScreen
import com.example.bookindexer.screens.SettingsScreen


data class BottomNavigationItem(
    val title : String,
    val selectedIcon : ImageVector,
    val unselectedIcon : ImageVector

)


@Suppress("UNCHECKED_CAST")
@OptIn(ExperimentalMaterial3Api::class)
class HomeActivity : ComponentActivity() {

    private lateinit var sessionManager: SessionManager


    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sessionManager = SessionManager(this)
        val token = sessionManager.fetchAuthToken().toString()

         val viewModel by viewModels<HomeViewModel>(factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return HomeViewModel(HomeRepositoryImpl(RetrofitInstance.api, "$token"))
                            as T
                }
            }
        })

        val favouriteViewModel by viewModels<FavouriteViewModel>(factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return FavouriteViewModel(FavouriteRepositoryImpl(
                        RetrofitInstance.api,
                        "$token"
                    ))
                            as T
                }
            }
        })



        val sessionManager = SessionManager(this)



        setContent {
            val navController = rememberNavController()
            val context = LocalContext.current.applicationContext




                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {


                    Scaffold(
                        content= {

                            NavHost(navController = navController, startDestination = "Home") {

                                composable("Home") { HomeScreen( viewModel,  ) }
                                composable("Search") { SearchScreen() }
                                composable("Favourites") { FavouritesScreen(navController, favouriteViewModel.books.collectAsState().value.books) }
                                composable("Settings") { SettingsScreen(navController = navController, token = token) }

                            }
                        },
                        topBar = {
                            TopAppBar(
                                colors = TopAppBarDefaults.topAppBarColors(
                                    containerColor = Color.Black,
                                    titleContentColor = Color.White,
                                ),

                                title = {
                                    Text("Bookly", fontWeight = FontWeight.Bold ,fontSize = 22.sp, textAlign = TextAlign.Center)

                                },
                                navigationIcon = {
                                    IconButton(onClick = { Toast.makeText(context, "Bookly", Toast.LENGTH_LONG).show() }) {
                                        Icon(
                                            painter =   painterResource(id = R.drawable.booklylogo),
                                            contentDescription = "Image",
                                            tint = Color.Unspecified,
                                            modifier = Modifier.size(77.dp)

                                        )

                                    }

                                },
                                actions = {
                                    IconButton(onClick = {
                                        sessionManager.saveAuthToken("")

                                        this@HomeActivity.finish()

                                    }) {
                                        Icon(Icons.Filled.ExitToApp, contentDescription = null, tint = Color.White)
                                    }
                                }

                            )

                        },
                        bottomBar = {Bar(navController, viewModel, favouriteViewModel)}
                    )
                }
            }

}}






