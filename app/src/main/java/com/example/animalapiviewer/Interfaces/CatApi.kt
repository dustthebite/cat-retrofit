package com.example.animalapiviewer.Interfaces
import com.example.animalapiviewer.DataClasses.Cat
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface CatApi {

    @GET("images/search")
    suspend fun getCatImage() : Response<List<Cat>>

    @GET("images/{id}")
    suspend fun getCatImageById(@Path("id") id : String) : Response<Cat>

}