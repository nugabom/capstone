package com.dsna19.instagram

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class CommentAdapter (var mContext : Context, var mComments : ArrayList<Comment>):
    RecyclerView.Adapter<CommentAdapter.ViewHolder>() {

    lateinit var firebaseUser : FirebaseUser

    inner class ViewHolder (itemview : View): RecyclerView.ViewHolder(itemview) {
        var image_profile : ImageView
        var username : TextView
        var comment : TextView

        init {
            image_profile = itemview.findViewById(R.id.image_profile)
            username = itemview.findViewById(R.id.username)
            comment = itemview.findViewById(R.id.comment)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.comment_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        firebaseUser = FirebaseAuth.getInstance().currentUser!!
        val comment = mComments[position]

        holder.comment.text = comment.comment
        getUserInfo(holder.image_profile, holder.username, comment.publisher)

        holder.comment.setOnClickListener {
            var intent = Intent(mContext, MainActivity::class.java)
            intent.putExtra("publisherid", comment.publisher)
            mContext.startActivity(intent)
        }

        holder.image_profile.setOnClickListener {
            var intent = Intent(mContext, MainActivity::class.java)
            intent.putExtra("publisherid", comment.publisher)
            mContext.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return mComments.size
    }

    fun getUserInfo(imageView: ImageView, username : TextView, publisherid : String) {
        var reference = FirebaseDatabase.getInstance().getReference()
            .child("Users")
            .child(publisherid)

        val getUserInfoListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)
                Glide.with(mContext).load(user!!.imageurl).into(imageView)
                username.text = user.username
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }

        reference.addValueEventListener(getUserInfoListener)
    }
}