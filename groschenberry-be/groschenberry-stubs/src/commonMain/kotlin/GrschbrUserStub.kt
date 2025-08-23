package com.otus.otuskotlin.groschenberry.stubs

import com.otus.otuskotlin.groschenberry.common.models.GrschbrCID
import com.otus.otuskotlin.groschenberry.common.models.GrschbrCIId
import com.otus.otuskotlin.groschenberry.common.models.GrschbrUserId
import com.otus.otuskotlin.groschenberry.stubs.GrschbrCIDStubCoins.COIND1

object GrschbrUserStub {
    val TEST_USER = GrschbrUserId("test_user_id")

    fun get(): GrschbrUserId = GrschbrUserId("test_user_id")

}
