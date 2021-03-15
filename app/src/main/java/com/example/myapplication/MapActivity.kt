package com.example.myapplication

import android.graphics.PointF
import android.location.Location
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.UiThread
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource
import kotlin.math.hypot


class MapActivity : AppCompatActivity(),
    OnMapReadyCallback, NaverMap.OnCameraChangeListener, NaverMap.OnCameraIdleListener {
    lateinit var locationSource: FusedLocationSource
    val ACCESS_LOCATION_PERMISSION_REQUEST_CODE = 100
    lateinit var mNaverMap : NaverMap
    var markerList : ArrayList<Marker> = arrayListOf()
    var isCameraAnimated = false
    lateinit var lastLocation : LatLng
    lateinit var results : ArrayList<Store>
    val occupies = arrayOf<String>("plenty", "some", "flew", "none")
    var range : Int = 0

    override fun onCameraChange(reason: Int, animated: Boolean) {
        isCameraAnimated = animated
    }

    override fun onCameraIdle() {
        if (isCameraAnimated) {
            val mapCenter = mNaverMap.cameraPosition.target
            val distance = distance(lastLocation, mapCenter)
            if (distance > 3000) {
                lastLocation = mapCenter
            }

            fetchStore(mapCenter, 1000)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
        range = intent.extras!!.getInt("range")

        var mapFragment :MapFragment = supportFragmentManager.findFragmentById(R.id.map) as MapFragment
        mapFragment.getMapAsync(this)

    }

    @UiThread
    override fun onMapReady(naverMap: NaverMap) {
        // Naver Map 초기 세팅
        mNaverMap = naverMap
        locationSource = FusedLocationSource(this, ACCESS_LOCATION_PERMISSION_REQUEST_CODE)
        lastLocation = mNaverMap.cameraPosition.target
        var uiSettings = mNaverMap.uiSettings
        uiSettings.isLocationButtonEnabled = true
        mNaverMap.locationSource = locationSource
        results = arrayListOf()
        // 현재 사용자를 기준으로 setting
        mNaverMap.locationTrackingMode = LocationTrackingMode.Follow

        // naverMap listener 설정

        mNaverMap.addOnCameraChangeListener(this)
        mNaverMap.addOnCameraIdleListener(this)
        mNaverMap.onMapDoubleTapListener = doubleClickListener
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            ACCESS_LOCATION_PERMISSION_REQUEST_CODE -> {
                locationSource.onRequestPermissionsResult(requestCode, permissions, grantResults)
                return
            }
        }
    }

    val doubleClickListener = object : NaverMap.OnMapDoubleTapListener {
        override fun onMapDoubleTap(p0: PointF, p1: LatLng): Boolean {
            Toast.makeText(this@MapActivity, "Location : ${p1.latitude}, ${p1.longitude}", Toast.LENGTH_SHORT).show()

            return true
        }
    }

    private fun distance(from : LatLng, to : LatLng) : Float {
        var result = floatArrayOf(0f, 0f)
        Location.distanceBetween(from.latitude, from.longitude, to.latitude, to.longitude, result)
        return hypot(result[0], result[1])
    }

    fun updateMapMakers(storeList : ArrayList<Store>) {
        resetMarkerList()
        for (store in storeList) {
            val mapCenter = mNaverMap.cameraPosition.target
            val distance = distance(mapCenter, store.latLng)
            if (distance > range) continue
            var marker = Marker()
            marker.position = store.latLng

            /*
            icon 설정

            if ("plenty".equals(store.occupy, ignoreCase = true)) {
                marker.icon = OverlayImage.fromResource(R.drawable.marker_green)
            } else if ("some".equals(store.occupy, ignoreCase = true)) {
                marker.icon = OverlayImage.fromResource(R.drawable.marker_yellow)
            } else if ("flew".equals(store.occupy, ignoreCase = true)) {
                marker.icon = OverlayImage.fromResource(R.drawable.marker_red)
            } else {
                marker.icon = OverlayImage.fromResource(R.drawable.marker_gray)
            }
            */

            marker.anchor = PointF(0.5f, 1.0f)
            marker.map = mNaverMap
            markerList.add(marker)
        }
    }

    private fun resetMarkerList() {
        if (markerList != null && markerList.size > 0) {
            for (marker in markerList) {
                marker.map = null
            }
            markerList.clear()
        }
    }

    fun fetchStore (location : LatLng, range : Int) {
        /* Fetch from Database or Files */
        updateMapMakers(results)
    }
}

data class Store (
    val latLng : LatLng,
    val occupy : String
)