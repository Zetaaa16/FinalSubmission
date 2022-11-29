package com.fadhil.finalsubmission.view.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import coil.load
import com.fadhil.finalsubmission.databinding.ActivityDetailBinding
import com.fadhil.finalsubmission.storage.database.story.StoryEntity

class DetailActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setView()
    }

    private fun setView() = with(binding) {

        val story = intent.getParcelableExtra<StoryEntity>(EXTRA_STORY) as StoryEntity
        itemPhoto.load(story.photoUrl)
        tvItemName.text = story.name
        tvDescription.text = story.description
    }

    companion object {
        const val EXTRA_STORY = "extra_story"
    }
}