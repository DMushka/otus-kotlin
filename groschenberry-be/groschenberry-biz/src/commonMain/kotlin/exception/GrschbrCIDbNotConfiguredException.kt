package com.otus.otuskotlin.groschenberry.biz.exception

import com.otus.otuskotlin.groschenberry.common.models.GrschbrWorkMode

class GrschbrCIDbNotConfiguredException(val workMode: GrschbrWorkMode): Exception(
    "Database is not configured properly for workmode $workMode"
)
