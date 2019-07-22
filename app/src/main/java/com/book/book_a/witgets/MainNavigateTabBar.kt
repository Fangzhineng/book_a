package com.book.book_a.witgets

import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentTransaction
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.book.book_a.R
import com.book.book_a.utils.ThemeUtils
import java.util.*

/**
 * User:Shine
 * Date:2015-10-29
 * Description:
 */
class MainNavigateTabBar @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr), View.OnClickListener {

    private val mViewHolderList: MutableList<ViewHolder>?
    private var mTabSelectListener: OnTabSelectedListener? = null
    private lateinit var mFragmentActivity: FragmentActivity
    private var mCurrentTag: String? = null
    private var mRestoreTag: String? = null
    /*主内容显示区域View的id*/
    private var mMainContentLayoutId: Int = 0
    /*选中的Tab文字颜色*/
    private var mSelectedTextColor: ColorStateList? = null
    /*正常的Tab文字颜色*/
    private var mNormalTextColor: ColorStateList? = null
    /*Tab文字的颜色*/
    private val mTabTextSize: Float
    /*默认选中的tab index*/
    private var mDefaultSelectedTab = 0

    private var mCurrentSelectedTab: Int = 0

    var currentSelectedTab: Int
        get() = mCurrentSelectedTab
        set(index) {
            if (index >= 0 && index < mViewHolderList!!.size) {
                val holder = mViewHolderList[index]
                showFragment(holder)
            }
        }


    init {

        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.MainNavigateTabBar, 0, 0)

        val tabTextColor = typedArray.getColorStateList(R.styleable.MainNavigateTabBar_navigateTabTextColor)
        val selectedTabTextColor = typedArray.getColorStateList(R.styleable.MainNavigateTabBar_navigateTabSelectedTextColor)

        mTabTextSize = typedArray.getDimensionPixelSize(R.styleable.MainNavigateTabBar_navigateTabTextSize, 0).toFloat()
        mMainContentLayoutId = typedArray.getResourceId(R.styleable.MainNavigateTabBar_containerId, 0)
        mNormalTextColor = tabTextColor
                ?: context.resources.getColorStateList(R.color.tab_text_normal)


        if (selectedTabTextColor != null) {
            mSelectedTextColor = selectedTabTextColor
        } else {
            ThemeUtils.checkAppCompatTheme(context)
            val typedValue = TypedValue()
            context.theme.resolveAttribute(R.attr.colorPrimary, typedValue, true)
            mSelectedTextColor = context.resources.getColorStateList(typedValue.resourceId)
        }

