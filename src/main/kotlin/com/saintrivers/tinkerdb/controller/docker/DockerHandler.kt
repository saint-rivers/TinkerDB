package com.saintrivers.tinkerdb.controller.docker

import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

import com.saintrivers.tinkerdb.docker.DockerService

@Component
class DockerHandler(private val dockerService: DockerService) {

    fun isUp(req: ServerRequest): Mono<ServerResponse> {
        val containerName = req.pathVariables()["name"]
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
            .body(
                BodyInserters.fromValue(
                    mapOf(
                        "status" to containerName?.let { dockerService.isUp(it) },
                        "request" to containerName
                    )
                )
            )
    }

}