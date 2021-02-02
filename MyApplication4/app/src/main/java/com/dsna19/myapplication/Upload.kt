package com.dsna19.myapplication

class Upload {
    private var mName : String = ""
    private var mImageUri : String = ""
    private var mKey : String = ""
    constructor() {

    }
    constructor(name:String, imageUri : String, key : String) {
        if (name.trim().equals(""))
            mName = "No Name"

        mName = name
        mImageUri = imageUri
        mKey = key
    }

    fun getName() :String {
        return mName
    }

    fun getImageUri() : String {
        return mImageUri
    }

    fun getKey() : String{
        return mKey
    }

    fun setName(name : String) {
        mName = name
    }

    fun setKey(key : String){
        mKey = key
    }

    fun setImageUri(imageUri : String) {
        mImageUri = imageUri
    }
}