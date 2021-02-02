package com.dsna19.myapplication

import android.content.ContentResolver
import android.content.Intent
import android.icu.util.ValueIterator
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.*
import androidx.core.content.MimeTypeFilter
import androidx.core.net.toFile
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {
    val PICK_IMAGE_REQUEST = 1

    lateinit var mButtonChooseImage : Button
    lateinit var mButtonUpLoad : Button
    lateinit var mTextViewShowUpLoad : TextView
    lateinit var mEditTextFileName : EditText
    lateinit var mImageView : ImageView
    lateinit var mProcessBar : ProgressBar

    lateinit var mImageUri :Uri

    lateinit var mStorageRef : StorageReference
    lateinit var mDatabaseRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        InitUi()
        InitDB()

    }

    private fun InitUi() {
        mButtonChooseImage = findViewById(R.id.button_choose_file)
        mButtonUpLoad = findViewById(R.id.button_upload)
        mTextViewShowUpLoad = findViewById(R.id.text_view_show_uploads)
        mEditTextFileName = findViewById(R.id.edit_text_file_name)
        mImageView = findViewById(R.id.image_view)
        mProcessBar = findViewById(R.id.progress_bar)

        mButtonUpLoad.setOnClickListener{
            uploadFile()
        }

        mButtonChooseImage.setOnClickListener{
            openFileChooser()
        }
        mTextViewShowUpLoad.setOnClickListener {
            openImagesActivity()
        }
    }

    private fun uploadFile() {
        if (mImageUri != null) {
            val fileReferenece =
                mStorageRef.child(System.currentTimeMillis().toString() + "." + getFileExtension(mImageUri))
            fileReferenece.putFile(mImageUri).addOnSuccessListener { task ->
                var handler = Handler()
                handler.postDelayed({
                                    mProcessBar.progress = 0
                }, 5000)
                fileReferenece.downloadUrl.addOnSuccessListener { uri ->
                    val upload = Upload(mEditTextFileName.text.toString().trim(),
                                uri.toString(), "")
                    val uploadId = mDatabaseRef.push().key!!
                    mDatabaseRef.child(uploadId).setValue(upload)
                    Log.d("data_metadata", task.metadata.toString())
                    Log.d("data_get", uri.toString())
                    Log.d("data_uri", mImageUri.toString())
                }
                Toast.makeText(this, "Upload successful", Toast.LENGTH_SHORT).show()

            }.addOnFailureListener{ e ->
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            }.addOnProgressListener {
                var progress = (100.0 * it.bytesTransferred / it.totalByteCount)
                mProcessBar.progress = progress.toInt()
            }
        } else{
            Toast.makeText(this, "No File selected", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getFileExtension(uri : Uri) : String? {
        val mine = MimeTypeMap.getSingleton()
        return mine.getExtensionFromMimeType(contentResolver.getType(uri))
    }
    private fun InitDB() {
        mStorageRef = FirebaseStorage.getInstance("gs://my-application-e09a0.appspot.com").getReference("uploads")
        mDatabaseRef = FirebaseDatabase.getInstance("https://my-application-e09a0-default-rtdb.firebaseio.com/").getReference()
    }

    private fun openImagesActivity() {
        val intent = Intent(this, ImagesActivity::class.java)
        startActivity(intent)
    }
    private fun openFileChooser() {
        var intent = Intent()
        intent.setType("image/*")
        intent.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST
            && resultCode == RESULT_OK
            && data != null && data.data != null) {
            mImageUri = data.data!!

            Picasso.with(this).load(mImageUri).into(mImageView)
        }
    }
}