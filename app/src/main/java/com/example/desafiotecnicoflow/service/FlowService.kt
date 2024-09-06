package com.example.desafiotecnicoflow.service

import com.example.desafiotecnicoflow.data.RickAndMortyInfoModel
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface FlowService {

    @GET("character")
    suspend fun getAlInfo() : Response<RickAndMortyInfoModel>


    companion object {
        var retrofitService: FlowService? = null
        fun getInstance() : FlowService {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://rickandmortyapi.com/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(FlowService::class.java)
            }
            return retrofitService!!
        }

    }
}