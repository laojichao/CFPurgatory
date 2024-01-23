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

    fun writeTextToFile(fileName: String, text: String) {
        File(fileName).writeText(Base64Utils.getBase64Encode(text))
    }

    fun readTextFromFile(fileName: String): String {
        return Base64Utils.getBase64Decode(File(fileName).readText())
    }

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

    fun writeConfig(fileName : String, config : Config) {
        val gson = Gson()
        val content = gson.toJson(config)
        writeTextToFile(fileName, content)
    }

}