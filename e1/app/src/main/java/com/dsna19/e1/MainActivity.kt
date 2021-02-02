package com.dsna19.e1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.Response.success
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    lateinit var queue : RequestQueue
    var exampleList = ArrayList<ExampleItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.println(Log.DEBUG, "살아있다","살아있다")
        Toast.makeText(this, "살아있다",Toast.LENGTH_SHORT)
        recycler_view.adapter = ExampleAdapter(exampleList, this)
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)
        queue = Volley.newRequestQueue(this)
        Log.println(Log.DEBUG, "queue", "generated!!!")
        getNews()
    }

    private fun getNews() {
        var url = "http://newsapi.org/v2/top-headlines?country=kr&apiKey=6ef22654f83b45b6b8d7655e71d40cd1"
        Log.println(Log.DEBUG, "GET_NEWS", "generated!!!")
        // Request a string response from the provided URL.
        val stringRequest = StringRequest(Request.Method.GET, url,
            Response.Listener<String> { response ->
                // Display the first 500 characters of the response string.
                Toast.makeText(this,"통신성공",Toast.LENGTH_LONG)
                Log.println(Log.DEBUG,"NEWS", response)
                try {
                    val jsonObj = JSONObject(response)

                    val articles  = jsonObj.getJSONArray("articles")
                    Log.d("articles", articles.length().toString())
                    for (i in 0 until articles.length()) {
                        var obj = articles.getJSONObject(i)

                        var item : ExampleItem = ExampleItem(
                            obj.getString("urlToImage"),
                            obj.getString("title"),
                            obj.getString("content")
                        )
                        Log.println(Log.DEBUG, "__TEST", item.imageResource)
                        Log.println(Log.DEBUG, "__TEST", item.Title)
                        Log.println(Log.DEBUG, "__TEST", item.Content)
                        exampleList.add(item)

                    }
                    recycler_view.adapter = ExampleAdapter(exampleList, this)
                    recycler_view.layoutManager = LinearLayoutManager(this)
                    recycler_view.setHasFixedSize(true)
                } catch (e : JSONException) {
                    e.printStackTrace()
                    Log.println(Log.DEBUG, "JSONERROR", "ss")
                }

            },
            Response.ErrorListener {
                Log.println(Log.DEBUG,"Fail", "Fail")
            })

        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }
}