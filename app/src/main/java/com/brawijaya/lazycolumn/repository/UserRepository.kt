package com.brawijaya.lazycolumn.repository

import com.brawijaya.lazycolumn.model.User
import com.brawijaya.lazycolumn.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(private val apiService: ApiService) {

    suspend fun getUsers(): Result<List<User>> {
        return try {
            withContext(Dispatchers.IO) {
                val users = apiService.getUsers()
                Result.success(users)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}