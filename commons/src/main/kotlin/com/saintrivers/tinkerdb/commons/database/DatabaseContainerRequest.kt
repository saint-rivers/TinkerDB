package com.saintrivers.tinkerdb.commons.database

class DatabaseContainerRequest(
    val databaseType: DatabaseType,
    val databaseVersion: String,
    var containerName: String,
    val rootPassword: String? = null,
    val databaseName: String,
    val dbUsername: String,
    val dbPassword: String,
    val databasePort: Int,
    var username: String? = null
)

