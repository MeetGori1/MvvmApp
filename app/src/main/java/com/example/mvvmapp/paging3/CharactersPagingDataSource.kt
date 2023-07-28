package com.example.mvvmapp.paging3

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mvvmapp.utils.PagingCustomException
import com.example.mvvmapp.api.ParserHelper
import com.example.mvvmapp.api.Results
import com.example.mvvmapp.model.ImageItem
import com.example.mvvmapp.repository.QuoteRepository
import retrofit2.HttpException
import java.io.IOException

class CharactersPagingDataSource(
    private val dataRepository: QuoteRepository,
    private val perPage:Int
) :
    PagingSource<Int, ImageItem>() {
    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ImageItem> {
        val pageNumber = params.key ?: 1
        return try {
            val response = dataRepository.getImages(pageNumber, perPage)
            val result = response.body()

            val prevKey = if (pageNumber == STARTING_PAGE_INDEX) null else pageNumber - 1
            val nextKey = pageNumber + 1
            if (result == null) {
//                LoadResult.Error<String,Any>(Exception("Data Not Found"))
                validData(Results.Error<ImageItem>(ParserHelper.baseError(response.errorBody())))
            }
            return LoadResult.Page(data = result ?: mutableListOf(), prevKey, nextKey)
        } catch (e: IOException) {
            LoadResult.Error(Exception("Something went Wrong"))
        } catch (e: HttpException) {
            LoadResult.Error(e)
        } catch (e: PagingCustomException) {
            LoadResult.Error(e)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    fun validData(error: Results.Error<ImageItem>) {
        throw PagingCustomException(error.errorMessage)
    }

    override fun getRefreshKey(state: PagingState<Int, ImageItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}