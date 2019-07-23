package com.book.book_a.http

import android.annotation.SuppressLint
import android.content.Context
import com.book.book_a.canstants.ServerConstants
import com.google.gson.Gson
import java.util.HashMap


/**
 * Created by Administrator on 2018/4/25.
 */

class RequestParams {

    var TAG = "RequestParams"

    private val okhttp =   OkHttpUtils.getInstance(context)

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var instance: RequestParams? = null

        @SuppressLint("StaticFieldLeak")
        private lateinit var context: Context



        //默认选择type
        fun getInstance(context: Context): RequestParams {
            this.context = context
            if (instance == null) {
                synchronized(OkHttpUtils::class.java) {
                    if (instance == null) {
                        instance = RequestParams()
                        return instance!!
                    }
                }
            }
            return instance!!
        }
    }


    private var mGson:Gson= Gson()

    init {

    }

    fun UpDataApp(callBack: ResultCallBack,url:String,flag:Int) {
        val params: MutableMap<String, String> = HashMap()
        params["app_id"] = "130001"
        okhttp.getGo(url, callBack, flag)
    }


    fun homeData(callBack: ResultCallBack,flag: Int){
        val url = ServerConstants.homeData
        okhttp.getGo(url, callBack, flag)
    }


    fun topData(callBack: ResultCallBack,flag: Int){
        val url = ServerConstants.top
        val cookie = "aliyungf_tc=AQAAABYe7Q6tDQYAVxYTOh6Iw0iZmIRY; _tt_=5d2fdc3200fc8c04301ede0e; searchID=246D9229C1B5D90FDCD3F8E3A298FC45; noresult=true; clientDownload=true; Hm_lvt_6c79b03c4a38f58353c660ab7687f9c5=1563434988,1563439794,1563442022,1563442598; Hm_lpvt_6c79b03c4a38f58353c660ab7687f9c5=1563442598; Hm_lvt_774f89cf371fe5674ad404a5f3994448=1563434988,1563439794,1563442023,1563442598; Hm_lpvt_774f89cf371fe5674ad404a5f3994448=1563442598"
        okhttp.setHeaders(true,"Cookie",cookie)
        okhttp.getGo(url, callBack, flag)
       // OkHttpUtils.getInstance(context).getGo(url, callBack, flag)
    }


    fun mainListData(callBack: ResultCallBack,page:Int,flag: Int){
        val url = ServerConstants.main_list+"?pageIndex="+page
        okhttp.getGo(url, callBack, flag)
    }

    fun kingData(callBack: ResultCallBack,page:Int,flag: Int){
        val url = ServerConstants.King+"?pageIndex="+page
        okhttp.getGo(url, callBack, flag)
    }


    fun ReviewData(callBack: ResultCallBack,isNeed:Boolean,flag: Int){
        val url = ServerConstants.Review+"?needTop="+isNeed
        okhttp.getGo(url, callBack, flag)
    }

    fun ReviewDetailData(callBack: ResultCallBack,id:Int,flag: Int){
        val url = ServerConstants.Review_detail+"?reviewId=$id"
        okhttp.getGo(url, callBack, flag)
    }



    fun TrailerData(callBack: ResultCallBack,flag: Int){
        val url = ServerConstants.prevue
        okhttp.getGo(url, callBack, flag)
    }

    fun MianTopDetailData(callBack: ResultCallBack,id:Int,flag: Int){
        val url = ServerConstants.main_list_detail+"?newsId=$id"
        okhttp.getGo(url, callBack, flag)
    }

    //pageIndex=1&type=2&toplistId=1474&pageSubAreaID=1474
    fun KingDetailData(callBack: ResultCallBack,page:Int,type:String,id:Int,flag: Int){
        val url = when(type){
            "1"->{
                ServerConstants.King_list_detial_top+"?pageIndex=$page&type=$type&toplistId=$id&pageSubAreaID=$id"
            }
            else->{
                ServerConstants.King_list_detial+"?pageIndex=$page&type=$type&toplistId=$id&pageSubAreaID=$id"
            }
        }
        okhttp.getGo(url, callBack, flag)
    }


    //movieId=91850&locationId=290&t=201971813585181003
    fun KingDetailTwoData(callBack: ResultCallBack,id:Int,locationId:Int,flag: Int){
        val url = ServerConstants.King_list_detial_two+"?movieId=$id&locationId=$locationId"
        okhttp.getGo(url, callBack, flag)
    }
}
