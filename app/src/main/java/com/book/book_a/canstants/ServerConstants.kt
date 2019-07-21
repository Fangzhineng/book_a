package com.book.book_a.canstants

/**
 * Created by Administrator on 2018/8/30.
 */
class ServerConstants {
    companion object {
        val SERVER_IP = "http://m.mtime.cn/Service/callback.mi/"

        val UpData = "http://www.ds06ji.com:15780/back/api.php?app_id=130001"

        //原URL： http://www.xiaokaxiu.com/video/web/get_splendid_video?limit=10&page=1#1
        val homeData = "https://api.shenjian.io/?appid=99ccd349dc1550f4f3b640ff5b18ac0d"

        //頂部
        val top=SERVER_IP+"PageSubArea/GetRecommendationIndexInfo.api"

        //首页列表数据
        val main_list = SERVER_IP+"News/NewsList.api"


        //首页列表数据详情
        val main_list_detail = SERVER_IP+"News/Detail.api"

        //预告片
        val prevue = SERVER_IP+"News/NewsList.api"

        //排行榜
        val King =  SERVER_IP+"TopList/TopListOfAll.api"


        val Review = SERVER_IP + "MobileMovie/Review.api"



    }
}
