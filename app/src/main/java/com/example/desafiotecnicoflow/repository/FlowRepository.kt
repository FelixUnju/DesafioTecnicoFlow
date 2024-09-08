package com.example.desafiotecnicoflow.repository

import com.example.desafiotecnicoflow.data.EpisodeModel
import com.example.desafiotecnicoflow.service.FlowService
import retrofit2.Response
import javax.inject.Inject

class FlowRepository @Inject constructor(private val retrofitService: FlowService){
    suspend fun getAllInfo() = retrofitService.getAlInfo()
    suspend fun getAllInfoPaging(page:Int) = retrofitService.getAlInfoPaging(page)
    suspend fun getEpisodes(url:String): Response<EpisodeModel> = retrofitService.getEpisodes(url)
}