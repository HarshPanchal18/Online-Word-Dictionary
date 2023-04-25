package com.example.dictionary

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dictionary.databinding.ActivityMainBinding
import com.example.dictionary.model.Word
import kotlinx.coroutines.DelicateCoroutinesApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val dataList: MutableList<Word> = mutableListOf()
    val adapter = WordAdapter(dataList)
    private val baseURL: String = "https://api.dictionaryapi.dev/api/v2/entries/en/"

    @DelicateCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.wordRecycle.adapter = adapter
        binding.wordRecycle.layoutManager = LinearLayoutManager(this)

        binding.searchBtn.setOnClickListener {
            fetchWordMeaningFromAPI(binding.enteredWord.text.trim().toString())
        }
    }

    private fun fetchWordMeaningFromAPI(word: String) {

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(ApiService::class.java)

        apiService.getData(word).enqueue(object : Callback<List<Word>> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<List<Word>>, response: Response<List<Word>>) {
                if (response.isSuccessful) {
                    val data = response.body()
                    dataList.clear()
                    dataList.addAll(data?: emptyList())
                    adapter.notifyDataSetChanged()
                } else {
                    // Handle error response
                    binding.errorResponse.text = response.errorBody().toString()
                    //Toast.makeText(this@MainActivity,response.errorBody().toString(),Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<Word>>, t: Throwable) {
                // Handle API call failure
                binding.errorResponse.text = t.message.toString()
                //Toast.makeText(this@MainActivity,t.message.toString(),Toast.LENGTH_LONG).show()
            }
        })
    }

    interface ApiService {
        @GET("https://api.dictionaryapi.dev/api/v2/entries/en/{word}/")
        fun getData(@Path("word") word: String): Call<List<Word>>
    }

}
