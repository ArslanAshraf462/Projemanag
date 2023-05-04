package com.example.projemanag.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.projemanag.R
import com.example.projemanag.databinding.ActivityMainBinding
import com.example.projemanag.databinding.ActivityMyProfileBinding
import kotlinx.android.synthetic.main.app_bar_main.*

class MyProfileActivity : BaseActivity() {
    private var binding : ActivityMyProfileBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyProfileBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        setupActionBar()
    }

    private fun  setupActionBar(){
        setSupportActionBar(binding!!.toolbarMyProfileActivity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
            actionBar.title = resources.getString(R.string.my_profile)
        }

        binding!!.toolbarMyProfileActivity.setNavigationOnClickListener { onBackPressed() }
    }
}