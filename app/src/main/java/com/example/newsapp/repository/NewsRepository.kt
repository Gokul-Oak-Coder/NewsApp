package com.example.newsapp.repository

import com.example.newsapp.api.RetrofitInstance
import com.example.newsapp.db.ArticleDatabase
import com.example.newsapp.models.Article

class NewsRepository(val articleDb : ArticleDatabase) {

    suspend fun getBreakingNews(countryCode : String, pageNumber : Int) =
        RetrofitInstance.api.getBreakingNews(countryCode, pageNumber)

    suspend fun  searchNews(searchQuery : String, pageNumber : Int) =
        RetrofitInstance.api.searchForNews(searchQuery, pageNumber)

    suspend fun upsirt(article : Article) = articleDb.getArticleDao().upsert(article)

    fun getSavedArticles() = articleDb.getArticleDao().getAllArticles()

    suspend fun deleteArticle(article: Article) = articleDb.getArticleDao().deleteArticle(article)
}