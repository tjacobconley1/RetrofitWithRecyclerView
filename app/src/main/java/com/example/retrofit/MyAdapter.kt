package com.example.retrofit

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row_items.view.*


// This class only needs to be called from the main activity
//
class MyAdapter(val context: Context, val userList: List<MyDataItem>): RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    // this class associated the data holder variables
    // to the corresponding text views
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var userId: TextView
        var title: TextView
        // must init each element that will be
        // pulled from the data class into
        // the UI
        init{
            userId = itemView.userId
            title = itemView.title
        }


    }

    // returns the inflated view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView = LayoutInflater.from(context).inflate(R.layout.row_items, parent, false)
        return ViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.userId.text = userList[position].userId.toString()
        holder.title.text = userList[position].title
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}