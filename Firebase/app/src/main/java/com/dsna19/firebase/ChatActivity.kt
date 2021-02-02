package com.dsna19.firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize

class ChatActivity : AppCompatActivity() {
    var chatList = arrayListOf<ChatData>()
    lateinit var recycler_view : RecyclerView
    var nickname = "nick2"
    lateinit var adapter : ChatAdapter

    lateinit var EditText_chat : EditText
    lateinit var Button_send : Button
    lateinit var myRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        Button_send = findViewById(R.id.Button_send)
        EditText_chat = findViewById(R.id.EditText_chat)

        Button_send.setOnClickListener {
            val msg = EditText_chat.text.toString()
            if (msg != null) {
                var chat = ChatData(nickname, msg)
                myRef.push().setValue(chat)
            }
            EditText_chat.text.clear()
        }
        recycler_view = findViewById(R.id.my_recycler_view)
        recycler_view.setHasFixedSize(true)
        recycler_view.layoutManager = LinearLayoutManager(this)
        adapter = ChatAdapter(this, chatList, nickname)
        recycler_view.adapter = adapter

        var database = FirebaseDatabase.getInstance()
        myRef = database.getReference("message")


        var childListener = object : ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                Log.d("CHAT_CHAT", snapshot.getValue().toString())
                var chat = snapshot.getValue(ChatData::class.java)
                if (chat != null) {
                    adapter.addChat(chat)
                    recycler_view.adapter=adapter
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }
        myRef.addChildEventListener(childListener)
    }
}