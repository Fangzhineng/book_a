package com.book.book_a.http

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Looper
import com.book.book_a.R
import com.book.book_a.canstants.CustomCanstant.Companion.COOKIE
import com.book.book_a.model.SuccessNoBodyBean
import com.book.book_a.utils.Preference
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import okhttp3.*
import org.jetbrains.anko.toast
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * Created by Administrator on 2018/4/25.
 */

class OkHttpUtils private constructor() {


    private val TAG = "OkHttpUtils"

    internal var cacheSize = 10 * 1024 * 1024

    private val handler: Handler

    private val httpClient: OkHttpClient

    private var isHeader = false//false无请求头    2.true 有请求头

    private var headerKey:String =""

    private var handlerValue:String =""


    var cookie: String by CustomDelegate.preference(COOKIE, context, "")

    object CustomDelegate{
        fun <T> preference(key: String, context: Context, default: T) = Preference(key, context, default)
    }

    init {
        httpClient = OkHttpClient().newBuilder()
            .connectTimeout(DEFAULT_TIMEOUT.toLong(), TimeUnit.MILLISECONDS)
            .readTimeout(DEFAULT_TIMEOUT.toLong(), TimeUnit.MILLISECONDS)
            .writeTimeout(DEFAULT_TIMEOUT.toLong(), TimeUnit.MILLISECONDS)
            .build()
        this.handler = Handler(Looper.getMainLooper())
    }


    fun setHeaders(isHeader:Boolean , headerKey:String, handlerValue:String){
        this.isHeader = isHeader
        this.headerKey = headerKey
        this.handlerValue=handlerValue
    }


    private fun isNetWorkConnected(callBack: ResultCallBack?): Boolean {

        if (NetWorkUtil.isNetWorkConnected(context)) {
            return true
        }
        context.toast(context.getString(R.string.toast_network_connection_tips))
        callBack?.onFailure(null, null, -2)
        return false
    }


    /***
     * POST请求
     */
    fun postGo(
        url: String,
        params: Map<String, Any>,
        callBack: ResultCallBack,
        flag: Int
    ) {
        if (!isNetWorkConnected(callBack)) {
            return
        }

        _postAsync(url, params, callBack, flag, "post")
    }


    /***
     * put请求
     */
    fun putGo(  url: String, params: Map<String, Any>, callBack: ResultCallBack, flag: Int) {
        if (!isNetWorkConnected( callBack)) {
            return
        }
        _postAsync(url, params, callBack, flag, "put")
    }


    /***
     * get请求
     */
    fun getGo( url: String, callBack: ResultCallBack, flag: Int) {

        if (!isNetWorkConnected( callBack)) {
            return
        }
        _getAsync( url, callBack, flag)
    }


    /***
     * delete请求
     */
    fun deleteGo( url: String, callBack: ResultCallBack, flag: Int) {

        if (!isNetWorkConnected( callBack)) {
            return
        }
        _deleteAsync(url, callBack, flag)
    }

    /***
     * deleteJson请求
     */
    fun deleteJson( url: String, json: String, callBack: ResultCallBack, flag: Int) {

        if (!isNetWorkConnected( callBack)) {
            return
        }
        _JsonOAsync( url, json, callBack, flag, "delete")
    }


    /***
     * put请求
     */
    fun putJson( url: String, json: String, callBack: ResultCallBack, flag: Int) {
        if (!isNetWorkConnected( callBack)) {
            return
        }
        _JsonOAsync( url, json, callBack, flag, "put")
    }


    /***
     * POST请求json
     */
    fun postJson( url: String, json: String, callBack: ResultCallBack, flag: Int) {
        if (!isNetWorkConnected( callBack)) {
            return
        }
        _JsonOAsync( url, json, callBack, flag, "post")
    }


    /***
     * POST请求上传图片
     */
    fun postFile(
        url: String,
        params: Map<String, Any>,
        callBack: ResultCallBack,
        fileParam: Map<String, File>,
        fileType: String,
        flag: Int
    ) {
        if (!isNetWorkConnected(callBack)) {
            return
        }
        _postFileAsync( url, params, callBack, fileParam, fileType, flag, "post")
    }


