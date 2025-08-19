package com.otus.otuskotlin.groschenberry.repo.common

import com.otus.otuskotlin.groschenberry.common.models.GrschbrCIB
import com.otus.otuskotlin.groschenberry.common.models.GrschbrCID
import com.otus.otuskotlin.groschenberry.common.repo.IRepoCI

interface IRepoCIInitializable: IRepoCI {
    fun saveCIB(cibs: Collection<GrschbrCIB>) : Collection<GrschbrCIB>
    fun saveCID(cids: Collection<GrschbrCID>) : Collection<GrschbrCID>
}
