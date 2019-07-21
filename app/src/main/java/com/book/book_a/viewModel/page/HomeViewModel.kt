package com.book.book_a.viewModel.page

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.book.book_a.http.RequestParams
import com.book.book_a.http.ResultCallBack
import com.book.book_a.model.*
import com.book.book_a.viewModel.BaseViewModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.IOException


/**
 * Created by Jin on 10/15/2018
 */
class HomeViewModel (val content: Application) : BaseViewModel(content),ResultCallBack{

    val topInfo: MutableLiveData<TopBean> = MutableLiveData()

    val mianListInfo:MutableLiveData<List<NewsList>> = MutableLiveData()

    private val gson:Gson = GsonBuilder().create()

    var mPage:Int = 1

    init {
        RequestParams.getInstance(content).topData(this,1)
    }


    override fun onSuccess(response: String, flag: Int) {
        loadingMessage.value = false
        if (flag == 1){
            val bean: TopBean = gson.fromJson(response, TopBean::class.java)
            topInfo.value = bean
            load()
        }

        if (flag == 2){
            val bean:MainListBean = gson.fromJson(response,MainListBean::class.java)
            mianListInfo.value = bean.newsList
        }

    }

    override fun onFailure(response: String?, e: IOException?, flag: Int) {
        loadingMessage.value = false
        toastStringMessage.value = response
    }


    fun load() {
        //.getInstance(content).homeData(this,1)
        RequestParams.getInstance(content).mainListData(this,mPage,2)
    }

}
