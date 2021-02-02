package com.dsna19.instagram

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FollowerActivity : AppCompatActivity() {
    lateinit var id : String
    lateinit var title : String

    lateinit var idList : ArrayList<String>

    lateinit var recyclerView : RecyclerView
    lateinit var userAdapter : UserAdapter
    lateinit var userList : ArrayList<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_follower)

        id = intent.getStringExtra("id")
        title = intent.getStringExtra("title")

        var toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        toolbar.setTitle(title)
        toolbar!!.visibility = View.VISIBLE
        toolbar.setNavigationOnClickListener {
            finish()
        }

        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        userList = arrayListOf()
        userAdapter = UserAdapter(this, userList, false)
        recyclerView.adapter = userAdapter

        idList = arrayListOf()

        when (title) {
            "likes" -> {
                getLikes()
            }
            "following" -> {
                getFollowings()
            }
            "followers" -> {
                getFollowers()
            }
        }
    }

    fun getLikes() {
        var reference = FirebaseDatabase.getInstance()
            .getReference("Likes")
            .child(id)

        val getLikesListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                idList.clear()
                for (postSnapshot in snapshot.children) {
                    idList.add(postSnapshot.key!!)
                }
                showUsers()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }

        reference.addValueEventListener(getLikesListener)
    }

    fun getFollowers() {
        var reference = FirebaseDatabase.getInstance()
            .getReference("Follow")
            .child(id)
            .child("followers")

        val getFollowers = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                idList.clear()
                for (postSnapshot in snapshot.children) {
                    idList.add(postSnapshot.key!!)
                }
                showUsers()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }

        reference.addValueEventListener(getFollowers)
    }

    fun getFollowings() {
        var reference = FirebaseDatabase.getInstance()
            .getReference("Follow")
            .child(id)
            .child("following")

        val getFollowingsListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                idList.clear()
                for (postSnapshot in snapshot.children) {
                    idList.add(postSnapshot.key!!)
                }
                showUsers()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }

        reference.addValueEventListener(getFollowingsListener)
    }

    fun showUsers() {
        var reference = FirebaseDatabase.getInstance()
            .getReference("Users")

        val getUsersListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                for (postSnapshot in snapshot.children) {
                    val user = postSnapshot.getValue(User::class.java)
                    for (id in idList) {
                        if(user!!.id.equals(id)) {
                            userList.add(user!!)
                        }
                    }
                }

                userAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }
        reference.addValueEventListener(getUsersListener)
    }
}