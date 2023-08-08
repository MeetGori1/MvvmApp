package com.example.mvvmapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mvvmapp.databinding.ActivityDataBindingBinding
import com.example.mvvmapp.viewmodel.DataBindingViewModel

class DataBindingActivity : AppCompatActivity() {
    lateinit var binding: ActivityDataBindingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding=ActivityDataBindingBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.i= DataBindingViewModel()
        binding.lifecycleOwner=this
    }
}