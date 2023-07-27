package com.example.mvvmapp.paging3

import android.net.Uri
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bumptech.glide.load.engine.Resource
import com.example.mvvmapp.api.ApiService
import com.example.mvvmapp.model.ImageItem
import com.example.mvvmapp.repository.QuoteRepository
import kotlin.math.log

class CharactersPagingDataSource(private val dataRepository: QuoteRepository) :
    PagingSource<Int, ImageItem>() {
    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ImageItem> {
        val pageNumber = params.key ?: 1

        val result: List<ImageItem> = dataRepository.getImages(pageNumber, params.loadSize).body() as  List<ImageItem>
        return LoadResult.Page(
            data = result,
            prevKey = if (pageNumber == STARTING_PAGE_INDEX) null else pageNumber - 1,
            nextKey = pageNumber + 1
        )
    }

    override fun getRefreshKey(state: PagingState<Int, ImageItem>): Int? {
        Log.e("refresh","refresh")
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}