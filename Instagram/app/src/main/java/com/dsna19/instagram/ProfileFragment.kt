package com.dsna19.instagram

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*
import kotlin.collections.ArrayList

class ProfileFragment : Fragment() {
    lateinit var image_profile : ImageView
    lateinit var options : ImageView

    lateinit var posts : TextView
    lateinit var followers : TextView
    lateinit var following : TextView
    lateinit var fullname : TextView
    lateinit var bio : TextView
    lateinit var username : TextView

    lateinit var edit_profile : Button

    lateinit var my_photos : ImageButton
    lateinit var saved_photos: ImageButton

    lateinit var recyclerView: RecyclerView
    lateinit var myPhotoAdapter: MyPhotoAdapter
    lateinit var myPhotoList : ArrayList<Post>

    lateinit var recyclerView_saves: RecyclerView
    lateinit var myPhotoAdapter_saves: MyPhotoAdapter
    lateinit var myPhotoList_save : ArrayList<Post>

    lateinit var mySaves : ArrayList<String>

    lateinit var firebaseUser : FirebaseUser
    var profileid : String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view =  inflater.inflate(R.layout.fragment_profile, container, false)

        firebaseUser = FirebaseAuth.getInstance().currentUser!!

        val prefs = context?.getSharedPreferences("PREFS", Context.MODE_PRIVATE)
        profileid = prefs!!.getString("profileid", "none")

        image_profile = view.findViewById(R.id.image_profile)
        options = view.findViewById(R.id.options)
        posts = view.findViewById(R.id.posts)
        followers = view.findViewById(R.id.followers)
        following = view.findViewById(R.id.following)
        fullname = view.findViewById(R.id.fullname)
        bio = view.findViewById(R.id.bio)
        username = view.findViewById(R.id.username)
        edit_profile = view.findViewById(R.id.edit_profile)
        my_photos = view.findViewById(R.id.my_photos)
        saved_photos = view.findViewById(R.id.saved_photos)

        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.setHasFixedSize(true)
        val linearLayoutManager : LinearLayoutManager = GridLayoutManager(context, 3)
        recyclerView.layoutManager = linearLayoutManager
        myPhotoList = arrayListOf()
        myPhotoAdapter = MyPhotoAdapter(context, myPhotoList)
        recyclerView.adapter = myPhotoAdapter

        recyclerView_saves = view.findViewById(R.id.recycler_view_save)
        recyclerView_saves.setHasFixedSize(true)
        val linearLayoutManager_save : LinearLayoutManager = GridLayoutManager(context, 3)
        recyclerView_saves.layoutManager = linearLayoutManager_save
        myPhotoList_save = arrayListOf()
        myPhotoAdapter_saves = MyPhotoAdapter(context, myPhotoList_save)
        recyclerView_saves.adapter = myPhotoAdapter_saves

        recyclerView.visibility = View.VISIBLE
        recyclerView_saves.visibility = View.GONE

        userInfo()
        getFollowers()
        getNrPosts()
        getMyPhotos()
        getMySave()

        Log.d("profileid", profileid)
        if (profileid.equals(firebaseUser.uid)) {
            edit_profile.text = "Edit Profile"
        } else {
            checkFollow()
            saved_photos.visibility = View.GONE
        }

        edit_profile.setOnClickListener {
            val btn = edit_profile.text.toString()


            if (btn.equals("Edit Profile")) {
                startActivity(Intent(context, EditProfileActivity::class.java))
            } else if (btn.equals("follow")) {
                FirebaseDatabase.getInstance().getReference()
                    .child("Follow")
                    .child(firebaseUser!!.uid)
                    .child("following")
                    .child(profileid!!)
                    .setValue(true)

                FirebaseDatabase.getInstance().getReference()
                    .child("Follow")
                    .child(profileid!!)
                    .child("followers")
                    .child(firebaseUser!!.uid)
                    .setValue(true)

                addNotifications()
            } else if (btn.equals("following")) {
                FirebaseDatabase.getInstance().getReference()
                    .child("Follow")
                    .child(firebaseUser!!.uid)
                    .child("following")
                    .child(profileid!!)
                    .removeValue()

                FirebaseDatabase.getInstance().getReference()
                    .child("Follow")
                    .child(profileid!!)
                    .child("followers")
                    .child(firebaseUser!!.uid)
                    .removeValue()
            }
        }

