package com.ipmosaico.routes

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.ipmosaico.models.User
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import java.util.*

fun Route.authRoutes() {

    route("/login") {
        post {
            val user = call.receive<User>()
            if (user.username == "ipmosaico"
                && user.password == "mosaicoadmin2022") {

                val secret = this@route.environment!!.config.property("jwt.secret").getString()
                val issuer = this@route.environment!!.config.property("jwt.issuer").getString()
                val audience = this@route.environment!!.config.property("jwt.audience").getString()

                val token = JWT.create()
                    .withAudience(audience)
                    .withIssuer(issuer)
                    .withClaim("username", user.username)
                    .withExpiresAt(Date(System.currentTimeMillis() + 30 * 60_000))
                    .sign(Algorithm.HMAC256(secret))

                call.respond(hashMapOf("token" to token))
            } else {
                call.respond(message = "nope", status = HttpStatusCode.Unauthorized)
            }
        }
    }

}