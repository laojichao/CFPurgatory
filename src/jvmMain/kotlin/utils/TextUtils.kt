package utils

import bean.Config
import com.google.gson.Gson
import java.io.File


/****
 *** author：lao
 *** package：
 *** project：CFPurgatory
 *** name：utils.TextUtils
 *** date：2023/12/31  15:16
 *** filename：utils.TextUtils
 *** desc：json文件读写Config
 ***/


object TextUtils {

    /** 文本Base64编码后写入文件（简单混淆防直接查看） */
    fun writeTextToFile(fileName: String, text: String) {
        File(fileName).writeText(Base64Utils.getBase64Encode(text))
    }

    /** 读取文件并Base64解码 */
    fun readTextFromFile(fileName: String): String {
        return Base64Utils.getBase64Decode(File(fileName).readText())
    }

    /** 读取配置文件；不存在则创建并写入默认配置 */
    fun readConfig(fileName : String) : Config {

        val file = File(fileName)
        val gson = Gson()
        val config : Config
        if (!file.exists()) {
            file.createNewFile()
            config = Config()
            val content = gson.toJson(config)
            writeTextToFile(fileName, content)
        } else {
            val json = readTextFromFile(fileName);
            config = gson.fromJson(json, Config::class.java)
            println(config.toString())
        }

        return config
    }

    /** 配置序列化为JSON后编码写回 */
    fun writeConfig(fileName : String, config : Config) {
        val gson = Gson()
        val content = gson.toJson(config)
        writeTextToFile(fileName, content)
    }

}
