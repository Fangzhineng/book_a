package com.book.book_a.http


class ErrMsg {

    var err: ErrInfo? = null

    inner class ErrInfo {

        lateinit var errMessage: String
        lateinit var errCode: String

        override fun toString(): String {
            return "LoginInfo [errMessage=$errMessage, errCode=$errCode]"
        }
    }

}
