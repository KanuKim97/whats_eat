package com.example.whats_eat.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.RequestManager
import com.example.whats_eat.R
import com.example.whats_eat.data.di.dispatcherQualifier.MainDispatcher
import com.example.whats_eat.databinding.FragmentDetailPlaceBinding
import com.example.whats_eat.viewModel.DetailPlaceViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FragmentDetailPlace : Fragment() {
    @MainDispatcher @Inject lateinit var mainDispatcher: CoroutineDispatcher
    @Inject lateinit var imageLoader: RequestManager
    private var _detailBinding: FragmentDetailPlaceBinding? = null
    private val detailBinding get() = _detailBinding!!
    private val detailPlaceViewModel: DetailPlaceViewModel by viewModels()
    private val placeID: String by lazy { setPlaceID() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getPlaceInformation(placeID)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _detailBinding = FragmentDetailPlaceBinding.inflate(inflater, container, false)
        return detailBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        detailPlaceViewModel.detailPlaceResult.observe(viewLifecycleOwner) { result ->
            setPlaceTitle(result.name)
            setPlaceAddress(result.formattedAddress)
            setOpenNow(result.isOpenNow)
            loadPlaceImage(result.photoRef)

            detailBinding.AddCollectionBtn.setOnClickListener {
                detailPlaceViewModel.saveUserCollection(result)
            }
        }
    }

    private fun setPlaceID(): String = arguments?.getString("PlaceID").toString()

    private fun getPlaceInformation(PlaceID: String): Job =
        detailPlaceViewModel.getPlaceDetailData(PlaceID)

    private fun setPlaceTitle(name: String?): Job = lifecycleScope.launch(mainDispatcher) {
        detailBinding.placeTitle.text = name
    }

    private fun setPlaceAddress(address: String?): Job = lifecycleScope.launch(mainDispatcher) {
        detailBinding.placeAddress.text = address
    }

    private fun setOpenNow(openNow: Boolean?): Job = lifecycleScope.launch(mainDispatcher) {
        when (openNow) {
            null -> detailBinding.placeOpenNow.visibility = View.GONE
            true -> detailBinding.placeOpenNow.text = getString(R.string.isOpenNow)
            false -> detailBinding.placeOpenNow.text = getString(R.string.isCloseNow)
        }
    }

    private fun loadPlaceImage(photoReference: String) =
        imageLoader.load(photoReference).into(detailBinding.detailPlaceImgView)

}