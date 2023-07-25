package com.example.mvvmapp.view

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmapp.databinding.ActivityMainBinding
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
        getData()
    }

    private fun apiCall(){
//        Handler(Looper.myLooper()!!).postDelayed({
            mainViewModel.getQuotes()
//        },5000)
    }

    private fun getData(){
        mainViewModel.getData().observe(this) {
            Log.d("water", it?.results.toString())
            binding.txtText.text= it!!.results[2].content.toString()
        }
    }
}