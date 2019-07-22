package com.book.book_a.utils

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Environment
import android.support.annotation.ColorInt
import android.support.v4.graphics.ColorUtils
import android.view.View
import android.view.WindowManager
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.text.DecimalFormat
import java.util.HashMap
import android.media.MediaMetadataRetriever
import android.graphics.Bitmap



class Utils {

    fun getVerName(context: Context): String {
        var verName = ""
        try {
            verName = context.packageManager.getPackageInfo(context.packageName, 0).versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        return verName
    }

    /**
     * 获取APP版本号
     *
     * @param ctx
     * @return
     */
    fun getVersionCode(ctx: Context): Int {
        // 获取packagemanager的实例
        var version = 0
        try {
            val packageManager = ctx.packageManager
            //getPackageName()是你当前程序的包名
            val packInfo = packageManager.getPackageInfo(ctx.packageName, 0)
            version = packInfo.versionCode
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return version
    }

    /**
     * 获取文件保存路径 sdcard根目录/download/文件名称
     *
     * @param fileUrl
     * @return
     */
    fun getSaveFilePath(fileUrl: String): String {
        val fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1, fileUrl.length)//获取文件名称
        return Environment.getExternalStorageDirectory().toString() + "/Download/" + fileName
    }

    //获取状态栏的高度  该方法会在低版本中失效
    fun getStatusBarHeight(activity: Activity): Int {
        val model = Build.MODEL
        val brand = Build.BOARD
        //        if (brand.toLowerCase().contains("HONOR".toLowerCase()) || brand.toLowerCase().contains("Huawei".toLowerCase()) || android.os.Build.VERSION.SDK_INT < 19 || model.contains("HUAWEI") || model.contains("huawei") || model.contains("PLK-AL10")) {
        //            return 0;
        //        }
        if (android.os.Build.VERSION.SDK_INT < 19) {
            return 0
        }
        var result = 0
        val resourceId = activity.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = activity.resources.getDimensionPixelSize(resourceId)
        }
        return result
    }


    fun getDaoHangHeight(context: Context): Int {
        val result = 0
        var resourceId = 0
        val rid = context.resources.getIdentifier("config_showNavigationBar", "bool", "android")
        if (rid != 0) {
            resourceId = context.resources.getIdentifier("navigation_bar_height", "dimen", "android")
            return context.resources.getDimensionPixelSize(resourceId)
        } else
            return 0
    }

    /**
     * 设置webView宽度
     *
     * @param context
     * @return
     */
    fun setWebViewWidth(context: Context): Int {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val width = wm.defaultDisplay.width
        return if (width > 650) {
            190
        } else if (width > 520) {
            160
        } else if (width > 450) {
            140
        } else if (width > 300) {
            120
        } else {
            100
        }
    }


    //音视频市场
    fun getRingDuring(mUri: String?): String? {
        var duration: String? = null
        val mmr = android.media.MediaMetadataRetriever()

        try {
            if (mUri != null) {
                var headers: HashMap<String, String>? = null
                if (headers == null) {
                    headers = HashMap()
                    headers["User-Agent"] = "Mozilla/5.0 (Linux; U; Android 4.4.2; zh-CN; MW-KW-001 Build/JRO03C) AppleWebKit/533.main_home_picture (KHTML, like Gecko) Version/4.0 UCBrowser/main_home_picture.0.0.001 U4/0.8.0 Mobile Safari/533.main_home_picture"
                }
                mmr.setDataSource(mUri, headers)
            }

            duration = mmr.extractMetadata(android.media.MediaMetadataRetriever.METADATA_KEY_DURATION)
        } catch (ex: Exception) {
        } finally {
            mmr.release()
        }
        return duration
    }


    /**
     * 根据地址获得数据的字节流并转换成大小
     *
     * @param strUrl 网络连接地址
     * @return
     */
    fun getFileSizeUrl(strUrl: String): String {
        val inStream: InputStream? = null
        val outStream: ByteArrayOutputStream? = null
        var size = ""
        var url: URL? = null
        try {
            url = URL(strUrl)
            val conn = url.openConnection() as HttpURLConnection
            conn.requestMethod = "HEAD"
            conn.connectTimeout = 5 * 1000
            val s = conn.contentLength
            if (s > 0) {
                val df = DecimalFormat("#.00")
                if (s < 1024) {
                    size = df.format(s.toDouble()) + "BT"
                } else if (s < 1048576) {
                    size = df.format(s.toDouble() / 1024) + "KB"
                } else if (s < 1073741824) {
                    size = df.format(s.toDouble() / 1048576) + "MB"
                } else {
                    size = df.format(s.toDouble() / 1073741824) + "GB"
                }
                println("文件大小=：$size")
            } else {
                println("没有从该连接获得内容")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                inStream?.close()
                outStream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
        return size
    }


    //设置顶部导航栏颜色
    fun setWindowStatusBarColor(activity: Activity, colorResId: Int) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val window = activity.window
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                window.statusBarColor = activity.resources.getColor(colorResId)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun setWindowBlack(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

    fun setWindowWrite(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
        }
    }


    /**
     * 判断颜色是不是亮色
     *
     * @param color
     * @return
     * @from https://stackoverflow.com/questions/24260853/check-if-color-is-dark-or-light-in-android
     */
    private fun isLightColor(@ColorInt color: Int): Boolean {
        return ColorUtils.calculateLuminance(color) >= 0.5
    }

    /**
     * 获取StatusBar颜色，默认白色
     *
     * @return
     */
    @ColorInt
    protected fun getStatusBarColor(): Int {
        return Color.WHITE
    }


    //影藏顶部导航栏
    fun setWindowNo(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = activity.window
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        }

    }


    fun getNetVideoBitmap(videoUrl: String): Bitmap? {
        var bitmap: Bitmap? = null

        val retriever = MediaMetadataRetriever()
        try {
            //根据url获取缩略图
            retriever.setDataSource(videoUrl, HashMap())
            //获得第一帧图片
            bitmap = retriever.frameAtTime
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        } finally {
            retriever.release()
        }
        return bitmap
    }
}