package com.hypo.driven

import com.hypo.driven.plugins.configureRouting
import com.hypo.driven.plugins.configureSockets
import io.ktor.server.application.Application
import io.ktor.server.netty.EngineMain

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {
    configureRouting()
    configureSockets()
}