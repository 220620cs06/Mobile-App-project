package com.example.apitest

import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.apitest.ui.screens.UserEditScreen
import com.example.apitest.ui.screens.UserListScreen
import com.example.apitest.viewmodel.UserViewModel

@Composable
fun SimpleApiApp() {
    val navController = rememberNavController()
    val userViewModel: UserViewModel = viewModel()

    MaterialTheme {
        Surface {
            Scaffold(
                bottomBar = {
                    BottomAppBar(
                        modifier = Modifier.fillMaxWidth(),
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            // Home Button
                            IconButton(onClick = { navController.navigate("userList") }) {
                                Icon(
                                    imageVector = Icons.Default.Home,
                                    contentDescription = "Home",
                                    tint = MaterialTheme.colorScheme.onSurface
                                )
                            }

                            // Add Button
                            IconButton(onClick = { /* Placeholder for your action */ }) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Add",
                                    tint = MaterialTheme.colorScheme.onSurface
                                )
                            }

                            // Like Button
                            IconButton(onClick = { /* Placeholder for your action */ }) {
                                Icon(
                                    imageVector = Icons.Default.Favorite,
                                    contentDescription = "Like",
                                    tint = MaterialTheme.colorScheme.onSurface
                                )
                            }

                            // Account Button
                            IconButton(onClick = { /* Placeholder for your action */ }) {
                                Icon(
                                    imageVector = Icons.Default.AccountCircle,
                                    contentDescription = "Account",
                                    tint = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }
            ) { padding ->
                NavHost(
                    navController = navController,
                    startDestination = "userList",
                    modifier = Modifier.padding(padding)
                ) {
                    composable("userList") {
                        UserListScreen(
                            userViewModel = userViewModel,
                            onUserClick = { userId ->
                                navController.navigate("userEdit/$userId")
                            }
                        )
                    }
                    composable(
                        route = "userEdit/{userId}",
                        arguments = listOf(navArgument("userId") { type = androidx.navigation.NavType.IntType })
                    ) { backStackEntry ->
                        val userId = backStackEntry.arguments?.getInt("userId") ?: 0
                        UserEditScreen(
                            userId = userId,
                            userViewModel = userViewModel,
                            onSave = { navController.popBackStack() },
                            onCancel = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}