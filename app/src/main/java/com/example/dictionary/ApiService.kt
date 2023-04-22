package com.example.dictionary

import com.example.dictionary.model.Word
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {
    //@GET("https://api.dictionaryapi.dev/api/v2/entries/en/hello")
    @GET("endpoint")
    suspend fun getData(): Call<List<Word>>
}

suspend fun fetchFromApi() {

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.dictionaryapi.dev/api/v2/entries/en")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)

    apiService.getData().enqueue(object: Callback<List<Word>> {
        override fun onResponse(call: Call<List<Word>>, response: Response<List<Word>>) {
            if(response.isSuccessful) {
                val data = response.body()
                if(data!=null) {
                    //
                }
            } else {
                //
            }
        }

        override fun onFailure(call: Call<List<Word>>, t: Throwable) {
            TODO("Not yet implemented")
        }
    })
}
