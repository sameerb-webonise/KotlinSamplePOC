package com.example.webonise.kotlinsamplepoc.interfaces

import com.example.webonise.kotlinsamplepoc.ui.PhotoViewActivity

/**
 * Created by webonise on 17/1/18.
 */
interface IPhotoView {
    fun setView(mActivity: PhotoViewActivity)
    fun saveImageToFile(photoUri: String)
}