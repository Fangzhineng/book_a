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
import com.book.book_a.databinding.FragmentKingDetialListTwoBinding
import com.book.book_a.model.Movy
import com.book.book_a.view.adapter.KingDetailAdapter
import com.book.book_a.viewModel.page.KingDetailListTwoModel
import com.jcodecraeer.xrecyclerview.XRecyclerView
import org.jetbrains.anko.toast

class KingTabFragment : BaseFragment<FragmentKingDetialListTwoBinding>() {

    private var topId = 0

    private var type:String = ""

    private var list = arrayListOf<Movy>()

    lateinit var adapter: KingDetailAdapter

    val viewModel: KingDetailListTwoModel by lazy {
        ViewModelProviders.of(this).get(KingDetailListTwoModel::class.java)
    }

    companion object{
        fun getInstance(topId:Int,type:String):KingTabFragment{
            val bundle:Bundle = Bundle()
            bundle.putInt("topId",topId)
            bundle.putString("type",type)
            val kingTabFragment:KingTabFragment = KingTabFragment()
            kingTabFragment.arguments = bundle
            return kingTabFragment
        }

    }

    override fun setCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): FragmentKingDetialListTwoBinding {
        return DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.fragment_king_detial_list_two,container,false)
    }

    override fun initView() {
        topId = arguments!!.getInt("topId")
        type =  arguments?.getString("type").toString()

        adapter = KingDetailAdapter(context!!)

        viewModel.load(type,topId)
        binding.recycler.layoutManager = LinearLayoutManager(context)
        binding.recycler.adapter = adapter

        binding.recycler.setLoadingMoreEnabled(false)
        binding.recycler.setLoadingListener(object : XRecyclerView.LoadingListener{
            override fun onLoadMore() {
//                viewModel.mPage ++
//                viewModel.load(type,id)
            }
            override fun onRefresh() {
                viewModel.mPage = 1
                viewModel.load(type,topId)
            }
        })
    }

    override fun initData() {
        viewModel.info.observe(this, Observer {
            it?.let {
                list.clear()
                list.addAll(it.movies.toMutableList())
                adapter.setData(list,it.topList)

            }
        })

        viewModel.toastStringMessage.observe(this, Observer {
            it?.let {
                context!!.toast(it)
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