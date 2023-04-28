package com.example.dictionary

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
import kotlin.system.exitProcess

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
            if (binding.enteredWord.text?.isNotEmpty() == true) {
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(it.applicationWindowToken, 0) // hiding keyboard
                fetchWordMeaningFromAPI(binding.enteredWord.text?.trim().toString())
            } else {
                binding.wordRecycle.visibility = View.GONE
                binding.errorResponse.visibility = View.VISIBLE
                binding.errorResponse.text = "Please Enter a word to find"
            }
        }
    }

    private fun fetchWordMeaningFromAPI(word: String?) {

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService: ApiService = retrofit.create(ApiService::class.java)

        apiService.getData(word ?: "").enqueue(object : Callback<List<Word>> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(call: Call<List<Word>>, response: Response<List<Word>>) {
                if (response.isSuccessful) {
                    binding.errorResponse.visibility = View.GONE
                    binding.wordRecycle.visibility = View.VISIBLE
                    val data = response.body()
                    dataList.clear()
                    dataList.addAll(data ?: emptyList())
                    adapter.notifyDataSetChanged()
                } else {
                    // Handle error response
                    if (response.errorBody().toString().contains("okhttp3.ResponseBody$1")) {
                        binding.wordRecycle.visibility = View.GONE
                        binding.errorResponse.text = "Please enter a valid word"
                    }
                }
            }

            override fun onFailure(call: Call<List<Word>>, t: Throwable) {
                // Handle API call failure
                if (t.message.toString().contains("Unable to resolve host")) {
                    binding.wordRecycle.visibility = View.GONE
                    binding.errorResponse.text =
                        "Please check your internet connection or try again leter"
                }
            }
        })
    }

    interface ApiService {
        @GET("https://api.dictionaryapi.dev/api/v2/entries/en/{word}/")
        fun getData(@Path("word") word: String): Call<List<Word>>
    }

    // Ask again for exit
    private var backPressedTime: Long = 0
    override fun onBackPressed() {

        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed()
            exitProcess(0)
        }
        Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show()
        backPressedTime = System.currentTimeMillis()
    }
}