    /***
     * post  put  delete ------ Json
     */
    private fun _JsonOAsync(
        url: String,
        json: String,
        callBack: ResultCallBack,
        flag: Int,
        a: String
    ) {
        val request = getJsonRequest( url, json, a)
        if (request == null) {
            context.toast(context.getString(R.string.toast_network_abnormal_loading_failure))
            callBack.onFailure(null, null, flag)
            return
        }
        deliveryResult( request, callBack, flag)
    }


    private fun _postAsync(
        url: String,
        params: Map<String, Any>,
        callBack: ResultCallBack,
        flag: Int,
        a: String
    ) {
        val paramsArr = map2Params(params)
        val request = getPostRequest(
            url,
            paramsArr,
            a
        )
        if (request == null) {
            context.toast(context.getString(R.string.toast_network_abnormal_loading_failure))
            callBack.onFailure(null, null, -2)
            return
        }
        deliveryResult( request, callBack, flag)
    }


    private fun _deleteAsync(url: String, callBack: ResultCallBack, flag: Int) {
        val request = getDeleteRequest(url)
        if (request == null) {
            context.toast(context.getString(R.string.toast_network_abnormal_loading_failure))
            callBack.onFailure(null, null, -2)
            return
        }
        deliveryResult( request, callBack, flag)
    }


    private fun _getAsync( url: String, callBack: ResultCallBack, flag: Int) {
        val request = getGetRequest(url)
        if (request == null) {
            context.toast(context.getString(R.string.toast_network_abnormal_loading_failure))
            callBack.onFailure(null, null, -2)
            return
        }
        deliveryResult(request, callBack, flag)
    }


    private fun _postFileAsync(
        url: String,
        params: Map<String, Any>,
        callBack: ResultCallBack,
        fileParam: Map<String, File>,
        fileType: String,
        flag: Int,
        a: String
    ) {
        val paramsArr = map2Params(params)
        val request = buildMultipartFormRequests( url, fileParam, paramsArr, fileType, a)
        if (request == null) {
            context.toast(context.getString(R.string.toast_network_abnormal_loading_failure))
            callBack.onFailure(null, null, -2)
            return
        }
        deliveryResult( request, callBack, flag)
    }




