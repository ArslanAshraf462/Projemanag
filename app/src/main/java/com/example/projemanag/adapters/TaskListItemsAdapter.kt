package com.example.projemanag.adapters

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projemanag.R
import com.example.projemanag.activities.TaskListActivity
import com.example.projemanag.models.Task
import kotlinx.android.synthetic.main.item_task.view.*

open class TaskListItemsAdapter(
    private val context: Context,
    private var list: ArrayList<Task>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


     // Inflates the item views which is designed in xml layout file

//      create a new
//      {@link ViewHolder} and initializes some private fields to be used by RecyclerView.

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        //  Here we have done some additional changes to display the item of the task list item in 70% of the screen size.
        val view = LayoutInflater.from(context).inflate(R.layout.item_task, parent, false)
        // Here the layout params are converted dynamically according to the screen size as width is 70% and height is wrap_content.
        val layoutParams = LinearLayout.LayoutParams(
            (parent.width * 0.7).toInt(),
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        // Here the dynamic margins are applied to the view.
        layoutParams.setMargins((15.toDp()).toPx(), 0, (40.toDp()).toPx(), 0)
        view.layoutParams = layoutParams

        return MyViewHolder(view)
    }

    // Binds each item in the ArrayList to a view

//      Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
//      an item.

//      This new ViewHolder should be constructed with a new View that can represent the items
//      of the given type. You can either create a new View manually or inflate it from an XML
//      layout file.

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val model = list[position]

        if (holder is MyViewHolder) {

            if (position == list.size - 1) {
                holder.itemView.tv_add_task_list.visibility = View.VISIBLE
                holder.itemView.ll_task_item.visibility = View.GONE
            } else {
                holder.itemView.tv_add_task_list.visibility = View.GONE
                holder.itemView.ll_task_item.visibility = View.VISIBLE
            }

            holder.itemView.tv_task_list_title.text = model.title
            holder.itemView.tv_add_task_list.setOnClickListener {
                holder.itemView.tv_add_task_list.visibility = View.GONE
                holder.itemView.cv_add_task_list_name.visibility = View.VISIBLE
            }

            holder.itemView.ib_close_list_name.setOnClickListener {
                holder.itemView.tv_add_task_list.visibility = View.VISIBLE
                holder.itemView.cv_add_task_list_name.visibility = View.GONE
            }

            holder.itemView.ib_done_list_name.setOnClickListener {
                val listName = holder.itemView.et_task_list_name.text.toString()
                if (listName.isNotEmpty()){
                    if (context is TaskListActivity){
                        context.createTaskList(listName)
                    }
                }else{
                    Toast.makeText(
                        context,
                        "Please Enter List Name",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        holder.itemView.ib_edit_list_name.setOnClickListener {
            holder.itemView.et_edit_task_list_name.setText(model.title)
            holder.itemView.ll_title_view.visibility = View.GONE
            holder.itemView.cv_edit_task_list_name.visibility = View.VISIBLE
        }

        holder.itemView.ib_close_editable_view.setOnClickListener {
            holder.itemView.ll_title_view.visibility = View.VISIBLE
            holder.itemView.cv_edit_task_list_name.visibility = View.GONE
        }

        holder.itemView.ib_done_edit_list_name.setOnClickListener {
            val listName = holder.itemView.et_edit_task_list_name.text.toString()
            if (listName.isNotEmpty()){
                if (context is TaskListActivity){
                    context.updateTaskList(position, listName, model)
                }
            }else{
                Toast.makeText(
                    context,
                    "Please Enter a List Name",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        holder.itemView.ib_delete_list.setOnClickListener {
            alertDialogForDeleteList(position, model.title)
        }

        holder.itemView.tv_add_card.setOnClickListener {
            holder.itemView.tv_add_card.visibility = View.GONE
            holder.itemView.cv_add_card.visibility = View.VISIBLE
        }

        holder.itemView.ib_close_card_name.setOnClickListener {
            holder.itemView.tv_add_card.visibility = View.VISIBLE
            holder.itemView.cv_add_card.visibility = View.GONE
        }

        holder.itemView.ib_done_card_name.setOnClickListener {
            val cardName = holder.itemView.et_card_name.text.toString()
            if (cardName.isNotEmpty()){
                if (context is TaskListActivity){
                    context.addCardToTaskList(position, cardName)
                }
            }else{
                Toast.makeText(
                    context,
                    "Please Enter a Card Name",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        holder.itemView.rv_card_list.layoutManager = LinearLayoutManager(context)
        holder.itemView.rv_card_list.setHasFixedSize(true)

        val adapter = CardListItemsAdapter(context, model.cards)
        holder.itemView.rv_card_list.adapter = adapter
            adapter.setOnClickListener(object :
                CardListItemsAdapter.OnClickListener {
                override fun onClick(cardPosition: Int) {

                    if (context is TaskListActivity) {
                        context.cardDetails(position, cardPosition)
                    }
                }
            })
        }
    }

    private fun alertDialogForDeleteList(position: Int, title: String){
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Alert")
        builder.setMessage("Are you sure you want to delete $title.")
        builder.setIcon(android.R.drawable.ic_dialog_alert)
        builder.setPositiveButton("Yes"){
            dialogInterface, which ->
            dialogInterface.dismiss()
            if (context is TaskListActivity) {
                context.deleteTaskList(position)
            }
        }
        builder.setNegativeButton("No") {
            dialogInterface, which ->
            dialogInterface.dismiss()
        }

        val alertDialog : AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    // Gets the number of items in the list

    override fun getItemCount(): Int {
        return list.size
    }

    // A function to get density pixel from pixel

    private fun Int.toDp(): Int =
        (this / Resources.getSystem().displayMetrics.density).toInt()


     // A function to get pixel from density pixel
    private fun Int.toPx(): Int =
        (this * Resources.getSystem().displayMetrics.density).toInt()

     // A ViewHolder describes an item view and metadata about its place within the RecyclerView.

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
}