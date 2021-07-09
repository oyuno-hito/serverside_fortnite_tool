package com.example.controller

import com.example.model.SampleSession
import io.ktor.application.call
import io.ktor.auth.*
import io.ktor.http.HttpStatusCode
import io.ktor.response.respondText
import io.ktor.response.respond
import io.ktor.routing.Route
import io.ktor.routing.route
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.put
import io.ktor.routing.delete
import io.ktor.sessions.*

fun Route.helloWorldController() {
    route("/hello_world") {
        get {
            val name = call.sessions.get<SampleSession>()?.name ?: "guest"
            call.respondText { "Hello $name!" }
        }
        authenticate("form") {
            post {
                val principal: UserIdPrincipal? = call.authentication.principal()
                if (principal?.name != null) {
                    call.sessions.set(SampleSession(name = principal.name, value = 0))
                }
                call.respond(HttpStatusCode.OK)
            }
        }
        put {
            call.respond(HttpStatusCode.BadRequest)
        }
        delete {
            call.respond(HttpStatusCode.BadRequest)
        }
    }
}
