package com.example

import com.example.routes.itemRoutes
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    // Configure JSON serialization
    install(ContentNegotiation) {
        json()
    }

    // Configure routing
    routing {
        get("/") {
            call.respondText("Welcome to Kotlin CRUD API! Use /items endpoints to manage items.")
        }
        
        itemRoutes()
    }
}
