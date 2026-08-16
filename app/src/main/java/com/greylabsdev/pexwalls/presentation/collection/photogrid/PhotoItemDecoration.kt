package com.greylabsdev.pexwalls.presentation.collection.photogrid

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class PhotoItemDecoration(private val offset: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val halfGap = offset / 4
        val edgeGap = offset
        val topGap = offset / 2

        val layoutParams = view.layoutParams
        if (layoutParams is StaggeredGridLayoutManager.LayoutParams) {
            if (layoutParams.isFullSpan) {
                outRect.set(edgeGap, topGap, edgeGap, halfGap)
                return
            }
            when (layoutParams.spanIndex) {
                0 -> outRect.set(edgeGap, topGap, halfGap, halfGap)
                else -> outRect.set(halfGap, topGap, edgeGap, halfGap)
            }
            return
        }

        val position = parent.getChildAdapterPosition(view)
        if (position == RecyclerView.NO_POSITION) return

        when {
            position == 0 -> outRect.set(edgeGap, topGap, halfGap, halfGap)
            position == 1 -> outRect.set(halfGap, topGap, edgeGap, halfGap)
            position.isEven() -> outRect.set(edgeGap, halfGap, halfGap, halfGap)
            else -> outRect.set(halfGap, halfGap, edgeGap, halfGap)
        }
    }

    private fun Int.isEven(): Boolean = this % 2 == 0
}
