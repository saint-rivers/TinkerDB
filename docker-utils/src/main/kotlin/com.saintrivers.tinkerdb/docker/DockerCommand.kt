package com.saintrivers.tinkerdb.docker

import com.saintrivers.tinkerdb.commons.database.DatabaseContainerRequest

fun stop(containerId: String): Array<String> {
    return arrayOf("stop", containerId)
}

fun start(containerId: String): Array<String> {
    return arrayOf("start", containerId)
}

fun runPostgres(request: DatabaseContainerRequest): Array<String> {
    return arrayOf(
        "run",
        "-itd",
        "--name",
        request.containerName,
        "-e",
        "POSTGRES_USER=${request.dbUsername}",
        "-e",
        "POSTGRES_DB=${request.databaseName}",
        "-e",
        "POSTGRES_PASSWORD=${request.dbPassword}",
        "-p",
        "${request.databasePort}:5432",
        "postgres:${request.databaseVersion}"
    )
}

fun runMySQL(request: DatabaseContainerRequest): Array<String> {
    return arrayOf(
        "run",
        "-itd",
        "--name",
        request.containerName,
        "-e",
        "MYSQL_USER=${request.dbUsername}",
        "-e",
        "MYSQL_PASSWORD=${request.dbPassword}",
        "-e",
        "MYSQL_ROOT_PASSWORD=${request.rootPassword}",
        "-e",
        "MYSQL_DATABASE=${request.databaseName}",
        "-p",
        "${request.databasePort}:3306",
        "mysql:${request.databaseVersion}"
    )
}



