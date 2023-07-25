package com.example.mvvmapp.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmapp.databinding.ActivityMainBinding
import com.example.mvvmapp.model.QuoteModel
import com.example.mvvmapp.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var mainViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        apiCall()
    }

    private fun apiCall() {
        mainViewModel.getQuotes.observe(this) {
            if (it != null) {
                //success
                binding.txtText.text=(it as QuoteModel).results[10].content
            } else {
                //Error
            }
        }
    }

}