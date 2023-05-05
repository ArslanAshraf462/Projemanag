package com.example.projemanag.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.projemanag.R
import com.example.projemanag.databinding.ActivityCreateBoardBinding
import com.example.projemanag.databinding.ActivityMyProfileBinding

class CreateBoardActivity : AppCompatActivity() {
    private var binding : ActivityCreateBoardBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateBoardBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        setupActionBar()
    }

    private fun  setupActionBar(){
        setSupportActionBar(binding!!.toolbarCreateBoardActivity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
            actionBar.title = resources.getString(R.string.create_board_title)
        }

        binding!!.toolbarCreateBoardActivity.setNavigationOnClickListener { onBackPressed() }
    }
}