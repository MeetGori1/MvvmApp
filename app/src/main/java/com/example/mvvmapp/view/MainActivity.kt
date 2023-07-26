package com.example.mvvmapp.view

import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.example.mvvmapp.adapter.DiffUtilListAdapter
import com.example.mvvmapp.api.Results
import com.example.mvvmapp.databinding.ActivityMainBinding
import com.example.mvvmapp.model.ImageItem
import com.example.mvvmapp.viewmodel.MainViewModel


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var mainViewModel: MainViewModel
    lateinit var adapter: DiffUtilListAdapter
    lateinit var layoutManager: GridLayoutManager
    var list: ArrayList<ImageItem>? = null

    var page = 1
    var perPage = 10
    var isLoading: Boolean = false
    var isLastPage: Boolean = false
    var isScrolling: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        layoutManager = GridLayoutManager(this,4)
        binding.rwRecyclerView.layoutManager = layoutManager

//        val snapHelper: SnapHelper = PagerSnapHelper()
//        snapHelper.attachToRecyclerView(binding.rwRecyclerView)
        adapter = DiffUtilListAdapter()
        binding.rwRecyclerView.adapter = adapter
        list = ArrayList()
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        apiCall(1)
        scrollListner()
    }

    private fun apiCall(page:Int) {
        mainViewModel.getQuotes(page,perPage).observe(this) {
            when (it) {
                is Results.Error -> {
                    Toast.makeText(
                        this,
                        it.errorMessage?.errors?.get(0)?.toString() ?: "water",
                        Toast.LENGTH_LONG
                    )
                        .show()
                }

                is Results.Success -> {
                    if (it.data!!.isNotEmpty()) {
//                        Toast.makeText(
//                            this,
//                            it.data!![0].description,
//                            Toast.LENGTH_SHORT
//                        ).show()
//                        list?.clear()
                        for (i in it.data) {
                            list?.add(i)
                        }
                        adapter.submitList(list)
                        adapter.notifyItemInserted(adapter.itemCount+1)
                    } else {
                        Toast.makeText(this, "No Data Found", Toast.LENGTH_SHORT).show()
                    }

                }

                is Results.Loading -> {}

            }
        }
    }

    fun scrollListner(){
        binding.rwRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                var visibleItemCount = layoutManager.childCount
                val totalItemCount: Int = layoutManager.itemCount
                val firstVisibleItemPosition: Int = layoutManager.findFirstVisibleItemPosition()
                if (!isLoading && !isLastPage) {
                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
                        && firstVisibleItemPosition >= 0
                    ) {
//                        binding.progressbar.visibility = View.VISIBLE
                        page++
                        apiCall(page)
                    }
                }
            }
        })
    }

}