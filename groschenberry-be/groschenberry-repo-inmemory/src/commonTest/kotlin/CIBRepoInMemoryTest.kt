import com.otus.otuskotlin.groschenberry.backend.repo.tests.*
import com.otus.otuskotlin.groschenberry.repo.common.CIRepoInitialized
import com.otus.otuskotlin.groschenberry.repo.inmemory.CIRepoInMemory


class CIBRepoInMemoryCreateTest : RepoCIBCreateTest() {
    override val repo = CIRepoInitialized(
        CIRepoInMemory(randomUuid = { uuidNew.asString() }),
        initCIBObjects = initObjects,
    )
}

class CIBRepoInMemoryDeleteTest : RepoCIBDeleteTest() {
    override val repo = CIRepoInitialized(
        CIRepoInMemory(),
        initCIBObjects = initObjects,
    )
}

class CIBRepoInMemoryReadTest : RepoCIBReadTest() {
    override val repo = CIRepoInitialized(
        CIRepoInMemory(),
        initCIBObjects = initObjects,
    )
}

class CIBRepoInMemorySearchTest : RepoCIBSearchTest() {
    override val repo = CIRepoInitialized(
        CIRepoInMemory(),
        initCIBObjects = initObjects,
    )
}

class CIBRepoInMemoryUpdateTest : RepoCIBUpdateTest() {
    override val repo = CIRepoInitialized(
        CIRepoInMemory(randomUuid = { lockNew.asString() }),
        initCIBObjects = initObjects,
    )
}
