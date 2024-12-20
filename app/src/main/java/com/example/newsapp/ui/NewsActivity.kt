package com.example.newsapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.newsapp.R
import com.example.newsapp.databinding.ActivityNewsBinding
import com.example.newsapp.db.ArticleDatabase
import com.example.newsapp.repository.NewsRepository
import com.example.newsapp.ui.viewmodel.NewsViewModel
import com.example.newsapp.ui.viewmodel.NewsViewModelProviderFactory
import com.google.android.material.bottomnavigation.BottomNavigationView

class NewsActivity : AppCompatActivity() {


    private lateinit var binding: ActivityNewsBinding
 //   lateinit var viewModel : NewsViewModel

//    private val newsRepository by lazy { NewsRepository(ArticleDatabase.getDatabase(this)) }
//    private val newsViewModel by viewModels<NewsViewModel> {
//        NewsViewModelProviderFactory(newsRepository)
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

       // val viewModelProviderFactory = NewsViewModelProviderFactory(application, newsRepository)
//
//        val articleDatabase = ArticleDatabase(this)
//        val newsRepository = NewsRepository(articleDatabase)
//        val viewModelProviderFactory = NewsViewModelProviderFactory(newsRepository)
//        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(NewsViewModel::class.java)

//        val newsRepository = NewsRepository(ArticleDatabase(this))
//        val viewModelProviderFactory = NewsViewModelProviderFactory(newsRepository)
//        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(NewsViewModel::class.java)

        val navController = findNavController(R.id.newsNavHostFragment)
       // NavigationUI.setupWithNavController(binding.bottomNavView,navController)
        binding.bottomNavView.setupWithNavController(navController)




    }
}