package com.ipmosaico.plugins

import com.ipmosaico.routes.authRoutes
import eventosRouting
import financeiroRouting
import io.ktor.server.application.*
import io.ktor.server.routing.*
import pastoraisRouting

fun Application.configureRouting() {

    routing {
        authRoutes()
        pastoraisRouting()
        financeiroRouting()
        eventosRouting()
    }
}
