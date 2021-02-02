package com.dsna19.instagram

import android.content.Context
import android.content.Intent
import android.text.BoringLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import de.hdodenhof.circleimageview.CircleImageView

class UserAdapter (private var mContext : Context?,
                   private var mUsers : ArrayList<User>,
                   private var isFragment: Boolean
                   )
    : RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    var firebaseUser : FirebaseUser? = null

    inner class ViewHolder(itemview :View) : RecyclerView.ViewHolder(itemview) {
        var username : TextView
        var fullname : TextView
        var image_profile : CircleImageView
        var btn_follow : Button

        init {
            username = itemview.findViewById(R.id.username)
            fullname = itemview.findViewById(R.id.fullname)
            image_profile = itemview.findViewById(R.id.image_profile)
            btn_follow = itemview.findViewById(R.id.btn_follow)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.user_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        firebaseUser = FirebaseAuth.getInstance().currentUser
        val user = mUsers[position]

        holder.btn_follow.visibility = View.VISIBLE

        holder.username.text = user.username
        holder.fullname.text = user.fullname
        Glide.with(mContext!!).load(user.imageurl).into(holder.image_profile)

        isFollowing(user.id, holder.btn_follow)

        if(user.id.equals(firebaseUser!!.uid)) {
            holder.btn_follow.visibility = View.INVISIBLE
        }

        holder.itemView.setOnClickListener {
            if (isFragment) {
                var editor = mContext!!.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit()
                editor.putString("profileid", user.id)
                editor.apply()

                (mContext as FragmentActivity).supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, ProfileFragment()).commit()
            } else {
                var intent = Intent(mContext, MainActivity::class.java)
                intent.putExtra("publisherid", user.id)
                mContext!!.startActivity(intent)
            }
        }

        holder.btn_follow.setOnClickListener {
            if (holder.btn_follow.text.toString().equals("follow")) {
                FirebaseDatabase.getInstance().getReference()
                    .child("Follow")
                    .child(firebaseUser!!.uid)
                    .child("following")
                    .child(user.id)
                    .setValue(true)

                FirebaseDatabase.getInstance().getReference()
                    .child("Follow")
                    .child(user.id)
                    .child("followers")
                    .child(firebaseUser!!.uid)
                    .setValue(true)

                addNotifications(user.id)
            } else {
                FirebaseDatabase.getInstance().getReference()
                    .child("Follow")
                    .child(firebaseUser!!.uid)
                    .child("following")
                    .child(user.id)
                    .removeValue()

                FirebaseDatabase.getInstance().getReference()
                    .child("Follow")
                    .child(user.id)
                    .child("followers")
                    .child(firebaseUser!!.uid)
                    .removeValue()
            }
        }
    }

    override fun getItemCount(): Int {
        return mUsers.size
    }

    fun isFollowing(userid : String, button: Button) {
        var reference = FirebaseDatabase.getInstance().getReference()
            .child("Follow")
            .child(firebaseUser!!.uid)
            .child("following")

        val valueEventListener = object :ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.child(userid).exists()) {
                    button.text = "following"
                } else {
                    button.text = "follow"
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }

        reference.addValueEventListener(valueEventListener)
    }

    fun addNotifications(userid : String) {
        var reference = FirebaseDatabase.getInstance()
            .getReference("Notifications")
            .child(userid)

        val notificationInfo = hashMapOf<String, Any>(
            "userid" to firebaseUser!!.uid,
            "text" to "started following you",
            "postid" to "",
            "ispost" to true
        )

        reference.push().setValue(notificationInfo)
    }
}