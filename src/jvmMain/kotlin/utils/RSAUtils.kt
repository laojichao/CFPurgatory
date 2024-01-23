package utils

import java.security.InvalidKeyException
import java.security.KeyFactory
import java.security.NoSuchAlgorithmException
import java.security.spec.InvalidKeySpecException
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.util.*
import javax.crypto.BadPaddingException
import javax.crypto.Cipher
import javax.crypto.IllegalBlockSizeException
import javax.crypto.NoSuchPaddingException

/****
 *** author：lao
 *** package：
 *** project：CFPurgatory
 *** name：utils.RSAUtils
 *** date：2024/1/1  22:05
 *** filename：utils.RSAUtils
 *** desc：RSA加解密
 ***/

object RSAUtils {
    /**
     * 公钥base64
     */
    const val PUBLIC_KEY = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAIedQFDyQLQdYkAc0aRitwZH0PB5wKQNWA0uzheJ3W/tDPICa4CuWUwJrDM07nR/Yhlvd5lMB5J+f6+OL6xkx+8CAwEAAQ=="
    /**
     * 私钥base64
     */
    const val PRIVATE_KEY = "MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAh51AUPJAtB1iQBzRpGK3BkfQ8HnApA1YDS7OF4ndb+0M8gJrgK5ZTAmsMzTudH9iGW93mUwHkn5/r44vrGTH7wIDAQABAkAVIae39UenyTxaCSORneAvFlm0XwRpi7rAsx5iBJWArxE70ESQSSA8TolxlH7Zs6ruk0syYjM+RfwTIg5BfcPhAiEAxiqgnTBQqdyuWjNpfA/ItrO2LRSHxYh9cwpfNeRYw60CIQCvMTvkj5i71UQc84M9F7145zjlTMdTX+iGDJzxEaXNiwIgaDHjr6LG1vmE50KfFS9Lbt1BTaS82t0wcPukIjcFgSUCIQCg3/AkyWwyh/t9LmxEbIlz/bg0cBXVzQuEFQ8FG/SKUwIgZLoeo9vJ4x+iLz3SE5Mu9bWFBQLxB12rIwQwjuD9tyU="


    /**
     * 公钥加密
     *
     * @return
     * @throws Exception
     */
    @Throws(Exception::class)
    fun encrypt(content: String): String {
        val publicKeyBytes = PUBLIC_KEY.toByteArray()
        val x = X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyBytes))
        val keyFactory = KeyFactory.getInstance("RSA")
        val pubKey = keyFactory.generatePublic(x)
        val cipher = Cipher.getInstance("RSA")
        cipher.init(Cipher.ENCRYPT_MODE, pubKey)
        val result = cipher.doFinal(content.toByteArray(charset("UTF-8")))
        return Base64.getEncoder().encodeToString(result)
    }

    /**
     * 私钥解密
     *
     * @param signEncrypt
     * @return
     * @throws Exception
     */
    @Throws(
        NoSuchAlgorithmException::class,
        InvalidKeySpecException::class,
        NoSuchPaddingException::class,
        InvalidKeyException::class,
        IllegalBlockSizeException::class,
        BadPaddingException::class
    )
    fun decrypt(signEncrypt: String): String {
        val privateKeyBytes = PRIVATE_KEY.toByteArray()
        val pkcs8EncodedKeySpec = PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyBytes))
        val keyFactory = KeyFactory.getInstance("RSA")
        val priKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec)
        val cipher = Cipher.getInstance("RSA")
        cipher.init(Cipher.DECRYPT_MODE, priKey)
        var result: ByteArray? = null
        try {
            result = cipher.doFinal(Base64.getDecoder().decode(signEncrypt))
        } catch (e: Exception) {
            e.printStackTrace()
            return ""
        }

        return String(result)
    }


    @Throws(Exception::class)
    fun main(args: Array<String>) {
        val password = "123456"
        val a = encrypt(password)
        println("AES加密秘钥：$a")
    }


}