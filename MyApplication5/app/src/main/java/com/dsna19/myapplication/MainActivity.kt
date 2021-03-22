package com.dsna19.myapplication

import android.graphics.PointF
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewDebug
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.InfoWindow
import com.naver.maps.map.overlay.InfoWindow.DEFAULT_ADAPTER
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.Overlay
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.FusedLocationSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.hypot
import kotlin.random.Random.Default.nextInt

class MainActivity : AppCompatActivity(),
        OnMapReadyCallback, NaverMap.OnCameraChangeListener, NaverMap.OnCameraIdleListener, NaverMap.OnMapClickListener,
        Overlay.OnClickListener {
    lateinit var locationSource: FusedLocationSource
    val ACCESS_LOCATION_PERMISSION_REQUEST_CODE = 100
    lateinit var mNaverMap : NaverMap
    var markerList : ArrayList<Marker> = arrayListOf()
    var infoWindow : InfoWindow? = null
    var gen : Int = 0

    var isCameraAnimated = false
    lateinit var memento : Pair<Double, Double>
    lateinit var results : ArrayList<Store>
    val colors = arrayOf<String>("plenty", "some", "flew", "none")

    override fun onClick(overlay: Overlay): Boolean {
        if (overlay is Marker) {
            val marker = overlay
            if (marker.infoWindow != null) {
                infoWindow?.close()
            } else {
                infoWindow!!.open(marker)
            }
            return true
        }
        return false
    }


    override fun onCameraChange(reason: Int, animated: Boolean) {
        isCameraAnimated = animated
        Log.d("onCameraChange", animated.toString())
    }

    override fun onMapClick(p0: PointF, p1: LatLng) {
        if (infoWindow?.marker != null) {
            infoWindow?.close()
        }
    }

    val doubleClickListener = object : NaverMap.OnMapDoubleTapListener {
        override fun onMapDoubleTap(p0: PointF, p1: LatLng): Boolean {
            Toast.makeText(this@MainActivity, "위치: ${p1.latitude}, ${p1.longitude}", Toast.LENGTH_SHORT).show()
            val store = Store(p1.latitude, p1.longitude, colors[rand()], "test : ${++gen}")
            results.add(store)
            fetchStoreSale(p1.latitude, p1.longitude, 1000)
            return true
        }
    }

    override fun onCameraIdle() {
        if (isCameraAnimated) {
            Toast.makeText(this, "on: 호출", Toast.LENGTH_SHORT).show()
            var result = floatArrayOf(0f, 0f)
            val mapCenter = mNaverMap.cameraPosition.target
            val mapCenter = mNaverMap.cameraPosition.target
            Location.distanceBetween(memento.first, memento.second, mapCenter.latitude, mapCenter.longitude, result)
            if (hypot(result[0], result[1]) < 1000 * 1000) {
                Log.d("onCameraIdle", hypot(result[0], result[1]).toString())
            }
            fetchStoreSale(mapCenter.latitude, mapCenter.longitude, 5000)
            isCameraAnimated = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        results = arrayListOf()
        var mapFragment = supportFragmentManager.findFragmentById(R.id.map) as MapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(naverMap: NaverMap) {
        Toast.makeText(this, "onMapReady: 호출", Toast.LENGTH_SHORT).show()
        mNaverMap = naverMap
        locationSource = FusedLocationSource(this, ACCESS_LOCATION_PERMISSION_REQUEST_CODE)
        naverMap.locationSource = locationSource
        naverMap.locationTrackingMode = LocationTrackingMode.Follow
        var uiSettings = naverMap.uiSettings
        uiSettings.isLocationButtonEnabled = true


        naverMap.addOnCameraChangeListener (this)
        naverMap.addOnCameraIdleListener (this)
        naverMap.onMapDoubleTapListener = doubleClickListener

        val mapCenter = naverMap.cameraPosition.target
        memento = Pair(mapCenter.latitude, mapCenter.longitude)
        fetchStoreSale(mapCenter.latitude, mapCenter.longitude, 5000)

        infoWindow = InfoWindow()
        infoWindow!!.adapter = object : InfoWindow.DefaultViewAdapter(this) {
            override fun getContentView(p0: InfoWindow): View {
                val marker = infoWindow!!.marker
                val store = marker?.tag as Store
                val result = floatArrayOf(0f, 0f)
                val mapCenter = mNaverMap.cameraPosition.target
                Location.distanceBetween(store.lat, store.lng, mapCenter.latitude, mapCenter.longitude, result)
                val dis = hypot(result[0], result[1])
                var view = View.inflate(this@MainActivity, R.layout.info_window, null)
                view.findViewById<TextView>(R.id.store_id).text = store.name
                view.findViewById<TextView>(R.id.store_distance).text = dis.toString()
                return view
            }
        }
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

    fun fetchStoreSale(lat : Double, lng : Double, m: Int) {
        updateMapMarkers(results)
    }

    private fun updateMapMarkers(result: ArrayList<Store>) {
        resetMarkerList()
        for (store in result) {
            var result = floatArrayOf(0f, 0f)
            val mapCenter = mNaverMap.cameraPosition.target
            Location.distanceBetween(store.lat, store.lng, mapCenter.latitude, mapCenter.longitude, result)
            val dis = hypot(result[0], result[1])
            Toast.makeText(this, dis.toString(), Toast.LENGTH_SHORT).show()
            if(dis > 1000) continue
            var marker = Marker()
            marker.position = LatLng(store.lat, store.lng)
            marker.tag = store
            if ("plenty".equals(store.color, ignoreCase = true)) {
                marker.icon = OverlayImage.fromResource(R.drawable.marker_green)
            } else if ("some".equals(store.color, ignoreCase = true)) {
                marker.icon = OverlayImage.fromResource(R.drawable.marker_yellow)
            } else if ("flew".equals(store.color, ignoreCase = true)) {
                marker.icon = OverlayImage.fromResource(R.drawable.marker_red)
            } else {
                marker.icon = OverlayImage.fromResource(R.drawable.marker_gray)
            }

            marker.anchor = PointF(0.5f, 1.0f)
            marker.map = mNaverMap
            marker.setOnClickListener(this)
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

    fun rand() : Int {
        return Random().nextInt(4)
    }
}