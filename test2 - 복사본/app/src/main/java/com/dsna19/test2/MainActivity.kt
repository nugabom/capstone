package com.dsna19.test2
import android.content.Intent
import android.os.Bundle;
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity;

class MainActivity : AppCompatActivity() {
    //lateinit var btn_customer :Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //btn_customer = findViewById(R.id.btn_customer_login)
        //btn_customer.setOnClickListener {
            //val log_intent = Intent(this, customer_login::class.java)
            //startActivity(log_intent)
        //}
    }
}

// import net.daum.mf.map.api.MapView;
//val mapView = MapView(this)
//val mapViewContainer = findViewById<View>(R.id.map_view) as ViewGroup
//mapViewContainer.addView(mapView)