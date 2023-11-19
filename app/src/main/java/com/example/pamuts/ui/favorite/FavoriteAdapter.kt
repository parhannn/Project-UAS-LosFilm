package com.example.pamuts.ui.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.pamuts.R

class FavoriteAdapter(private var list: ArrayList<FavoriteData>) : RecyclerView.Adapter<FavoriteAdapter.MyViewHolder>() {

    var onItemClick : ((FavoriteData) -> Unit)? = null

    fun setFilteredList(list: ArrayList<FavoriteData>){
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.fav_item , parent , false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = list[position]
        holder.ivMovieImage.setImageResource(currentItem.favMovieImage)
        holder.tvMovieName.text = currentItem.favMovieName

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(currentItem)
        }
    }

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val ivMovieImage : AppCompatImageView = itemView.findViewById(R.id.favMovieImage)
        val tvMovieName : TextView = itemView.findViewById(R.id.favMovieTitle)
    }
}