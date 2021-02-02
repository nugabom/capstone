package com.dsna19.instagram

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeFragment : Fragment() {
    lateinit var recyclerView: RecyclerView
    lateinit var postAdapter: PostAdapter
    lateinit var postLists : ArrayList<Post>

    lateinit var followingList : ArrayList<String>
    lateinit var progressBar: ProgressBar

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_home, container, false)

        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.setHasFixedSize(true)
        var linearLayout = LinearLayoutManager(context)
        linearLayout.reverseLayout = true
        linearLayout.stackFromEnd = true
        recyclerView.layoutManager = linearLayout
        postLists = arrayListOf()
        postAdapter = PostAdapter(context, postLists)
        recyclerView.adapter = postAdapter

        progressBar = view.findViewById(R.id.progress_circular)
        checkFollowing()

        return view
    }

    fun checkFollowing() {
        followingList = arrayListOf()

        var reference = FirebaseDatabase.getInstance()
                .getReference("Follow")
                .child(FirebaseAuth.getInstance().currentUser!!.uid)
                .child("following")

        val checkFollowListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                followingList.clear()
                for (postSnapshot in snapshot.children) {
                    followingList.add(postSnapshot.key!!)
                    Log.println(Log.DEBUG, "follow", postSnapshot.toString())
                }

                readPosts()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }
        reference.addValueEventListener(checkFollowListener)
    }

    fun readPosts() {
        var reference = FirebaseDatabase.getInstance().getReference("Posts")

        val readListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                postLists.clear()
                for (postSnapshot in snapshot.children) {
                    val post = postSnapshot.getValue(Post::class.java)
                    for (id in followingList) {
                        if (post!!.publisher.equals(id)) {
                            postLists.add(post)
                            Log.d("post", post.toString())
                        }
                    }
                }
                postAdapter.notifyDataSetChanged()
                progressBar.visibility = View.GONE
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }

        reference.addValueEventListener(readListener)
    }
}