package com.otus.otuskotlin.groschenberry.common.repo.exceptions

import com.otus.otuskotlin.groschenberry.common.models.GrschbrCIId

class RepoEmptyLockException(id: GrschbrCIId): RepoCIException(
    id,
    "Lock is empty in DB"
)
