package com.dsna19.test2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.dsna19.test2.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
/*
        var button_call_baedal: Button = findViewById(R.id.button_call_baedal).also {
            it.setOnClickListener(View.onClickListener(){
                onClick(View v){

                }
            })
        }*/
        var button_call_baedal: Button = findViewById(R.id.button_call_baedal)

        button_call_baedal.setOnClickListener{
            val intent=Intent(this, baedal_main::class.java)
            startActivity(intent)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    /*
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if(requestCode==REQUEST_CODE_MENU){
            Toast.makeText(getApplicationContext(), "aaa"+requestCode+", 결과코드:"+resultCode, Toast.LENGTH_LONG ).show()

            if (resultCode == RESULT_OK){
                var name:String = data?.getExtras()?.getString("name").toString()
                Toast.makeText(getApplicationContext(), "응답으로 전달된 name : "+name, Toast.LENGTH_LONG).show


            }
        }
    }*/
}