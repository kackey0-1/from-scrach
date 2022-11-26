package com.hypo.simpledb.plugins

import com.hypo.simpledb.routes.customerRouting
import com.hypo.simpledb.routes.getOrderRoute
import com.hypo.simpledb.routes.listOrdersRoute
import com.hypo.simpledb.routes.totalizeOrderRoute
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
