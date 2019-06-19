package com.josancamon19.rappimoviedatabaseapi.movies

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.josancamon19.rappimoviedatabaseapi.R
import com.josancamon19.rappimoviedatabaseapi.data.models.Movie
import com.josancamon19.rappimoviedatabaseapi.databinding.FragmentMoviesBinding
import timber.log.Timber

class MoviesFragment : Fragment(), MoviesListAdapter.OnMovieClickListener, AdapterView.OnItemSelectedListener {


    private lateinit var binding: FragmentMoviesBinding
    private lateinit var viewModel: MoviesViewModel
    private lateinit var adapter: MoviesListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movies, container, false)
        setSpinner()
        setupSearchView()
        setupRecycler()

        setupViewModel()

        return binding.root
    }

    private fun setSpinner() {
        ArrayAdapter.createFromResource(
            context!!,
            R.array.movies_categories,
            R.layout.spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinner.adapter = adapter
        }
        binding.spinner.onItemSelectedListener = this
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        binding.root.hideKeyboard()
        viewModel.setCategory(p0?.getItemAtPosition(p2).toString())
    }

    private fun setupSearchView() {
        binding.searchView.setOnSearchClickListener {
            Timber.d("search view clicked")
            binding.spinner.visibility = GONE
        }
        binding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                binding.root.hideKeyboard()
                p0?.let { viewModel.queryMovies(it) }
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })

        binding.searchView.setOnCloseListener {
            binding.root.hideKeyboard()
            viewModel.clearQuery()
            binding.searchView.onActionViewCollapsed()
            binding.spinner.visibility = VISIBLE
            true
        }
    }

    private fun setupRecycler() {
        binding.recycler.setHasFixedSize(true)
        val orientation: Int = resources.configuration.orientation

        val manager: GridLayoutManager
        manager = if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            GridLayoutManager(context, 3, RecyclerView.VERTICAL, false)
        } else {
            GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)
        }

        binding.recycler.layoutManager = manager
        adapter = MoviesListAdapter(this)
        binding.recycler.adapter = adapter
    }

    private fun setupViewModel() {
        Timber.d("setting up ViewModel")
        viewModel = ViewModelProviders.of(this).get(MoviesViewModel::class.java)
        viewModel.getMovies().observe(this, Observer {
            adapter.submitList(it)
            Timber.d("List submitted")
        })

    }

    override fun setOnMovieClickListener(movie: Movie) {
        binding.root.findNavController()
            .navigate(MoviesFragmentDirections.actionMoviesFragmentToDetailsFragment(movie))
    }

    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }


}