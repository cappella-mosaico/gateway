package com.ipmosaico

import com.ipmosaico.plugins.configureRouting
import com.ipmosaico.plugins.configureSecurity
import com.ipmosaico.plugins.configureSerialization
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.cors.*

fun main(args: Array<String>): Unit =
  io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {

  install(CORS) {
    val allowHost = System.getenv("IPMOSAICO_ALLOW_HOST")
    if (allowHost == null) {
      throw Exception("no IPMOSAICO_ALLOW_HOST env variable set up")
    }
    allowHost.split(",").forEach {
      allowHost(it, schemes = listOf("https"))
    }
    allowHeader(HttpHeaders.ContentType)
    allowHeader(HttpHeaders.Authorization)
    allowMethod(HttpMethod.Post)
    allowMethod(HttpMethod.Put)
    allowMethod(HttpMethod.Delete)
  }

  configureRouting()
  configureSerialization()
  configureSecurity()

}
