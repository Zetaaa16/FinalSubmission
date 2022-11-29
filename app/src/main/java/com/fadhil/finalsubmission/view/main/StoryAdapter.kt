package com.fadhil.finalsubmission.view.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.fadhil.finalsubmission.databinding.ItemRowStoryBinding
import com.fadhil.finalsubmission.storage.database.story.StoryEntity
import com.fadhil.finalsubmission.view.detail.DetailActivity


class StoryAdapter :
    PagingDataAdapter<StoryEntity, StoryAdapter.StoryViewHolder>(diffCallback) {

    inner class StoryViewHolder(private val binding: ItemRowStoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: StoryEntity?) {
            with(binding) {
                imageview.load(item?.photoUrl)
                textView.text = item?.name
                itemRowStory.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_STORY, item)

                    itemView.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder =
        StoryViewHolder(
            ItemRowStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<StoryEntity>() {
            override fun areItemsTheSame(
                oldItem: StoryEntity,
                newItem: StoryEntity
            ): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: StoryEntity,
                newItem: StoryEntity
            ): Boolean = oldItem == newItem

        }
    }
}