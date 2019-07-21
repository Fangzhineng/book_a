package com.book.book_a.utils



/**
 * Created by Jin on 10/15/2018
 */
class ToastStringMessage : SingleLiveEvent<String>() {
    override fun setValue(t: String?) {
        super.setValue(t)
        super.setValue(null)
    }

    override fun postValue(t: String?) {
        super.postValue(t)
        super.postValue(null)
    }
}