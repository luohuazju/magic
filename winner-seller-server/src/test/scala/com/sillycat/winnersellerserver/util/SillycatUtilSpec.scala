package com.sillycat.winnersellerserver.util

import org.scalatest.{ BeforeAndAfterAll, FunSuite }
import org.specs2.matcher.ShouldMatchers

/**
 * Created by carl on 8/15/13.
 */
class SillycatUtilSpec extends FunSuite with ShouldMatchers with BeforeAndAfterAll {

  test("Verify the environment") {
    assert("x" === "x")
  }

  /**
   * online tool
   * http://www.xorbin.com/tools/md5-hash-calculator
   *
   */
  test("Verify the md5 encryption") {
    val source = "password"
    val encode = SillycatUtil.md5(source)
    info("source = " + source + " and encode = " + encode)
    assert(encode.equals("5f4dcc3b5aa765d61d8327deb882cf99"))
  }

  /**
   * online tool
   * http://www.xorbin.com/tools/sha256-hash-calculator
   */
  test("Verify SHA256 encryption") {
    val source = "password"
    val encode = SillycatUtil.sha256(source)
    info("source = " + source + " and encode = " + encode)
    assert(encode.equals("5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8"))
  }

  /**
   * online tool
   * http://www.xorbin.com/tools/base64-encoder-and-decoder
   */
  test("Verify the Base64 encode and decode") {
    val source = "password"
    val encode = SillycatUtil.base64Encode(source)
    val decode = SillycatUtil.base64Decode(encode)
    info("source = " + source + " encode = " + encode + " decode = " + decode)
    assert(source.equals(decode))
  }

}
