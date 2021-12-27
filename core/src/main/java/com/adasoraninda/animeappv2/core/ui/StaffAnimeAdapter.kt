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
import com.adasoraninda.animeappv2.core.databinding.ItemStaffBinding
import com.adasoraninda.animeappv2.core.domain.model.AnimeStaff

class StaffAnimeAdapter :
    PagingDataAdapter<AnimeStaff, StaffAnimeAdapter.StaffViewHolder>(DIFF_CALLBACK) {

    class StaffViewHolder(private val binding: ItemStaffBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(staff: AnimeStaff?) {
            val context = binding.root.context

            binding.textName.text = staff?.name
            binding.textJob.text = staff?.positions

            binding.imageStaff.load(staff?.imageUrl) {
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
            fun from(parent: ViewGroup): StaffViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemStaffBinding.inflate(inflater, parent, false)
                return StaffViewHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StaffViewHolder =
        StaffViewHolder.from(parent)

    override fun onBindViewHolder(holder: StaffViewHolder, position: Int) {
        val staff = getItem(position)
        holder.bind(staff)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<AnimeStaff>() {
            override fun areItemsTheSame(oldItem: AnimeStaff, newItem: AnimeStaff): Boolean {
                return oldItem.malId == newItem.malId
            }

            override fun areContentsTheSame(oldItem: AnimeStaff, newItem: AnimeStaff): Boolean {
                return oldItem == newItem
            }
        }

    }
}