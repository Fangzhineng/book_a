package com.book.book_a.view.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.widget.LinearLayoutManager
import com.book.book_a.R
import com.book.book_a.base.BaseActivity
import com.book.book_a.databinding.ActivityMianTopDetailBinding
import com.book.book_a.utils.StatusBarUtil
import com.book.book_a.view.adapter.MainReviewDetailAdapter
import com.book.book_a.view.adapter.MainTopDetailAdapter
import com.book.book_a.viewModel.page.MainReviewDetailViewModel
import com.book.book_a.viewModel.page.MainTopDetailViewModel
import com.jcodecraeer.xrecyclerview.XRecyclerView
import org.jetbrains.anko.toast

class MainReviewDetailActivity : BaseActivity<ActivityMianTopDetailBinding>() {


    private var id: Int = 0

    private val adapter: MainReviewDetailAdapter = MainReviewDetailAdapter(this)

    fun startActivity(context: Context, id: Int ?) {
        val intent: Intent = Intent()
        intent.putExtra("id", id)
        intent.setClass(context, MainReviewDetailActivity::class.java)
        context.startActivity(intent)
    }

    private val mainTopDetailVM: MainReviewDetailViewModel by lazy {
        ViewModelProviders.of(this).get(MainReviewDetailViewModel::class.java)
    }

    override fun setCreateView(): ActivityMianTopDetailBinding {
        //状态栏透明和间距处理
        StatusBarUtil.immersive(window)
        return DataBindingUtil.setContentView(this, R.layout.activity_mian_top_detail)
    }

    override fun initView() {
        id = intent.getIntExtra("id",0)

        mainTopDetailVM.setData(id)

        binding.recycler.layoutManager = LinearLayoutManager(this)
        binding.recycler.adapter =adapter

        binding.recycler.setLoadingMoreEnabled(false)
        binding.recycler.setLoadingListener(object : XRecyclerView.LoadingListener{
            override fun onLoadMore() {

            }

            override fun onRefresh() {
                mainTopDetailVM.setData(id)
            }

        })
    }

    override fun initData() {
        mainTopDetailVM.info.observe(this, Observer {
            it?.let {
                adapter.set(it)
            }

        })

        mainTopDetailVM.toastStringMessage.observe(this, Observer {
            it?.let{
                toast(it)
            }
        })

        mainTopDetailVM.loadingMessage.observe(this, Observer {
            if (it == false){
                binding.recycler.refreshComplete()
                binding.recycler.loadMoreComplete()
            }
        })

    }
}