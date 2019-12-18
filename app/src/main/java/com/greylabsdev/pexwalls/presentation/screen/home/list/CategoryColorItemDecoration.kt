package com.greylabsdev.pexwalls.presentation.screen.home.list

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class CategoryColorItemDecoration(
    private val offset: Int
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        if (parent.getChildLayoutPosition(view) == 0) outRect.left = offset
        else outRect.left = offset / 4
        outRect.right = offset / 4
    }
}
