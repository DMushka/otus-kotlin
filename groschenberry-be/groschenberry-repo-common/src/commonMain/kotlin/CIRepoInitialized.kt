package com.otus.otuskotlin.groschenberry.repo.common

import com.otus.otuskotlin.groschenberry.common.models.GrschbrCIB
import com.otus.otuskotlin.groschenberry.common.models.GrschbrCID

/**
 * Делегат для всех репозиториев, позволяющий инициализировать базу данных предзагруженными данными
 */
class CIRepoInitialized(
    private val repo: IRepoCIInitializable,
    initCIBObjects: Collection<GrschbrCIB> = emptyList(),
    initCIDObjects: Collection<GrschbrCID> = emptyList(),
) : IRepoCIInitializable by repo {
    @Suppress("unused")
    val initializedCIBObjects: List<GrschbrCIB> = saveCIB(initCIBObjects).toList()
    val initializedCIDObjects: List<GrschbrCID> = saveCID(initCIDObjects).toList()
}
