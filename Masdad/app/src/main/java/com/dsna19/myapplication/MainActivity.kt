package com.dsna19.myapplication

import android.app.VoiceInteractor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONException

class MainActivity : AppCompatActivity() {
    private val url = "https://pixabay.com/api/?key=20005551-10cb7f6bb55d85ff5ae720efe&q=kitten=photo"
    lateinit var recycler_view : RecyclerView
    lateinit var queue : RequestQueue
    lateinit var itemAdapter: ItemAdapter
    lateinit var itemList: ArrayList<item>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler_view = findViewById(R.id.recycler_view)
        recycler_view.setHasFixedSize(true)
        recycler_view.layoutManager = LinearLayoutManager(this)
        itemList = arrayListOf()

        queue = Volley.newRequestQueue(this)
        parseJson()
    }

    private fun parseJson() {
        Log.println(Log.DEBUG, "parseJSON", "실행")
        var request = JsonObjectRequest(Request.Method.GET, url, null,
        Response.Listener { response ->
            try {
                var jsonArray = response.getJSONArray("hits")

                for (i in 0 until jsonArray.length()) {
                    var hit = jsonArray.getJSONObject(i)

                    val creatorName = hit.getString("user")
                    val imageUrl = hit.getString("webformatURL")
                    val likeCount = hit.getInt("likes")

                    itemList.add(item(imageUrl, creatorName, likeCount))
                }
                Log.println(Log.DEBUG, "성공", "성공")
                itemAdapter = ItemAdapter(this, itemList)
                recycler_view.adapter = itemAdapter
            } catch (e : JSONException) {
                e.printStackTrace()
            }
        },
        Response.ErrorListener { error ->
            error.printStackTrace()
        })

        queue.add(request)
    }
}
