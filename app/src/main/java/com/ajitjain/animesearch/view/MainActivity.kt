package com.ajitjain.animesearch.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ajitjain.animesearch.R
import com.ajitjain.animesearch.adapters.AnimeAdapter
import com.ajitjain.animesearch.databinding.LayoutMainActivityBinding
import com.ajitjain.animesearch.viewmodel.AnimeViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: LayoutMainActivityBinding

    private val viewModel: AnimeViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return AnimeViewModel(application) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutMainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val loading = binding.loadingAnimation
        val recyclerView = binding.recyclerView

        loading.playAnimation()
        loading.isVisible = true
        recyclerView.isVisible = false

        val animeAdapter = AnimeAdapter { clickedAnime ->
            val fragment = AnimeDetailFragment.newInstance(clickedAnime.malId)


            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .addToBackStack(null)
                .commit()

            binding.recyclerView.isVisible = false
            binding.fragmentContainer.isVisible = true
        }
        recyclerView.adapter = animeAdapter

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val visibleItemCount = layoutManager.childCount
                    val totalItemCount = layoutManager.itemCount
                    val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount &&
                        firstVisibleItemPosition >= 0
                    ) {
                        viewModel.fetchAll()
                    }
                }
            }
        })

        viewModel.fetchAll()

        viewModel.animes.observe(this) { animeList ->
            if (animeList.isNotEmpty()) {
                lifecycleScope.launch {
                    delay(2000)
                    loading.cancelAnimation()
                    loading.isVisible = false

                    recyclerView.isVisible = true
                    animeAdapter.submitList(animeList)
                }
            }
        }

        onBackPressedDispatcher.addCallback(this) {
            if (supportFragmentManager.backStackEntryCount > 0) {
                supportFragmentManager.popBackStack()
                binding.recyclerView.isVisible = true
                binding.fragmentContainer.isVisible = false
            } else {
                finish()
            }
        }
    }

}
