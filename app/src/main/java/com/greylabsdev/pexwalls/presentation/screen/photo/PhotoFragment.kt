package com.greylabsdev.pexwalls.presentation.screen.photo

import android.annotation.SuppressLint
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
import com.greylabsdev.pexwalls.databinding.FragmentPhotoBinding
import com.greylabsdev.pexwalls.presentation.base.BaseFragment
import com.greylabsdev.pexwalls.presentation.base.ProgressState
import com.greylabsdev.pexwalls.presentation.ext.argSerializable
import com.greylabsdev.pexwalls.presentation.ext.dpToPix
import com.greylabsdev.pexwalls.presentation.ext.setHeight
import com.greylabsdev.pexwalls.presentation.ext.setTint
import com.greylabsdev.pexwalls.presentation.model.PhotoModel
import com.greylabsdev.pexwalls.presentation.view.PlaceholderView
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PhotoFragment : BaseFragment<FragmentPhotoBinding>(
    bindingFactory = FragmentPhotoBinding::inflate,
    hasToolbarBackButton = true,
    transparentStatusBar = true
) {
    override val viewModel by viewModel<PhotoViewModel> {
        parametersOf(photoModel)
    }
    override val toolbarTitle: String? = null
    override val placeholderView: PlaceholderView?
        get() = binding?.placeholderView
    override val contentView: View?
        get() = binding?.contentLl

    private val photoModel: PhotoModel by argSerializable(ARG_KEY)

    @SuppressLint("SetTextI18n")
    override fun initViews() {
        binding?.bottomSheet?.root?.setOnApplyWindowInsetsListener { view, windowInsets ->
            setupNeededPeekHeight(windowInsets.systemWindowInsetBottom)
            windowInsets
        }
        binding?.bottomSheet?.navbarBottomSpacerV?.setOnApplyWindowInsetsListener { view, windowInsets ->
            view.setHeight(windowInsets.systemWindowInsetBottom)
            windowInsets
        }
        binding?.bottomSheet?.navbarTopSpacerV?.setOnApplyWindowInsetsListener { view, windowInsets ->
            view.setHeight(windowInsets.systemWindowInsetBottom)
            windowInsets
        }
        binding?.bottomSheet?.photographerTv?.text = photoModel.photographer
        binding?.bottomSheet?.resolutionTv?.text = "${photoModel.width} x ${photoModel.height}"
        binding?.photoIv?.let {
            Glide.with(it).load(photoModel.bigPhotoUrl).transform(CenterCrop()).into(it)
        }

        binding?.backIv?.setTint(R.color.colorLight)
        binding?.bottomSheet?.navbarBottomSpacerV?.isVisible = true
    }

    override fun initListeners() {
        binding?.let { bind ->
            bind.backBtnLl.setOnClickListener { navigateBack() }
            bind.likeBtnIv.setOnClickListener { viewModel.switchPhotoInFavoritesState() }
            bind.bottomSheet.downloadBtn.setOnClickListener {
                requestStoragePermissionWithAction {
                    viewModel.downloadPhoto(useOriginalResolution = true)
                }
            }
            bind.bottomSheet.wallpaperBtn.setOnClickListener {
                requestStoragePermissionWithAction {
                    viewModel.downloadPhoto(setAsWallpaper = true)
                }
            }

//            todo Add saveState for lifecycle safe alpha switching for
//            bind.bottomSheet.disappearingContainerLl
//            Why its happening - cause after changing Wallpaper fragment is restarting (?)
//            after fixing this - uncomment string with alpha = 0f
//            bind.bottomSheet.disappearingContainerLl.alpha = 0f

            val bottomSheetBehavior = BottomSheetBehavior.from(bind.bottomSheet.root)
            bottomSheetBehavior.setBottomSheetCallback(object :
                BottomSheetBehavior.BottomSheetCallback() {
                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    bind.bottomSheet.imgSlide.animate().alpha(1 - slideOffset).setDuration(0)
                        .start()
                    bind.bottomSheet.imgSlideUpsideDown.animate().alpha(0 + slideOffset)
                        .setDuration(0).start()
                    bind.bottomSheet.disappearingContainerLl.animate().alpha(0 + slideOffset)
                        .setDuration(0).start()
                }

                override fun onStateChanged(bottomSheet: View, state: Int) {}
            })
        }
    }

    override fun initViewModelObserving() {
        viewModel.isPhotoFavorite.observe(this, Observer { inFavorites ->
            if (inFavorites) binding?.likeBtnIv?.setImageResource(R.drawable.ic_favorite_fill)
            else binding?.likeBtnIv?.setImageResource(R.drawable.ic_favorite_outline)
        })
        viewModel.progressState.observe(this, Observer { state ->
            binding?.let { bind ->
                when (state) {
                    is ProgressState.DONE -> {
                        val snack = Snackbar.make(
                            bind.rootCl, state.doneMessage ?: "", Snackbar.LENGTH_SHORT
                        )
                        ViewCompat.setOnApplyWindowInsetsListener(snack.view) { v, insets ->
                            v.setPadding(v.paddingLeft, v.paddingTop, v.paddingRight, v.paddingTop)
                            val params = v.layoutParams as ViewGroup.MarginLayoutParams
                            params.setMargins(
                                params.leftMargin,
                                params.topMargin,
                                params.rightMargin,
                                params.bottomMargin + insets.systemWindowInsetBottom
                            )
                            v.layoutParams = params
                            insets
                        }
                        snack.show()
                    }

                    is ProgressState.INITIAL -> {
                        val snack = Snackbar.make(
                            bind.rootCl, state.initialMessage ?: "", Snackbar.LENGTH_SHORT
                        )
                        ViewCompat.setOnApplyWindowInsetsListener(snack.view) { v, insets ->
                            v.setPadding(v.paddingLeft, v.paddingTop, v.paddingRight, v.paddingTop)
                            val params = v.layoutParams as ViewGroup.MarginLayoutParams
                            params.setMargins(
                                params.leftMargin,
                                params.topMargin,
                                params.rightMargin,
                                params.bottomMargin + insets.systemWindowInsetBottom
                            )
                            v.layoutParams = params
                            insets
                        }
                        snack.show()
                    }

                    else -> {}
                }
            }

        })
    }

    private fun setupNeededPeekHeight(bottomInsetns: Int) {
        val allMargins = requireContext().dpToPix(55)
        binding?.bottomSheet?.photographerTitleTv?.measure(0, 0)
        binding?.bottomSheet?.photographerTv?.measure(0, 0)
        binding?.bottomSheet?.let {
            val photographerTitleHeight = it.photographerTitleTv.measuredHeight
            val photographerTextHeight = it.photographerTv.measuredHeight
            val finalPeekHeight =
                allMargins + photographerTitleHeight + photographerTextHeight + bottomInsetns
            val bottomSheetBehavior = BottomSheetBehavior.from(it.root)
            bottomSheetBehavior.peekHeight = finalPeekHeight.toInt()
        }

    }

    companion object {
        const val ARG_KEY = "photo"
    }
}
