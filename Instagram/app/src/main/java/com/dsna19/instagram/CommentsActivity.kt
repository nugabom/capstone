package com.dsna19.instagram

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CommentsActivity : AppCompatActivity() {
    lateinit var addcomment : EditText
    lateinit var image_profile : ImageView
    lateinit var post : TextView

    lateinit var postid : String
    lateinit var publisherid : String

    lateinit var firebaseUser: FirebaseUser

    lateinit var recyclerView: RecyclerView
    lateinit var commentAdapter: CommentAdapter
    lateinit var commentList: ArrayList<Comment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comments)

        var toolbar = findViewById<Toolbar>(R.id.toolbar)
        supportActionBar!!.title = "Comments"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        toolbar.setNavigationOnClickListener {
            finish()
        }

        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.setHasFixedSize(true)
        val linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager
        commentList = arrayListOf()
        commentAdapter = CommentAdapter(this, commentList)
        recyclerView.adapter = commentAdapter

        firebaseUser = FirebaseAuth.getInstance().currentUser!!
        addcomment = findViewById(R.id.add_comment)
        image_profile = findViewById(R.id.image_profile)
        post = findViewById(R.id.post)

        postid = intent.getStringExtra("postid")
        publisherid = intent.getStringExtra("publisherid")

        post.setOnClickListener {
            if (addcomment.text.toString().equals("")) {
                Toast.makeText(this, "You can't send empty comment", Toast.LENGTH_SHORT).show()
            } else {
                addComent()
            }
        }

        getImage()
        readComments()
    }

    private fun addComent() {
        val reference = FirebaseDatabase.getInstance().getReference("Comments").child(postid)

        val commentInfo = hashMapOf<String, Any>(
            "comment" to addcomment.text.toString(),
            "publisher" to firebaseUser.uid
        )

        reference.push().setValue(commentInfo)
        addNotifications()
        addcomment.text.clear()
    }

    fun getImage() {
        var reference = FirebaseDatabase.getInstance().getReference("Users")
            .child(firebaseUser.uid)

        val getImageListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)
                Glide.with(applicationContext).load(user!!.imageurl).into(image_profile)
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }

        reference.addValueEventListener(getImageListener)
    }

    private fun readComments() {
        var reference = FirebaseDatabase.getInstance().getReference("Comments")
            .child(postid)

        val readCommentListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d("comment",snapshot.toString())
                commentList.clear()
                for (postSnapshot in snapshot.children) {
                    val comment = postSnapshot.getValue(Comment::class.java)
                    commentList.add(comment!!)
                }
                commentAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }

        reference.addValueEventListener(readCommentListener)
    }

    fun addNotifications() {
        var reference = FirebaseDatabase.getInstance()
            .getReference("Notifications")
            .child(publisherid)

        val notificationInfo = hashMapOf<String, Any>(
            "userid" to firebaseUser.uid,
            "text" to "commented: " + addcomment.text.toString(),
            "postid" to postid,
            "ispost" to true
        )

        reference.push().setValue(notificationInfo)
    }
}