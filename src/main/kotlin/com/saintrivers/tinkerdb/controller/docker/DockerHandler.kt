package com.saintrivers.tinkerdb.controller.docker

import com.saintrivers.tinkerdb.commons.database.DatabaseType
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
        val databaseRequest = DatabaseContainerRequest(
            databaseType = DatabaseType.MYSQL,
            databaseVersion = "8.0.31-oracle",
            containerName = "mysql-testing",
            rootPassword = "postgres",
            username = "admin",
            password = "postgres",
            databaseName = "neon",
            databasePort = 5433
        )
        val details = dockerService.createDatabaseContainer(databaseRequest)
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
            .bodyValue(
                mapOf(
                    "status" to "success",
                    "details" to details
                )
            )
    }
}