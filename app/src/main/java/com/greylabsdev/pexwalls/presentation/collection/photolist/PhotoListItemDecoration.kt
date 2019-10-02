package com.greylabsdev.pexwalls.presentation.collection.photolist

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class PhotoListItemDecoration (private val offset: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.left = offset
        outRect.right = offset
        outRect.bottom = offset / 2
        if (parent.getChildLayoutPosition(view) == 0) outRect.top = offset
        else outRect.top = offset / 2

    }

}