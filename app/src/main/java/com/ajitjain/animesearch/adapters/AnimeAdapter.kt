package com.ajitjain.animesearch.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ajitjain.animesearch.R
import com.ajitjain.animesearch.databinding.ItemAnimeBinding
import com.ajitjain.animesearch.model.AnimeDetail

class AnimeAdapter(
    private val onItemClick: (AnimeDetail) -> Unit
) : ListAdapter<AnimeDetail, AnimeAdapter.AnimeViewHolder>(AnimeDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder {
        val binding = ItemAnimeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AnimeViewHolder(binding)
    }


    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem, onItemClick)
        }
    }


    class AnimeViewHolder(private val binding: ItemAnimeBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(anime: AnimeDetail, onItemClick: (AnimeDetail) -> Unit) {
            binding.apply {
                tvAnimeTitle.text = anime.title

                Log.d("ajit","url"+anime.imageUrl)
                Glide.with(itemView.context)
                    .load(anime.imageUrl)
                    .into(ivAnimePoster)

                tvAnimeEpisodes.text = if (anime.episodes != null && anime.episodes > 0) {
                    itemView.context.getString(R.string.episode_count, anime.episodes)
                } else {
                    itemView.context.getString(R.string.episodes_unknown)
                }

                if (anime.score != null && anime.score > 0) {
                    layoutRatingContainer.visibility = View.VISIBLE
                    tvAnimeRating.text = String.format("%.2f", anime.score)
                } else {
                    layoutRatingContainer.visibility = View.GONE
                }

                itemView.setOnClickListener {
                    onItemClick(anime)
                }
            }
        }
    }


    class AnimeDiffCallback : DiffUtil.ItemCallback<AnimeDetail>() {
        override fun areItemsTheSame(oldItem: AnimeDetail, newItem: AnimeDetail): Boolean {
            return oldItem.malId == newItem.malId
        }

        override fun areContentsTheSame(oldItem: AnimeDetail, newItem: AnimeDetail): Boolean {
            return oldItem == newItem
        }
    }
}