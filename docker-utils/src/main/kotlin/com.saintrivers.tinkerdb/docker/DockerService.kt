package com.saintrivers.tinkerdb.docker

interface DockerService {
    fun isUp(containerName: String): Boolean
}