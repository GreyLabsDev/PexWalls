package com.greylabsdev.pexwalls.presentation.screen.categoryphotos.list

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.greylabsdev.pexwalls.R
import com.greylabsdev.pexwalls.presentation.ext.inflate
import com.greylabsdev.pexwalls.presentation.model.PhotoModel

class PhotoGridAdapter(
    private val itemWidth: Int,
    private val itemHeight: Int
): RecyclerView.Adapter<PhotoGridViewHolder>() {

    var photos: List<PhotoModel> = listOf()

    fun updatePhotos(newPhotos: List<PhotoModel>) {
        photos = photos.toMutableList().apply { addAll(newPhotos) }.toList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoGridViewHolder {
        val view = parent.inflate(R.layout.item_photo, parent, false)
        return PhotoGridViewHolder(view, itemWidth, itemHeight)
    }

    override fun getItemCount(): Int = photos.size

    override fun onBindViewHolder(holder: PhotoGridViewHolder, position: Int) {
        holder.bind(photos[position])
    }

}