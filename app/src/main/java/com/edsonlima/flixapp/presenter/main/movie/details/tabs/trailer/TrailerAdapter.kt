package com.edsonlima.flixapp.presenter.main.movie.details.tabs.trailer

import android.view.LayoutInflater
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.edsonlima.flixapp.databinding.ItemMovieTrailerBinding
import com.edsonlima.flixapp.domain.model.Trailer

class TrailerAdapter : ListAdapter<Trailer, TrailerAdapter.TrailerViewHolder>(diffCallback) {

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Trailer>() {
            override fun areItemsTheSame(
                oldItem: Trailer,
                newItem: Trailer
            ): Boolean {
                return oldItem.key == newItem.key
            }

            override fun areContentsTheSame(
                oldItem: Trailer,
                newItem: Trailer
            ): Boolean {
                return oldItem == newItem
            }

        }
    }

    inner class TrailerViewHolder(
        val binding: ItemMovieTrailerBinding
    ) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrailerViewHolder {

        val inflater = LayoutInflater.from(parent.context)

        val viewItem = ItemMovieTrailerBinding.inflate(inflater, parent, false)

        return TrailerViewHolder(viewItem)

    }

    override fun onBindViewHolder(holder: TrailerViewHolder, position: Int) {

        val trailer = getItem(position)

        with(holder.binding.webTrailer.settings) {
            javaScriptEnabled = true
            domStorageEnabled = true
            mediaPlaybackRequiresUserGesture = false
        }

        holder.binding.webTrailer.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                return false
            }
        }

        holder.binding.webTrailer.webChromeClient = WebChromeClient()

        val embedHtml = """
            <html>
                <body style="margin:0;padding:0;">
                    <iframe width="100%" height="100%"
                    src="https://www.youtube.com/embed/${trailer.key}"
                    frameborder="0"
                    allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
                    allowfullscreen></iframe>
                </body>
            </html>
        """
        holder.binding.webTrailer.loadDataWithBaseURL(null, embedHtml, "text/html", "utf-8", null)
    }

}