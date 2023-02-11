package com.saintrivers.tinkerdb.controller

import com.saintrivers.tinkerdb.controller.docker.DockerHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router

@Configuration(proxyBeanMethods = false)
class Router() {

    @Bean
    fun route(dockerHandler: DockerHandler): RouterFunction<ServerResponse> = router {
        accept(MediaType.APPLICATION_JSON).nest {
            GET("/docker/healthcheck/{name}").invoke(dockerHandler::isUp)
        }
    }
}