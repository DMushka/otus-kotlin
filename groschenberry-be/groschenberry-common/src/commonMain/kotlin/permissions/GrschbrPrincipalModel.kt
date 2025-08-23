package com.otus.otuskotlin.groschenberry.common.permissions

import com.otus.otuskotlin.groschenberry.common.models.GrschbrUserId

data class GrschbrPrincipalModel(
    val id: GrschbrUserId = GrschbrUserId.NONE,
    val fname: String = "",
    val mname: String = "",
    val lname: String = "",
    val groups: Set<GrschbrUserGroups> = emptySet()
) {
    fun genericName() = "$fname $mname $lname"
    companion object {
        val NONE = GrschbrPrincipalModel()
    }
}
