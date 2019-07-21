package com.book.book_a.model

import java.io.Serializable

class ResultBean : Serializable {
    var is_wap: String? = null
    var wap_url: String? = null
    var is_update: String? = null
    var update_url: String? = null
    var code: Int = 0
    var msg: String? = null
}
