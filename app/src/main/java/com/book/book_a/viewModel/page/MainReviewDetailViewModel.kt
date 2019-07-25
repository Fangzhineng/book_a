package com.book.book_a.viewModel.page

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.book.book_a.http.RequestParams
import com.book.book_a.http.ResultCallBack
import com.book.book_a.model.MainReviewDetailBean
import com.book.book_a.viewModel.BaseViewModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.IOException

class MainReviewDetailViewModel(val context: Application)  : BaseViewModel(context), ResultCallBack {


    private val gson: Gson = GsonBuilder().create()

    var info: MutableLiveData<MainReviewDetailBean> = MutableLiveData()

    override fun onSuccess(response: String, flag: Int) {
        loadingMessage.value = false
        if (flag == 1){
            val bean: MainReviewDetailBean = gson.fromJson(response, MainReviewDetailBean::class.java)
            info.value = bean
        }

    }

    override fun onFailure(response: String?, e: IOException?, flag: Int) {
        loadingMessage.value = false
        toastStringMessage.value = response
    }


    fun setData(id:Int){
        RequestParams.getInstance(context).ReviewDetailData(this,id,1)
    }
}