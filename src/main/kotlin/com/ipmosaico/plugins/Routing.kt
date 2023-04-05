package com.ipmosaico.plugins

import com.ipmosaico.routes.authRoutes
import pastoraisRouting
import financeiroRouting
import eventosRouting
import compromissosRouting
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {

    routing {
        authRoutes()
        pastoraisRouting()
        financeiroRouting()
        eventosRouting()
        compromissosRouting()
    }
}
