package com.example.myapplication.payPage

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.bookMenu.DataMenuToPay
import com.example.myapplication.bookTable.TableData
import com.example.myapplication.bookTime.BookData
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import okio.Buffer
import org.json.JSONObject
import java.io.IOException

class PayPage: AppCompatActivity() {
    lateinit var dataMenuToPay:DataMenuToPay
    val NotSelected = -1
    var price = 0
    var couponData = CouponData("1234")
    lateinit var check_select_button : RadioGroup
    lateinit var payButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("확인 PayPage", "1")
        setContentView(R.layout.paypage)
        //Log.d("확인 PayPage", "2")
        check_select_button = findViewById(R.id.check_select_button)
        payButton = findViewById(R.id.payButton)

        var intent = getIntent()
        if (intent != null) {
            dataMenuToPay = intent.getSerializableExtra("dataMenuToPay") as DataMenuToPay
            price = intent.getIntExtra("price", 0)
            //Log.d("확인 PayPage", "price 확인"+price.toString())

        }
        else{
            Log.d("확인 PayPage", "intent 데이터 가져오기 오류")
        }
        var payPageSikdangName:TextView = findViewById(R.id.payPageSikdangName)
        payPageSikdangName.setText(dataMenuToPay.sikdangName)

        var reqEditText:EditText = findViewById(R.id.reqEditText)
        reqEditText.setSingleLine(false)


        var payPageOriginalPrice:TextView = findViewById(R.id.payPageOriginalPrice)
        payPageOriginalPrice.setText(price.toString()+" 원")




        //쿠폰 관련 부분
        var couponTV = findViewById<TextView>(R.id.couponTV)
        couponTV.setOnClickListener {
            showDialog()
        }

        payButton.setOnClickListener {
            val radioId = check_select_button.checkedRadioButtonId

            when (radioId) {
                R.id.kakaoPayCB-> {kakaoPayProcess(price)}
                R.id.secondPayCB->{Toast.makeText(this, "미구현", Toast.LENGTH_SHORT).show()}
                NotSelected->{
                    Toast.makeText(this, "사용하실 수단을 선택해 주세요", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    /*

    public fun getUserId():Int{
        return 987321
    }*/

    public fun showDialog(){
        var customDialog = PayPageCouponDialog(this, couponData, dataMenuToPay.sikdangId)
        customDialog.show()
    }

    fun kakaoPayProcess(price : Int) {
        val requestBody = FormBody.Builder()
            .add("cid", "TC0ONETIME")
            .add("partner_order_id", "partner_order_id")
            .add("partner_user_id", "partner_user_id")
            .add("item_name", "초코파이")
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
                                is_sign_up = true
                            }
                        }
                    })

                }
            }
        })
    }
}

