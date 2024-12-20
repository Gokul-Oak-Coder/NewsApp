package com.example.newsapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.models.Article

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {

    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.findViewById(R.id.ivArticleImage)
        var title: TextView = itemView.findViewById(R.id.tvTitle)
        var description: TextView = itemView.findViewById(R.id.tvDescription)
        var source: TextView = itemView.findViewById(R.id.tvSource)
        var publish: TextView = itemView.findViewById(R.id.tvPublishedAt)
    }

    private val diffCallback = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_article_preview, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = differ.currentList[position]

        holder.apply {
            if(article.urlToImage != null) {
                Glide.with(itemView.context).load(article.urlToImage).into(image)
            }
            else{
                Glide.with(itemView.context).load(R.drawable.news).into(image)
            }
            title.text = article.title
            description.text = article.description
            source.text = article.source?.name
            publish.text = article.publishedAt
            //val ur = article.url
            itemView.setOnClickListener {
                onItemClickListener?.let { it(article) }
            }
        }
    }

    private var onItemClickListener : ((Article) -> Unit)? = null

    fun setOnItemClickListener (listener : (Article) -> Unit) {
        onItemClickListener = listener
    }
}
