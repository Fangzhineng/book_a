package com.book.book_a.view

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.View
import com.book.book_a.R
import com.book.book_a.canstants.CustomCanstant.Companion.TAG_PAGE_CITY
import com.book.book_a.canstants.CustomCanstant.Companion.TAG_PAGE_HOME
import com.book.book_a.canstants.CustomCanstant.Companion.TAG_PAGE_MESSAGE
import com.book.book_a.canstants.CustomCanstant.Companion.TAG_PAGE_PERSON
import com.book.book_a.databinding.ActivityMainBinding
import com.book.book_a.utils.StatusBarUtil
import com.book.book_a.base.ActivityManager
import com.book.book_a.base.BaseActivity
import com.book.book_a.view.fragment.*
import com.book.book_a.witgets.MainNavigateTabBar
import org.jetbrains.anko.toast

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private var firstTime: Long = 0


    override fun setCreateView(): ActivityMainBinding {
        return DataBindingUtil.setContentView(this, R.layout.activity_main)
    }


    override fun initView() {
        //状态栏透明和间距处理
        StatusBarUtil.immersive(window)
        binding.mainTabBar.onRestoreInstanceState(getBundle())

//        binding.mainTabBar.addTab(MainHomeFragment::class.java, MainNavigateTabBar.TabParam(
//            R.mipmap.comui_tab_home,
//            R.mipmap.comui_tab_home_selected, TAG_PAGE_HOME))
//        binding.mainTabBar.addTab(MainKingFragment::class.java, MainNavigateTabBar.TabParam(
//            R.mipmap.comui_tab_city,
//            R.mipmap.comui_tab_city_selected, TAG_PAGE_CITY))
//       // binding.mainTabBar.addTab(MainPublishFragment::class.java, MainNavigateTabBar.TabParam(-1, -1, TAG_PAGE_PUBLISH))
//        binding.mainTabBar.addTab(MainTrailerFragment::class.java, MainNavigateTabBar.TabParam(
//            R.mipmap.comui_tab_message,
//            R.mipmap.comui_tab_message_selected, TAG_PAGE_MESSAGE))
//        binding.mainTabBar.addTab(
//            MainFilmReviewFragment::class.java, MainNavigateTabBar.TabParam(
//            R.mipmap.comui_tab_person,
//            R.mipmap.comui_tab_person_selected, TAG_PAGE_PERSON))
        binding.mainTabBar.addTab(MainHomeFragment::class.java, MainNavigateTabBar.TabParam(
                -2,
                -2, TAG_PAGE_HOME))
        binding.mainTabBar.addTab(MainKingFragment::class.java, MainNavigateTabBar.TabParam(
                -2,
                -2, TAG_PAGE_CITY))
        // binding.mainTabBar.addTab(MainPublishFragment::class.java, MainNavigateTabBar.TabParam(-1, -1, TAG_PAGE_PUBLISH))
        binding.mainTabBar.addTab(MainTrailerFragment::class.java, MainNavigateTabBar.TabParam(
                -2,
                -2, TAG_PAGE_MESSAGE))
        binding.mainTabBar.addTab(
                MainFilmReviewFragment::class.java, MainNavigateTabBar.TabParam(
                -2,
                -2, TAG_PAGE_PERSON))
    }


    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        if (outState != null) {
            binding.mainTabBar.onSaveInstanceState(outState)
        }
    }

    fun onClickPublish(v: View) {
        binding.mainTabBar.currentSelectedTab = 2
    }

    override fun initData() {

    }


    override fun onBackPressed() {
        if (System.currentTimeMillis() - firstTime < 2000) {
            //ExitApplication.getInstance().exit(this);
            ActivityManager.activityManager.finishAllActivity()
            return
        }
        toast("在按一次退出")
        firstTime = System.currentTimeMillis()
    }
}
