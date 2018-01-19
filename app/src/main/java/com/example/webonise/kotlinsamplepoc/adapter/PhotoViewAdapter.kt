package com.example.webonise.kotlinsamplepoc.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.webonise.kotlinsamplepoc.R
import com.example.webonise.kotlinsamplepoc.presenter.PlaceDetailsPresenter
import com.example.webonise.kotlinsamplepoc.ui.PlaceDetailsActivity
import kotlinx.android.synthetic.main.photo_list_item.view.*
import java.util.*

class PhotoViewAdapter(): RecyclerView.Adapter<PhotoViewAdapter.PhotoListHolder>() {
    private lateinit var mContext: PlaceDetailsActivity
    private lateinit var mPresenter: PlaceDetailsPresenter
    private lateinit var mPhotoList: ArrayList<String>

    constructor(context: PlaceDetailsActivity, presenter: PlaceDetailsPresenter, photoList: ArrayList<String>) : this() {
        mContext = context
        mPresenter = presenter
        mPhotoList = photoList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.photo_list_item, parent,
                false)
        return PhotoListHolder(view)
    }

    override fun onBindViewHolder(holder: PhotoListHolder, position: Int) {
        Glide.with(mContext).load(mPhotoList[holder.adapterPosition]).into(holder.imageView)
        holder.imageView.setOnClickListener { mPresenter.handleItemClick(position) }
    }

    override fun getItemCount(): Int {
        return mPhotoList.size
    }

    class PhotoListHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal val imageView: ImageView

        init {
            imageView = view.ivPhoto as ImageView
        }

    }
}