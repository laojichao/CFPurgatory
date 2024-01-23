package network

import bean.SysTime
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/****
 *** author：lao
 *** package：
 *** project：CSGO
 *** name：network.TimeService
 *** date：2023/12/28  23:12
 *** filename：network.TimeService
 *** desc：时间服务器接口
 ***/

interface TimeService {
    @GET("/rest/api3.do")
    fun getSysTime(@Query("api") api: String?): Call<SysTime?>?
}