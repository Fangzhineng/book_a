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
import com.book.book_a.databinding.FragmentMainFilmReviewBinding
import com.book.book_a.model.Review
import com.book.book_a.model.ReviewBean
import com.book.book_a.view.adapter.FilmReviewFragmentAdapter
import com.book.book_a.viewModel.page.FilmReviewViewModel
import com.jcodecraeer.xrecyclerview.XRecyclerView
import org.jetbrains.anko.toast

class MainFilmReviewFragment : BaseFragment<FragmentMainFilmReviewBinding>() {

    private lateinit var adapter:FilmReviewFragmentAdapter

    private val fViewModel : FilmReviewViewModel by lazy {
        ViewModelProviders.of(this).get(FilmReviewViewModel::class.java)
    }

    private var list  = arrayListOf<ReviewBean>()

    private var review: Review ?= null

    override fun setCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): FragmentMainFilmReviewBinding {
        return DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.fragment_main_film_review , container, false)
    }

    override fun initView() {
        userVisibleHint = true

        adapter = FilmReviewFragmentAdapter(context!!)

        binding.recycler.layoutManager = LinearLayoutManager(context)
        binding.recycler.adapter = adapter
        binding.recycler.setLoadingMoreEnabled(false)
        binding.recycler.setLoadingListener(object :XRecyclerView.LoadingListener{
            override fun onLoadMore() {


            }

            override fun onRefresh() {
                fViewModel.load()
            }

        })

    }

    override fun initData() {

        fViewModel.mianListInfo.observe(this, Observer {
            it?.let {
                list.clear()
                list .addAll(it.toMutableList())
                adapter.setBean(list,review)
            }

        })


        fViewModel.topInfo.observe(this, Observer { it ->
            it?.let {
                review = it.review
            }

        })


        fViewModel.toastStringMessage.observe(this, Observer {
            it?.let {
                context?.toast(it)
            }
        })


        fViewModel.loadingMessage.observe(this, Observer {
            if (it == false){
                binding.recycler.refreshComplete()
                binding.recycler.loadMoreComplete()
            }
        })
    }

}