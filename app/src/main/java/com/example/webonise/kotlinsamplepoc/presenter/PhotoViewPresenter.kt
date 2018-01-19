package com.example.webonise.kotlinsamplepoc.presenter

import android.graphics.Bitmap
import android.os.Environment
import com.bumptech.glide.Glide
import com.example.webonise.kotlinsamplepoc.interfaces.IPhotoView
import com.example.webonise.kotlinsamplepoc.ui.PhotoViewActivity
import java.io.File
import java.io.FileOutputStream

class PhotoViewPresenter: IPhotoView{
    private var mView: PhotoViewActivity? = null

    override fun setView(mActivity: PhotoViewActivity) {
        mView = mActivity
    }

    override fun saveImageToFile(photoUri: String) {
        Thread(Runnable {
            var theBitmap: Bitmap?
            try {
                theBitmap = Glide.with(mView).load(photoUri).asBitmap().into(-1, -1).get()
                val extStorageDirectory = Environment.getExternalStorageDirectory().toString()
                val filename = System.currentTimeMillis().toString() + ".png"
                var file = File(extStorageDirectory, filename)
                if (file.exists()) {
                    file.delete()
                    file = File(extStorageDirectory, filename)
                }
                val outStream = FileOutputStream(file)
                theBitmap!!.compress(Bitmap.CompressFormat.PNG, 100, outStream)
                outStream.flush()
                outStream.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }).start()
        mView?.showToast()
    }

}