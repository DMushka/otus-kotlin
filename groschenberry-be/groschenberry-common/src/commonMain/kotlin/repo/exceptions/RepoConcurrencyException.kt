package com.otus.otuskotlin.groschenberry.common.repo.exceptions

import com.otus.otuskotlin.groschenberry.common.models.GrschbrCIId
import com.otus.otuskotlin.groschenberry.common.models.GrschbrCILock

class RepoConcurrencyException(id: GrschbrCIId, expectedLock: GrschbrCILock, actualLock: GrschbrCILock?): RepoCIException(
    id,
    "Expected lock is $expectedLock while actual lock in db is $actualLock"
)
