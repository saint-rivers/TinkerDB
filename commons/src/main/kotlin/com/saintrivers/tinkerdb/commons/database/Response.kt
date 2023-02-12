package com.saintrivers.tinkerdb.commons.database

data class DatabaseConnectionResponse(
    val databaseName: String,
    val username: String,
    val databasePort: Int
)

data class DatabaseCreatedResponse(
    val containerId: String,
    val databaseType: DatabaseType,
    val databaseVersion: String,
    val containerName: String,
    val connection: DatabaseConnectionResponse,
) {
    companion object
}

