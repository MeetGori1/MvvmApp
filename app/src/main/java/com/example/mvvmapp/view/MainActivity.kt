package com.example.mvvmapp.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmapp.api.Results
import com.example.mvvmapp.databinding.ActivityMainBinding
import com.example.mvvmapp.model.ImageItem
import com.example.mvvmapp.view.adapter.DiffUtilListAdapter
import com.example.mvvmapp.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var mainViewModel: MainViewModel
    lateinit var adapter: DiffUtilListAdapter
    var list: ArrayList<ImageItem>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = DiffUtilListAdapter()
        binding.rwRecyclerView.adapter = adapter
        list = ArrayList()
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        apiCall()
    }

    private fun apiCall() {
        mainViewModel.getQuotes(1, 10).observe(this) {
            when (it) {

                is Results.Error -> {
                    Toast.makeText(this, it.errorMessage?.errors?.toString(), Toast.LENGTH_LONG)
                        .show()
                }

                is Results.Success -> {
                    if (it.data!!.isNotEmpty()) {
                        Toast.makeText(
                            this,
                            it.data!![0].description,
                            Toast.LENGTH_SHORT
                        ).show()
                        for (i in it.data){
                            list?.add(i)
                        }
                        adapter.submitList(list)
                    }else{
                        Toast.makeText(this, "No Data Found", Toast.LENGTH_SHORT).show()
                    }

                }

                is Results.Loading -> {}

            }
        }
    }

}