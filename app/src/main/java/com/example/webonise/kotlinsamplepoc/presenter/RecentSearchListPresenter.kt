package com.example.webonise.kotlinsamplepoc.presenter

import com.example.webonise.kotlinsamplepoc.adapter.RecentPlaceListAdapter
import com.example.webonise.kotlinsamplepoc.interfaces.IRecentSearchList
import com.example.webonise.kotlinsamplepoc.models.RecentPlaceListModel
import com.example.webonise.kotlinsamplepoc.models.RecentPlaces
import com.example.webonise.kotlinsamplepoc.ui.RecentSearchListActivity
import io.realm.Realm

class RecentSearchListPresenter: IRecentSearchList {

    private var mView: RecentSearchListActivity? = null
    private var mAdaptor: RecentPlaceListAdapter? = null
    private var recentPlacesListModel: ArrayList<RecentPlaceListModel>? = null

    override fun setView(mActivity: RecentSearchListActivity) {
        mView = mActivity
        val data: ArrayList<RecentPlaceListModel> = getData()
        mAdaptor = RecentPlaceListAdapter(mView as RecentSearchListActivity, this, data)
        mView?.updateUI()
        //mView!!.runOnUiThread(Runnable { mAdaptor!!.notifyDataSetChanged() })
    }

    fun getAdaptor(): RecentPlaceListAdapter? {
        return mAdaptor
    }

    override fun getData(): ArrayList<RecentPlaceListModel> {
        val realm = Realm.getDefaultInstance()
        val realmQuery = realm.where(RecentPlaces::class.java)
        val realmResults = realmQuery.findAll()
        recentPlacesListModel = ArrayList()
        if(realmResults.isNotEmpty()) {
            for (realmResult in realmResults) {
                recentPlacesListModel?.add(RecentPlaceListModel(realmResult.name!!, realmResult.latlng!!))
            }
            //realmResults.mapTo(recentPlacesListModel) { RecentPlaceListModel(it.name, it.latlng) }
        }
        realm.close()
        return recentPlacesListModel as ArrayList<RecentPlaceListModel>
    }

    override fun handleItemClick(position: Int) {
        mView?.showPlaceDetailsSreen(recentPlacesListModel?.get(position))
    }
}