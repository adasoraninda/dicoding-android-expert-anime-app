package com.adasoraninda.animeappv2.core.ui

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.adasoraninda.animeappv2.core.R
import com.adasoraninda.animeappv2.core.databinding.ItemCharacterBinding
import com.adasoraninda.animeappv2.core.domain.model.AnimeCharacter

class CharacterAnimeAdapter : PagingDataAdapter
<AnimeCharacter, CharacterAnimeAdapter.CharacterViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder =
        CharacterViewHolder.from(parent)

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = getItem(position)
        holder.bind(character)
    }

    class CharacterViewHolder(private val binding: ItemCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(character: AnimeCharacter?) {
            val context = binding.root.context

            val voiceActor = character?.voiceActor

            binding.textCharName.text = character?.name
            binding.textCharJob.text = character?.role
            binding.imageCharacter.load(character?.imageUrl) {
                crossfade(true)
                placeholder(R.drawable.ic_image_load)
                error(R.drawable.ic_image_error)
            }

            binding.textActorName.text = voiceActor?.name
            binding.textActorLanguage.text = voiceActor?.language
            binding.imageActor.load(voiceActor?.imageUrl) {
                crossfade(true)
                placeholder(R.drawable.ic_image_load)
                error(R.drawable.ic_image_error)
            }

            val backgroundColor = getBackgroundColor(context, bindingAdapterPosition)
            binding.cardLayout.setCardBackgroundColor(backgroundColor)
        }

        private fun getBackgroundColor(context: Context, position: Int): ColorStateList {
            val colorId = if (position % 2 == 1) {
                R.color.card_background_2
            } else {
                R.color.card_background_1
            }

            val color = ContextCompat.getColor(context, colorId)

            return ColorStateList.valueOf(color)
        }

        companion object {
            fun from(parent: ViewGroup): CharacterViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemCharacterBinding.inflate(inflater, parent, false)
                return CharacterViewHolder(binding)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<AnimeCharacter>() {
            override fun areItemsTheSame(
                oldItem: AnimeCharacter,
                newItem: AnimeCharacter
            ): Boolean {
                return oldItem.malId == newItem.malId
            }

            override fun areContentsTheSame(
                oldItem: AnimeCharacter,
                newItem: AnimeCharacter
            ): Boolean {
                return oldItem == newItem
            }
        }
    }


}