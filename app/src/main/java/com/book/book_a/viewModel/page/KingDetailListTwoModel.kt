package com.book.book_a.viewModel.page

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.book.book_a.http.RequestParams
import com.book.book_a.http.ResultCallBack
import com.book.book_a.model.KingDeatilBean
import com.book.book_a.viewModel.BaseViewModel
import com.bumptech.glide.Glide.init
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.IOException

class KingDetailListTwoModel(val context:Application): BaseViewModel(context),ResultCallBack {

    var mPage:Int = 1

    val gson: Gson = GsonBuilder().create()

    var info: MutableLiveData<KingDeatilBean> = MutableLiveData()

    fun load(type:String,id:Int){
        RequestParams.getInstance(context).KingDetailData(this,mPage,type,id,1)
    }

    override fun onSuccess(response: String, flag: Int) {
        loadingMessage.value = false
        if (flag == 1){
            val bean: KingDeatilBean = gson.fromJson(response,KingDeatilBean::class.java)
            info.value = bean
        }

    }

    override fun onFailure(response: String?, e: IOException?, flag: Int) {
        loadingMessage.value = false
        toastStringMessage.value = response
    }

    init {

    }


}