package com.josancamon19.rappimoviedatabaseapi.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.josancamon19.rappimoviedatabaseapi.R
import com.josancamon19.rappimoviedatabaseapi.data.database.AppDatabase
import com.josancamon19.rappimoviedatabaseapi.data.models.Movie
import com.josancamon19.rappimoviedatabaseapi.data.network.MoviesApi
import com.josancamon19.rappimoviedatabaseapi.databinding.FragmentDetailsBinding
import com.josancamon19.rappimoviedatabaseapi.di.viewmodel.ViewModelFactory
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class DetailsFragment : DaggerFragment() {
    private lateinit var binding: FragmentDetailsBinding
    @Inject
    lateinit var moviesApi: MoviesApi
    private lateinit var viewModel: DetailsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)
        binding.arrowBack.setOnClickListener {
            this.findNavController().navigateUp()
        }
        val args = DetailsFragmentArgs.fromBundle(arguments!!)
        val movie = args.movie
        binding.movie = movie
        setupViewModel(movie)
        return binding.root
    }

    private fun setupViewModel(movie: Movie) {
        val dao = AppDatabase.getInstance(context!!.applicationContext).genresDao
        val factory = DetailsViewModelFactory(dao, movie.genreIds, movie.id, moviesApi)

        viewModel = ViewModelProviders.of(this, factory).get(DetailsViewModel::class.java)
        viewModel.getMovieGenres().observe(this, Observer { movieGenres ->
            viewModel.getMovieGenres().removeObservers(this)
            if (movieGenres != null && movieGenres.isNotEmpty()) {
                binding.hashtags.setData(movieGenres) { it.name }
            }
        })
        viewModel.getVideos().observe(this, Observer {
            viewModel.getVideos().removeObservers(this)
            // It could be a recycler but it is not a good idea to fit a scrollable inside a scrollable
            // also removing the recycler scrollable will remove the main recycler ability(recycler)
            if (!it.isNullOrEmpty()) {
                binding.videosTitle.visibility = VISIBLE
                it.forEach { video ->
                    val player = YouTubePlayerView(context!!)
                    player.setPadding(0, 16, 0, 16)
                    player.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                        override fun onReady(youTubePlayer: YouTubePlayer) {
                            youTubePlayer.loadVideo(video.key, 0F)
                        }
                    })
                    lifecycle.addObserver(player)
                    val textViewTitle = TextView(context)
                    textViewTitle.text = video.name
                    textViewTitle.setPadding(0, 16, 0, 0)
                    binding.videosContainer.addView(textViewTitle)
                    binding.videosContainer.addView(player)
                }
            } else {
                binding.videosTitle.visibility = GONE
            }
        })
    }

}