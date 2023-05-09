package com.example.projemanag.activities

import android.os.Bundle
import com.example.projemanag.R
import com.example.projemanag.models.Board
import com.example.projemanag.utilis.Constants
import kotlinx.android.synthetic.main.activity_card_details.*

class CardDetailsActivity : BaseActivity() {

    // A global variable for board details
    private lateinit var mBoardDetails: Board
    // A global variable for task item position
    private var mTaskListPosition: Int = -1
    // A global variable for card item position
    private var mCardPosition: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_details)
        getIntentData()
        setupActionBar()
    }

    private fun setupActionBar() {

        setSupportActionBar(toolbar_card_details_activity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)

            actionBar.title = mBoardDetails.taskList[mTaskListPosition].cards[mCardPosition].name

        }

        toolbar_card_details_activity.setNavigationOnClickListener { onBackPressed() }
    }


    // A function to get all the data that is sent through intent.
    private fun getIntentData() {

        if (intent.hasExtra(Constants.BOARD_DETAIL)) {
            mBoardDetails = intent.getParcelableExtra(Constants.BOARD_DETAIL)!!
        }
        if (intent.hasExtra(Constants.TASK_LIST_ITEM_POSITION)) {
            mTaskListPosition = intent.getIntExtra(Constants.TASK_LIST_ITEM_POSITION, -1)
        }
        if (intent.hasExtra(Constants.CARD_LIST_ITEM_POSITION)) {
            mCardPosition = intent.getIntExtra(Constants.CARD_LIST_ITEM_POSITION, -1)
        }
    }
}