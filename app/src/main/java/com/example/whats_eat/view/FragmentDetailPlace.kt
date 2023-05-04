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
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FragmentDetailPlace : Fragment(), OnMapReadyCallback {
    @MainDispatcher @Inject lateinit var mainDispatcher: CoroutineDispatcher
    @Inject lateinit var imageLoader: RequestManager
    private lateinit var mapView: MapView
    private var _detailBinding: FragmentDetailPlaceBinding? = null
    private val detailBinding get() = _detailBinding!!
    private val detailPlaceViewModel: DetailPlaceViewModel by viewModels()
    private val placeID: String by lazy { setPlaceID() }
    private val markerOption: MarkerOptions by lazy { setMarkerOptions() }

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
        mapView = _detailBinding!!.PlaceMapView
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
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

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onMapReady(gMap: GoogleMap): Unit =
        detailPlaceViewModel.detailPlaceResult.observe(viewLifecycleOwner) { result ->
            setMarkerOnPlace(LatLng(result.lat!!, result.lng!!), gMap)
        }


    private fun setMarkerOnPlace(latLng: LatLng, gMap: GoogleMap): GoogleMap = gMap.apply {
        moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18f))
        addMarker(markerOption.position(latLng))
    }

    private fun setMarkerOptions(): MarkerOptions =
        MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker())

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