    /***
     * 请求服务返回结果
     * @param request       请求Request
     * @param callBack      回调函数
     * @param flag          请求标识
     */
    private fun deliveryResult(request: Request, callBack: ResultCallBack, flag: Int) {
        httpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // TODO Auto-generated method stub
                sendFailureCallBack( request.toString(), e, callBack, flag)
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                // TODO Auto-generated method stub
                val headers = response.headers()
                val cookies = headers.values("Set-Cookie")
                if (cookies.size > 0) {
                    val session = cookies[0]
                    val result = session.substring(0, session.indexOf(";"))
                    //cookie = result
                }
                try {
                    val result = response.body()!!.string()
                    sendSuccessCallBack(result, callBack, flag, response.code())
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }

        })
    }


    private fun sendSuccessCallBack(
        response: String,
        callBack: ResultCallBack?,
        flag: Int,
        statusCode: Int
    ) {
        handler.post {
            // TODO Auto-generated method stub
            if (callBack != null) {
                if (statusCode == 200) {
                    callBack.onSuccess(response, flag)
                }else{
                    if (isJSONValid(response)) {
                        val bean = mGson.fromJson(response, SuccessNoBodyBean::class.java)
                        callBack.onFailure(bean.message, null, flag)
                    } else {
                        callBack.onFailure("请求服务器失败！", null, flag)
                    }

                }
            }
        }
    }

    private fun sendFailureCallBack(
        request: String,
        e: IOException,
        callBack: ResultCallBack?,
        flag: Int
    ) {
        handler.post {
            // TODO Auto-generated method stub
            callBack?.onFailure(request, e, flag)
        }
    }


    /**
     * 图片上传
     *
     * @param url
     * @param fileParam
     * @param params
     * @return
     */
    private fun buildMultipartFormRequests(
        url: String,
        fileParam: Map<String, File>?,
        params: Array<Param?>,
        fileType: String,
        a: String
    ): Request? {
        if (fileParam == null) {
            return null
        }
        val requestBuild = MultipartBody.Builder().setType(MultipartBody.FORM)
        val entries = fileParam.entries.iterator()
        while (entries.hasNext()) {
            val entry = entries.next()
            val fileName = entry.value.name
            val fileBody = RequestBody.create(MediaType.parse(fileType), entry.value)
            requestBuild.addFormDataPart(entry.key, fileName, fileBody)
        }

        for (param in params) {
            requestBuild.addFormDataPart(param!!.key, param.value.toString())
        }
        val requestBody = requestBuild.build()

        return setRequest( url, requestBody, a)
    }


    private fun getPostRequest(url: String, params: Array<Param?>, a: String): Request? {
        return try {
            val builder = FormBody.Builder()
            for (param in params) {
                builder.add(param!!.key, param.value.toString())
            }
            val body = builder.build()
            setRequest(url, body, a)
        } catch (e: Exception) {
            null
        }

    }


    private fun getJsonRequest(url: String, params: String, a: String): Request? {
        return try {
            //            LogUtils.i(TAG, JSON.toJSONString(params));
            val contentType = MediaType.parse("application/json; charset=utf-8")
            val body = RequestBody.create(contentType, params)
            setRequest(url, body, a)
        } catch (e: Exception) {
            null
        }

    }


    private fun setRequest(url: String, requestBody: RequestBody, a: String): Request {
        if (!isHeader) {
            return when (a) {
                "post" -> //post
                    Request.Builder()
                        .url(url)
                        .post(requestBody).build()
                "put" -> //put
                    Request.Builder()
                        .url(url)
                        .put(requestBody)
                        .build()
                else -> //delete
                    Request.Builder()
                        .url(url)
                        .delete(requestBody)
                        .build()
            }
        }else{
            return when (a) {
                "post" -> //post
                    Request.Builder()
                        .header(headerKey,handlerValue)
                        .url(url)
                        .post(requestBody).build()
                "put" -> //put
                    Request.Builder()
                        .url(url)
                        .header(headerKey,handlerValue)
                        .put(requestBody)
                        .build()
                else -> //delete
                    Request.Builder()
                        .url(url)
                        .header(headerKey,handlerValue)
                        .delete(requestBody)
                        .build()
            }
        }

    }


    private fun getDeleteRequest(url: String): Request? {
        return if (!isHeader) {
            Request.Builder()
                .delete()
                .url(url).build()
        } else {
            Request.Builder()
                .header(headerKey,handlerValue)
                .delete()
                .url(url).build()
        }
    }


    private fun getGetRequest(url: String): Request? {
        return if (!isHeader) {
            Request.Builder()
                .url(url).build()
        } else {
            Request.Builder()
                .header(headerKey, handlerValue)
                .url(url).build()
        }
    }


    private fun map2Params(params: Map<String, Any>?): Array<Param?> {
        if (params == null)
            return arrayOfNulls(0)
        val size = params.size
        val res = arrayOfNulls<Param>(size)
        val entries = params.entries
        var i = 0
        for ((key, value) in entries) {
            res[i++] = Param(key, value)
        }
        return res
    }

    class Param(internal var key: String, internal var value: Any)

    companion object {

        @SuppressLint("StaticFieldLeak")
        private  var instance: OkHttpUtils?= null

        private val DEFAULT_TIMEOUT = 30000

        private var mGson = GsonBuilder().create()

        @SuppressLint("StaticFieldLeak")
        private lateinit var context:Context


        //默认1
        fun getInstance(context: Context): OkHttpUtils {
            Companion.context = context
            if (instance == null) {
                synchronized(OkHttpUtils::class.java) {
                    if (instance == null) {
                        instance = OkHttpUtils()
                        return instance!!
                    }
                }
            }
            return instance!!
        }


        /**
         * Google Gson
         *
         * @param jsonInString
         * @return
         */
        fun isJSONValid(jsonInString: String): Boolean {
            return try {
                mGson.fromJson(jsonInString, Any::class.java)
                true
            } catch (ex: JsonSyntaxException) {
                false
            }

        }
    }


}