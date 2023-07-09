package com.example.pixabay.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.pixabay.R
import com.example.pixabay.adapter.PostsAdapter
import com.example.pixabay.databinding.FragmentHomeBinding
import com.example.pixabay.model.FavouritePost
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment(), SearchView.OnQueryTextListener {
    private val viewModel: PostsViewModel by lazy {
        val activity = requireActivity().application
        ViewModelProvider(this, PostsViewModelFactory(activity))[PostsViewModel::class.java]
    }
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: PostsAdapter

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.progressBar.visibility = ProgressBar.VISIBLE
        adapter = PostsAdapter(PostsAdapter.PostClickListener { post ->
            if (!post.isFavourite) {
                post.isFavourite = true
                val favouritePost = FavouritePost(
                    post.collections,
                    post.comments,
                    post.downloads,
                    post.id,
                    post.imageHeight,
                    post.imageSize,
                    post.imageWidth,
                    post.largeImageURL,
                    post.likes,
                    post.pageURL,
                    post.previewHeight,
                    post.previewURL,
                    post.previewWidth,
                    post.tags,
                    post.type,
                    post.user,
                    post.userImageURL,
                    post.user_id,
                    post.views,
                    post.webformatHeight,
                    post.webformatURL,
                    post.webformatWidth,
                    true
                )

                viewModel.insertFavouritePost(favouritePost, requireContext())
                adapter.notifyDataSetChanged()
                Toast.makeText(requireContext(), "post inserted to favourites", Toast.LENGTH_SHORT)
                    .show()
            } else {
                post.isFavourite = false
                viewModel.deleteFavouritePost(post.id)
                adapter.notifyDataSetChanged()
                Toast.makeText(requireContext(), "post deleted from favourites", Toast.LENGTH_SHORT)
                    .show()
            }
        })

        binding.searchView.setOnQueryTextListener(this)
        CoroutineScope(Dispatchers.IO).launch {
            val cachedPostsJob = launch {
                viewModel.resourceStateFlow.collect { postsResource ->
                    if (postsResource.data.isNotEmpty()) {
                        withContext(Dispatchers.Main) {
                            adapter.submitList(postsResource.data)
                            binding.progressBar.visibility = ProgressBar.INVISIBLE
                            binding.noPostsTextView.visibility = TextView.INVISIBLE
                        }
                    } else {
                        delay(3000)
                        withContext(Dispatchers.Main) {
                            binding.noPostsTextView.visibility = TextView.VISIBLE
                            binding.progressBar.visibility = ProgressBar.INVISIBLE
                        }
                    }
                }
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            viewModel.postsMutableFlow.collect { taggedPosts ->
                if (taggedPosts.isNotEmpty()) {
                    withContext(Dispatchers.Main) {
                        adapter.submitList(taggedPosts)
                        binding.progressBar.visibility = ProgressBar.INVISIBLE
                        binding.noPostsTextView.visibility = TextView.INVISIBLE
                    }
                } else {
                    binding.noPostsTextView.text = getString(R.string.no_posts_available)
                    binding.noPostsTextView.visibility = TextView.INVISIBLE
                }
            }
        }

        binding.postsRecyclerView.adapter = adapter
        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToFavouriteFragment()
            )
        }
        return binding.root
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        binding.progressBar.visibility = ProgressBar.VISIBLE
        binding.noPostsTextView.text = "searching..."
        viewModel.getPostsByTags(query!!)
        Log.d(TAG, "onQueryTextSubmit: $query")
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean = true

    companion object {
        private const val TAG = "HomeFragment"
    }
}


