package com.josancamon19.rappimoviedatabaseapi.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.josancamon19.rappimoviedatabaseapi.R
import com.josancamon19.rappimoviedatabaseapi.data.models.Video
import com.josancamon19.rappimoviedatabaseapi.databinding.ListItemVideoBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import timber.log.Timber

class VideosListAdapter : ListAdapter<Video, VideosListAdapter.VideosViewHolder>(DiffCallBackVideos()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideosViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_video, parent, false)
        return VideosViewHolder(ListItemVideoBinding.bind(view))
    }

    override fun onBindViewHolder(holder: VideosViewHolder, position: Int) {
        Timber.d(getItem(position).toString())
        holder.bind(getItem(position))
    }

    class VideosViewHolder(private val itemBinding: ListItemVideoBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(video: Video) {
            Timber.d("Video received: %s", video.toString())
            if (video.key.isNotEmpty() && video.site == "YouTube") {
                itemBinding.video = video
                itemBinding.youtubeVideoPlayer.addYouTubePlayerListener(object: AbstractYouTubePlayerListener(){
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        youTubePlayer.loadVideo(video.key, 0F)
                    }
                })
            }
            itemBinding.executePendingBindings()
        }
    }

}

class DiffCallBackVideos : DiffUtil.ItemCallback<Video>() {
    override fun areItemsTheSame(oldItem: Video, newItem: Video): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Video, newItem: Video): Boolean {
        return oldItem == newItem
    }
}