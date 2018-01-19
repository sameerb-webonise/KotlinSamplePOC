package com.example.webonise.kotlinsamplepoc.interfaces

import com.example.webonise.kotlinsamplepoc.ui.PhotoViewActivity

interface IPhotoView {
    fun setView(mActivity: PhotoViewActivity)
    fun saveImageToFile(photoUri: String)
}