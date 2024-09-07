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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class FlowViewModel constructor(private val flowRepository: FlowRepository) : ViewModel() {

    private  val _infoCharacters = MutableLiveData<RickAndMortyInfoModel>()
    val infoCharacters: LiveData<RickAndMortyInfoModel> get() = _infoCharacters

    private val _episodeCharacters = MutableLiveData<List<EpisodeModel>>()
    val episodeCharacters: LiveData<List<EpisodeModel>> get() = _episodeCharacters

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> get() = _error

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    val listDate = Pager(
        config = PagingConfig(pageSize = 1),
        pagingSourceFactory = {InfoCharactersPaging(flowRepository)},
        initialKey = 1 )
        .flow.cachedIn(viewModelScope)


    private val infoRick = MutableLiveData<PagingData<RickAndMortyInfoModel>>()
    val _infoRick : LiveData<PagingData<RickAndMortyInfoModel>> get() = infoRick

    fun getEpisodesCharacter(endpoints: List<String>){
        CoroutineScope(Dispatchers.IO).launch {
            val result = mutableListOf<EpisodeModel>()
            endpoints.forEach { url ->
                try {
                    val dataResponse : Response<EpisodeModel> = flowRepository.getEpisodes(url)
                    if (dataResponse.isSuccessful) {
                        dataResponse.body()?.let {
                            result.add(it)
                        }
                    } else {
                        _error.postValue(true)
                    }
                }catch (e: Exception){
                    _error.postValue(true)
                }
            }
            _episodeCharacters.postValue(result)
        }
    }

    fun getInfo(){
        CoroutineScope(Dispatchers.IO).launch {
            val response = flowRepository.getAllInfo()
            if (response.isSuccessful) {
                _infoCharacters.postValue(response.body())
               _loading.postValue(false)
            } else {
                onError("Error : ${response.message()} ")
            }
        }
    }

    private fun onError(message: String) {
        _errorMessage.value = message
        _loading.postValue(false)
    }
}