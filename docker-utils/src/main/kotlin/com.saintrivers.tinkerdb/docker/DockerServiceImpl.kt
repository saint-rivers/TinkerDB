package com.saintrivers.tinkerdb.docker

import com.saintrivers.tinkerdb.commons.database.DatabaseConnectionResponse
import com.saintrivers.tinkerdb.commons.database.DatabaseCreatedResponse
import com.saintrivers.tinkerdb.commons.database.DatabaseType
import com.saintrivers.tinkerdb.commons.database.DatabaseContainerRequest
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import org.springframework.stereotype.Component
import kotlin.time.Duration.Companion.seconds
import com.saintrivers.tinkerdb.commons.util.executeShellCommand

@Component
class DockerServiceImpl : DockerService {

    private final val docker = "docker"

    override fun isUp(containerName: String): Boolean = runBlocking {
        val r = withTimeout(3.seconds) {
            executeShellCommand(docker, "ps", "-a", "-f", "name=$containerName")
        }
        r.stdout.contains("UP", ignoreCase = true) || r.stdout.contains("HEALTHY", ignoreCase = true)
    }

    override fun stopDatabaseContainer(containerId: String): Boolean = runBlocking {
        val r = withTimeout(3.seconds) {
            executeShellCommand(docker, *stop(containerId))
        }
        r.stdout == containerId
    }

    override fun createDatabaseContainer(request: DatabaseContainerRequest): DatabaseCreatedResponse =
        runBlocking {
            val r = withTimeout(3.seconds) {
                executeShellCommand(
                    docker,
                    // the * converts the Array<String> into a pointer for the varargs parameter
                    *when (request.databaseType) {
                        DatabaseType.POSTGRES -> runPostgres(request)
                        DatabaseType.MYSQL -> runMySQL(request)
                        else -> {
                            throw IllegalArgumentException("invalid database vendor provided")
                        }
                    }
                )
            }
            return@runBlocking DatabaseCreatedResponse.from(r.stdout, request)
        }
}

private fun DatabaseCreatedResponse.Companion.from(
    stdout: String,
    request: DatabaseContainerRequest
): DatabaseCreatedResponse =
    DatabaseCreatedResponse(
        containerId = stdout,
        databaseType = request.databaseType,
        databaseVersion = request.databaseVersion,
        connection = DatabaseConnectionResponse(
            databaseName = request.databaseName,
            username = request.dbUsername,
            // skips database password
            // we will not send it to the user unless they ask for it
            databasePort = request.databasePort
        ),
        containerName = request.containerName
    )