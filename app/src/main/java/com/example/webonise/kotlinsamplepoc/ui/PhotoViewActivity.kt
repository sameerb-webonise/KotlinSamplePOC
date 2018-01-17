package com.example.webonise.kotlinsamplepoc.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.webonise.kotlinsamplepoc.R
import com.example.webonise.kotlinsamplepoc.presenter.PhotoViewPresenter
import kotlinx.android.synthetic.main.activity_photo_view.*

/**
 * Created by webonise on 17/1/18.
 */
class PhotoViewActivity : AppCompatActivity() {
    private var photoUrl:String = ""
    private var mPresenter: PhotoViewPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_view)
        init()
    }

    private fun init() {
        photoUrl = intent.getStringExtra("photoUrl")
        loadPhoto()
        mPresenter = PhotoViewPresenter()
        mPresenter?.setView(this)
        button.setOnClickListener{mPresenter?.saveImageToFile(photoUrl)}
    }

    private fun loadPhoto() {
        Glide.with(this).load(photoUrl).into(imageView)
    }

    fun showToast() {
        Toast.makeText(this, getString(R.string.photo_downloaded), Toast.LENGTH_SHORT).show()
    }
}