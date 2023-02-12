package com.saintrivers.tinkerdb.controller

import com.saintrivers.tinkerdb.controller.docker.DockerHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router

@Configuration//(proxyBeanMethods = false)
class Router {

    @Bean
    fun route(dockerHandler: DockerHandler): RouterFunction<ServerResponse> = router {
        path("/api/docker").nest {
            accept(MediaType.APPLICATION_JSON).nest {
                GET("/healthcheck/{name}").invoke(dockerHandler::isUp)
                POST("/database").invoke(dockerHandler::createPostgres)
                PUT("/database/stop/{containerId}").invoke(dockerHandler::stopDatabase)
                PUT("/database/start/{containerId}").invoke(dockerHandler::startDatabase)
            }
        }

    }
}