        mViewHolderList = ArrayList()
    }


    fun addTab(frameLayoutClass: Class<*>?, tabParam: TabParam) {
        val defaultLayout = R.layout.comui_tab_view
        //        if (tabParam.tabViewResId > 0) {
        //            defaultLayout = tabParam.tabViewResId;
        //        }
        if (TextUtils.isEmpty(tabParam.title)) {
            tabParam.title = context.getString(tabParam.titleStringRes)
        }

        val view = LayoutInflater.from(context).inflate(defaultLayout, null)
        view.isFocusable = true

        val holder = ViewHolder()

        holder.tabIndex = mViewHolderList!!.size

        holder.fragmentClass = frameLayoutClass
        holder.tag = tabParam.title
        holder.pageParam = tabParam

        holder.tabIcon = view.findViewById(R.id.tab_icon)
        holder.tabTitle = view.findViewById(R.id.tab_title)

        if (TextUtils.isEmpty(tabParam.title)) {
            holder.tabTitle!!.visibility = View.INVISIBLE
        } else {
            holder.tabTitle!!.text = tabParam.title
        }

        if (mTabTextSize != 0f) {
            holder.tabTitle!!.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTabTextSize)
        }
        if (mNormalTextColor != null) {
            holder.tabTitle!!.setTextColor(mNormalTextColor)
        }

        if (tabParam.backgroundColor > 0) {
            view.setBackgroundResource(tabParam.backgroundColor)
        }

        if (tabParam.iconResId > 0) {
            holder.tabIcon!!.setImageResource(tabParam.iconResId)
        } else {
            holder.tabIcon!!.visibility = View.INVISIBLE
        }

        if (tabParam.iconResId > 0 && tabParam.iconSelectedResId > 0) {
            view.tag = holder
            view.setOnClickListener(this)
            mViewHolderList.add(holder)
        }
        if (tabParam.iconResId == -1 && tabParam.iconSelectedResId == -1) {
            view.tag = holder
            view.setOnClickListener(this)
            mViewHolderList.add(holder)
        }
        if (tabParam.iconResId == -2 && tabParam.iconSelectedResId == -2) {
            view.tag = holder
            holder.tabIcon!!.visibility = View.GONE
            holder.tabTitle!!.textSize = 16f
            view.setOnClickListener(this)
            mViewHolderList.add(holder)
        }
        addView(view, LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f))

    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        if (mMainContentLayoutId == 0) {
            throw RuntimeException("mFrameLayoutId Cannot be 0")
        }
        if (mViewHolderList!!.size == 0) {
            throw RuntimeException("mViewHolderList.size Cannot be 0, Please call addTab()")
        }
        if (context !is FragmentActivity) {
            throw RuntimeException("parent activity must is extends FragmentActivity")
        }
        mFragmentActivity = context as FragmentActivity

        var defaultHolder: ViewHolder? = null

        hideAllFragment()
        if (!TextUtils.isEmpty(mRestoreTag)) {
            for (holder in mViewHolderList) {
                if (TextUtils.equals(mRestoreTag, holder.tag)) {
                    defaultHolder = holder
                    mRestoreTag = null
                    break
                }
            }
        } else {
            defaultHolder = mViewHolderList[mDefaultSelectedTab]
        }

        showFragment(defaultHolder!!)
    }

    override fun onClick(v: View) {
        val `object` = v.tag
        if (`object` != null && `object` is ViewHolder) {
            val holder = v.tag as ViewHolder
            showFragment(holder)
            if (mTabSelectListener != null) {
                mTabSelectListener!!.onTabSelected(holder)
            }
        }
    }

    /**
     * 显示 holder 对应的 fragment
     *
     * @param holder
     */
    private fun showFragment(holder: ViewHolder) {
        val transaction = mFragmentActivity.supportFragmentManager.beginTransaction()
        if (isFragmentShown(transaction, holder.tag)) {
            return
        }
        setCurrSelectedTabByTag(holder.tag)

        var fragment: Fragment? = mFragmentActivity.supportFragmentManager.findFragmentByTag(holder.tag)
        if (fragment == null) {
            fragment = getFragmentInstance(holder.tag)
            transaction.add(mMainContentLayoutId, fragment!!, holder.tag)
        } else {
            transaction.show(fragment)
        }
        transaction.commit()
        mCurrentSelectedTab = holder.tabIndex
    }

    private fun isFragmentShown(transaction: FragmentTransaction, newTag: String?): Boolean {
        if (TextUtils.equals(newTag, mCurrentTag)) {
            return true
        }

        if (TextUtils.isEmpty(mCurrentTag)) {
            return false
        }

        val fragment = mFragmentActivity.supportFragmentManager.findFragmentByTag(mCurrentTag)
        if (fragment != null && !fragment.isHidden) {
            transaction.hide(fragment)
        }

        return false
    }

    /*设置当前选中tab的图片和文字颜色*/
    private fun setCurrSelectedTabByTag(tag: String?) {
        if (TextUtils.equals(mCurrentTag, tag)) {
            return
        }
        for (holder in mViewHolderList!!) {
            if (TextUtils.equals(mCurrentTag, holder.tag)) {
                if (holder.pageParam!!.iconResId > 0)
                    holder.tabIcon!!.setImageResource(holder.pageParam!!.iconResId)
                holder.tabTitle!!.setTextColor(mNormalTextColor)
            } else if (TextUtils.equals(tag, holder.tag)) {
                if (holder.pageParam!!.iconSelectedResId > 0)
                    holder.tabIcon!!.setImageResource(holder.pageParam!!.iconSelectedResId)
                holder.tabTitle!!.setTextColor(mSelectedTextColor)
            }
        }
        mCurrentTag = tag
    }


    private fun getFragmentInstance(tag: String?): Fragment? {
        var fragment: Fragment? = null
        for (holder in mViewHolderList!!) {
            if (TextUtils.equals(tag, holder.tag)) {
                try {
                    fragment = Class.forName(holder.fragmentClass!!.name).newInstance() as Fragment
                } catch (e: InstantiationException) {
                    e.printStackTrace()
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                } catch (e: ClassNotFoundException) {
                    e.printStackTrace()
                }

                break
            }
        }
        return fragment
    }

    private fun hideAllFragment() {
        if (mViewHolderList == null || mViewHolderList.size == 0) {
            return
        }
        val transaction = mFragmentActivity!!.fragmentManager.beginTransaction()

        for (holder in mViewHolderList) {
            val fragment = mFragmentActivity!!.fragmentManager.findFragmentByTag(holder.tag)
            if (fragment != null && !fragment.isHidden) {
                transaction.hide(fragment)
            }
        }
        transaction.commit()
    }

    fun setSelectedTabTextColor(selectedTextColor: ColorStateList) {
        mSelectedTextColor = selectedTextColor
    }

    fun setSelectedTabTextColor(color: Int) {
        mSelectedTextColor = ColorStateList.valueOf(color)
    }

    fun setTabTextColor(color: ColorStateList) {
        mNormalTextColor = color
    }

    fun setTabTextColor(color: Int) {
        mNormalTextColor = ColorStateList.valueOf(color)
    }

    fun setFrameLayoutId(frameLayoutId: Int) {
        mMainContentLayoutId = frameLayoutId
    }

    fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            mRestoreTag = savedInstanceState.getString(KEY_CURRENT_TAG)
        }
    }

    fun onSaveInstanceState(outState: Bundle) {
        outState.putString(KEY_CURRENT_TAG, mCurrentTag)
    }

    class ViewHolder {
        var tag: String? = null
        var pageParam: TabParam? = null
        var tabIcon: ImageView? = null
        var tabTitle: TextView? = null
        var fragmentClass: Class<*>? = null
        var tabIndex: Int = 0
    }


    //iconResId和iconSelectedResId同时-1的时候凸起部分有布局
    class TabParam {
        var backgroundColor = android.R.color.white
        var iconResId: Int = 0
        var iconSelectedResId: Int = 0
        var titleStringRes: Int = 0
        //        public int tabViewResId;
        lateinit var title: String

        constructor(iconResId: Int, iconSelectedResId: Int, title: String) {
            this.iconResId = iconResId
            this.iconSelectedResId = iconSelectedResId
            this.title = title
        }

        constructor(iconResId: Int, iconSelectedResId: Int, titleStringRes: Int) {
            this.iconResId = iconResId
            this.iconSelectedResId = iconSelectedResId
            this.titleStringRes = titleStringRes
        }

        constructor(backgroundColor: Int, iconResId: Int, iconSelectedResId: Int, titleStringRes: Int) {
            this.backgroundColor = backgroundColor
            this.iconResId = iconResId
            this.iconSelectedResId = iconSelectedResId
            this.titleStringRes = titleStringRes
        }

        constructor(backgroundColor: Int, iconResId: Int, iconSelectedResId: Int, title: String) {
            this.backgroundColor = backgroundColor
            this.iconResId = iconResId
            this.iconSelectedResId = iconSelectedResId
            this.title = title
        }
    }


    interface OnTabSelectedListener {
        fun onTabSelected(holder: ViewHolder)
    }

    fun setTabSelectListener(tabSelectListener: OnTabSelectedListener) {
        mTabSelectListener = tabSelectListener
    }

    fun setDefaultSelectedTab(index: Int) {
        if (index >= 0 && index < mViewHolderList!!.size) {
            mDefaultSelectedTab = index
        }
    }

    companion object {

        private val KEY_CURRENT_TAG = "com.startsmake.template.currentTag"
    }


}
