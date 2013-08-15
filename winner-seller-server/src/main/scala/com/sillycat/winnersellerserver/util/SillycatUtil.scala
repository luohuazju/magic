package com.sillycat.winnersellerserver.util

//import com.sillycat.winnersellerserver.CrossDomainHeaders.`Access-Control-Allow-Origin`
import spray.http.HttpHeader
import com.typesafe.config.ConfigFactory
import spray.http.HttpHeaders._
import spray.http.HttpHeaders.RawHeader
import org.joda.time.DateTime
import java.security.MessageDigest
import java.math.BigInteger
import org.parboiled.common.Base64

/**
 * Created with IntelliJ IDEA.
 * User: carl
 * Date: 6/12/13
 * Time: 3:07 PM
 * To change this template use File | Settings | File Templates.
 */
object SillycatUtil {
  def getCrossDomainHeaders(originHeader: Option[String]): List[RawHeader] = {
    val headers = List(RawHeader("Access-Control-Max-Age", "86400"), RawHeader("Access-Control-Allow-Credentials", "true"))
    val config = ConfigFactory.load()
    if (config.getBoolean("server.crossdomain.eanble") == true) {
      val lists = config.getStringList("server.crossdomain.list")
      if (lists.contains(originHeader.getOrElse(""))) {
        headers
          .::(RawHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS"))
          .::(RawHeader("Access-Control-Allow-Headers", "accept, origin, authentication, authorization, content-type, X-Requested-With, X-HTTP-Method-Override"))
          .::(RawHeader("Access-Control-Allow-Origin", originHeader.get))
      } else {
        headers.::(RawHeader("Access-Control-Allow-Origin", ""))
      }
    } else {
      headers
    }
  }

  def getDateNow(): String = {
    SillycatConstant.DATE_TIME_FORMAT_2.print(DateTime.now)
  }

  def md5(password: String): String = {
    val messageDigest = MessageDigest.getInstance("MD5")
    messageDigest.update(password.getBytes("UTF-8"), 0, password.length())
    var hashedPass = new BigInteger(1, messageDigest.digest()).toString(16)
    if (hashedPass.length() < 32) {
      hashedPass = "0" + hashedPass
    }
    hashedPass
  }

  def sha256(password: String): String = {
    val sha = MessageDigest.getInstance("SHA-256")
    sha.digest(password.getBytes("UTF-8"))
      .foldLeft("")((s: String, b: Byte) => s +
        Character.forDigit((b & 0xf0) >> 4, 16) +
        Character.forDigit(b & 0x0f, 16))
  }

  def base64Encode(src: String): String = {
    //new sun.misc.BASE64Encoder().encode(src.getBytes("UTF-8"))
    Base64.rfc2045.encodeToString(src.getBytes("UTF-8"), false)
  }

  def base64Decode(src: String): String = {
    //new String(new sun.misc.BASE64Decoder().decodeBuffer(src), "UTF-8")
    new String(Base64.rfc2045.decode(src), "UTF-8")
  }

}
