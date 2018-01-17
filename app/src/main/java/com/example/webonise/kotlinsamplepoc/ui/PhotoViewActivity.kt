package com.example.webonise.kotlinsamplepoc.ui

import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.webonise.kotlinsamplepoc.R
import kotlinx.android.synthetic.main.activity_photo_view.*
import java.io.File
import java.io.FileOutputStream

/**
 * Created by webonise on 17/1/18.
 */
class PhotoViewActivity : AppCompatActivity() {
    private var photoUrl:String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_view)
        init()
    }

    private fun init() {
        photoUrl = intent.getStringExtra("photoUrl")
        loadPhoto()
        button.setOnClickListener{saveImageToFile(photoUrl)}
    }

    private fun loadPhoto() {
        Glide.with(this).load(photoUrl).into(imageView)
    }

    fun saveImageToFile(photoUri: String) {
        Thread(Runnable {
            var theBitmap: Bitmap? = null
            try {
                theBitmap = Glide.with(this).load(photoUri).asBitmap().into(-1, -1).get()
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
        Toast.makeText(this, getString(R.string.photo_downloaded), Toast.LENGTH_SHORT).show()
    }
}