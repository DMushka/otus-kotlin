package com.otus.otuskotlin.groschenberry.e2e.be.test.action.cib

import io.kotest.matchers.Matcher
import io.kotest.matchers.MatcherResult
import io.kotest.matchers.and
import com.otus.otuskotlin.groschenberry.api.v1.models.*

fun haveResult(result: ResponseResult) = Matcher<IBasicResponse> {
    MatcherResult(
        it.result == result,
        { "actual result ${it.result} but we expected $result" },
        { "result should not be $result" }
    )
}

val haveNoErrors = Matcher<IBasicResponse> {
    MatcherResult(
        it.errors.isNullOrEmpty(),
        { "actual errors ${it.errors} but we expected no errors" },
        { "errors should not be empty" }
    )
}

//fun haveError(code: String) = haveResult(ResponseResult.ERROR)
//    .and(Matcher<IResponse> {
//        MatcherResult(
//            it.errors?.firstOrNull { e -> e.code == code } != null,
//            { "actual errors ${it.errors} but we expected error with code $code" },
//            { "errors should not contain $code" }
//        )
//    })

val haveSuccessResult = haveResult(ResponseResult.SUCCESS) and haveNoErrors

val IBasicResponse.cib: CIBResponseObject?
    get() = when (this) {
        is CIBCreateResponse -> cib
        is CIBReadResponse -> cib
        is CIBUpdateResponse -> cib
        is CIBDeleteResponse -> cib
        else -> throw IllegalArgumentException("Invalid response type: ${this::class}")
    }
