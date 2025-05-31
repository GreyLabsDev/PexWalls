package com.greylabsdev.pexwalls.presentation.collection.photolist

import android.graphics.Outline
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.greylabsdev.pexwalls.databinding.ItemPhotoInListBinding
import com.greylabsdev.pexwalls.presentation.model.PhotoModel

class PhotoListDataViewHolder(
    private val binding: ItemPhotoInListBinding,
    private val itemHeight: Int,
    private val photoCardCornerRadius: Float

) : RecyclerView.ViewHolder(binding.root) {
    private val outlineProvider by lazy {
        object : ViewOutlineProvider() {
            override fun getOutline(view: View, outline: Outline) {
                outline.setRoundRect(0, 0, view.width, view.height, photoCardCornerRadius)
            }
        }
    }

    fun bind(item: PhotoModel) {
        itemView.layoutParams =
            ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, itemHeight)
        Glide.with(itemView.context)
            .load(item.normalPhotoUrl)
            .transform(CenterCrop())
            .into(binding.photoIv)
        binding.authorTv.text = item.photographer
        binding.photoIv.outlineProvider = outlineProvider
        binding.photoIv.clipToOutline = true
    }

    fun setOnClickListener(onClickListener: () -> Unit) {
        binding.photoIv.setOnClickListener { onClickListener.invoke() }
    }
}
