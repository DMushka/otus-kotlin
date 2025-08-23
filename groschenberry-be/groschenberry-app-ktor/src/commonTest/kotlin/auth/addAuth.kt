package com.otus.otuskotlin.groschenberry.app.ktor.auth

import io.ktor.client.request.*
import com.otus.otuskotlin.groschenberry.app.common.AUTH_HEADER
import com.otus.otuskotlin.groschenberry.app.common.createJwtTestHeader
import com.otus.otuskotlin.groschenberry.common.models.GrschbrUserId
import com.otus.otuskotlin.groschenberry.common.permissions.GrschbrPrincipalModel
import com.otus.otuskotlin.groschenberry.common.permissions.GrschbrUserGroups
import com.otus.otuskotlin.groschenberry.stubs.GrschbrUserStub

fun HttpRequestBuilder.addAuth(principal: GrschbrPrincipalModel) {
    header(AUTH_HEADER, principal.createJwtTestHeader())
}

fun HttpRequestBuilder.addAuth(
    id: GrschbrUserId = GrschbrUserStub.TEST_USER,
    groups: Collection<GrschbrUserGroups> = listOf(GrschbrUserGroups.TEST, GrschbrUserGroups.EXPERT),
) {
    addAuth(GrschbrPrincipalModel(id, groups = groups.toSet()))
}
