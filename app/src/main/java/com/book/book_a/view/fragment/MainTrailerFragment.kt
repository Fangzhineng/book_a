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
import com.book.book_a.databinding.FragmentMainTrailerBinding
import com.book.book_a.model.Trailer
import com.book.book_a.model.TrailerBody
import com.book.book_a.view.adapter.TrailerFragmentAdapter
import com.book.book_a.viewModel.page.TrailerViewModel
import com.jcodecraeer.xrecyclerview.XRecyclerView
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard
import org.jetbrains.anko.toast

class MainTrailerFragment : BaseFragment<FragmentMainTrailerBinding>() {

    private var list  = arrayListOf<TrailerBody>()

    private var trailer: Trailer?= null

    private lateinit var adapter: TrailerFragmentAdapter

    private val trailerViewModel : TrailerViewModel by lazy {
        ViewModelProviders.of(this).get(TrailerViewModel::class.java)
    }

    override fun setCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): FragmentMainTrailerBinding {
        return DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.fragment_main_trailer,container,false)
    }

    override fun initView() {
        userVisibleHint = true

        adapter = TrailerFragmentAdapter(context!!)

        binding.recycler.layoutManager = LinearLayoutManager(context)
        binding.recycler.adapter = adapter
        binding.recycler.setLoadingMoreEnabled(false)
        binding.recycler.setLoadingListener(object : XRecyclerView.LoadingListener{
            override fun onLoadMore() {


            }

            override fun onRefresh() {
                trailerViewModel.load()
            }

        })
    }

    override fun initData() {

        trailerViewModel.trailerInfo.observe(this, Observer {
            it?.let {
                list.clear()
                list .addAll(it.toMutableList())
                adapter.setBean(list,trailer)
            }

        })


        trailerViewModel.topInfo.observe(this, Observer { it ->
            it?.let {
                trailer = it
            }

        })


        trailerViewModel.toastStringMessage.observe(this, Observer {
            it?.let {
                context?.toast(it)
            }
        })


        trailerViewModel.loadingMessage.observe(this, Observer {
            if (it == false){
                binding.recycler.refreshComplete()
                binding.recycler.loadMoreComplete()
            }
        })
    }

    override fun onPause() {
        super.onPause()
        JCVideoPlayerStandard.releaseAllVideos()
    }


    override fun onDestroy() {
        super.onDestroy()
        JCVideoPlayerStandard.releaseAllVideos()
    }

}