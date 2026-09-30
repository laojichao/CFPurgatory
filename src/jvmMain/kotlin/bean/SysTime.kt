package bean

/****
 *** author：lao
 *** package：
 *** project：CSGO
 *** name：bean.SysTime
 *** date：2023/12/28  23:13
 *** filename：bean.SysTime
 *** desc：
 ***/

class SysTime {
    /**
     * api : mtop.common.getTimestamp
     * v : *
     * ret : ["SUCCESS::接口调用成功"]
     * data : {"t":"1586519130440"}
     */
    private var api: String? = null
    private var v: String? = null

    /**
     * t : 1586519130440
     */
    private var data: DataBean? = null
    private var ret: List<String?>? = null

    fun getApi(): String? {
        return api
    }

    fun setApi(api: String?) {
        this.api = api
    }

    fun getV(): String? {
        return v
    }

    fun setV(v: String?) {
        this.v = v
    }

    fun getData(): DataBean? {
        return data
    }

    fun setData(data: DataBean?) {
        this.data = data
    }

    fun getRet(): List<String?>? {
        return ret
    }

    fun setRet(ret: List<String?>?) {
        this.ret = ret
    }

    class DataBean {
        private val t: String? = null

        fun getT(): String? {
            return t
        }
    }

}