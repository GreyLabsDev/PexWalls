package com.greylabsdev.pexwalls.presentation.collection.photogrid

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.greylabsdev.pexwalls.presentation.ext.isEven
import com.greylabsdev.pexwalls.presentation.ext.isOdd

class PhotoItemDecoration(private val offset: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        when {
            parent.getChildLayoutPosition(view) == 0 -> {
                outRect.left = offset
                outRect.right = offset / 4
                outRect.bottom = offset / 4
                outRect.top = offset / 2
            }
            parent.getChildLayoutPosition(view) == 1 -> {
                outRect.left = offset / 4
                outRect.right = offset
                outRect.bottom = offset / 4
                outRect.top = offset / 2
            }
            parent.getChildAdapterPosition(view).isEven() &&
                    parent.getChildLayoutPosition(view) != 1
                    && parent.getChildLayoutPosition(view) != 0 -> {
                outRect.left = offset
                outRect.right = offset / 4
                outRect.bottom = offset / 4
                outRect.top = offset / 4
            }
            parent.getChildAdapterPosition(view).isOdd() &&
                    parent.getChildLayoutPosition(view) != 1
                    && parent.getChildLayoutPosition(view) != 0 -> {
                outRect.right = offset
                outRect.left = offset / 4
                outRect.bottom = offset / 4
                outRect.top = offset / 4
            }
        }
    }
}
