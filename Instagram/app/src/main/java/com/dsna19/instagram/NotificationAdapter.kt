package com.dsna19.instagram

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class NotificationAdapter(var mContext : Context?, var mNotifications : ArrayList<Notification>)
    : RecyclerView.Adapter<NotificationAdapter.ViewHolder>(){
    inner class ViewHolder (itemview : View) : RecyclerView.ViewHolder(itemview) {
        var image_profile : ImageView
        var post_image : ImageView
        var username : TextView
        var text : TextView

        init {
            image_profile = itemview.findViewById(R.id.image_profile)
            post_image = itemview.findViewById(R.id.post_image)
            username = itemview.findViewById(R.id.username)
            text = itemview.findViewById(R.id.comment)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.notification_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val notification = mNotifications[position]

        holder.text.text = notification.text
        getUserInfo(holder.image_profile, holder.username, notification.userid)

        if (notification.isPost) {
            holder.post_image.visibility = View.VISIBLE
            getPost(holder.post_image, notification.postid)
        } else {
            holder.post_image.visibility = View.GONE
        }

        holder.itemView.setOnClickListener {
            if (notification.isPost) {
                var editor = mContext!!.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit()
                editor.putString("postid", notification.postid)
                editor.apply()

                (mContext as FragmentActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, PostDetailFragment())
                    .commit()
            } else {
                var editor = mContext!!.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit()
                editor.putString("profileid", notification.userid)
                editor.apply()

                (mContext as FragmentActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, ProfileFragment())
                    .commit()
            }
        }
    }

    override fun getItemCount(): Int {
        return mNotifications.size
    }

    fun getUserInfo (imageView: ImageView, username : TextView, publisherid : String) {
        var reference = FirebaseDatabase.getInstance().getReference("Users")
            .child(publisherid)

        val getUserInfoListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)
                Glide.with(mContext!!).load(user!!.imageurl).into(imageView)
                username.text = user.username
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }

        reference.addValueEventListener(getUserInfoListener)
    }

    fun getPost (imageView: ImageView, postid : String) {
        var reference = FirebaseDatabase.getInstance().getReference("Posts")
            .child(postid)

        val getPostListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val post = snapshot.getValue(Post::class.java)
                Glide.with(mContext!!).load(post!!.postimage).into(imageView)
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }

        reference.addValueEventListener(getPostListener)
    }
}