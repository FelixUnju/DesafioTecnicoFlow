package com.example.desafiotecnicoflow.repository

import com.example.desafiotecnicoflow.service.FlowService

class FlowRepository constructor(private val retrofitService: FlowService){

    suspend fun getAllMovies() = retrofitService.getAlInfo()


}