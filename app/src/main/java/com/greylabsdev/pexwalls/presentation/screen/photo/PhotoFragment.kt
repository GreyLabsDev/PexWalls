package com.greylabsdev.pexwalls.presentation.screen.photo

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.view.*
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import com.greylabsdev.pexwalls.R
import com.greylabsdev.pexwalls.presentation.base.BaseFragment
import com.greylabsdev.pexwalls.presentation.base.ProgressState
import com.greylabsdev.pexwalls.presentation.ext.argSerializable
import com.greylabsdev.pexwalls.presentation.ext.setTint
import com.greylabsdev.pexwalls.presentation.model.PhotoModel
import com.greylabsdev.pexwalls.presentation.view.PlaceholderView
import kotlinx.android.synthetic.main.bottom_sheet_photo_actions.view.*
import kotlinx.android.synthetic.main.fragment_photo.*
import kotlinx.android.synthetic.main.view_placeholder.*
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

    private val photoModel: PhotoModel by argSerializable(ARG_KEY)

    override fun initViews() {
        bottom_sheet.photographer_tv.text = photoModel.photographer
        bottom_sheet.resolution_tv.text = "${photoModel.width} x ${photoModel.height}"
        Glide.with(photo_iv)
            .load(photoModel.bigPhotoUrl)
            .transform(CenterCrop())
            .into(photo_iv)
        back_iv.setTint(R.color.colorLight)
    }

    override fun initListeners() {
        back_btn_ll.setOnClickListener { navigateBack() }
        like_btn_iv.setOnClickListener { viewModel.switchPhotoInFavoritesState() }
        bottom_sheet.download_btn.setOnClickListener {
            requestStoragePermissionWithAction {
                viewModel.downloadPhoto(true)
            }
        }
        bottom_sheet.wallpaper_btn.setOnClickListener {
            requestStoragePermissionWithAction {
                viewModel.downloadPhoto()
            }
        }
        val bottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet)
        bottomSheetBehavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                bottom_sheet.img_slide.animate()
                    .alpha(1-slideOffset)
                    .setDuration(0)
                    .start()
                bottom_sheet.img_slide_upside_down.animate()
                    .alpha(0+slideOffset)
                    .setDuration(0)
                    .start()
            }
            override fun onStateChanged(bottomSheet: View, state: Int) {
            }
        })
    }

    override fun initViewModelObserving() {
//        super.initViewModelObserving()
        viewModel.isPhotoFavorite.observe(this, Observer { inFavorites ->
            if (inFavorites) like_btn_iv.setImageResource(R.drawable.ic_favorite_fill)
            else like_btn_iv.setImageResource(R.drawable.ic_favorite_outline)
        })
        viewModel.progressState.observe(this, Observer {state ->
            when (state) {
               is ProgressState.DONE -> {
                   Snackbar.make(root_cl, state.doneMessage ?: "", Snackbar.LENGTH_SHORT).show()
               }
            }
        })

    }

    companion object {
        const val ARG_KEY = "photo"
    }
}