import com.otus.otuskotlin.groschenberry.backend.repo.tests.*
import com.otus.otuskotlin.groschenberry.repo.common.CIRepoInitialized
import com.otus.otuskotlin.groschenberry.repo.inmemory.CIRepoInMemory

class CIDRepoInMemoryCreateTest : RepoCIDCreateTest() {
    override val repo = CIRepoInitialized(
        CIRepoInMemory(randomUuid = { uuidNew.asString() }),
        initCIDObjects = initObjects,
    )
}

class CIDRepoInMemoryDeleteTest : RepoCIDDeleteTest() {
    override val repo = CIRepoInitialized(
        CIRepoInMemory(),
        initCIDObjects = initObjects,
    )
}

class CIDRepoInMemoryReadTest : RepoCIDReadTest() {
    override val repo = CIRepoInitialized(
        CIRepoInMemory(),
        initCIDObjects = initObjects,
    )
}

class CIDRepoInMemorySearchTest : RepoCIDSearchTest() {
    override val repo = CIRepoInitialized(
        CIRepoInMemory(),
        initCIDObjects = initObjects,
    )
}

class CIDRepoInMemoryUpdateTest : RepoCIDUpdateTest() {
    override val repo = CIRepoInitialized(
        CIRepoInMemory(randomUuid = { lockNew.asString() }),
        initCIDObjects = initObjects,
    )
}
