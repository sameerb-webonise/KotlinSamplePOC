package com.example.webonise.kotlinsamplepoc.interfaces

import com.example.webonise.kotlinsamplepoc.models.RecentPlaceListModel
import com.example.webonise.kotlinsamplepoc.ui.RecentSearchListActivity

interface IRecentSearchList {
    fun setView(mActivity: RecentSearchListActivity)

    fun getData(): ArrayList<RecentPlaceListModel>

    fun handleItemClick(position: Int)
}