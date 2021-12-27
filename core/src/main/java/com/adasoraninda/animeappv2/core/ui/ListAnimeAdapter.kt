package com.adasoraninda.animeappv2.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.adasoraninda.animeappv2.core.R
import com.adasoraninda.animeappv2.core.databinding.ItemAnimeBinding
import com.adasoraninda.animeappv2.core.domain.model.Anime

class ListAnimeAdapter : PagingDataAdapter<Anime, ListAnimeAdapter.AnimeViewHolder>(DIFF_CALLBACK) {

    var onItemClick: ((id: Int?) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder =
        AnimeViewHolder.from(parent)

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        val anime = getItem(position)
        holder.bind(anime, onItemClick)
    }

    class AnimeViewHolder private constructor(private val binding: ItemAnimeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(anime: Anime?, clickListener: ((id: Int?) -> Unit)?) {
            val context = binding.root.context

            binding.textTitle.text = anime?.title
            binding.textMembers.text = context.getString(
                R.string.member_format,
                anime?.members
            )
            binding.textDescription.text =
                context.getString(
                    R.string.stats_format,
                    anime?.type,
                    anime?.episodes,
                    anime?.score
                )

            binding.imagePoster.load(anime?.imageUrl) {
                crossfade(true)
                placeholder(R.drawable.ic_image_load)
                error(R.drawable.ic_image_error)
            }

            binding.root.setOnClickListener {
                clickListener?.invoke(anime?.malId)
            }
        }

        companion object {
            fun from(parent: ViewGroup): AnimeViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemAnimeBinding.inflate(inflater, parent, false)
                return AnimeViewHolder(binding)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Anime>() {
            override fun areItemsTheSame(oldItem: Anime, newItem: Anime): Boolean =
                oldItem.malId == newItem.malId

            override fun areContentsTheSame(oldItem: Anime, newItem: Anime): Boolean =
                oldItem == newItem
        }
    }

}