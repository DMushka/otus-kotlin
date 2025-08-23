package com.otus.otuskotlin.groschenberry.biz

import com.otus.otuskotlin.groschenberry.common.GrschbrContext
import com.otus.otuskotlin.groschenberry.common.models.GrschbrUserId
import com.otus.otuskotlin.groschenberry.common.permissions.GrschbrPrincipalModel
import com.otus.otuskotlin.groschenberry.common.permissions.GrschbrUserGroups
import com.otus.otuskotlin.groschenberry.stubs.GrschbrUserStub

fun GrschbrContext.addTestPrincipal(userId: GrschbrUserId = GrschbrUserStub.TEST_USER) {
    principal = GrschbrPrincipalModel(
        id = userId,
        groups = setOf(
            GrschbrUserGroups.EXPERT,
            //GrschbrUserGroups.USER,
            GrschbrUserGroups.TEST,
        )
    )
}
