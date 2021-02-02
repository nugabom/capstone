package com.dsna19.instagram

import android.content.Context
import android.os.Bundle
import android.view.ContextMenu
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PostDetailFragment : Fragment() {
    var postid : String? = null

    lateinit var recyclerView: RecyclerView
    lateinit var postAdapter: PostAdapter
    lateinit var postList : ArrayList<Post>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_post_detail, container, false)
        val preferences = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)
        postid = preferences!!.getString("postid", "none")

        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.setHasFixedSize(true)
        var linearLayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = linearLayoutManager

        postList = arrayListOf()
        postAdapter = PostAdapter(context, postList)
        recyclerView.adapter = postAdapter

        readPost()

        return view
    }

    fun readPost() {
        var reference = FirebaseDatabase.getInstance().getReference()
            .child("Posts")
            .child(postid!!)

        val readPostListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                postList.clear()
                val post = snapshot.getValue(Post::class.java)
                postList.add(post!!)

                postAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }

        reference.addValueEventListener(readPostListener)
    }
}