package com.dsna19.test_01_30

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.JsonObject
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import okio.Buffer
import org.json.JSONObject
import retrofit2.http.*
import java.io.IOException
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets

class PayActivity :AppCompatActivity() {
    lateinit var button_first: Button
    lateinit var web_view : WebView
    var tid : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay)
        button_first = findViewById(R.id.button_first)
        web_view = findViewById(R.id.web_view)
        web_view.webViewClient = WebViewClient()
        var webSettings = web_view.settings
        webSettings.javaScriptEnabled = true

        val hashmap = hashMapOf<String, Any>(
                "cid" to "TC0ONETIME",
                "partner_order_id" to "partner_order_id",
                "partner_user_id" to "partner_user_id",
                "item_name" to "초코파이",
                "quantity" to 1,
                "total_amount" to 2200,
                "vat_amount" to 200,
                "tax_free_amount" to 0,
                "approval_url" to "http://localhost:3000/kakaopayment",
                "fail_url" to "http://localhost:3000/kakaopayment",
                "cancel_url" to "http://localhost:3000/kakaopayment"
        )
        val requestBody = FormBody.Builder()
                .add("cid", "TC0ONETIME")
                .add("partner_order_id", "partner_order_id")
                .add("partner_user_id", "partner_user_id")
                .add("item_name", "초코파이")
                .add("quantity", "1")
                .add("total_amount", "2200")
                .add("vat_amount", "200")
                .add("tax_free_amount", "0")
                .add("approval_url", "http://192.168.147.1:3000/kakaopayment")
                .add("fail_url", "http://192.168.147.1:3000/kakaopayment")
                .add("cancel_url", "http://192.168.147.1:3000/kakaopayment")
                .build()
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        var client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()
        var request = okhttp3.Request.Builder()
                .url("https://kapi.kakao.com/v1/payment/ready")
                .addHeader("Authorization", "KakaoAK f16d4e8048a301aa905a42c285e88b9d")
                .addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
                //.addHeader("Content-Type","application/x-www-form-urlencoded;charset=utf-8")
                .post(requestBody)
                .build()

        button_first.setOnClickListener {
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.d("fail 1", request.headers.toString())
                    Log.d("fail 2", request.body.toString())
                    Log.d("fail 2", requestBody.toString())
                }

                override fun onResponse(call: Call, response: Response) {
                    var buffer = Buffer()
                    request.body!!.writeTo(buffer)
                    Log.d("sucess 1", request.headers.toString())
                    Log.d("sucess 2", buffer.readUtf8())
                    val jsonData = response.body!!.string()
                    val jobject = JSONObject(jsonData)

                    val tid = jobject.getString("tid")
                    val popup = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(jobject.getString("next_redirect_pc_url"))
                    )
                    startActivity(popup)
                    var is_sign_up = false
                    while (!is_sign_up) {
                        Thread.sleep(4000)
                        client = OkHttpClient.Builder()
                            .addInterceptor(logging)
                            .build()

                        val requestBody = FormBody.Builder()
                            .add("cid", "TC0ONETIME")
                            .add("tid", tid)
                            .add("partner_order_id", "partner_order_id")
                            .add("partner_user_id", "partner_user_id")
                            .add("pg_token", "pg_token=xxxxxxxxxxxxxxxxxxxx")
                            .build()

                        var request = okhttp3.Request.Builder()
                            .url("https://kapi.kakao.com/v1/payment/approve")
                            .addHeader("Authorization", "KakaoAK f16d4e8048a301aa905a42c285e88b9d")
                            .addHeader(
                                "Content-Type",
                                "application/x-www-form-urlencoded;charset=utf-8"
                            )
                            //.addHeader("Content-Type","application/x-www-form-urlencoded;charset=utf-8")
                            .post(requestBody)
                            .build()


                        client.newCall(request).enqueue(object : Callback {
                            override fun onFailure(call: Call, e: IOException) {
                                Log.d("fail_after 1", request.headers.toString())
                                Log.d("fail_after 2", request.body.toString())
                                Log.d("fail_after 3", requestBody.toString())
                            }

                            override fun onResponse(call: Call, response: Response) {
                                Log.d("sucess_after 1", request.headers.toString())
                                Log.d("sucess_after 2", buffer.readUtf8())
                                if (response.code == -708) {
                                    is_sign_up = true
                                }
                            }
                        })

                    }
                }
            })
        }

    }
}
/*
data class KakaoResult (
        var tid : String = "",
        var next_redirect_app_url : String = "",
        var next_redirect_mobile_url : String = "",
        var next_redirect_pc_url : String = "",
        var android_app_scheme : String = "",
        var ios_app_scheme : String = ""
)

interface KaKaoPayService {

    @FormUrlEncoded
    @POST("/v1/payment/ready")
    @Headers("Authorization: KakaoAK f16d4e8048a301aa905a42c285e88b9d",
            "Content-Type: application/x-www-form-urlencoded; charset=utf-8")
    fun payForThis(
           //@Header("Authorization") key : String,
            @Field("cid") cid:String,
            @Field("partner_order_id") order_id : String,
            @Field("partner_user_id") user_id : String,
            @Field("item_name") item_name : String,
            @Field("quantity") quantity : Int,
            @Field("total_amount") total_amount : Int,
            @Field("tax_free_amount") tax_free_amount : Int,
            @Field("approval_url") approval_url : String,
            @Field("cancel_url") cancel_url : String,
            @Field("fail_url") fail_url : String
            )
    : Call<KakaoResult>
}
*/