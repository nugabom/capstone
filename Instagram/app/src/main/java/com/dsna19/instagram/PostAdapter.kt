package com.dsna19.instagram

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PostAdapter (val mContext : Context?, var mPosts : ArrayList<Post>) : RecyclerView.Adapter<PostAdapter.ViewHolder>() {
    lateinit var firebaseUser : FirebaseUser

    inner class ViewHolder (itemview : View): RecyclerView.ViewHolder (itemview){
        var image_profile : ImageView
        var post_image : ImageView
        var like : ImageView
        var comment : ImageView
        var save : ImageView

        var username : TextView
        var likes : TextView
        var comments : TextView
        var publisher : TextView
        var description: TextView


        init {
            image_profile = itemview.findViewById(R.id.image_profile)
            post_image = itemview.findViewById(R.id.post_image)
            like = itemview.findViewById(R.id.like)
            comment = itemview.findViewById(R.id.comment)
            save = itemview.findViewById(R.id.save)

            username = itemview.findViewById(R.id.username)
            likes = itemview.findViewById(R.id.likes)
            comments = itemview.findViewById(R.id.comments)
            publisher = itemview.findViewById(R.id.publisher)
            description = itemview.findViewById(R.id.description)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.post_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        firebaseUser = FirebaseAuth.getInstance().currentUser!!
        val post = mPosts[position]

        Glide.with(mContext!!).load(post.postimage)
            .apply(RequestOptions().placeholder(R.drawable.placeholder)).into(holder.post_image)

        if (post.description.equals("")) {
            holder.description.visibility = View.GONE
        } else {
            holder.description.visibility = View.VISIBLE
            holder.description.text = post.description
        }

        firebaseUser = FirebaseAuth.getInstance().currentUser!!
        publisherInfo(holder.image_profile, holder.username, holder.publisher, post.publisher)
        isLikes(post.postid, holder.like)
        nrLikes(holder.likes, post.postid)
        getComments(post.postid, holder.comments)
        isSaved(post.postid, holder.save)

        holder.image_profile.setOnClickListener {
            var editor = mContext.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit()
            editor.putString("profileid", post.publisher)
            editor.apply()

            (mContext as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ProfileFragment())
                .commit()
        }

        holder.post_image.setOnClickListener {
            var editor = mContext.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit()
            editor.putString("postid", post.postid)
            editor.apply()

            (mContext as FragmentActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, PostDetailFragment())
                .commit()
        }

        holder.like.setOnClickListener {
            if(holder.like.tag.equals("like")) {
                FirebaseDatabase.getInstance().getReference()
                        .child("Likes").child(post.postid)
                        .child(firebaseUser.uid).setValue(true)
                addNotifications(post.publisher, post.postid)
            } else {
                FirebaseDatabase.getInstance().getReference()
                        .child("Likes").child(post.postid)
                        .child(firebaseUser.uid).removeValue()
            }
        }

        holder.comment.setOnClickListener {
            var intent = Intent(mContext, CommentsActivity::class.java)
            intent.putExtra("postid", post.postid)
            intent.putExtra("publisherid", post.publisher)
            mContext.startActivity(intent)
        }

        holder.comments.setOnClickListener {
            var intent = Intent(mContext, CommentsActivity::class.java)
            intent.putExtra("postid", post.postid)
            intent.putExtra("publisherid", post.publisher)
            mContext.startActivity(intent)
        }

        holder.save.setOnClickListener {
            Log.d("save_setOnClickListener", "is called")
            Log.d("save_setOnClickListener", "save_tag : "+holder.save.tag.toString())
            if (!holder.save.tag.equals("Saved")) {
                FirebaseDatabase.getInstance().getReference()
                    .child("Saves")
                    .child(firebaseUser.uid)
                    .child(post.postid)
                    .setValue(true)
            } else {
                FirebaseDatabase.getInstance().getReference()
                    .child("Saves")
                    .child(firebaseUser.uid)
                    .child(post.postid)
                    .removeValue()
            }
        }

        holder.likes.setOnClickListener {
            var intent = Intent(mContext, FollowerActivity::class.java)
            intent.putExtra("id", post.postid)
            intent.putExtra("title", "likes")
            mContext.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return mPosts.size
    }

    fun publisherInfo (image_profile : ImageView,
                       username : TextView,
                       publisher : TextView,
                       userid : String) {
        var reference = FirebaseDatabase.getInstance().getReference("Users")
                .child(userid)

        val publisherinfoListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)
                Glide.with(mContext!!).load(user!!.imageurl).into(image_profile)
                username.text = user.username
                publisher.text = user.username
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }

        reference.addValueEventListener(publisherinfoListener)
    }

    fun isLikes (postid : String, imageView: ImageView) {
        firebaseUser = FirebaseAuth.getInstance().currentUser!!

        var reference = FirebaseDatabase.getInstance().getReference()
                .child("Likes")
                .child(postid)

        val likeListener = object :ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.child(firebaseUser.uid!!).exists()) {
                    imageView.setImageResource(R.drawable.ic_liked)
                    imageView.tag = "liked"
                } else {
                    imageView.setImageResource(R.drawable.ic_like)
                    imageView.tag = "like"
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }
        reference.addValueEventListener(likeListener)
    }

    fun nrLikes(likes : TextView, postid: String) {
        val reference = FirebaseDatabase.getInstance().getReference()
                .child("Likes")
                .child(postid)

        val nrLikesListener = object :ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                likes.text = snapshot.childrenCount.toString() + " likes"
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }
        reference.addValueEventListener(nrLikesListener)
    }

    fun getComments(postid: String, comments : TextView) {
        var reference = FirebaseDatabase.getInstance().getReference()
            .child("Comments")
            .child(postid)

        val getCommentsListener = object :ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                comments.text = "View All " + snapshot.childrenCount.toString() + " Comments"
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }

        reference.addValueEventListener(getCommentsListener)
    }

    fun isSaved(postid: String, imageView: ImageView) {
        val firebaseUser = FirebaseAuth.getInstance().currentUser
        var reference = FirebaseDatabase.getInstance().getReference()
            .child("Saves")
            .child(firebaseUser!!.uid)

        val getSaveListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.child(postid).exists()) {
                    imageView.setImageResource(R.drawable.ic_booked)
                    imageView.tag = "Saved"
                } else {
                    imageView.setImageResource(R.drawable.ic_save)
                    imageView.tag = "Save"
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }

        reference.addValueEventListener(getSaveListener)
    }

    fun addNotifications(userid : String, postid : String) {
        var reference = FirebaseDatabase.getInstance()
            .getReference("Notifications")
            .child(userid)

        val notificationInfo = hashMapOf<String, Any>(
            "userid" to firebaseUser.uid,
            "text" to "liked your post",
            "postid" to postid,
            "ispost" to true
        )

        reference.push().setValue(notificationInfo)
    }

}