package com.example.newsapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.repository.NewsRepository


class NewsViewModelProviderFactory(
    val application : Application,
    val newsRepository: NewsRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NewsViewModel(application, newsRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}











//class NewsViewModelProviderFactory(
//    val newsRepository: NewsRepository
//) : ViewModelProvider.Factory {
//
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        return NewsViewModel(newsRepository) as T
//
//    }
//}