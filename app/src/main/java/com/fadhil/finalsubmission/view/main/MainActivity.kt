package com.fadhil.finalsubmission.view.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.fadhil.finalsubmission.R
import com.fadhil.finalsubmission.adapter.LoadingStateAdapter
import com.fadhil.finalsubmission.databinding.ActivityMainBinding
import com.fadhil.finalsubmission.storage.pref.PreferenceDataSource
import com.fadhil.finalsubmission.utils.ViewModelFactory
import com.fadhil.finalsubmission.view.login.LoginActivity
import com.fadhil.finalsubmission.view.maps.MapsActivity
import com.fadhil.finalsubmission.view.upload.UploadActivity


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var storyAdapter: StoryAdapter

    private val pref by lazy {
        PreferenceDataSource.invoke(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setView()
        setObserver()

        supportActionBar?.let {

            it.title = resources.getString(R.string.storyapp)
        }


        binding.btnAddStory.setOnClickListener {
            val mIntent = Intent(this@MainActivity,UploadActivity::class.java)
            startActivity(mIntent)
        }
    }




    private fun setObserver() {
        viewModel.listStoryResponse.observe(this) {
            storyAdapter.submitData(lifecycle, it)
        }
    }

    private fun setView() = with(binding) {
        storyAdapter = StoryAdapter()
        rvList.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
            adapter = storyAdapter.withLoadStateFooter(
                footer = LoadingStateAdapter {
                    storyAdapter.retry()
                }
            )
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.mainmenu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> {
                val alertDialog = AlertDialog.Builder(this)
                alertDialog.setTitle(getString(R.string.message_logout))
                    ?.setPositiveButton(getString(R.string.action_yes)) {_,_ ->
                        pref.deleteDataAuth()
                        val intent = Intent (this,LoginActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                        startActivity(intent)
                        finish()

                    }
                    ?.setNegativeButton(getString(R.string.action_cancel),null)
                val alert = alertDialog.create()
                alert.show()
            }
            R.id.language -> {
                val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(mIntent)
            }
            R.id.maps -> {
                val mIntent = Intent(this@MainActivity,MapsActivity::class.java)
                startActivity(mIntent)
            }

        }
        return true
    }

    override fun onBackPressed() {
        finishAffinity()
        super.onBackPressed()
    }



}