package com.ipmosaico

import io.ktor.server.application.*
import com.ipmosaico.plugins.*
import io.ktor.http.*
import io.ktor.server.plugins.cors.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {

    install(CORS) {
        allowHost("localhost:3000")
        allowHeader(HttpHeaders.ContentType)
        allowHeader(HttpHeaders.Authorization)
    }

    configureRouting()
    configureSerialization()
    configureSecurity()

}
