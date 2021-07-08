package com.example

import com.example.controller.helloWorldController
import com.example.model.SampleSession
import io.ktor.server.netty.*
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.routing.*
import io.ktor.sessions.*

fun main(args: Array<String>): Unit = EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    val auths = listOf(
        Pair("taro", "password"),
        Pair("hanako", "password")
    )
    install(Authentication) {
        basic(name = "basic") {
            realm = "Ktor Server"
            // TODO: DBから認証情報の取得
            validate { credentials ->
                val auth = auths.first { auth -> credentials.name == auth.first }
                if (credentials.name == auth.first && credentials.password == auth.second) UserIdPrincipal(auth.first) else null
            }
        }
    }
    install(Sessions) {
        cookie<SampleSession>("COOKIE_NAME")
    }

    routing {
        helloWorldController()
    }
}
