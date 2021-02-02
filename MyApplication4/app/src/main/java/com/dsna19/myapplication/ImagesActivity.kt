package com.dsna19.myapplication

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import java.time.LocalDate
import com.dsna19.myapplication.Upload
import com.google.firebase.storage.FirebaseStorage

class ImagesActivity : AppCompatActivity(), ImageAdapter.OnItemClickListener{

    private lateinit var mRecyclerView : RecyclerView
    private lateinit var mAdapter : ImageAdapter
    private lateinit var mUploads : ArrayList<Upload>
    private lateinit var mProgressCircle : ProgressBar

    private lateinit var mDatabaseRef :DatabaseReference
    private lateinit var mStorage : FirebaseStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_images)
        mUploads = ArrayList()
        mRecyclerView = findViewById(R.id.recycler_view)
        mRecyclerView.setHasFixedSize(true)
        mAdapter = ImageAdapter(this, mUploads, this)
        mRecyclerView.adapter = mAdapter
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mProgressCircle = findViewById(R.id.progress_circle)

        mStorage = FirebaseStorage.getInstance("gs://my-application-e09a0.appspot.com")
        mDatabaseRef = FirebaseDatabase
                .getInstance("https://my-application-e09a0-default-rtdb.firebaseio.com/")
                .getReference()
        Toast.makeText(this,  "OnCreate in ImagesActivity", Toast.LENGTH_SHORT).show()
        var uploadListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                mUploads.clear()
                if (!snapshot.exists()) {
                    Log.println(Log.DEBUG, "asd", "시발")
                }
                for (postSnapshot in snapshot.children) {
                    Log.d("Snap: ", postSnapshot.getValue().toString())
                    var upload = postSnapshot.getValue(Upload::class.java)
                    upload!!.setKey(postSnapshot.key!!)
                    mUploads.add(upload!!)
                }
                Log.d("Loop","Loop end")
                mUploads.forEach {
                    Log.d("객체", it.getName()+it.getImageUri())
                }
                mAdapter = ImageAdapter(this@ImagesActivity, mUploads, this@ImagesActivity)
                mRecyclerView.adapter = mAdapter
                mAdapter.notifyDataSetChanged()
                mProgressCircle.visibility= View.INVISIBLE
                Toast.makeText(this@ImagesActivity, "성공", Toast.LENGTH_SHORT).show()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ImagesActivity, error.message.toString(), Toast.LENGTH_SHORT).show()
                mProgressCircle.visibility=View.INVISIBLE
            }
        }
        mDatabaseRef.addValueEventListener(uploadListener)
        Toast.makeText(this, "Listener add on", Toast.LENGTH_SHORT).show()
    }

    override fun onItemClick(position: Int) {
        Toast.makeText(this, "Normal click at position: " + position, Toast.LENGTH_SHORT).show()
    }

    override fun onWhatEverClick(position: Int) {
        Toast.makeText(this, "whatever click at position: " + position, Toast.LENGTH_SHORT).show()
    }

    override fun onDeleteClick(position: Int) {
        val selectedItem = mUploads[position]
        val selectedKey = selectedItem.getKey()
        var imageRef = mStorage.getReferenceFromUrl(selectedItem.getImageUri())
        imageRef.delete().addOnSuccessListener {
            mDatabaseRef.child(selectedKey).removeValue()
            Toast.makeText(this@ImagesActivity, "Item deleted", Toast.LENGTH_SHORT).show()
        }
    }
}