        my_photos.setOnClickListener {
            recyclerView.visibility = View.VISIBLE
            recyclerView_saves.visibility = View.GONE
        }

        saved_photos.setOnClickListener {
            recyclerView_saves.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        }

        followers.setOnClickListener {
            var intent = Intent(context, FollowerActivity::class.java)
            intent.putExtra("id", profileid)
            intent.putExtra("title", "followers")
            startActivity(intent)
        }

        following.setOnClickListener {
            var intent = Intent(context, FollowerActivity::class.java)
            intent.putExtra("id", profileid)
            intent.putExtra("title", "following")
            startActivity(intent)
        }

        options.setOnClickListener {
            var intent = Intent(context, OptionsActivity::class.java)
            startActivity(intent)
        }
        return view
    }

    fun userInfo() {
        var reference = FirebaseDatabase.getInstance()
            .getReference("Users")
            .child(profileid!!)

        val getuserinfoListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (context == null) {
                    return ;
                }

                val user = snapshot.getValue(User::class.java)
                Glide.with(context!!).load(user!!.imageurl).into(image_profile)
                username.text = user!!.username
                fullname.text = user!!.fullname
                bio.text = user!!.bio
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }

        reference.addValueEventListener(getuserinfoListener)
    }

    fun checkFollow() {
        var reference = FirebaseDatabase.getInstance().getReference()
            .child("Follow")
            .child(firebaseUser.uid)
            .child("following")
        Log.d("chekckFollow", "called")

        val checkFollowListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.child(profileid!!).exists()) {
                    edit_profile.text = "following"
                } else {
                    edit_profile.text = "follow"
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }

        reference.addValueEventListener(checkFollowListener)
    }

    fun getFollowers() {
        var reference = FirebaseDatabase.getInstance().getReference()
            .child("Follow")
            .child(profileid!!)
            .child("followers")

        val getFollowersListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                followers.text = ""+snapshot.childrenCount.toString()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }

        reference.addValueEventListener(getFollowersListener)

        reference = FirebaseDatabase.getInstance().getReference()
            .child("Follow")
            .child(profileid!!)
            .child("following")

        val getFollowingListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                following.text = ""+snapshot.childrenCount.toString()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }

        reference.addValueEventListener(getFollowingListener)
    }

    fun getNrPosts() {
        var reference = FirebaseDatabase.getInstance().getReference("Posts")
        val getNrPostsListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var i = 0
                for (postSnapshot in snapshot.children) {
                    val post = postSnapshot.getValue(Post::class.java)
                    if (post!!.publisher.equals(profileid!!)) {
                        i++
                    }
                }

                posts.text = i.toString()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }

        reference.addValueEventListener(getNrPostsListener)
    }

    fun getMyPhotos() {
        var reference = FirebaseDatabase.getInstance().getReference("Posts")

        val getMyPhotosListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                myPhotoList.clear()
                for (postSnapshot in snapshot.children) {
                    val post = postSnapshot.getValue(Post::class.java)
                    if (post!!.publisher.equals(profileid)) {
                        myPhotoList.add(post)
                    }
                }
                Collections.reverse(myPhotoList)
                myPhotoAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }

        reference.addValueEventListener(getMyPhotosListener)
    }

    fun getMySave() {
        mySaves = arrayListOf()
        var reference = FirebaseDatabase.getInstance().getReference("Saves")
            .child(firebaseUser.uid)

        val getMySaveListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (postSnapshot in snapshot.children) {
                    mySaves.add(postSnapshot.key!!)
                }

                readSaves()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }

        reference.addValueEventListener(getMySaveListener)
    }

    fun readSaves() {
        var reference = FirebaseDatabase.getInstance().getReference("Posts")

        val readSavesListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                myPhotoList_save.clear()
                for (postSnapshot in snapshot.children) {
                    val post = postSnapshot.getValue(Post::class.java)

                    for (id in mySaves) {
                        if (post!!.postid.equals(id)) {
                            myPhotoList_save.add(post)
                        }
                    }
                }
                myPhotoAdapter_saves.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }

        reference.addValueEventListener(readSavesListener)
    }

    fun addNotifications() {
        var reference = FirebaseDatabase.getInstance()
            .getReference("Notifications")
            .child(profileid!!)

        val notificationInfo = hashMapOf<String, Any>(
            "userid" to firebaseUser.uid,
            "text" to "started following you",
            "postid" to "",
            "ispost" to false
        )

        reference.push().setValue(notificationInfo)
    }
}