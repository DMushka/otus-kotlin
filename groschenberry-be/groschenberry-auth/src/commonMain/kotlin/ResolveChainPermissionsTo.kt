package com.otus.otuskotlin.groschenberry.auth

import com.otus.otuskotlin.groschenberry.common.permissions.GrschbrUserGroups
import com.otus.otuskotlin.groschenberry.common.permissions.GrschbrUserPermissions

/**
 * На вход подаем группы/роли из JWT, на выход получаем пермишины, соответствующие этим группам/ролям
 */
fun resolveChainPermissions(
    groups: Iterable<GrschbrUserGroups>,
) = mutableSetOf<GrschbrUserPermissions>()
    .apply {
        // Группы, добавляющие права (пермишины)
        addAll(groups.flatMap { groupPermissionsAdmits[it] ?: emptySet() })
        // Группы, запрещающие права (пермишины)
        removeAll(groups.flatMap { groupPermissionsDenys[it] ?: emptySet() }.toSet())
    }
    .toSet()

//
private val groupPermissionsAdmits = mapOf(
    GrschbrUserGroups.USER to setOf(
        GrschbrUserPermissions.READ,
        GrschbrUserPermissions.SEARCH
    ),
    GrschbrUserGroups.EXPERT to setOf(
        GrschbrUserPermissions.READ,
        GrschbrUserPermissions.CREATE,
        GrschbrUserPermissions.UPDATE,
        GrschbrUserPermissions.DELETE,
        GrschbrUserPermissions.SEARCH
    ),
    GrschbrUserGroups.ADMIN_CI to setOf(),
    GrschbrUserGroups.TEST to setOf(),
)

private val groupPermissionsDenys = mapOf(
    GrschbrUserGroups.USER to setOf(
        GrschbrUserPermissions.UPDATE,
        GrschbrUserPermissions.CREATE,
        GrschbrUserPermissions.DELETE,
    ),
    GrschbrUserGroups.EXPERT to setOf(),
    GrschbrUserGroups.ADMIN_CI to setOf(),
    GrschbrUserGroups.TEST to setOf(),
)
