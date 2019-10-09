package com.greylabsdev.pexwalls.presentation.screen.photo

import android.view.*
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.greylabsdev.pexwalls.R
import com.greylabsdev.pexwalls.presentation.base.BaseFragment
import com.greylabsdev.pexwalls.presentation.ext.argSerializable
import com.greylabsdev.pexwalls.presentation.ext.getScreenResolution
import com.greylabsdev.pexwalls.presentation.ext.setTint
import com.greylabsdev.pexwalls.presentation.model.PhotoModel
import com.greylabsdev.pexwalls.presentation.view.PlaceholderView
import kotlinx.android.synthetic.main.fragment_photo.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PhotoFragment : BaseFragment(
    layoutResId = R.layout.fragment_photo,
    hasToolbarBackButton = true,
    transparentStatusBar = true
) {
    override val viewModel by viewModel<PhotoViewModel>{
        parametersOf(photoModel)
    }
    override val toolbarTitle: String? = null
    override val placeholderView: PlaceholderView? by lazy { placeholder_view }
    override val contentView: View? by lazy { content_ll }

    private val photoModel: PhotoModel by argSerializable<PhotoModel>(ARG_KEY)

    override fun initViews() {
        Glide.with(photo_iv)
            .load(photoModel.bigPhotoUrl)
            .transform(CenterCrop())
            .into(photo_iv)
        back_iv.setTint(R.color.colorLight)
    }

    override fun initListeners() {
        back_btn_ll.setOnClickListener { navigateBack() }
        download_btn_iv.setOnClickListener { viewModel.downloadPhoto(requireContext().getScreenResolution()) }
        like_btn_iv.setOnClickListener { viewModel.switchPhotoInFavoritesState() }
    }

    override fun initViewModelObserving() {
        super.initViewModelObserving()
        viewModel.isPhotoFavorite.observe(this, Observer { inFavorites ->
            if (inFavorites) like_btn_iv.setImageResource(R.drawable.ic_favorite_fill)
            else like_btn_iv.setImageResource(R.drawable.ic_favorite_outline)
        })
    }

    companion object {
        const val ARG_KEY = "photo"
    }
}