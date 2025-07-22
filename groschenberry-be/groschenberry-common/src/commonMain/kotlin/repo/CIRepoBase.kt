package com.otus.otuskotlin.groschenberry.common.repo

import com.otus.otuskotlin.groschenberry.common.helpers.errorSystem

abstract class CIRepoBase: IRepoCI {

    protected suspend fun tryCIMethod(block: suspend () -> IDbCIResponse) = try {
        block()
    } catch (e: Throwable) {
        DbCIResponseErr(errorSystem("methodException", e = e))
    }

    protected suspend fun tryCIsMethod(block: suspend () -> IDbCIsResponse) = try {
        block()
    } catch (e: Throwable) {
        DbCIsResponseErr(errorSystem("methodException", e = e))
    }

}