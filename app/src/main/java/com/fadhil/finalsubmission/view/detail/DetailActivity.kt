package com.fadhil.finalsubmission.view.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import coil.load
import com.bumptech.glide.Glide
import com.fadhil.finalsubmission.R
import com.fadhil.finalsubmission.databinding.ActivityDetailBinding
import com.fadhil.finalsubmission.storage.database.story.StoryEntity

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setView()

        supportActionBar?.let {

            it.title = resources.getString(R.string.detailstory)
        }
    }

    private fun setView() = with(binding) {

        val story = intent.getParcelableExtra<StoryEntity>(EXTRA_STORY) as StoryEntity
        Glide.with(this@DetailActivity)
            .load(story.photoUrl)
            .into(itemPhoto)
        tvItemName.text = story.name
        tvDescription.text = story.description
    }



    companion object {
        const val EXTRA_STORY = "extra_story"
    }

}