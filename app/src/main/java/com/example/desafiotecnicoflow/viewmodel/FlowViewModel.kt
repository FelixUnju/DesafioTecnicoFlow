package com.example.desafiotecnicoflow.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.desafiotecnicoflow.data.RickAndMortyInfoModel
import com.example.desafiotecnicoflow.repository.FlowRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FlowViewModel constructor(private val flowRepository: FlowRepository) : ViewModel() {

    private  val _infoCharacters = MutableLiveData<RickAndMortyInfoModel>()
    val infoCharacters: LiveData<RickAndMortyInfoModel> get() = _infoCharacters

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> get() = _error

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading


    fun getInfo(){
        CoroutineScope(Dispatchers.IO).launch {
            val response = flowRepository.getAllMovies()
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