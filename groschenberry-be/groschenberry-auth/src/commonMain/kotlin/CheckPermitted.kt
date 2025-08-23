package com.otus.otuskotlin.groschenberry.auth

import com.otus.otuskotlin.groschenberry.common.models.GrschbrCommand
import com.otus.otuskotlin.groschenberry.common.permissions.GrschbrPrincipalRelations
import com.otus.otuskotlin.groschenberry.common.permissions.GrschbrUserPermissions

/**
 * Вычисляет доступность выполнения операции.
 * Здесь происходит сравнение доступных прав (пермишинов) и фактических отношений принципала к объекту, с которым работаем
 */
fun checkPermitted(
    command: GrschbrCommand,
    relations: Iterable<GrschbrPrincipalRelations>,
    permissions: Iterable<GrschbrUserPermissions>,
) =
    relations.asSequence().flatMap { relation ->
        permissions.map { permission ->
            AccessTableConditions(
                command = command,
                permission = permission,
                relation = relation,
            )
        }
    }.any {
        accessTable[it] != null
    }
    // Дополнительно можно сделать проверку на отсутствие в результатах false

private data class AccessTableConditions(
    val command: GrschbrCommand,
    val permission: GrschbrUserPermissions,
    val relation: GrschbrPrincipalRelations
)

private val accessTable = mapOf(
    // Create
    AccessTableConditions(
        command = GrschbrCommand.CREATE,
        permission = GrschbrUserPermissions.CREATE,
        relation = GrschbrPrincipalRelations.NEW,
    ) to true,

    // Read
    AccessTableConditions(
        command = GrschbrCommand.READ,
        permission = GrschbrUserPermissions.READ,
        relation = GrschbrPrincipalRelations.ALL,
    ) to true,

    // Update
    AccessTableConditions(
        command = GrschbrCommand.UPDATE,
        permission = GrschbrUserPermissions.UPDATE,
        relation = GrschbrPrincipalRelations.ALL,
    ) to true,

    // Delete
    AccessTableConditions(
        command = GrschbrCommand.DELETE,
        permission = GrschbrUserPermissions.DELETE,
        relation = GrschbrPrincipalRelations.ALL,
    ) to true,
)
