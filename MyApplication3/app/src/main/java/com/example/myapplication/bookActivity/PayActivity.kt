package com.example.myapplication.bookActivity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.dataclass.StoreInfo
import com.google.firebase.auth.FirebaseAuth
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import okio.Buffer
import org.json.JSONObject
import java.io.IOException

class PayActivity : AppCompatActivity() {
    companion object {
        val NotSelected = -1
    }
    lateinit var store_name : TextView
    lateinit var requset : TextView
    lateinit var face_price : TextView
    lateinit var coupon_directory : TextView
    lateinit var real_price : TextView
    lateinit var table_directory : RecyclerView
    lateinit var check_select_button : RadioGroup
    lateinit var pay_button : Button

    lateinit var stocks : HashMap<String, ChoiceItem>
    lateinit var storeInfo : StoreInfo

    var price : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay)

        stocks = intent.getSerializableExtra("stocks") as HashMap<String, ChoiceItem>
        price = intent.getIntExtra("price", 0)
        storeInfo = intent.getSerializableExtra("store_info") as StoreInfo

        if(price == 0) {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            finish()
        }
        init_UI()

    }

    private fun init_UI() {
        store_name = findViewById(R.id.payPageSikdangName)
        store_name.text = storeInfo.store_name
        requset = findViewById(R.id.reqEditText)
        face_price = findViewById(R.id.payPageOriginalPrice)
        face_price.text = "매장 가격 : ${price}"
        coupon_directory = findViewById(R.id.couponNumTV)
        table_directory = findViewById(R.id.couponRV)
        real_price = findViewById(R.id.totalPriceTV)
        pay_button = findViewById(R.id.payButton)
        check_select_button = findViewById(R.id.check_select_button)

        pay_button.setOnClickListener {
            val radioId = check_select_button.checkedRadioButtonId

            when (radioId) {
                R.id.kakaoPayCB-> {kakaoPayProcess(price, FirebaseAuth.getInstance().currentUser!!.uid, storeInfo.store_name!!)}
                R.id.secondPayCB->{Toast.makeText(this, "미구현", Toast.LENGTH_SHORT).show()}
                NotSelected->{
                    Toast.makeText(this, "사용하실 수단을 선택해 주세요", Toast.LENGTH_SHORT).show()
                }
            }
        }

        init_table_directory()
    }

    private fun init_table_directory() {
        table_directory.adapter = TableDirectoryAdapter(applicationContext, stocks)
        table_directory.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        table_directory.setHasFixedSize(true)
    }

    private fun kakaoPayProcess(price : Int, user_id : String, store_id : String) {
        val requestBody = FormBody.Builder()
            .add("cid", "TC0ONETIME")
            .add("partner_order_id", user_id)
            .add("partner_user_id", store_id)
            .add("item_name", "예약")
            .add("quantity", "1")
            .add("total_amount", price.toString())
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
            .addHeader("Authorization", "KakaoAK 697a966c0fe76c2e80470060ab00fe30")
            .addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
            //.addHeader("Content-Type","application/x-www-form-urlencoded;charset=utf-8")
            .post(requestBody)
            .build()

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
                                Log.d("success", "임시적 성공")
                                is_sign_up = true
                            }
                        }
                    })

                }
            }
        })
    }
}