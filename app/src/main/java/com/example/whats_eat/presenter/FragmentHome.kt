package com.example.whats_eat.presenter

import android.content.Context
import android.content.Intent
import android.location.Geocoder
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.whats_eat.R
import com.example.whats_eat.di.dispatcherQualifier.IoDispatcher
import com.example.whats_eat.di.dispatcherQualifier.MainDispatcher
import com.example.whats_eat.databinding.FragmentHomeBinding
import com.example.whats_eat.presenter.adapter.MainBannerAdapter
import com.example.whats_eat.presenter.adapter.SubFoodGridAdapter
import com.example.whats_eat.viewModel.HomeViewModel
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class FragmentHome: Fragment() {
    @Inject @MainDispatcher lateinit var mainDispatcher: CoroutineDispatcher
    @Inject @IoDispatcher lateinit var ioDispatcher: CoroutineDispatcher
    @Inject lateinit var locationRequest: CurrentLocationRequest
    @Inject lateinit var toastMessage: Toast

    private var _homeBinding: FragmentHomeBinding? = null
    private val homeBinding get() = _homeBinding!!
    private val homeViewModel: HomeViewModel by viewModels()

    private val mainBanner: ViewPager2 by lazy { homeBinding.MainBanner }
    private val subGridView: RecyclerView by lazy { homeBinding.FoodGridView }
    private val myLocationManger: LocationManager by lazy { setLocationManager() }
    private val myFusedLocationClient: FusedLocationProviderClient by lazy { setFusedLocationClient() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _homeBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return homeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        checkGPSFunction()
        initMainBannerViewPager()
        initSubItemGridView()
        setMainBannerAdapter()
        setSubItemGridAdapter()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _homeBinding = null
    }

    private fun checkGPSFunction() {
        if(!checkGPSON()) {
            toastMessage.apply {
                setText(R.string.ActiveGPS)
                duration = Toast.LENGTH_SHORT
            }.show()
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        } else {
            getCurrentLocation()
        }
    }

    private fun initMainBannerViewPager(): Job = lifecycleScope.launch(mainDispatcher) {
        mainBanner.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        mainBanner.scrollIndicators = ViewPager2.SCROLL_INDICATOR_END
    }

    private fun initSubItemGridView(): Job = lifecycleScope.launch(mainDispatcher) {
        subGridView.layoutManager = GridLayoutManager(requireContext(), 2)
        subGridView.setHasFixedSize(true)
    }

    private fun setMainBannerAdapter(): Unit =
        homeViewModel.mainBannerItems.observe(viewLifecycleOwner) {
            lifecycleScope.launch(mainDispatcher) {
                mainBanner.adapter = MainBannerAdapter(requireActivity(), it)
            }
        }

    private fun setSubItemGridAdapter(): Unit =
        homeViewModel.subFoodItems.observe(viewLifecycleOwner) {
            lifecycleScope.launch(mainDispatcher) {
                subGridView.adapter = SubFoodGridAdapter(requireActivity(), it)
            }
        }

    private fun checkGPSON(): Boolean =
        myLocationManger.isProviderEnabled(LocationManager.GPS_PROVIDER)

    private fun setLocationManager(): LocationManager =
        requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager

    private fun setFusedLocationClient(): FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(requireContext())

    private fun getCurrentLocation(): Job = lifecycleScope.launch(ioDispatcher) {
        try {
            myFusedLocationClient.getCurrentLocation(locationRequest, object: CancellationToken() {
                override fun onCanceledRequested(p0: OnTokenCanceledListener): CancellationToken =
                    CancellationTokenSource().token

                override fun isCancellationRequested(): Boolean = false
            }).addOnCompleteListener { location ->
                val lat: Double = location.result.latitude
                val lng: Double = location.result.longitude
                geoCoderService(lat, lng)
                getMainBannerItems(StringBuilder("$lat, $lng").toString())
                getSubFoodItems(StringBuilder("$lat, $lng").toString())
            }
        } catch (e: SecurityException) { e.printStackTrace() }
    }

    private fun geoCoderService(
        lat: Double,
        lng: Double
    ): Job = lifecycleScope.launch(mainDispatcher) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val subLocalList = withContext(ioDispatcher) {
                Geocoder(requireContext(), Locale.KOREA).getFromLocation(lat, lng, 5)
            }

            for (element in 0..subLocalList!!.lastIndex) {
                if (subLocalList[element].subLocality != null) {
                    homeBinding.locationTxt.text = subLocalList[element].subLocality
                    break
                } else {
                    homeBinding.locationTxt.visibility = View.GONE
                }
            }
        }
    }

  private fun getMainBannerItems(latLng: String): Job = homeViewModel.getMainBannerItems(latLng)

  private fun getSubFoodItems(latLng: String): Job = homeViewModel.getSubGridViewItems(latLng)


}