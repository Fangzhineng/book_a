package com.book.book_a.http

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.support.v4.app.ActivityCompat
import android.telephony.TelephonyManager

object NetWorkUtil {


    /***
     * 判断当前网络是否可用
     *
     * @param context
     * @return
     */
    fun isNetWorkAvailable(context: Context?): Boolean {

        if (context != null) {
            val mgr = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val info = mgr.allNetworkInfo
            if (info != null) {
                for (i in info.indices) {
                    if (info[i].state == NetworkInfo.State.CONNECTED) {
                        return true
                    }
                }
            }
        }

        return false
    }

    /***
     * 判断当前网络是否连接
     *
     * @param context
     * @return
     */
    fun isNetWorkConnected(context: Context?): Boolean {
        if (context != null) {
            val mConnectivityManager = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val mNetworkInfo = mConnectivityManager
                .activeNetworkInfo
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable
            }
        }
        return false
    }


    /***
     * 判断WIFI网络是否可用
     *
     * @param context
     * @return
     */
    fun isWifiConnected(context: Context?): Boolean {
        if (context != null) {
            val mConnectivityManager = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val mWiFiNetworkInfo = mConnectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI)
            if (mWiFiNetworkInfo != null) {
                return mWiFiNetworkInfo.isAvailable
            }
        }
        return false
    }

    /***
     * 判断MOBILE网络是否可用
     *
     * @param context
     * @return
     */
    fun isMobileConnected(context: Context?): Boolean {
        if (context != null) {
            val mConnectivityManager = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val mWiFiNetworkInfo = mConnectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
            if (mWiFiNetworkInfo != null) {
                return mWiFiNetworkInfo.isAvailable
            }
        }
        return false
    }

    /**
     * 获取当前网络连接的类型信息
     *
     * @param context
     * @return
     */
    fun getConnectedType(context: Context?): Int {
        if (context != null) {
            val mConnectivityManager = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val mNetworkInfo = mConnectivityManager
                .activeNetworkInfo
            if (mNetworkInfo != null && mNetworkInfo.isAvailable) {
                return mNetworkInfo.type
            }
        }
        return -1
    }

    /***
     * 获取当前的网络状态 -1：没有网络 1：WIFI网络 2：wap 网络 3：net网络
     *
     * @param context
     * @return
     */
    fun getCurrentNetWorkStatus(context: Context?): Int {

        if (context != null) {
            val mConnectivityManager = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val mNetworkInfo = mConnectivityManager
                .activeNetworkInfo ?: return -1
            val type = mNetworkInfo.type
            if (type == ConnectivityManager.TYPE_MOBILE) {
                return if (mNetworkInfo.extraInfo.toLowerCase() == "cmnet") {
                    3
                } else {
                    2
                }
            } else if (type == ConnectivityManager.TYPE_WIFI) {
                return 1
            }
        }
        return -1
    }


    /**
     * 获取手机服务商名字
     *
     * @param context
     * @return
     */
    fun getProvidersName(context: Context): String {
        var ProvidersName: String? = null
        // 返回唯一的用户ID;就是这张卡的编号神马的
        val IMSI = getIMSI(context)
        // IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信。
        println(IMSI)
        if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {
            ProvidersName = "中国移动"
        } else if (IMSI.startsWith("46001")) {
            ProvidersName = "中国联通"
        } else if (IMSI.startsWith("46003")) {
            ProvidersName = "中国电信"
        } else {
            ProvidersName = "其他服务商"
        }
        return ProvidersName
    }

    /**
     * 获取手机IMSI号码
     *
     * @param context
     * @return
     */
    @SuppressLint("HardwareIds")
    fun getIMSI(context: Context?): String {
        if (context != null) {
            val telephonyManager: TelephonyManager = context
                .getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            // 返回唯一的用户ID;就是这张卡的IMSI编号
            return if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.READ_PHONE_STATE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                telephonyManager.subscriberId
            } else telephonyManager.subscriberId
        }
        return ""
    }

    /***
     * 获取手机IMEI号码
     *
     * @param context
     * @return
     */
    @SuppressLint("MissingPermission", "HardwareIds")
    fun getIMEI(context: Context?): String {
        if (context != null) {
            val telephonyManager: TelephonyManager = context
                .getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            // 返回唯一的用户ID;就是这张卡的IMSI编号
            return telephonyManager.deviceId
        }
        return ""
    }
}
