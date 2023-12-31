package com.example.pamuts.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.pamuts.R

class MyAdapter(private var list: ArrayList<MovieData>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    var onItemClick : ((MovieData) -> Unit)? = null

    fun setFilteredList(list: ArrayList<MovieData>){
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item , parent , false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = list[position]
        holder.ivMovieImage.setImageResource(currentItem.movieImage)
        holder.tvMovieName.text = currentItem.movieName

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(currentItem)
        }
    }

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val ivMovieImage : AppCompatImageView = itemView.findViewById(R.id.movieImage)
        val tvMovieName : TextView = itemView.findViewById(R.id.movieTitle)
    }
}