package com.example.onlinestore.data.base

import retrofit2.Call

open class BaseRepository {

    protected suspend fun <T : Any> safeApiCall(call: () -> Call<T>): Result<T> {
        return try {
            val response = call().execute()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.Success(body)
                } else {
                    Result.Error("Response body is null")
                }
            } else {
                Result.Error("Error response: ${response.code()} ${response.message()}")
            }
        } catch (e: Exception) {
            Result.Error("Exception: ${e.message}")
        }
    }

    sealed class Result<out T> {
        data class Success<out T>(val data: T) : Result<T>()
        data class Error(val message: String) : Result<Nothing>()
    }
}
