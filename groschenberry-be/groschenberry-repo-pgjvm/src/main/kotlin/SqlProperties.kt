package com.otus.otuskotlin.groschenberry.backend.repo.postgresql

data class SqlProperties(
    val host: String = "localhost",
    val port: Int = 5432,
    val user: String = "postgres",
    val password: String = "groschenberry-pass",
    val database: String = "groschenberry_cis",
    val schema: String = "public",
    val tableCIB: String = "cibs",
    val tableCID: String = "cids",
) {
    val url: String
        get() = "jdbc:postgresql://${host}:${port}/${database}"
}
