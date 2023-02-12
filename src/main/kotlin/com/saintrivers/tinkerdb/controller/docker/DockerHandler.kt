package com.saintrivers.tinkerdb.controller.docker

import com.saintrivers.tinkerdb.commons.database.DatabaseContainerRequest
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

import com.saintrivers.tinkerdb.docker.DockerService

@Component
class DockerHandler(private val dockerService: DockerService) {

    fun isUp(req: ServerRequest): Mono<ServerResponse> {
        val containerName = req.pathVariables()["name"]
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
            .bodyValue(
                mapOf(
                    "status" to containerName?.let { dockerService.isUp(it) },
                    "request" to containerName
                )
            )
    }

    fun createPostgres(req: ServerRequest): Mono<ServerResponse> {
        return req.bodyToMono(DatabaseContainerRequest::class.java)
            .doOnNext {
                it.username = "eamdayan"
                dockerService.createDatabaseContainer(request = it)
            }
            .flatMap {
                ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(
                        mapOf(
                            "status" to "success",
                            "details" to it
                        )
                    )
            }
    }

    fun stopDatabase(req: ServerRequest): Mono<ServerResponse> {
        val containerId = req.pathVariables()["containerId"]
        val isStopped = containerId?.let { dockerService.stopDatabaseContainer(it) }
        return ServerResponse.ok().bodyValue(
            mapOf("containerStatus" to isStopped)
        )
    }

}