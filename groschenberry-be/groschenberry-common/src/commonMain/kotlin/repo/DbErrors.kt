package com.otus.otuskotlin.groschenberry.common.repo

import com.otus.otuskotlin.groschenberry.common.helpers.errorSystem
import com.otus.otuskotlin.groschenberry.common.models.GrschbrCIB
import com.otus.otuskotlin.groschenberry.common.models.GrschbrCID
import com.otus.otuskotlin.groschenberry.common.models.GrschbrCIId
import com.otus.otuskotlin.groschenberry.common.models.GrschbrCILock
import com.otus.otuskotlin.groschenberry.common.models.GrschbrError
import com.otus.otuskotlin.groschenberry.common.repo.exceptions.RepoConcurrencyException
import com.otus.otuskotlin.groschenberry.common.repo.exceptions.RepoException

const val ERROR_GROUP_REPO = "repo"

fun errorNotFound(id: GrschbrCIId) = DbCIResponseErr(
    GrschbrError(
        code = "$ERROR_GROUP_REPO-not-found",
        group = ERROR_GROUP_REPO,
        field = "id",
        message = "Object with ID: ${id.asString()} is not Found",
    )
)

val errorEmptyId = DbCIResponseErr(
    GrschbrError(
        code = "$ERROR_GROUP_REPO-empty-id",
        group = ERROR_GROUP_REPO,
        field = "id",
        message = "Id must not be null or blank"
    )
)

fun errorCIBRepoConcurrency(
    oldCIB: GrschbrCIB,
    expectedLock: GrschbrCILock,
    exception: Exception = RepoConcurrencyException(
        id = oldCIB.id,
        expectedLock = expectedLock,
        actualLock = oldCIB.lock,
    ),
) = DbCIBResponseErrWithData(
    data = oldCIB,
    err = GrschbrError(
        code = "$ERROR_GROUP_REPO-cib-concurrency",
        group = ERROR_GROUP_REPO,
        field = "lock",
        message = "The object with ID ${oldCIB.id.asString()} has been changed concurrently by another user or process",
        exception = exception,
    ),
)

fun errorCIDRepoConcurrency(
    oldCID: GrschbrCID,
    expectedLock: GrschbrCILock,
    exception: Exception = RepoConcurrencyException(
        id = oldCID.id,
        expectedLock = expectedLock,
        actualLock = oldCID.lock,
    ),
) = DbCIDResponseErrWithData(
    data = oldCID,
    err = GrschbrError(
        code = "$ERROR_GROUP_REPO-cid-concurrency",
        group = ERROR_GROUP_REPO,
        field = "lock",
        message = "The object with ID ${oldCID.id.asString()} has been changed concurrently by another user or process",
        exception = exception,
    ),
)

fun errorEmptyLock(id: GrschbrCIId) = DbCIResponseErr(
    GrschbrError(
        code = "$ERROR_GROUP_REPO-lock-empty",
        group = ERROR_GROUP_REPO,
        field = "lock",
        message = "Lock for CI ${id.asString()} is empty that is not admitted"
    )
)

fun errorDb(e: RepoException) = DbCIResponseErr(
    errorSystem(
        violationCode = "dbLockEmpty",
        e = e
    )
)
