package com.otus.otuskotlin.groschenberry.biz.permissions

import com.otus.otuskotlin.groschenberry.auth.resolveFrontPermissions
import com.otus.otuskotlin.groschenberry.auth.resolveRelationsTo
import com.otus.otuskotlin.groschenberry.biz.validation.validateStringField
import com.otus.otuskotlin.groschenberry.common.GrschbrContext
import com.otus.otuskotlin.groschenberry.common.models.GrschbrState
import com.otus.otuskotlin.groschenberry.common.models.GrschbrType
import com.otus.otuskotlin.groschenberry.cor.ICorChainDsl
import com.otus.otuskotlin.groschenberry.cor.worker

fun ICorChainDsl<GrschbrContext>.frontPermissions(title: String) = worker {
    this.title = title
    description = "Вычисление разрешений пользователей для фронтенда"

    on { state == GrschbrState.RUNNING }

    handle {
        when(type) {
            GrschbrType.BASIC -> {
                cibRepoDone.permissionsClient.addAll(
                    resolveFrontPermissions(
                        permissionsChain,
                        // Повторно вычисляем отношения, поскольку они могли измениться при выполении операции
                        cibRepoDone.resolveRelationsTo(principal)
                    )
                )

                for (cib in cibsRepoDone) {
                    cib.permissionsClient.addAll(
                        resolveFrontPermissions(
                            permissionsChain,
                            cib.resolveRelationsTo(principal)
                        )
                    )
                }
            }
            GrschbrType.DETAIL -> {
                cidRepoDone.permissionsClient.addAll(
                    resolveFrontPermissions(
                        permissionsChain,
                        // Повторно вычисляем отношения, поскольку они могли измениться при выполении операции
                        cidRepoDone.resolveRelationsTo(principal)
                    )
                )

                for (cid in cidsRepoDone) {
                    cid.permissionsClient.addAll(
                        resolveFrontPermissions(
                            permissionsChain,
                            cid.resolveRelationsTo(principal)
                        )
                    )
                }
            }
            GrschbrType.NONE -> null
        }
    }
}
