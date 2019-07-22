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
        val prevue = SERVER_IP+"PageSubArea/TrailerList.api"

        //影评
        val Review = SERVER_IP + "MobileMovie/Review.api"

        val Review_detail = SERVER_IP + "Review/Detail.api"


        //排行榜
        //https://m.mtime.cn/Service/callback.mi/TopList/TopListOfAll.api?t=201971813494694650&pageIndex=1
        val King =  SERVER_IP+"TopList/TopListOfAll.api"

        //排行榜顶部点击事件
        // https://m.mtime.cn/Service/callback.mi/TopList/TopListDetails.api?t=201971813564439909&pageIndex=1&type=2&toplistId=1474&pageSubAreaID=1474
        val King_list_detial =  SERVER_IP+"TopList/TopListDetails.api"
        //排行榜顶部里面内容点击 https://m.mtime.cn/Service/callback.mi/movie/Detail.api?movieId=91850&locationId=290&t=201971813585181003
        val King_list_detial_two =  SERVER_IP+"movie/Detail.api"
        //时光网Top100 https://m.mtime.cn/Service/callback.mi/TopList/TopListDetailsByRecommend.api?t=201971813595171578&pageIndex=1&type=1&toplistId=2065&pageSubAreaID=2065
        val King_xigua_100 =  SERVER_IP+"TopList/TopListDetailsByRecommend.api"
        //华语top100 https://m.mtime.cn/Service/callback.mi/TopList/TopListDetailsByRecommend.api?t=20197181404668409&pageIndex=1&type=1&toplistId=2066&pageSubAreaID=2066//
        val King_huayu_100 =  SERVER_IP+"TopList/TopListDetailsByRecommend.api"

//排行榜列表点击 https://m.mtime.cn/Service/callback.mi/TopList/TopListDetails.api?t=20197181434988002&pageIndex=1&type=2&toplistId=92&pageSubAreaID=92
//北美票房 http://m.mtime.cn/Service/callback.mi/TopList/TopListDetailsByRecommend.api?t=201972217565831790&pageIndex=1&type=1&toplistId=2015&pageSubAreaID=2015
//内地 http://m.mtime.cn/Service/callback.mi/TopList/TopListDetailsByRecommend.api?t=201972217575084539&pageIndex=1&type=1&toplistId=2020&pageSubAreaID=2020
//香港 http://m.mtime.cn/Service/callback.mi/TopList/TopListDetailsByRecommend.api?t=20197221758464068&pageIndex=1&type=1&toplistId=2016&pageSubAreaID=2016
//台湾 http://m.mtime.cn/Service/callback.mi/TopList/TopListDetailsByRecommend.api?t=201972217592357911&pageIndex=1&type=1&toplistId=2019&pageSubAreaID=2019
//日本 http://m.mtime.cn/Service/callback.mi/TopList/TopListDetailsByRecommend.api?t=201972217595523205&pageIndex=1&type=1&toplistId=2017&pageSubAreaID=2017
//韩国 http://m.mtime.cn/Service/callback.mi/TopList/TopListDetailsByRecommend.api?t=20197221802027612&pageIndex=1&type=1&toplistId=2018&pageSubAreaID=2018
// http://m.mtime.cn/Service/callback.mi/movie/Detail.api?movieId=229235&locationId=290&t=2019722183923203

    }
}
