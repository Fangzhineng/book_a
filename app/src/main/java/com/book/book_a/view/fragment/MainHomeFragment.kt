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
import com.book.book_a.databinding.FragmentMainHomeBinding
import com.book.book_a.model.NewsList
import com.book.book_a.view.adapter.HomeAllFragmentAdapter
import com.book.book_a.viewModel.page.HomeViewModel
import com.jcodecraeer.xrecyclerview.XRecyclerView
import org.jetbrains.anko.toast

class MainHomeFragment : BaseFragment<FragmentMainHomeBinding>() {

    private var list:MutableList<NewsList> = arrayListOf()

    private lateinit var adapter:HomeAllFragmentAdapter

    private var page = 1

    private val homeViewModel: HomeViewModel by lazy {
        ViewModelProviders.of(this).get(HomeViewModel::class.java)
    }

    override fun setCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): FragmentMainHomeBinding {
        return DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.fragment_main_home, container, false)
    }


    override fun initView() {

        userVisibleHint = true

        binding.recyclerView.isFocusableInTouchMode = false //设置不需要焦点
        binding.recyclerView.requestFocus()//

        binding.recyclerView.setLoadingListener(object :XRecyclerView.LoadingListener{
            override fun onLoadMore() {
                page++
                homeViewModel.mPage = page
                homeViewModel.load()
            }

            override fun onRefresh() {
                page = 1
                homeViewModel.mPage = page
                homeViewModel.load()
            }

        })

        adapter=HomeAllFragmentAdapter(context!!)

        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        binding.recyclerView.adapter = adapter
    }

    override fun initData() {
        homeViewModel.loadingMessage.observe(this, Observer {
            if (it == false){
                binding.recyclerView.refreshComplete()
                binding.recyclerView.loadMoreComplete()
            }
        })

        homeViewModel.topInfo.observe(this, Observer {
            it?.let {
                adapter.setData(it.news)
            }
        })

        homeViewModel.mianListInfo.observe(this, Observer {
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
                        adapter.setData(list)
                    }


            }
        })


        homeViewModel.toastStringMessage.observe(this, Observer {
            it?.let {
                context?.toast(it)
            }
        })
    }


}