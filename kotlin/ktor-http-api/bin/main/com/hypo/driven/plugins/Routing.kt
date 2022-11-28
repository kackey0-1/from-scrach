package com.hypo.driven.plugins

import com.hypo.driven.routes.customerRouting
import com.hypo.driven.routes.getOrderRoute
import com.hypo.driven.routes.listOrdersRoute
import com.hypo.driven.routes.totalizeOrderRoute
import io.ktor.server.application.Application
import io.ktor.server.routing.routing

fun Application.configureRouting() {
    routing {
        customerRouting()
        listOrdersRoute()
        getOrderRoute()
        totalizeOrderRoute()
    }
}
