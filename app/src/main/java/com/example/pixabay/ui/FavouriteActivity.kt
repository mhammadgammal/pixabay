package com.example.pixabay.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.pixabay.R
import com.example.pixabay.adapter.FavouriteAdapter
import com.example.pixabay.databinding.ActivityFavouriteBinding
import com.example.pixabay.model.FavouritePost
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavouriteActivity : AppCompatActivity() {
    private lateinit var adapter: FavouriteAdapter
    private lateinit var binding: ActivityFavouriteBinding
    private val viewModel: PostsViewModel by lazy {
        val activity = application
        ViewModelProvider(this, PostsViewModelFactory(activity))[PostsViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityFavouriteBinding?>(
            this,
            R.layout.activity_favourite
        ).apply { lifecycleOwner = this@FavouriteActivity }
        viewModel.getFavouritePost()

        adapter =
            FavouriteAdapter(FavouriteAdapter.FavouriteOnClickListener { favouritePost, binding ->
                viewModel.deleteFavouritePost(favouritePost.id)
                viewModel.getFavouritePost()
                binding.likeIcon.setImageDrawable(AppCompatResources.getDrawable(
                    this, R.drawable.outline_favorite_border_24
                ))
            })
        CoroutineScope(Dispatchers.IO).launch{
            viewModel.favouritePostsStateFlow.collect {
                adapter.submitList(it)
            }
        }


        binding.favouritePostsRecyclerVIew.adapter = adapter
    }
    companion object{
        private const val TAG = "FavouriteActivity"
    }
}