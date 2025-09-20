package com.ajitjain.animesearch.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.activityViewModels
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ajitjain.animesearch.R
import com.ajitjain.animesearch.databinding.ActivityAnimeDetailBinding
import com.ajitjain.animesearch.viewmodel.AnimeViewModel
import com.bumptech.glide.Glide
import com.google.android.material.chip.Chip

class AnimeDetailFragment : Fragment() {

    companion object {
        private const val ARG_MAL_ID = "mal_id"

        fun newInstance(malId: Int): AnimeDetailFragment {
            val fragment = AnimeDetailFragment()
            val args = Bundle()
            args.putInt(ARG_MAL_ID, malId)
            fragment.arguments = args
            return fragment
        }
    }

    private var malId: Int? = null
    private var trailerUrl : String? = null

    private var _binding: ActivityAnimeDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AnimeViewModel by activityViewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return AnimeViewModel(requireActivity().application) as T
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        malId = arguments?.getInt(ARG_MAL_ID)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ActivityAnimeDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        malId?.let {
            viewModel.fetchAnimeDetail(it)
        }

        viewModel.animeDetail.observe(viewLifecycleOwner) { anime ->
            binding.collapsingToolbar.title = anime.title
            binding.tvDetailRating.text = anime.score.toString()
            binding.tvDetailEpisodes.text = "${anime.episodes} Episodes"
            binding.tvDetailSynopsis.text = anime.synopsis

            Log.d("ajit","trailer--"+anime.trailerUrl)
            trailerUrl = anime.trailerUrl

            Glide.with(this)
                .load(anime.imageUrl)
                .into(binding.ivDetailPoster)


            binding.chipgroupGenres.removeAllViews()
            anime.genres.forEach { genre ->
                val chip = Chip(requireContext())
                chip.text = genre.name
                chip.isCheckable = false
                binding.chipgroupGenres.addView(chip)
            }
        }

        binding.ivPlayTrailer.setOnClickListener {
            trailerUrl?.let { url ->
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                intent.setPackage("com.google.android.youtube")

                try {
                    startActivity(intent)
                } catch (e: Exception) {
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    startActivity(browserIntent)
                }
            } ?: run {
                Toast.makeText(requireContext(), "Trailer not available", Toast.LENGTH_SHORT).show()
            }
        }


    }
}