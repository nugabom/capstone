package com.example.myapplication.start

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.R
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.text.TextUtils
import android.util.Base64
import android.util.Log
import android.widget.*
import androidx.annotation.RequiresApi
import com.example.myapplication.mainPage.Sikdang_main
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.*
import java.security.MessageDigest
/*
    phone_number : 구글계정에 없으면 못씀
 */
class CustomerLogInActivity : AppCompatActivity() {
    lateinit var auth : FirebaseAuth

    lateinit var google_login : ImageButton
    lateinit var googleClient : GoogleSignInClient

    lateinit var kakao_login : ImageButton
    val GOOGLE_SIGN_IN = 9001

    lateinit var reference : DatabaseReference

    lateinit var sign_up : TextView

    lateinit var login : Button
    lateinit var email : EditText
    lateinit var password : EditText
    lateinit var findPassword: TextView
    lateinit var findEmail: TextView

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_login)
        auth = FirebaseAuth.getInstance()
        google_login = findViewById(R.id.google_btn)
        kakao_login = findViewById(R.id.kakao_btn)
        sign_up = findViewById(R.id.sign_up)
        login = findViewById(R.id.btn_login)
        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        findPassword = findViewById(R.id.find_password)
        findEmail = findViewById(R.id.find_email)

        getKeyHashBase64(this)


        google_login.setOnClickListener {
            setGoogleLogin()
            SignIn()

        }

        findEmail.setOnClickListener {
            startActivity(Intent(this, FindEmail::class.java))
        }
        findPassword.setOnClickListener {
            startActivity(Intent(this, FindPassword::class.java))
        }
        kakao_login.setOnClickListener {

        }

        sign_up.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        login.setOnClickListener {
            val str_email = email.text.toString()
            val str_password = password.text.toString()

            if (TextUtils.isEmpty(str_email) || TextUtils.isEmpty(str_password)) {
                Toast.makeText(this, "All fileds are required!", Toast.LENGTH_SHORT).show()
            } else {
                auth.signInWithEmailAndPassword(str_email, str_password).addOnCompleteListener { task ->
                    if(task.isSuccessful()) {
                        var reference = FirebaseDatabase.getInstance().getReference()
                            .child("Users")
                            .child(auth.currentUser!!.uid)

                        val loginEventListener = object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                var intent = Intent(this@CustomerLogInActivity, Sikdang_main::class.java)
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                startActivity(intent)
                                finish()
                            }

                            override fun onCancelled(error: DatabaseError) {
                                TODO("Not yet implemented")
                            }

                        }
                        reference.addValueEventListener(loginEventListener)

                    } else {
                        Toast.makeText(this, "로그인 정보가 잘못되었습니다.", Toast.LENGTH_SHORT).show()
                        email.text.clear()
                        password.text.clear()
                    }
                }
            }
        }
    }

    fun setGoogleLogin() {
        var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleClient = GoogleSignIn.getClient(this, gso)
    }

    fun SignIn() {
        var intent = googleClient.signInIntent
        startActivityForResult(intent, GOOGLE_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GOOGLE_SIGN_IN) {
            var task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                var account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account)
            }
            catch (e : ApiException) {
                Toast.makeText(this, "google login 실패", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount?) {
        var credential = GoogleAuthProvider.getCredential(account?.idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    updateSignIn(auth)
                    Toast.makeText(this, "Google Login 성공", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, Sikdang_main::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Google Login 실패", Toast.LENGTH_SHORT).show()
                }
            }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun getKeyHashBase64(context : Context) {
        val info = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNING_CERTIFICATES)
        val signatures = info.signingInfo.apkContentsSigners
        val md = MessageDigest.getInstance("SHA")
        for (signature in signatures) {
            val md = MessageDigest.getInstance("SHA")
            md.update(signature.toByteArray())
            val key = String(Base64.encode(md.digest() , 0))
            Log.d("hash key", key.toString())
        }
    }

    private fun updateSignIn(firebaseAuth: FirebaseAuth) {
        val userid = firebaseAuth.currentUser!!.uid
        reference = FirebaseDatabase.getInstance().getReference()
            .child("Users")
            .child(userid)

        Log.d("updateSignIn", firebaseAuth.currentUser!!.phoneNumber.toString())
        val userInfo = hashMapOf<String, Any>(
            "id" to userid,
            "username" to firebaseAuth.currentUser!!.displayName.toString(),
            "phone_number" to firebaseAuth.currentUser!!.phoneNumber.toString(),
            "email" to ""
        )

        reference.setValue(userInfo).addOnCompleteListener { task ->
            if(!task.isSuccessful) {
                Log.d("register", userid + " : Database 유저정보 업데이트 실패")
                return@addOnCompleteListener
            }
        }
    }
}