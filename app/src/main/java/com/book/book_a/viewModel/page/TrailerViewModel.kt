package com.book.book_a.viewModel.page

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.book.book_a.http.RequestParams
import com.book.book_a.http.ResultCallBack
import com.book.book_a.model.TopBean
import com.book.book_a.model.Trailer
import com.book.book_a.model.TrailerBean
import com.book.book_a.model.TrailerBody
import com.book.book_a.viewModel.BaseViewModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.IOException

class TrailerViewModel(val context:Application) :BaseViewModel(context) ,ResultCallBack{

    var trailerInfo: MutableLiveData<List<TrailerBody>> = MutableLiveData()

    var topInfo: MutableLiveData<Trailer> = MutableLiveData()

    private val gson: Gson = GsonBuilder().create()


    override fun onSuccess(response: String, flag: Int) {
        loadingMessage.value = false
        if (flag == 1){
            val bean: TopBean = gson.fromJson(response, TopBean::class.java)
            topInfo.value = bean.trailer
            load()
        }

        if (flag == 2){
            val bean: TrailerBean = gson.fromJson(response, TrailerBean::class.java)
            trailerInfo.value = bean.trailers
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
        RequestParams.getInstance(context).TrailerData(this,2)
    }

}