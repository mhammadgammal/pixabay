package com.example.pixabay.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.pixabay.adapter.FavouriteAdapter
import com.example.pixabay.databinding.FragmentFavouriteBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


/**
 * A simple [Fragment] subclass.
 * Use the [FavouriteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FavouriteFragment : Fragment() {
    private lateinit var adapter: FavouriteAdapter
    private lateinit var binding: FragmentFavouriteBinding
    private val viewModel: PostsViewModel by lazy {
        val activity = requireActivity().application
        ViewModelProvider(this, PostsViewModelFactory(activity))[PostsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        binding = FragmentFavouriteBinding.inflate(inflater)
            .apply { lifecycleOwner = this@FavouriteFragment }
        viewModel.getFavouritePost()

        adapter =
            FavouriteAdapter(FavouriteAdapter.FavouriteOnClickListener { favouritePost ->
                viewModel.deleteFavouritePost(favouritePost.id)
                viewModel.getFavouritePost()
            })
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.favouritePostsStateFlow.collect {
                adapter.submitList(it)
            }
        }

        binding.favouritePostsRecyclerVIew.adapter = adapter
        // Inflate the layout for this fragment
        return binding.root
    }

    companion object {
        private const val TAG = "FavouriteActivity"
    }
}