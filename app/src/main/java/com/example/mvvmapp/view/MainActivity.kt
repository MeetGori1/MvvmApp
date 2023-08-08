package com.example.mvvmapp.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.example.mvvmapp.adapter.DiffUtilListAdapter
import com.example.mvvmapp.adapter.LoadingStateAdapter
import com.example.mvvmapp.adapter.Paging3Adapter
import com.example.mvvmapp.api.Results
import com.example.mvvmapp.databinding.ActivityMainBinding
import com.example.mvvmapp.model.ImageItem
import com.example.mvvmapp.paging3.PagingModelFactory
import com.example.mvvmapp.paging3.PagingViewModel
import com.example.mvvmapp.repository.QuoteRepository
import com.example.mvvmapp.viewmodel.MainViewModel
import com.example.mvvmapp.viewmodel.MainViewModelFactory
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

        binding.btnNext.setOnClickListener {
            val intent=Intent(this, DataBindingActivity::class.java)
            startActivity(intent)
        }
    }

    private fun apiCall() {
        var list: ArrayList<ImageItem>? = null
        adapter = DiffUtilListAdapter()
        binding.rwRecyclerView.adapter = adapter
        binding.layoutLoading.root.isVisible = false
        list = ArrayList()
        mainViewModel =
            ViewModelProvider(this, MainViewModelFactory(1, 20))[MainViewModel::class.java]
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
        val snapHelper: SnapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.rwRecyclerView)
        paging3adapter = Paging3Adapter()
        binding.layoutLoading.retryButton.setOnClickListener {
            paging3adapter.retry()
        }

        binding.refreshLayout.setOnRefreshListener {
//            paging3adapter.submitData(lifecycle, PagingData.empty())
//            paging3adapter.refresh()
            paging3adapter.submitData(lifecycle, PagingData.empty())
//            Handler(Looper.myLooper()!!).postDelayed({
            lifecycleScope.launch {
                paginngViewModel.getImages()


            }
//            }, 2000)


        }

//        paging3adapter.withLoadStateHeaderAndFooter(
//            header = LoadingStateAdapter(paging3adapter),
//            footer = LoadingStateAdapter(paging3adapter)
//        )
        binding.rwRecyclerView.apply {
            val footerAdapter =
                paging3adapter.withLoadStateHeaderAndFooter(
                    header = LoadingStateAdapter(paging3adapter),
                    footer = LoadingStateAdapter(paging3adapter)
                )
            adapter = footerAdapter
        }
        repository = QuoteRepository()
        paginngViewModel = ViewModelProvider(this, PagingModelFactory(1, 24))[PagingViewModel::class.java]

        paginngViewModel.getImages()

        lifecycleScope.launch {
            paginngViewModel.results.observe(this@MainActivity) {
                paging3adapter.submitData(lifecycle, it)
                binding.refreshLayout.isRefreshing = false
            }

            // for use with kotlin flow
//            paginngViewModel.images.collectLatest {
//                Toast.makeText(this@MainActivity, "new Data", Toast.LENGTH_SHORT).show()
//                paging3adapter.submitData(it)
//                binding.refreshLayout.isRefreshing = false
//            }

            // for use with live data
//            paginngViewModel.images.observe(this@MainActivity) {
//                paging3adapter.submitData(lifecycle, it)
//            }

        }





        paging3adapter.addLoadStateListener { loadState ->
            when (val state = loadState.source.refresh) {
                is LoadState.NotLoading -> {
                    /**
                     * Setting up the views as per your requirement
                     */
                    binding.layoutLoading.root.isVisible = false
//                    Toast.makeText(this, "NotLoading", Toast.LENGTH_SHORT).show()
                }

                is LoadState.Loading -> {
                    /**
                     * Setting up the views as per your requirement
                     */
                    binding.layoutLoading.apply {
                        errorMsg.isVisible = false
                        retryButton.isVisible = false
                        progressBar.isVisible = true
                    }
//                    Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show()
                }

                is LoadState.Error -> {
                    /**
                     * Setting up the views as per your requirement
                     */
                    binding.layoutLoading.root.isVisible = true
                    binding.layoutLoading.apply {
                        errorMsg.isVisible = true
                        retryButton.isVisible = true
                        progressBar.isVisible = false
                        errorMsg.text = state.error.message.orEmpty()
                    }

//                    Toast.makeText(this, state.error.message.orEmpty(), Toast.LENGTH_SHORT).show()
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

