package com.hypo.simpledb

import com.hypo.simpledb.plugins.configureRouting
import com.hypo.simpledb.plugins.configureSockets
import io.ktor.server.application.Application
import io.ktor.server.netty.EngineMain

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {
    configureRouting()
    configureSockets()
}