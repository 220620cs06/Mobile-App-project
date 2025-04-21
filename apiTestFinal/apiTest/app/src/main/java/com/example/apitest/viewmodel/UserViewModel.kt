package com.example.apitest.viewmodel
import com.example.apitest.data.model.User
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.example.apitest.data.network.RetrofitInstance

class UserViewModel : ViewModel() {
    val users = mutableStateListOf<User>()
    val selectedUser = mutableStateOf<User?>(null)

    private val beautifulImages = listOf(
        "https://images.unsplash.com/photo-1589657296374-67e11cd50e8f?q=80&w=1974&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        "https://images.unsplash.com/photo-1741332966361-f242276f9430?q=80&w=1934&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        "https://images.unsplash.com/photo-1743658849022-f8874e7a106d?q=80&w=1935&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        "https://images.unsplash.com/photo-1722995690313-9ef561d30143?q=80&w=1984&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        "https://plus.unsplash.com/premium_photo-1740453877176-f2fb8d39cf38?q=80&w=1936&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        "https://images.unsplash.com/photo-1742268350534-d453e71222cf?q=80&w=1974&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        "https://images.unsplash.com/photo-1742925602178-0f5939ee6845?q=80&w=1974&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        "https://images.unsplash.com/photo-1743485753903-379f8aa68ad1?q=80&w=1974&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        "https://plus.unsplash.com/premium_photo-1674007584834-271ef37d2387?q=80&w=1935&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",        "https://images.unsplash.com/photo-1743160661846-e9c9b8b88cbe?q=80&w=1935&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
    )
    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getUsers()
                users.clear()
                users.addAll(response)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun fetchUserById(id: Int) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getUserByID(id) // Fixed to match ApiService
                selectedUser.value = response
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateUser(user: User) {
        viewModelScope.launch {
            try {
                val updatedUser = RetrofitInstance.api.updateUser(user.id, user)
                selectedUser.value = updatedUser
                val index = users.indexOfFirst { it.id == user.id }
                if (index != -1) {
                    users[index] = updatedUser
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun deleteUser(id: Int) {
        viewModelScope.launch {
            try {
                RetrofitInstance.api.deleteUser(id)
                users.removeAll { it.id == id }
                selectedUser.value = null
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getUserPhotoUrl(userId: Int): String {
        return beautifulImages[userId % beautifulImages.size]
    }

    fun getUserPhotoUrls(userId: Int, count: Int = 5): List<String> {
        val startIndex = userId % beautifulImages.size
        return (0 until count).map { i ->
            beautifulImages[(startIndex + i) % beautifulImages.size]
        }
    }
}