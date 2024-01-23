package utils
import java.io.IOException
import java.math.BigInteger
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*


/****
 *** author：lao
 *** package：
 *** project：CFPurgatory
 *** name：MachineCodeUtils
 *** date：2024/1/1  21:38
 *** filename：MachineCodeUtils
 *** desc：机器码获取
 ***/


object MachineCodeUtil {

    const val LINUX_OS_NAME = "LINUX"
    const val SYSTEM_PROPERTY_OS_NAME = "os.name"
    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {
        println(getThisMachineCode())
        println(getCpuId())
        println(getBiosUuid())
        //        System.out.println("编码:"+encode(getThisMachineCode()));
        println("编码:" + encode("#######ssssss"))
        println("解码:" + encode(getThisMachineCode(), "$$$$****"))
    }

    fun getThisMachineCodeMd5(): String {
        return encode(getThisMachineCode(), "$$$$****").uppercase(Locale.getDefault())
    }
    /**
     * java 实现Md5
     */
    /**
     * 直接加密
     *
     * @param data 要加密的数据
     * @return 加密后的结果
     */
    fun encode(data: String): String {
        try {
            val messageDigest = MessageDigest.getInstance("MD5")
            messageDigest.update(data.toByteArray(StandardCharsets.UTF_8))
            val bytes = messageDigest.digest()
            return BigInteger(1, bytes).toString(16)
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
        return ""
    }

    /**
     * 加盐或者密钥的MD5
     *
     * @param data 要加密的数据
     * @param salt 盐或者密钥
     * @return 加密后的结果
     */
    fun encode(data: String, salt: String): String {
        // TODO 此处可以加一些特殊的处理
        return encode(data + salt)
    }

    /**
     * 获取机器唯一识别码（CPU ID + BIOS UUID）
     *
     * @return 机器唯一识别码
     */
    fun getThisMachineCode(): String {
        try {
            return getCpuId() + getBiosUuid()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    /**
     * 获取当前系统CPU序列，可区分linux系统和windows系统
     */
    @Throws(IOException::class)
    fun getCpuId(): String {
        val cpuId: String?
        // 获取当前操作系统名称
        var os = System.getProperty(SYSTEM_PROPERTY_OS_NAME)
        os = os.uppercase(Locale.getDefault())
        cpuId = if (LINUX_OS_NAME == os) {
            getLinuxDmidecodeInfo("dmidecode -t processor | grep 'ID'", "ID", ":")
        } else {
            getWindowsCpuId()
        }
        return cpuId!!.uppercase(Locale.getDefault()).replace(" ", "")
    }

    /**
     * 获取linux系统
     * dmidecode
     * 命令的信息
     */
    @Throws(IOException::class)
    fun getLinuxDmidecodeInfo(cmd: String?, record: String?, symbol: String): String? {
        val execResult = executeLinuxCmd(cmd)
        val infos = execResult.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        for (info in infos) {
            val info = info.trim { it <= ' ' }
            if (info.contains(record!!)) {
                info.replace(" ", "")
                val sn = info.split(symbol.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                return sn[1]
            }
        }
        return null
    }

    /**
     * 执行Linux 命令
     *
     * @param cmd Linux 命令
     * @return 命令结果信息
     * @throws IOException 执行命令期间发生的IO异常
     */
    @Throws(IOException::class)
    fun executeLinuxCmd(cmd: String?): String {
        val run = Runtime.getRuntime()
        val process: Process
        process = run.exec(cmd)
        val processInputStream = process.inputStream
        val stringBuilder = StringBuilder()
        val b = ByteArray(8192)
        var n: Int
        while (processInputStream.read(b).also { n = it } != -1) {
            stringBuilder.append(String(b, 0, n))
        }
        processInputStream.close()
        process.destroy()
        return stringBuilder.toString()
    }

    /**
     * 获取windows系统CPU序列
     */
    @Throws(IOException::class)
    fun getWindowsCpuId(): String {
        val process =
            Runtime.getRuntime().exec(arrayOf("wmic", "cpu", "get", "ProcessorId"))
        process.outputStream.close()
        val sc = Scanner(process.inputStream)
        sc.next()
        return sc.next()
    }

    /**
     * 获取 BIOS UUID
     *
     * @return BIOS UUID
     * @throws IOException 获取BIOS UUID期间的IO异常
     */
    @Throws(IOException::class)
    fun getBiosUuid(): String {
        val cpuId: String?
        // 获取当前操作系统名称
        var os = System.getProperty("os.name")
        os = os.uppercase(Locale.getDefault())
        cpuId = if ("LINUX" == os) {
            getLinuxDmidecodeInfo("dmidecode -t system | grep 'UUID'", "UUID", ":")
        } else {
            getWindowsBiosUUID()
        }
        return cpuId!!.uppercase(Locale.getDefault()).replace(" ", "")
    }

    /**
     * 获取windows系统 bios uuid
     *
     * @return
     * @throws IOException
     */
    @Throws(IOException::class)
    fun getWindowsBiosUUID(): String {
        val process = Runtime.getRuntime()
            .exec(arrayOf("wmic", "path", "win32_computersystemproduct", "get", "uuid"))
        process.outputStream.close()
        val sc = Scanner(process.inputStream)
        sc.next()
        return sc.next()
    }
}
