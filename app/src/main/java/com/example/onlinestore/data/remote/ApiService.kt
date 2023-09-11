package com.example.onlinestore.data.remote

import com.example.onlinestore.data.model.Product
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("products/categories")
    fun fetchCategories(): Call<List<String>>

    @GET("products/category/{category}")
    fun fetchProductsByCategory(
        @Path("category") category: String,
        @Query("sort") sort: String? = null
    ): Call<List<Product>>

    @GET("products/{productId}")
    fun fetchProductDetails(@Path("productId") productId: Int): Call<Product>
}