package com.saintrivers.tinkerdb.docker

import com.saintrivers.tinkerdb.commons.database.DatabaseCreatedResponse
import com.saintrivers.tinkerdb.commons.database.DatabaseContainerRequest

interface DockerService {
    fun isUp(containerName: String): Boolean
    fun createDatabaseContainer(request: DatabaseContainerRequest): DatabaseCreatedResponse
//    fun stopDatabaseContainer()
}
