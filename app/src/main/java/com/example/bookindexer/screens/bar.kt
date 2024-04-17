package com.example.bookindexer.screens

import android.annotation.SuppressLint
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import com.example.bookindexer.BottomNavigationItem
import com.example.bookindexer.Model.FavouriteViewModel
import com.example.bookindexer.Model.HomeViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Bar(navController: NavController, viewModel: HomeViewModel, favouriteViewModel: FavouriteViewModel) {
    val items = listOf(
        BottomNavigationItem(
            title = "Home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            ),
        BottomNavigationItem(
            title = "Favourites",
            selectedIcon = Icons.Filled.Star,
            unselectedIcon = Icons.Outlined.Star,

            ),
        BottomNavigationItem(
            title = "Settings",
            selectedIcon = Icons.Filled.Settings,
            unselectedIcon = Icons.Outlined.Settings,

            ),
    )
    var selectedItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }


    NavigationBar {

        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItemIndex == index,
                onClick = {
                    if(item.title == "Home"){viewModel.loadBooks()}
                    if(item.title == "Favourites"){favouriteViewModel.loadFavourite()}

                    selectedItemIndex = index
                    navController.navigate(item.title)
                },
                label = {
                    Text(text = item.title)
                },
                alwaysShowLabel = true,
                icon = {

                    Icon(
                        imageVector = if (index == selectedItemIndex) {
                            item.selectedIcon
                        } else item.unselectedIcon,
                        contentDescription = item.title
                    )

                }
            )
        }
    }
}