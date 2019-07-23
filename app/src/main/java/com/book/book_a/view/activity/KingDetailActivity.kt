package com.book.book_a.view.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.widget.LinearLayoutManager
import com.book.book_a.R
import com.book.book_a.base.BaseActivity
import com.book.book_a.databinding.ActivityMianRatingDetailBinding
import com.book.book_a.model.Movy
import com.book.book_a.utils.StatusBarUtil
import com.book.book_a.view.adapter.KingDetailAdapter
import com.book.book_a.viewModel.page.KingDetailViewModel
import com.jcodecraeer.xrecyclerview.XRecyclerView
import org.jetbrains.anko.toast

class KingDetailActivity : BaseActivity<ActivityMianRatingDetailBinding>() {

    private var id:Int = 0

    private var type:String = ""

    private var list = arrayListOf<Movy>()

    val adapter:KingDetailAdapter = KingDetailAdapter(this)

    val viewModel:KingDetailViewModel by lazy {
        ViewModelProviders.of(this).get(KingDetailViewModel::class.java)
    }

    override fun setCreateView(): ActivityMianRatingDetailBinding {
        //状态栏透明和间距处理
        StatusBarUtil.immersive(window)
        return DataBindingUtil.setContentView(this, R.layout.activity_mian_rating_detail)
    }

    fun startActivity(context: Context, type: String ?, id: Int ?) {
        val intent: Intent = Intent()
        intent.putExtra("id", id)
        intent.putExtra("type", type)
        intent.setClass(context, KingDetailActivity::class.java)
        context.startActivity(intent)
    }

    override fun initView() {
        id = intent.getIntExtra("id",0)
        type = intent.getStringExtra("type")

        viewModel.load(type,id)

        binding.recycler.layoutManager = LinearLayoutManager(this)
        binding.recycler.adapter = adapter

        binding.recycler.setLoadingListener(object :XRecyclerView.LoadingListener{
            override fun onLoadMore() {
                viewModel.mPage ++
                viewModel.load(type,id)

            }
            override fun onRefresh() {
                viewModel.mPage = 1
                viewModel.load(type,id)
            }
        })
    }

    override fun initData() {
        viewModel.info.observe(this, Observer {
            it?.let {
                if (viewModel.mPage == 1){
                    list.clear()
                }

                if (it.movies.toMutableList().size == 0){
                    viewModel.mPage = 1
                    binding.recycler.setLoadingMoreEnabled(false)
                }else{
                    binding.recycler.setLoadingMoreEnabled(true)
                    list.addAll(it.movies.toMutableList())
                    adapter.setData(list,it.topList)
                }

            }
        })

        viewModel.toastStringMessage.observe(this, Observer {
            it?.let {
                toast(it)
            }
        })

        viewModel.loadingMessage.observe(this, Observer {
            if (it == false){
                binding.recycler.loadMoreComplete()
                binding.recycler.refreshComplete()
            }
        })
    }
}