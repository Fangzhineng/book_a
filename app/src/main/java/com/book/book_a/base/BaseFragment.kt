package com.book.book_a.base

import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class BaseFragment<T : ViewDataBinding> : Fragment() {

    private var isVisible: Boolean? = false                   //是否可见状态
    private var isPrepared: Boolean? = false                 //标志位，View已经初始化完成。
    private var isFirstLoad: Boolean? = true       //是否第一次加载
    private lateinit var inflater: LayoutInflater

    lateinit var binding: T

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        this.inflater = inflater
        isFirstLoad = true
        isPrepared = true
        binding = setCreateView(inflater, container, savedInstanceState)
        initView()
        lazyLoad()
        return binding.root
    }



    /**
     * 如果是通过FragmentTransaction的show和hide的方法来控制显示，调用的是onHiddenChanged.
     * 若是初始就show的Fragment 为了触发该事件 需要先hide再show
     */
    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            isVisible = true
            onVisible()
        } else {
            isVisible = false
            onInvisible()
        }
    }

    private fun onInvisible() {

    }

    private fun onVisible() {
        lazyLoad()
    }

    private fun lazyLoad() {
        if (isPrepared == false || isVisible == false || isFirstLoad == false) {
            return
        }
        isFirstLoad = false
        initData()
    }

    /**
     * 如果是与ViewPager一起使用，调用的是setUserVisibleHint
     */
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (userVisibleHint) {
            isVisible = true
            onVisible()
        } else {
            isVisible = false
            onInvisible()
        }
    }

    abstract fun setCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): T

    abstract fun initView()

    abstract fun initData()



}