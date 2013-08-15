package com.sillycat.winnersellerserver.service.auth

import org.parboiled.common.Base64

/**
 * Created by carl on 8/15/13.
 */

/**
 * response of the authUser information
 * @param userId
 * @param secretKey
 * @param brandCode
 */
case class AuthUser(userId: String, secretKey: String, brandCode: String)

/**
 * request of the information inside userId
 * @param brandCode
 * @param userType
 * @param username
 */
case class AuthUserId(brandCode: String, userType: String, username: String)

/**
 * all the information we can get from the header
 * @param lpAuthUserId
 * @param lpAuthUserType
 * @param lpAuthSignature
 * @param lpAuthTimeStamp
 * @param lpAuthVersion
 * @param resourceURI
 */
case class AuthHeader(lpAuthUserId: String,
    lpAuthUserType: String,
    lpAuthSignature: String,
    lpAuthTimeStamp: String,
    lpAuthVersion: String,
    resourceURI: String) {
  val authScheme = "HashAuth"

  def encode: String = {
    val params = "lpauth_user_id=" + lpAuthUserId + "," +
      "lpauth_user_type=" + lpAuthUserType + "," +
      "lpauth_signature=" + lpAuthSignature + "," +
      "lpauth_timestamp=" + lpAuthTimeStamp + "," +
      "lpauth_version=" + lpAuthVersion

    val base64Encoded = Base64.rfc2045.encodeToString(params.getBytes("UTF-8"), false)

    "%s %s".format(authScheme, base64Encoded)
  }
}
