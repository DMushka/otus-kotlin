package com.otus.otuskotlin.groschenberry.auth

import com.otus.otuskotlin.groschenberry.common.models.GrschbrCIPermissionClient
import com.otus.otuskotlin.groschenberry.common.permissions.GrschbrPrincipalRelations
import com.otus.otuskotlin.groschenberry.common.permissions.GrschbrUserPermissions

fun resolveFrontPermissions(
    permissions: Iterable<GrschbrUserPermissions>,
    relations: Iterable<GrschbrPrincipalRelations>,
) = mutableSetOf<GrschbrCIPermissionClient>()
    .apply {
        for (permission in permissions) {
            for (relation in relations) {
                accessTable[permission]?.get(relation)?.let { this@apply.add(it) }
            }
        }
    }
    .toSet()

/**
 * Это трехмерная таблица пермишин в бэкенде->отношение к объявлению->пермишин на фронте
 */
private val accessTable = mapOf(
    // READ
    GrschbrUserPermissions.READ to mapOf(
        GrschbrPrincipalRelations.ALL to GrschbrCIPermissionClient.READ
    ),

    // UPDATE
    GrschbrUserPermissions.UPDATE to mapOf(
        GrschbrPrincipalRelations.ALL to GrschbrCIPermissionClient.UPDATE
    ),

    // DELETE
    GrschbrUserPermissions.DELETE to mapOf(
        GrschbrPrincipalRelations.ALL to GrschbrCIPermissionClient.DELETE
    ),
)
