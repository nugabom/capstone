package com.dsna19.instagram

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.rengwuxian.materialedittext.MaterialEditText
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView

class EditProfileActivity : AppCompatActivity() {
    lateinit var close : ImageView
    lateinit var image_profile: ImageView

    lateinit var save : TextView
    lateinit var tv_change : TextView

    lateinit var fullname : MaterialEditText
    lateinit var username : MaterialEditText
    lateinit var bio : MaterialEditText

    lateinit var firebaseUser: FirebaseUser

    var mImageUri : Uri? = null
    lateinit var uploadTask: UploadTask
    lateinit var storageReference: StorageReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        close = findViewById(R.id.close)
        image_profile = findViewById(R.id.image_profile)
        save = findViewById(R.id.save)
        tv_change = findViewById(R.id.tv_change)
        fullname = findViewById(R.id.fullname)
        username = findViewById(R.id.username)
        bio = findViewById(R.id.bio)

        firebaseUser = FirebaseAuth.getInstance().currentUser!!
        storageReference = FirebaseStorage.getInstance().getReference("uploads")

        var reference = FirebaseDatabase.getInstance().getReference("Users")
            .child(firebaseUser.uid)

        val getProfileInfoListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)
                fullname.setText(user!!.fullname)
                username.setText(user!!.username)
                bio.setText(user!!.bio)
                Glide.with(applicationContext).load(user!!.imageurl).into(image_profile)
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }

        reference.addValueEventListener (getProfileInfoListener)

        close.setOnClickListener {
            finish()
        }

        tv_change.setOnClickListener {
            CropImage.activity()
                .setAspectRatio(1, 1)
                .start(this)
        }

        image_profile.setOnClickListener {
            CropImage.activity()
                .setAspectRatio(1, 1)
                .start(this)
        }

        save.setOnClickListener {
            updateProfile(  fullname.text.toString(),
                            username.text.toString(),
                            bio.text.toString())
            finish()
        }
    }

    fun updateProfile(fullname : String, username : String, bio : String) {
        var reference = FirebaseDatabase.getInstance().getReference()
            .child("Users")
            .child(firebaseUser.uid)

        val userInfo = hashMapOf<String, Any>(
            "fullname" to fullname,
            "username" to username,
            "bio" to bio
        )

        reference.updateChildren(userInfo)
    }

    private fun getFileExtension(uri : Uri) : String? {
        var mimeTypeMap = MimeTypeMap.getSingleton()
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri))
    }

    fun uploadImage() {
        if (mImageUri != null) {
            var fileReference = storageReference.child(
                System.currentTimeMillis().toString() + "." + this.getFileExtension(mImageUri!!)
            )
            uploadTask = fileReference.putFile(mImageUri!!)
            uploadTask.continueWithTask { task ->
                if (!task.isSuccessful) {
                    throw task.exception!!
                }

                return@continueWithTask fileReference.downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result
                    val myUrl = downloadUri.toString()

                    val reference = FirebaseDatabase.getInstance().getReference("Users")
                        .child(firebaseUser.uid)

                    val updateUri = hashMapOf<String, Any>(
                        "imageurl" to myUrl
                    )
                    reference.updateChildren(updateUri)
                } else {
                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener { error ->
                Toast.makeText(this, error.message!!, Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE
            && resultCode == RESULT_OK) {
            val result = CropImage.getActivityResult(data)
            mImageUri = result.uri
            uploadImage()
        } else {
            Toast.makeText(this, "Something gome wrong!", Toast.LENGTH_SHORT).show()
        }
    }
}