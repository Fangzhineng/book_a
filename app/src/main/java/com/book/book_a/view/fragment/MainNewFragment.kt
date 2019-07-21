package com.book.book_a.view.fragment

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.book.book_a.R
import com.book.book_a.databinding.FragmentMainNewsBinding
import com.book.book_a.base.BaseFragment

class MainNewFragment : BaseFragment<FragmentMainNewsBinding>() {
    override fun setCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): FragmentMainNewsBinding {
        return DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.fragment_main_news,container,false)
    }

    override fun initView() {

    }

    override fun initData() {

    }
}