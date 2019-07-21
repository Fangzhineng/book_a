package com.book.book_a.viewModel.page

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.book.book_a.http.RequestParams
import com.book.book_a.http.ResultCallBack
import com.book.book_a.model.ReviewBean
import com.book.book_a.model.TopBean
import com.book.book_a.viewModel.BaseViewModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import java.io.IOException

class FilmReviewViewModel(val context:Application): BaseViewModel(context) ,ResultCallBack{

    val topInfo: MutableLiveData<TopBean> = MutableLiveData()

    val mianListInfo: MutableLiveData<List<ReviewBean>> = MutableLiveData()

    private val gson: Gson = GsonBuilder().create()

    override fun onSuccess(response: String, flag: Int) {
        loadingMessage.value = false
        if (flag == 1){
            val bean: TopBean = gson.fromJson(response, TopBean::class.java)
            topInfo.value = bean
            load()
        }

        if (flag == 2){
            val mList = arrayListOf<ReviewBean>()
            val jsonArray = JsonParser().parse(response).asJsonArray
            for (json in jsonArray) {
                val bean: ReviewBean = gson.fromJson(response, ReviewBean::class.java)
                mList.add(bean)
            }
            mianListInfo.value = mList
        }

    }


    override fun onFailure(response: String?, e: IOException?, flag: Int) {
        loadingMessage.value = false
        toastStringMessage.value = response
    }

    init {
        RequestParams.getInstance(context).topData(this,1)
    }


    fun load(){
        RequestParams.getInstance(context).ReviewData(this,false,2)
    }


}