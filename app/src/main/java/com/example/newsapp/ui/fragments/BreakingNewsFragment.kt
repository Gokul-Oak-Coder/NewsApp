package com.example.newsapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.adapters.NewsAdapter
import com.example.newsapp.db.ArticleDatabase
import com.example.newsapp.models.Article
import com.example.newsapp.repository.NewsRepository
import com.example.newsapp.ui.NewsActivity
import com.example.newsapp.ui.viewmodel.NewsViewModel
import com.example.newsapp.ui.viewmodel.NewsViewModelProviderFactory
import com.example.newsapp.util.Constants.Companion.QUERY_PAGE_SIZE
import com.example.newsapp.util.Resource

class BreakingNewsFragment : Fragment(R.layout.fragment_breaking_news) {

    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var rvBreakingNews: RecyclerView
    private lateinit var progressBar: ProgressBar

    private val TAG = "BreakingNewsFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvBreakingNews = view.findViewById(R.id.rvBreakingNews)
        progressBar = view.findViewById(R.id.paginationProgressBar)

    //    viewModel = (activity as NewsActivity).viewModel
        newsAdapter = NewsAdapter()

        newsAdapter.setOnItemClickListener {
            Toast.makeText(view.context, "Clicked something", Toast.LENGTH_SHORT)
            val bundle = Bundle().apply{
                putSerializable("article", it)
            }
            findNavController().navigate(
                R.id.action_breakingNewsFragment_to_articleFragment, bundle
            )
        }

        val newsRepository = NewsRepository(ArticleDatabase(requireContext()))
        val viewModelProviderFactory = NewsViewModelProviderFactory(requireActivity().application, newsRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(NewsViewModel::class.java)

        setRecycler()

        viewModel.breakingNews.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articles.toList())
                        val totalPages = newsResponse.totalResults / QUERY_PAGE_SIZE + 2
                        isLastPage = viewModel.breakingNewsPage == totalPages
                        if(isLastPage){
                            rvBreakingNews.setPadding(0,0,0,0)
                        }
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Toast.makeText(activity, "An error occurred $message", Toast.LENGTH_SHORT ).show()
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }

                else -> {}
            }
        })
    }

    private fun hideProgressBar() {
        progressBar.visibility = View.INVISIBLE
        isLoading = false
    }

    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
        isLoading = true
    }

    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    val scrollListener = object : RecyclerView.OnScrollListener(){
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotAtLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= QUERY_PAGE_SIZE
            val shouldPaginate = isNotLoadingAndNotAtLastPage && isAtLastItem && isNotAtBeginning &&
                    isTotalMoreThanVisible && isScrolling
            if(shouldPaginate) {
                viewModel.getBreakingNews("us")
                isScrolling = false
            }
        }
    }

    private fun setRecycler() {
       // newsAdapter = NewsAdapter()

        rvBreakingNews.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@BreakingNewsFragment.scrollListener)
        }
    }
}





























//package com.example.newsapp.ui.fragments
//
//import android.os.Bundle
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ProgressBar
//import androidx.fragment.app.Fragment
//import androidx.lifecycle.Observer
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.example.newsapp.R
//import com.example.newsapp.adapters.NewsAdapter
//import com.example.newsapp.ui.NewsActivity
//import com.example.newsapp.ui.viewmodel.NewsViewModel
//import com.example.newsapp.util.Resource
//
//class BreakingNewsFragment: Fragment(R.layout.fragment_breaking_news) {
//
//    lateinit var viewModel : NewsViewModel
//    private lateinit var newsAdapter : NewsAdapter
//    private lateinit var iv_recycle : RecyclerView
//    private lateinit var progressBar : ProgressBar
//
//    private val TAG = "BreakingNewsFragment"
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        iv_recycle = view.findViewById(R.id.rvBreakingNews)
//        progressBar = view.findViewById(R.id.paginationProgressBar)
//
//        viewModel = (activity as NewsActivity).viewModel
//
//        setRecycler()
//
//        viewModel.breakingNews.observe(viewLifecycleOwner, Observer {response ->
//            when(response) {
//                is Resource.Success -> {
//                    hideProgressBar()
//                    response.data?.let {newsResponse ->
//                        newsAdapter.differ.submitList(newsResponse.articles)
//                    }
//                }
//                is Resource.Error ->{
//                    hideProgressBar()
//                    response.message?.let { message ->
//                        Log.e(TAG, "An Error accurd $message")
//                    }
//
//                }
//                is Resource.Loading ->{
//                    showProgressBar()
//                }
//            }
//        })
//
//
//    }
//    private fun hideProgressBar(){
//        progressBar.visibility = View.INVISIBLE
//    }
//
//    private fun showProgressBar(){
//        progressBar.visibility = View.VISIBLE
//    }
//    private fun setRecycler(){
//        newsAdapter = NewsAdapter()
//
//        iv_recycle.apply {
//            adapter = newsAdapter
//            layoutManager = LinearLayoutManager(activity)
//        }
//
//    }
//}