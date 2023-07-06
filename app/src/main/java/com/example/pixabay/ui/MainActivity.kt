package com.example.pixabay.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.pixabay.R
import com.example.pixabay.adapter.PostsAdapter
import com.example.pixabay.databinding.ActivityMainBinding
import com.example.pixabay.model.FavouritePost
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private val viewModel: PostsViewModel by lazy {
        val activity = application
        ViewModelProvider(this, PostsViewModelFactory(activity))[PostsViewModel::class.java]
    }

    private lateinit var adapter: PostsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        binding.progressBar.visibility = ProgressBar.VISIBLE
        adapter = PostsAdapter(PostsAdapter.PostClickListener { post, _binding ->
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

                viewModel.insertFavouritePost(favouritePost, this)
                _binding.likeIcon.setImageResource(R.drawable.favorite)
                Toast.makeText(this, "post inserted to favourites", Toast.LENGTH_SHORT).show()
            } else {
                post.isFavourite = false
                viewModel.deleteFavouritePost(post.id)
                _binding.likeIcon.setImageResource(R.drawable.outline_favorite_border_24)
                Toast.makeText(this, "post deleted from favourites", Toast.LENGTH_SHORT).show()
            }
        })

        binding.searchView.setOnQueryTextListener(this)
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.resourceStateFlow.collect { postsResource ->
                if (postsResource.data.isNotEmpty()) {
                    withContext(Dispatchers.Main) {
                        adapter.submitList(postsResource.data)
                        binding.progressBar.visibility = ProgressBar.INVISIBLE
                        binding.noPostsTextView.visibility = TextView.INVISIBLE
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        binding.noPostsTextView.visibility = TextView.VISIBLE
                        binding.progressBar.visibility = ProgressBar.INVISIBLE
                    }
                }
            }
        }
        binding.postsRecyclerView.adapter = adapter
        binding.floatingActionButton.setOnClickListener{
        val intent = Intent(
            this,
            FavouriteActivity::class.java
        )
        startActivity(intent)
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        viewModel.getPostsByTags(query!!)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean = true
}