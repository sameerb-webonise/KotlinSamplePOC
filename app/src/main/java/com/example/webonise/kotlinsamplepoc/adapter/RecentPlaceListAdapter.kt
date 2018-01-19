package com.example.webonise.kotlinsamplepoc.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.webonise.kotlinsamplepoc.R
import com.example.webonise.kotlinsamplepoc.models.RecentPlaceListModel
import com.example.webonise.kotlinsamplepoc.presenter.RecentSearchListPresenter
import com.example.webonise.kotlinsamplepoc.ui.RecentSearchListActivity
import kotlinx.android.synthetic.main.recent_place_list_item.view.*
import java.util.ArrayList

class RecentPlaceListAdapter(): RecyclerView.Adapter<RecentPlaceListAdapter.RecentPlaceListHolder>()  {
    private lateinit var mContext: RecentSearchListActivity
    private lateinit var mPresenter: RecentSearchListPresenter
    private lateinit var mRecentPlaceList: ArrayList<RecentPlaceListModel>

    constructor(context: RecentSearchListActivity, presenter: RecentSearchListPresenter, photoList: ArrayList<RecentPlaceListModel>) : this() {
        mContext = context
        mPresenter = presenter
        mRecentPlaceList = photoList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentPlaceListAdapter.RecentPlaceListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recent_place_list_item, parent,
                false)
        return RecentPlaceListAdapter.RecentPlaceListHolder(view)
    }

    override fun onBindViewHolder(holder: RecentPlaceListAdapter.RecentPlaceListHolder, position: Int) {
        holder.placeName.text = mRecentPlaceList.get(position).name
        holder.placeName.setOnClickListener { mPresenter.handleItemClick(position) }
    }

    override fun getItemCount(): Int {
        return mRecentPlaceList.size
    }

    class RecentPlaceListHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal val placeName: TextView

        init {
            placeName = view.placeName as TextView
        }

    }
}