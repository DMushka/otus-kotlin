package com.otus.otuskotlin.groschenberry.auth

import com.otus.otuskotlin.groschenberry.common.models.GrschbrCIB
import com.otus.otuskotlin.groschenberry.common.models.GrschbrCID
import com.otus.otuskotlin.groschenberry.common.models.GrschbrCIId
import com.otus.otuskotlin.groschenberry.common.permissions.GrschbrPrincipalModel
import com.otus.otuskotlin.groschenberry.common.permissions.GrschbrPrincipalRelations

fun GrschbrCIB.resolveRelationsTo(principal: GrschbrPrincipalModel): Set<GrschbrPrincipalRelations> = setOfNotNull(
    GrschbrPrincipalRelations.NONE,
    // Используется при создании нового объявления
    GrschbrPrincipalRelations.NEW.takeIf { id == GrschbrCIId.NONE },
    GrschbrPrincipalRelations.ALL,
)

fun GrschbrCID.resolveRelationsTo(principal: GrschbrPrincipalModel): Set<GrschbrPrincipalRelations> = setOfNotNull(
    GrschbrPrincipalRelations.NONE,
    // Используется при создании нового объявления
    GrschbrPrincipalRelations.NEW.takeIf { id == GrschbrCIId.NONE },
    GrschbrPrincipalRelations.ALL,
)
