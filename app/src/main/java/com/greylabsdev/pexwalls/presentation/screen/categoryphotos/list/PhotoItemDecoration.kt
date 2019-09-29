package com.greylabsdev.pexwalls.presentation.screen.categoryphotos.list

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.greylabsdev.pexwalls.presentation.ext.isEven
import com.greylabsdev.pexwalls.presentation.ext.isOdd
import com.greylabsdev.pexwalls.presentation.ext.orNot
import com.greylabsdev.pexwalls.presentation.ext.then

class PhotoItemDecoration(private val offset: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        when {
            parent.getChildLayoutPosition(view) == 0 -> {
                outRect.top = offset
                outRect.left = offset
                outRect.right = offset / 2
            }
            parent.getChildLayoutPosition(view) == 1 -> {
                outRect.right = offset
                outRect.top = offset
            }
            parent.getChildAdapterPosition(view).isOdd() -> {
                outRect.top = offset / 2
                outRect.right = offset
            }
            parent.getChildAdapterPosition(view).isEven() -> {
                outRect.top = offset / 2
                outRect.left = offset
                outRect.right = offset / 2
            }
        }
    }

}