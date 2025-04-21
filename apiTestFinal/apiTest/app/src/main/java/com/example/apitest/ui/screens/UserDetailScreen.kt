package com.example.apitest.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.apitest.data.model.User
import com.example.apitest.viewmodel.UserViewModel

@Composable
fun UserDetailsScreen(
    userId: Int,
    userViewModel: UserViewModel = viewModel(),
    onBackClick: () -> Unit
) {
    val user by userViewModel.selectedUser

    // Fetch user data
    userViewModel.fetchUserById(userId)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("User Details") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = androidx.compose.material.icons.Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            user?.let { userData ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = "ID: ${userData.id}")
                    Text(text = "Name: ${userData.name}")
                    Text(text = "Email: ${userData.email}")
                }
            } ?: CircularProgressIndicator()
        }
    }
}