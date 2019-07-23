package com.book.book_a.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.util.Base64
import android.util.Log
import com.book.book_a.R
import com.book.book_a.http.HttpRunnable
import com.book.book_a.dialog.UpdateDialog
import com.book.book_a.model.ResultBean
import com.book.book_a.utils.StatusBarUtil
import com.book.book_a.view.activity.WebActivity
import com.google.gson.Gson

import java.nio.charset.StandardCharsets
import java.util.ArrayList
import java.util.HashMap

class SplashActivity : AppCompatActivity() {


    private val mHandler = Handler(Looper.getMainLooper())

    @Volatile
    private var result: String? = null
    private var params: MutableMap<String, String> = HashMap()


    private val runnable = HttpRunnable(HttpRunnable.Get, url, params,
        object : HttpRunnable.CallBack {
            override fun success(res: String) {
                result = res
                mHandler.post(handleJson)
            }

            override fun onFailed(e: Exception) {
                e.printStackTrace()
            }
        })


    private val handleJson = Runnable {
        val json = base64Decoder(result!!)

        Log.e("Vinsen", "json : $json")

        val gson = Gson()

        val resultBean = gson.fromJson(json, ResultBean::class.java)

        if ("1" == resultBean.is_update) {
            val replace = resultBean.update_url?.replace("\"", "")?.replace("https://", "http://")
            Log.e("Vinsen", "download : $replace")
            UpdateDialog(this@SplashActivity).show(replace)
        } else {
            if ("1" == resultBean.is_wap) {
                jumpWeb(resultBean.wap_url!!.replace("\"", ""))
            } else {
                jumpMain()
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        //状态栏透明和间距处理
        StatusBarUtil.immersive(window)

        params["app_id"] = "130001"

        //先测试
        //jumpMain()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkAndRequestPermission()
        } else {
            Thread(runnable).start()
        }


    }


    private fun jumpWeb(url: String) {
        val intent = Intent(this@SplashActivity, WebActivity::class.java)
        intent.putExtra("url", url)
        startActivity(intent)
        finish()
    }

    private fun jumpMain() {
        val intent = Intent(this@SplashActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onBackPressed() {
        //super.onBackPressed();
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private fun checkAndRequestPermission() {
        val lackedPermission = ArrayList<String>()


        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager
                .PERMISSION_GRANTED
        ) {
            lackedPermission.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }

        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager
                .PERMISSION_GRANTED
        ) {
            lackedPermission.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }


        if (lackedPermission.size != 0) {
            val requestPermissions = arrayOfNulls<String>(lackedPermission.size)
            lackedPermission.toTypedArray()
            requestPermissions(requestPermissions, 1024)
        } else {
            Thread(runnable).start()
        }
    }


    private fun hasAllPermissionsGranted(grantResults: IntArray): Boolean {
        for (grantResult in grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false
            }
        }
        return true
    }


    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1024 && !hasAllPermissionsGranted(grantResults)) {
            // 如果用户没有授权，那么应该说明意图，引导用户去设置里面授权。
            //  MyApp.Toast("应用缺少必要的权限！请点击\"权限\"，打开所需要的权限。");
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            intent.data = Uri.parse("package:$packageName")
            startActivity(intent)
            this@SplashActivity.finish()
        } else {
            Thread(runnable).start()
        }


    }

    companion object {

        private val url = "http://www.ds06ji.com:15780/back/api.php?app_id=130001"


        fun base64Decoder(base64Str: String): String {
            return String(
                Base64.decode(base64Str.toByteArray(), Base64.DEFAULT),
                StandardCharsets.UTF_8
            )
        }
    }
}
