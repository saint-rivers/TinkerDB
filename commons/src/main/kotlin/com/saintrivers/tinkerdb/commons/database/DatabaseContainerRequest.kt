package com.saintrivers.tinkerdb.commons.database

class DatabaseContainerRequest(
    val databaseType: DatabaseType,
    val databaseVersion: String,
    val containerName: String,
    val rootPassword: String?,
    val databaseName: String,
    val username: String,
    val password: String,
    val databasePort: Int
)

