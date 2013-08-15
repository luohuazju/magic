package com.sillycat.winnersellerserver.service.auth

import spray.routing.directives.{ PathDirectives, SecurityDirectives, HostDirectives, HeaderDirectives }
import scala.slick.util.Logging
import spray.routing.{ RequestContext, PathMatcher }
import shapeless.{ HNil, :: }
import spray.http.HttpHeaders.Authorization
import spray.routing.directives.{ PathDirectives, SecurityDirectives, HostDirectives, HeaderDirectives }
import shapeless._
import spray.routing.{ AuthenticationFailedRejection, PathMatcher, RequestContext }
import scala.slick.util.Logging
import scala.concurrent.Future
import spray.routing.authentication._
import scala.Some
import shapeless.::
import scala.concurrent.ExecutionContext.Implicits.global
import spray.http.HttpHeaders.Authorization
import spray.util.pimpSeq
import com.sillycat.winnersellerserver.util.SillycatUtil
import com.sillycat.winnersellerserver.model.{ UserType, User }
import scala.util.matching.Regex
import com.sillycat.winnersellerserver.service.auth.AuthHeader
import com.sillycat.winnersellerserver.service.auth.AuthUserId
import com.sillycat.winnersellerserver.dao.BaseDAO
import org.joda.time.DateTime
import spray.routing.HttpService

import scala.slick.session.Database.threadLocalSession

/**
 * Created by carl on 8/15/13.
 */
trait HashAuthenticationDirectives extends HeaderDirectives
    with HostDirectives
    with SecurityDirectives
    with PathDirectives
    with Logging {

  implicit val dao: BaseDAO = BaseDAO.apply("app")

  val Version = PathMatcher("""v([0-9]+)""".r)
    .flatMap {
      case string :: HNil => {
        try Some(java.lang.Integer.parseInt(string) :: HNil)
        catch {
          case _: NumberFormatException => None
        }
      }
    }

  val BrandPattern = "([a-zA-Z0-9]*).api.[.a-zA-Z0-9]*".r

  def getAuthHeader(ctx: RequestContext): Option[AuthHeader] = {
    val authHeader = ctx.request.headers.findByType[`Authorization`]
    logger.debug("authHeader from the Authorization tag =" + authHeader)
    val uri = ctx.request.uri.path.toString()
    authHeader match {
      case Some(x) => {
        val x_string = x.value
        logger.debug("decode authHeader = " + x_string)
        x_string.indexOf("HashAuth") match {
          case -1 => None
          case ix => {
            val kv_string = x_string.substring(ix + 9)

            //decode the header with base 64
            val kv_string_decode = SillycatUtil.base64Decode(kv_string)

            logger.debug("The the string value of key value pairs =" + kv_string_decode)
            val kv_map = kv_string_decode.split(",").map(_ split "=") collect { case Array(k, v) => (k, v) } toMap

            Some(AuthHeader(kv_map("lpauth_user_id"),
              kv_map("lpauth_user_type"),
              kv_map("lpauth_signature"),
              kv_map("lpauth_timestamp"),
              kv_map("lpauth_version"), uri))
          }
        }
      }
      case _ => None
    }
  }

  def extractBrand(hostname: String): Option[String] = {
    hostname match {
      case BrandPattern(b) => Some(b)
      case _ => None
    }
  }

  def checkSignature(authHeader: AuthHeader, brandCode: String) = {
    Future {
      //load the security key, userId, secret key, brand code
      Some(AuthUser("userId", "secret", "sillycat")) match {
        case Some(user) => {
          // secret key + timestamp + userId + userType + uri
          val prepare_encode = user.secretKey + authHeader.lpAuthTimeStamp + authHeader.lpAuthUserId + authHeader.lpAuthUserType.toString + authHeader.resourceURI
          logger.debug("The prepare encode string = " + prepare_encode)
          val sig_we_have = SillycatUtil.sha256(prepare_encode)
          logger.debug("The signature we have=" + sig_we_have)
          logger.debug("The signature from the header =" + authHeader.lpAuthSignature)
          if (sig_we_have == authHeader.lpAuthSignature) {
            dao.db withSession {
              dao.Users.getForEmail(authHeader.lpAuthUserId) match {
                case Some(x) => Right(x)
                case _ => Left(AuthenticationFailedRejection("Realm"))
              }
            }
          } else {
            Left(AuthenticationFailedRejection("Realm"))
          }
        }
        case _ => Left(AuthenticationFailedRejection("Realm"))
      }
    }
  }

  def hashAuths: RequestContext => Future[Authentication[User]] = {
    ctx: RequestContext =>
      val authHeaderOption = getAuthHeader(ctx)
      extractBrand(ctx.request.uri.authority.host.address) match {
        case Some(b) => {
          authHeaderOption match {
            case Some(x) => checkSignature(x, b)
            case _ => Future(Left(AuthenticationFailedRejection("Realm")))
          }
        }
        case _ => Future(Left(AuthenticationFailedRejection("Realm")))
      }
  }

  val localpointAuth =
    authenticate(hashAuths) &
      pathPrefix(Version)

}
