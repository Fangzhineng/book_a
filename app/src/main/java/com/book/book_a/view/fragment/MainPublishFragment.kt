package com.book.book_a.view.fragment

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.book.book_a.R
import com.book.book_a.databinding.FragmentMainPublishBinding
import com.book.book_a.base.BaseFragment

class MainPublishFragment : BaseFragment<FragmentMainPublishBinding>() {
    override fun setCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): FragmentMainPublishBinding {
        return DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.fragment_main_publish,container,false)
    }

    override fun initView() {

    }

    override fun initData() {

    }
}