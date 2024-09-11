package com.example.desafiotecnicoflow.repository

import com.example.desafiotecnicoflow.data.Character
import com.example.desafiotecnicoflow.data.EpisodeModel
import com.example.desafiotecnicoflow.data.InfoModel
import com.example.desafiotecnicoflow.data.Location
import com.example.desafiotecnicoflow.data.Origin
import com.example.desafiotecnicoflow.data.RickAndMortyInfoModel
import com.example.desafiotecnicoflow.service.FlowService
import com.example.desafiotecnicoflow.utils.Constants.BASE_URL
import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.HttpURLConnection

class FlowRepositoryTest{

    private lateinit var repository: FlowRepository
    private lateinit var testApis: FlowService
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        testApis = Retrofit.Builder()
            .baseUrl(mockWebServer.url(BASE_URL).toString())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(FlowService::class.java)
        repository = FlowRepository(testApis)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `for one character, api must return the episode with http code 200`() = runTest {
        val users = EpisodeModel("Rest and Ricklaxation",
            "August 27, 2017",
            "S03E06")
        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(Gson().toJson(users))
        mockWebServer.enqueue(expectedResponse)

        val actualResponse = repository.getEpisodes("https://rickandmortyapi.com/api/episode/27")
        assertThat(actualResponse.body()?.episode).isNotEmpty()
        assertThat(actualResponse.body()).isEqualTo(users)
    }

    @Test
    fun `for a episode id not available, api must return  null episode object`() = runTest {
        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_NOT_FOUND)
        mockWebServer.enqueue(expectedResponse)

        val actualResponse = repository.getEpisodes("https://rickandmortyapi.com/api/episode/1000")
        assertThat(actualResponse.body()).isNull()
        assertThat(actualResponse.code()).isEqualTo(HttpURLConnection.HTTP_NOT_FOUND)

    }

    @Test
    fun `for server error, api must return with http code 5xx`() = runTest {
        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_INTERNAL_ERROR)
        mockWebServer.enqueue(expectedResponse)

        val actualResponse = repository.getEpisodes("https://rickandmortyapi.com/episode/27")
        assertThat(actualResponse.code()).isEqualTo(HttpURLConnection.HTTP_INTERNAL_ERROR)
    }

    @Test
    fun `api must return a character with http code 200`() = runTest {
        val users = RickAndMortyInfoModel(
            info = InfoModel(826,42,"https://rickandmortyapi.com/api/character/?page=3","https://rickandmortyapi.com/api/character/?page=1"),
            results = listOf(Character(21,"","","","","",
                origin = Origin("",""),
                location = Location("",""),"",
                episode = listOf(""),"",""))
        )
        val expectedResponse = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
            .setBody(Gson().toJson(users))
        mockWebServer.enqueue(expectedResponse)

        val actualResponse = repository.getAllInfoPaging(1)
        assertThat(actualResponse.body()?.info).isNotNull()
        assertThat(actualResponse.body()).isNotEqualTo(users)
    }
}