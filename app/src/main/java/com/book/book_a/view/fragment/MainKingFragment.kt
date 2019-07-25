package com.book.book_a.view.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.ViewGroup
import com.book.book_a.R
import com.book.book_a.base.BaseFragment
import com.book.book_a.databinding.FragmentMainKingBinding
import com.book.book_a.model.TopList
import com.book.book_a.model.TopLists
import com.book.book_a.view.adapter.KingFragmentAdapter
import com.book.book_a.viewModel.page.KingViewModel
import com.jcodecraeer.xrecyclerview.XRecyclerView
import org.jetbrains.anko.toast

class MainKingFragment : BaseFragment<FragmentMainKingBinding>() {

    private var page = 1

    private var list = arrayListOf<TopLists>()
    private var news: TopList?= null
    private val kingViewModel: KingViewModel by lazy {
       ViewModelProviders.of(this).get(KingViewModel::class.java)
    }

    private lateinit var adapter:KingFragmentAdapter

    override fun setCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): FragmentMainKingBinding {
        return DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.fragment_main_king,container,false)
    }

    override fun initView() {
        userVisibleHint = true

        adapter = KingFragmentAdapter(context!!)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = adapter

        binding.recyclerView.setLoadingListener(object :XRecyclerView.LoadingListener{
            override fun onRefresh() {
                page = 1
                kingViewModel.mPage = page
                kingViewModel.load()
            }

            override fun onLoadMore() {
                page++
                kingViewModel.mPage = page
                kingViewModel.load()

            }
        })

    }

    override fun initData() {
        kingViewModel.loadingMessage.observe(this, Observer {
            if (it == false){
                binding.recyclerView.refreshComplete()
                binding.recyclerView.loadMoreComplete()
            }
        })


        kingViewModel.kingInfo.observe(this, Observer {
            it?.let {
                if (page == 1){
                    list.clear()
                }
                if (it.toMutableList().size == 0){
                    page = 1
                    binding.recyclerView.setLoadingMoreEnabled(false)
                }else{
                    binding.recyclerView.setLoadingMoreEnabled(true)
                    list.addAll(it.toMutableList())
                    adapter.setList(list,news!!)
                }


            }
        })


        kingViewModel.topInfo.observe(this, Observer {
            it?.let {
                news = it.topList
            }
        })

        kingViewModel.toastStringMessage.observe(this, Observer {
            it?.let {
                context?.toast(it)
            }
        })
    }

}