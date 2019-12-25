package com.greylabsdev.pexwalls.presentation.screen.photo

import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import com.greylabsdev.pexwalls.R
import com.greylabsdev.pexwalls.presentation.base.BaseFragment
import com.greylabsdev.pexwalls.presentation.base.ProgressState
import com.greylabsdev.pexwalls.presentation.ext.argSerializable
import com.greylabsdev.pexwalls.presentation.ext.dpToPix
import com.greylabsdev.pexwalls.presentation.ext.setHeight
import com.greylabsdev.pexwalls.presentation.ext.setTint
import com.greylabsdev.pexwalls.presentation.model.PhotoModel
import com.greylabsdev.pexwalls.presentation.view.PlaceholderView
import kotlinx.android.synthetic.main.bottom_sheet_photo_actions.view.disappearing_container_ll
import kotlinx.android.synthetic.main.bottom_sheet_photo_actions.view.download_btn
import kotlinx.android.synthetic.main.bottom_sheet_photo_actions.view.img_slide
import kotlinx.android.synthetic.main.bottom_sheet_photo_actions.view.img_slide_upside_down
import kotlinx.android.synthetic.main.bottom_sheet_photo_actions.view.navbar_bottom_spacer_v
import kotlinx.android.synthetic.main.bottom_sheet_photo_actions.view.navbar_top_spacer_v
import kotlinx.android.synthetic.main.bottom_sheet_photo_actions.view.photographer_title_tv
import kotlinx.android.synthetic.main.bottom_sheet_photo_actions.view.photographer_tv
import kotlinx.android.synthetic.main.bottom_sheet_photo_actions.view.resolution_tv
import kotlinx.android.synthetic.main.bottom_sheet_photo_actions.view.wallpaper_btn
import kotlinx.android.synthetic.main.fragment_photo.back_btn_ll
import kotlinx.android.synthetic.main.fragment_photo.back_iv
import kotlinx.android.synthetic.main.fragment_photo.bottom_sheet
import kotlinx.android.synthetic.main.fragment_photo.content_ll
import kotlinx.android.synthetic.main.fragment_photo.like_btn_iv
import kotlinx.android.synthetic.main.fragment_photo.photo_iv
import kotlinx.android.synthetic.main.fragment_photo.placeholder_view
import kotlinx.android.synthetic.main.fragment_photo.root_cl
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PhotoFragment : BaseFragment(
    layoutResId = R.layout.fragment_photo,
    hasToolbarBackButton = true,
    transparentStatusBar = true
) {
    override val viewModel by viewModel<PhotoViewModel> {
        parametersOf(photoModel)
    }
    override val toolbarTitle: String? = null
    override val placeholderView: PlaceholderView? by lazy { placeholder_view }
    override val contentView: View? by lazy { content_ll }

    private val photoModel: PhotoModel by argSerializable(ARG_KEY)

    override fun initViews() {
        bottom_sheet.setOnApplyWindowInsetsListener { view, windowInsets ->
            setupNeededPeekHeight(windowInsets.systemWindowInsetBottom)
            windowInsets
        }
        bottom_sheet.navbar_bottom_spacer_v.setOnApplyWindowInsetsListener { view, windowInsets ->
            view.setHeight(windowInsets.systemWindowInsetBottom)
            windowInsets
        }
        bottom_sheet.navbar_top_spacer_v.setOnApplyWindowInsetsListener { view, windowInsets ->
            view.setHeight(windowInsets.systemWindowInsetBottom)
            windowInsets
        }
        bottom_sheet.photographer_tv.text = photoModel.photographer
        bottom_sheet.resolution_tv.text = "${photoModel.width} x ${photoModel.height}"
        Glide.with(photo_iv)
            .load(photoModel.bigPhotoUrl)
            .transform(CenterCrop())
            .into(photo_iv)
        back_iv.setTint(R.color.colorLight)
        bottom_sheet.navbar_bottom_spacer_v.isVisible = true
    }

    override fun initListeners() {
        back_btn_ll.setOnClickListener { navigateBack() }
        like_btn_iv.setOnClickListener { viewModel.switchPhotoInFavoritesState() }
        bottom_sheet.download_btn.setOnClickListener {
            requestStoragePermissionWithAction {
                viewModel.downloadPhoto(useOriginalResolution = true)
            }
        }
        bottom_sheet.wallpaper_btn.setOnClickListener {
            requestStoragePermissionWithAction {
                viewModel.downloadPhoto(setAsWallpaper = true)
            }
        }
        val bottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet)
        bottomSheetBehavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                bottom_sheet.img_slide.animate()
                    .alpha(1 - slideOffset)
                    .setDuration(0)
                    .start()
                bottom_sheet.img_slide_upside_down.animate()
                    .alpha(0 + slideOffset)
                    .setDuration(0)
                    .start()
                bottom_sheet.disappearing_container_ll.animate()
                    .alpha(0 + slideOffset)
                    .setDuration(0)
                    .start()
            }
            override fun onStateChanged(bottomSheet: View, state: Int) {}
        })
    }

    override fun initViewModelObserving() {
        viewModel.isPhotoFavorite.observe(this, Observer { inFavorites ->
            if (inFavorites) like_btn_iv.setImageResource(R.drawable.ic_favorite_fill)
            else like_btn_iv.setImageResource(R.drawable.ic_favorite_outline)
        })
        viewModel.progressState.observe(this, Observer { state ->
            when (state) {
                is ProgressState.DONE -> {
                    val snack = Snackbar.make(root_cl, state.doneMessage ?: "", Snackbar.LENGTH_SHORT)
                    ViewCompat.setOnApplyWindowInsetsListener(snack.view) { v, insets ->
                        v.setPadding(v.paddingLeft, v.paddingTop, v.paddingRight, v.paddingTop)
                        val params = v.layoutParams as ViewGroup.MarginLayoutParams
                        params.setMargins(
                            params.leftMargin,
                            params.topMargin,
                            params.rightMargin,
                            params.bottomMargin + insets.systemWindowInsetBottom)
                        v.layoutParams = params
                        insets
                    }
                    snack.show()
                }
                is ProgressState.INITIAL -> {
                    val snack = Snackbar.make(root_cl, state.initialMessage ?: "", Snackbar.LENGTH_SHORT)
                    ViewCompat.setOnApplyWindowInsetsListener(snack.view) { v, insets ->
                        v.setPadding(v.paddingLeft, v.paddingTop, v.paddingRight, v.paddingTop)
                        val params = v.layoutParams as ViewGroup.MarginLayoutParams
                        params.setMargins(
                            params.leftMargin,
                            params.topMargin,
                            params.rightMargin,
                            params.bottomMargin + insets.systemWindowInsetBottom)
                        v.layoutParams = params
                        insets
                    }
                    snack.show()
                }
            }
        })
    }

    private fun setupNeededPeekHeight(bottomInsetns: Int) {
        val allMargins = requireContext().dpToPix(55)
        bottom_sheet.photographer_title_tv.measure(0, 0)
        bottom_sheet.photographer_tv.measure(0, 0)
        val photographerTitleHeight = bottom_sheet.photographer_title_tv.measuredHeight
        val photographerTextHeight = bottom_sheet.photographer_tv.measuredHeight
        val finalPeekHeight = allMargins + photographerTitleHeight + photographerTextHeight + bottomInsetns
        val bottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet)
        bottomSheetBehavior.peekHeight = finalPeekHeight.toInt()
    }

    companion object {
        const val ARG_KEY = "photo"
    }
}
