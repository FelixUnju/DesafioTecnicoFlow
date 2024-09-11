package com.example.desafiotecnicoflow.di

import com.example.desafiotecnicoflow.service.FlowService
import com.example.desafiotecnicoflow.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
@Module
@InstallIn(SingletonComponent::class)
object AppModules {
    @Provides
    fun providesRetrofitInterface(): FlowService{
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(FlowService::class.java)
    }
}