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
import java.security.MessageDigest
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.Json
import java.io.File
import java.io.InputStream

fun Route.authRoutes() {

    val inputStream: InputStream = File("/ipmosaico-users/users.json").inputStream()
    val input = inputStream.bufferedReader().use { it.readText() }
    val jsonObjo = Json.parseToJsonElement(input)
    val users = jsonObjo.jsonObject.toMap()


    route("/login") {
        post {

            val user = call.receive<User>()
            val passwordDigest = calculateSHA256Hash(user.password)
            val storedPassword = users[user.username].toString().replace("\"", "")
            if (storedPassword == passwordDigest) {

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

fun calculateSHA256Hash(input: String): String {
    val messageDigest = MessageDigest.getInstance("SHA-256")
    val hashBytes = messageDigest.digest(input.toByteArray())

    val hexString = StringBuilder()
    for (byte in hashBytes) {
        val hex = Integer.toHexString(0xFF and byte.toInt())
        if (hex.length == 1) {
            hexString.append('0')
        }
        hexString.append(hex)
    }

    return hexString.toString()
}
