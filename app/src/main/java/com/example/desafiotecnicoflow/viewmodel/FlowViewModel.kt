package com.example.desafiotecnicoflow.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.desafiotecnicoflow.data.Character
import com.example.desafiotecnicoflow.data.EpisodeModel
import com.example.desafiotecnicoflow.data.RickAndMortyInfoModel
import com.example.desafiotecnicoflow.repository.FlowRepository
import com.example.desafiotecnicoflow.ui.adapter.InfoCharactersPaging
import com.example.desafiotecnicoflow.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Request
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class FlowViewModel @Inject constructor(private val flowRepository: FlowRepository) : ViewModel() {

    private val _episodeResult = MutableLiveData<NetworkResult<List<EpisodeModel>>> ()
    val episodeResult: LiveData<NetworkResult<List<EpisodeModel>>> get() = _episodeResult

    val listDate = Pager(
                        config = PagingConfig(pageSize = 20),
                        pagingSourceFactory = {InfoCharactersPaging(flowRepository)},
                        initialKey = 1 )
                        .flow.cachedIn(viewModelScope)

    suspend fun getEpisodesResult (endpoints: List<String>){
        _episodeResult.postValue(NetworkResult.Loading())
        CoroutineScope(Dispatchers.IO).launch {
            val result = mutableListOf<EpisodeModel>()
            endpoints.forEach { url ->
                try {
                    val dataResponse : Response<EpisodeModel> = flowRepository.getEpisodes(url)
                    if (dataResponse.isSuccessful && dataResponse.body() != null) {
                        dataResponse.body()?.let {
                            result.add(it)
                        }
                    } else if (dataResponse.errorBody() != null){
                        _episodeResult.postValue(NetworkResult.Error("Error en la peticion"))
                    }else{
                        _episodeResult.postValue(NetworkResult.Error("Error el servidor"))
                    }
                }catch (e: Exception){
                    _episodeResult.postValue(NetworkResult.Error("Error el servidor"))
                }
            }
            _episodeResult.postValue(NetworkResult.Success(result))
        }
    }
}