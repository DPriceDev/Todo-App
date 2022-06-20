package dev.dprice.productivity.todo.main.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import dev.dprice.productivity.todo.main.model.Constants
import dev.dprice.productivity.todo.ui.theme.MediumBlue

@Composable
fun BottomNavigationBar(
    navController: NavController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    if (navBackStackEntry?.destination?.route != "Auth") {
        Column {
            BottomNavigation(
                backgroundColor = MediumBlue,
                contentColor = Color.White
            ) {
                val currentRoute = navBackStackEntry?.destination?.route

                Constants.BottomNavItems.forEach { navItem ->
                    BottomNavigationItem(
                        selected = currentRoute == navItem.route,
                        onClick = { navController.navigate(navItem.route) },
                        icon = {
                            Icon(imageVector = navItem.icon, contentDescription = navItem.label)
                        },
                        label = {
                            Text(text = navItem.label)
                        },
                        alwaysShowLabel = false
                    )
                }
            }
        }
    }
}