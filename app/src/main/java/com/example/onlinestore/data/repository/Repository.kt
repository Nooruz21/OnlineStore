package com.example.onlinestore.data.repository

import com.example.onlinestore.data.base.BaseRepository
import com.example.onlinestore.data.local.ProductDao
import com.example.onlinestore.data.model.Product
import com.example.onlinestore.data.remote.ApiService
import kotlinx.coroutines.flow.Flow

class Repository(private val apiService: ApiService, private val productDao: ProductDao) :
    BaseRepository() {

    suspend fun getCategories(): List<String> {
        val result = safeApiCall { apiService.fetchCategories() }
        return when (result) {
            is Result.Success -> result.data
            is Result.Error -> emptyList()
        }
    }

    suspend fun getProductsByCategory(category: String, sortBy: String): List<Product> {
        val result = safeApiCall { apiService.fetchProductsByCategory(category, sortBy) }
        return when (result) {
            is Result.Success -> result.data
            is Result.Error -> emptyList()

        }
    }

    suspend fun getProductById(productId: Int): Product? {
        val result = safeApiCall { apiService.fetchProductById(productId) }
        return when (result) {
            is Result.Success -> result.data
            is Result.Error -> null
        }
    }

    fun getAllProducts(): Flow<List<Product>> {
        return productDao.getAllProducts()
    }

    suspend fun saveProductsLocally(products: List<Product>) {
        productDao.insertProducts(products)
    }
}
