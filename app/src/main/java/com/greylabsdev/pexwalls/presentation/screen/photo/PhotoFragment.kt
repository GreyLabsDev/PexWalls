package com.greylabsdev.pexwalls.presentation.screen.photo

import android.graphics.Outline
import android.view.View
import android.view.ViewOutlineProvider
import com.bumptech.glide.Glide
import com.greylabsdev.pexwalls.R
import com.greylabsdev.pexwalls.presentation.base.BaseFragment
import com.greylabsdev.pexwalls.presentation.const.Consts
import com.greylabsdev.pexwalls.presentation.ext.argSerializable
import com.greylabsdev.pexwalls.presentation.ext.dpToPix
import com.greylabsdev.pexwalls.presentation.model.PhotoModel
import com.greylabsdev.pexwalls.presentation.view.PlaceholderView
import kotlinx.android.synthetic.main.fragment_photo.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PhotoFragment : BaseFragment(
    layoutResId = R.layout.fragment_photo,
    hasToolbarBackButton = true
) {
    override val viewModel by viewModel<PhotoViewModel>{
        parametersOf(photoModel)
    }
    override val toolbarTitle: String? = null
    override val placeholderView: PlaceholderView? by lazy { placeholder_view }
    override val contentView: View? by lazy { content_ll }

    private val photoModel: PhotoModel by argSerializable<PhotoModel>(ARG_KEY)
    private val photoCornerRadius by lazy { requireActivity().dpToPix(Consts.DEFAULT_CORNER_RADIUS_DP) }
    private val outlineProvider by lazy {
        object : ViewOutlineProvider() {
            override fun getOutline(view: View, outline: Outline) {
                outline.setRoundRect(0, 0, view.width, view.height, photoCornerRadius)
            }
        }
    }

    override fun initViews() {
        Glide.with(photo_iv)
            .load(photoModel.bigPhotoUrl)
            .into(photo_iv)
        photo_iv.outlineProvider = outlineProvider
        photo_iv.clipToOutline = true
    }

    override fun initListeners() {

    }

    companion object {
        const val ARG_KEY = "photo"
    }
}