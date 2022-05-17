package com.ipmosaico.plugins

import com.ipmosaico.routes.authRoutes
import io.ktor.server.application.*
import io.ktor.server.routing.*
import pastoraisRouting
import financeiroRouting

fun Application.configureRouting() {

    routing {
        authRoutes()
        pastoraisRouting()
        financeiroRouting()
    }
}
