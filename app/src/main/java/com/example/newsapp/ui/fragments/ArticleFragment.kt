package com.example.newsapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.newsapp.R
import com.example.newsapp.db.ArticleDatabase
import com.example.newsapp.repository.NewsRepository
import com.example.newsapp.ui.NewsActivity
import com.example.newsapp.ui.viewmodel.NewsViewModel
import com.example.newsapp.ui.viewmodel.NewsViewModelProviderFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class ArticleFragment: Fragment(R.layout.fragment_article) {

    lateinit var viewModel : NewsViewModel
    lateinit var webView : WebView
    lateinit var fab : FloatingActionButton
    val args : ArticleFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       // viewModel = (activity as NewsActivity).viewModel

        val newsRepository = NewsRepository(ArticleDatabase(requireContext()))
        val viewModelProviderFactory = NewsViewModelProviderFactory(requireActivity().application,newsRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(NewsViewModel::class.java)

        webView = view.findViewById(R.id.webView)
        val article = args.article
        webView.apply {
            webViewClient = WebViewClient()
            if(article.url != null) {
                loadUrl(article.url.toString())
            }
            else{
                loadUrl("https://www.google.com/")
            }
        }

        fab = view.findViewById(R.id.fab)

        fab.setOnClickListener {
            viewModel.saveArticle(article)
            Snackbar.make(view, "Article added Successfully" , Snackbar.LENGTH_SHORT).show()
        }


    }
}