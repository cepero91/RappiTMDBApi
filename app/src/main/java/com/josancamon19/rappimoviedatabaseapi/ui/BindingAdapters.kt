package com.josancamon19.rappimoviedatabaseapi.ui

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.josancamon19.rappimoviedatabaseapi.R


@BindingAdapter("posterPath")
fun bindImage(imgView: ImageView, posterPath: String?) {
    posterPath?.let {
        val posterUri = Uri.parse("http://image.tmdb.org/t/p/w185/${posterPath.trim()}")
        GlideApp.with(imgView.context)
            .load(posterUri)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image)
            )
            .into(imgView)
    }
}

