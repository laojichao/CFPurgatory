package utils

import java.util.*


/****
 *** author：lao
 *** package：utils
 *** project：CFPurgatory
 *** name：Base64Utils
 *** date：2024/1/4  16:50
 *** filename：Base64Utils
 *** desc：Base64加解密
 ***/

object Base64Utils {
    /**
     * 编码
     *
     * @param str
     * @return
     */
    @Suppress("deprecation")
    fun getBase64Encode(str: String): String {
        var str = str
        val encoder = Base64.getEncoder()
        try {
            val bytes = encoder.encode(str.toByteArray())
            str = String(bytes)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return str
    }

    /**
     * 解码
     *
     * @param str
     * @return
     */
    @Suppress("deprecation")
    fun getBase64Decode(str: String?): String {
        var str = str
        val decoder = Base64.getMimeDecoder()
        try {
            val bytes = decoder.decode(str)
            str = String(bytes)
        } catch (e: Exception) {
            e.printStackTrace()
            return ""
        }
        return str
    }
}