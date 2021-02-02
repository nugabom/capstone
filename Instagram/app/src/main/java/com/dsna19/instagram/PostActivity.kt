package com.dsna19.instagram

import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageActivity
import java.util.*
import kotlin.reflect.typeOf

class PostActivity : AppCompatActivity() {
    lateinit var imageUri : Uri
    var myUrl :String = ""
    lateinit var storageReference : StorageReference
    lateinit var uploadTask: UploadTask

    lateinit var close : ImageView
    lateinit var image_added : ImageView
    lateinit var post : TextView
    lateinit var description : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        close = findViewById(R.id.close)
        image_added = findViewById(R.id.image_added)
        post = findViewById(R.id.post)
        description = findViewById(R.id.description)

        storageReference = FirebaseStorage.getInstance().getReference("posts")

        close.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        post.setOnClickListener {
            uploadImage()
        }

        CropImage.activity()
            .setAspectRatio(1, 1)
            .start(this)
    }

    private fun getFileExtension(uri : Uri) : String? {
        var mime = MimeTypeMap.getSingleton()
        return mime.getExtensionFromMimeType(contentResolver.getType(uri))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE
            && resultCode == RESULT_OK) {
            var result = CropImage.getActivityResult(data)
            imageUri = result.uri
            image_added.setImageURI(imageUri)
        } else {
            Toast.makeText(this, "Something gone wrong!", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
    private fun uploadImage() {
        if (imageUri != null) {
            val fileReference = storageReference.child(
                System.currentTimeMillis().toString() + "." + getFileExtension(imageUri))
            uploadTask = fileReference.putFile(imageUri)
            uploadTask.continueWithTask { task ->
                if (!task.isComplete) {
                    throw task.exception!!
                }

                Log.d("downLoadUrl", fileReference.downloadUrl.toString())
                return@continueWithTask fileReference.downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result
                    myUrl = downloadUri.toString()
                    val reference = FirebaseDatabase.getInstance().getReference("Posts")
                    var postid = reference.push().key!!

                    var uploadInfo = hashMapOf<String, Any>(
                        "postid" to postid,
                        "postimage" to myUrl,
                        "description" to description.text.toString(),
                        "publisher" to FirebaseAuth.getInstance().currentUser!!.uid
                    )

                    reference.child(postid).setValue(uploadInfo)
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener { error ->
                Toast.makeText(this, ""+error.message, Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "No Image Select", Toast.LENGTH_SHORT).show()
        }
    }
}