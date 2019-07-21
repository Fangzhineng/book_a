package com.book.book_a.viewModel.page

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.book.book_a.http.RequestParams
import com.book.book_a.http.ResultCallBack
import com.book.book_a.model.KingBean
import com.book.book_a.model.MainListBean
import com.book.book_a.model.TopBean
import com.book.book_a.model.TopLists
import com.book.book_a.viewModel.BaseViewModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.IOException

class KingViewModel(val content:Application):BaseViewModel(content) ,ResultCallBack{

    var kingInfo:MutableLiveData<List<TopLists>> = MutableLiveData()

    var topInfo:MutableLiveData<TopBean> = MutableLiveData()

    var mPage = 1

    private val gson: Gson = GsonBuilder().create()


    override fun onSuccess(response: String, flag: Int) {
        loadingMessage.value = false
        if (flag == 1){
            val bean: TopBean = gson.fromJson(response, TopBean::class.java)
            topInfo.value = bean
            load()
        }

        if (flag == 2){
            val bean: KingBean = gson.fromJson(response, KingBean::class.java)
            kingInfo.value = bean.topLists
        }

    }

    override fun onFailure(response: String?, e: IOException?, flag: Int) {
        loadingMessage.value = false
        toastStringMessage.value = response
    }


    init {
        RequestParams.getInstance(content).topData(this,1)
    }


    fun load(){
        RequestParams.getInstance(content).kingData(this,mPage,2)
    }


}