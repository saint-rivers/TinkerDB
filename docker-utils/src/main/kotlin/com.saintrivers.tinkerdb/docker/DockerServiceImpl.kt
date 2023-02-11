package com.saintrivers.tinkerdb.docker

import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import org.springframework.stereotype.Component
import kotlin.time.Duration.Companion.seconds
import com.saintrivers.tinkerdb.commons.executeShellCommand

@Component
class DockerServiceImpl : DockerService {
    override fun isUp(containerName: String): Boolean = runBlocking {
        val r = withTimeout(3.seconds) {
            executeShellCommand("docker", "ps", "-a", "-f", "name=$containerName")
        }
        print(r.stdout)
        System.err.print(r.stderr)
//        exitProcess(r.exitCode)
        true
    }
}