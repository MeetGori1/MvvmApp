package com.example.mvvmapp.view

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.example.mvvmapp.api.Results
import com.example.mvvmapp.databinding.ActivityMainBinding
import com.example.mvvmapp.model.ImageItem
import com.example.mvvmapp.adapter.DiffUtilListAdapter
import com.example.mvvmapp.adapter.Paging3Adapter
import com.example.mvvmapp.paging3.PagingModelFactory
import com.example.mvvmapp.paging3.PagingViewModel
import com.example.mvvmapp.repository.QuoteRepository
import com.example.mvvmapp.viewmodel.MainViewModel
import com.example.mvvmapp.viewmodel.MainViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var mainViewModel: MainViewModel
    lateinit var paginngViewModel: PagingViewModel
    lateinit var adapter: DiffUtilListAdapter
    lateinit var paging3adapter: Paging3Adapter

    var repository: QuoteRepository? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

// to load normally data
//        apiCall()
//        to load data with paging 3
        apiCallPaging()
    }

    private fun apiCall() {
        var list: ArrayList<ImageItem>? = null
        adapter = DiffUtilListAdapter()
        binding.rwRecyclerView.adapter = adapter
        list = ArrayList()
        mainViewModel =
            ViewModelProvider(this, MainViewModelFactory(1, 2))[MainViewModel::class.java]
        mainViewModel.results.observe(this) {
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
//                            it.data[0].description,
//                            Toast.LENGTH_SHORT
//                        ).show()
                        for (i in it.data) {
                            list.add(i)
                        }
                        adapter.submitList(list)
                    } else {
                        Toast.makeText(this, "No Data Found", Toast.LENGTH_SHORT).show()
                    }

                }

                is Results.Loading -> {}

            }
        }


    }

    private fun apiCallPaging() {

        paging3adapter = Paging3Adapter()
        binding.rwRecyclerView.adapter = paging3adapter
        repository = QuoteRepository()
        paginngViewModel =
            ViewModelProvider(this, PagingModelFactory(1, 20))[PagingViewModel::class.java]

        lifecycleScope.launch {
            // for use with kotlin flow
            paginngViewModel.images.collectLatest {
                paging3adapter.submitData(it)
            }

            // for use with live data
//            paginngViewModel.images.observe(this@MainActivity) {
//                paging3adapter.submitData(lifecycle, it)
//            }

        }




        paging3adapter.addLoadStateListener{ loadState ->
            when (val state = loadState.source.refresh) {
                is LoadState.NotLoading -> {
                    /**
                     * Setting up the views as per your requirement
                     */
                    Toast.makeText(this, "NotLoading", Toast.LENGTH_SHORT).show()
                }

                is LoadState.Loading -> {
                    /**
                     * Setting up the views as per your requirement
                     */

                    Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show()
                }

                is LoadState.Error -> {
                    /**
                     * Setting up the views as per your requirement
                     */

                    Toast.makeText(this, state.error.message.orEmpty(), Toast.LENGTH_SHORT).show()
//                    Youractivity?.showMessage()
                }
            }
        }


    }
//    private fun setLoadingState() {
//        adapterNotification?.addLoadStateListener {
//            if (it.source.append !is LoadState.Loading && it.source.prepend !is LoadState.Loading) {
//                when (it.source.refresh) {
//                    is LoadState.Loading -> {
//                        if (adapterNotification?.itemCount == 0) {
//                            mBinding.rvListing.hide()
//                            mBinding.txtError.hide()
//                            mBinding.animatedLoader.show()
//                        } else {
//                            mBinding.rvListing.show()
//                            mBinding.txtError.hide()
//                            mBinding.animatedLoader.hide()
//                        }
//                    }
//                    is LoadState.Error -> {
//                        mBinding.animatedLoader.hide()
//                        mBinding.txtError.show()
//                        mBinding.txtError.text = getString(R.string.currently_your_notification_list_is_empty)
//                        //mBinding.txtError.text = (it.source.refresh as LoadState.Error).error.localizedMessage
//                        mBinding.rvListing.hide()
//                    }
//                    else -> {
//                        mBinding.rvListing.show()
//                        mBinding.txtError.hide()
//                        mBinding.animatedLoader.hide()
//                    }
//                }
//            }
//        }
//    }
}

