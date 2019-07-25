package com.book.book_a.view.activity

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentStatePagerAdapter
import com.book.book_a.R
import com.book.book_a.base.BaseActivity
import com.book.book_a.databinding.ActivityMianKingDetailTwoBinding
import com.book.book_a.utils.StatusBarUtil
import com.book.book_a.view.fragment.KingTabFragment

class KingDetailTwoActivity : BaseActivity<ActivityMianKingDetailTwoBinding>() {


    val city:Array<String> = arrayOf("北美","内地","香港","台湾","日本","韩国")
    val cityId:Array<Int> = arrayOf(2015,2020,2016,2019,2017,2018)

    fun startActivity(context: Context) {
        val intent: Intent = Intent()
        intent.setClass(context, KingDetailTwoActivity::class.java)
        context.startActivity(intent)
    }

    override fun setCreateView(): ActivityMianKingDetailTwoBinding {
        //状态栏透明和间距处理
        StatusBarUtil.immersive(window)
        return  DataBindingUtil.setContentView(this, R.layout.activity_mian_king_detail_two)
    }

    private val fragmentList: MutableList<Fragment> = arrayListOf()

    override fun initView() {
        addTabToTabLayout()
        setupWithViewPager()
    }

    override fun initData() {


    }

    private fun setupWithViewPager(){
        binding.viewPage.adapter = pagerAdapter
        binding.tlMain.setupWithViewPager(binding.viewPage)
        for (index in city.indices) {
            binding.tlMain.getTabAt(index)?.text = city[index]
        }
    }

    private fun addTabToTabLayout() {
        fragmentList.clear()
        for (index in city.indices) {
            fragmentList.add(KingTabFragment.getInstance(cityId[index],"1"))
            binding.tlMain.addTab(binding.tlMain.newTab().setText(city[index]))
        }


    }


    private val pagerAdapter: FragmentStatePagerAdapter = object : FragmentStatePagerAdapter(supportFragmentManager){
        override fun getItem(p0: Int): Fragment {
            return fragmentList[p0]
        }

        override fun getCount(): Int {
            return  fragmentList.size
        }

    }

}