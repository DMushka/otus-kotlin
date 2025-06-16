import org.junit.Test
import com.otus.otuskotlin.groschenberry.api.v1.models.CIBCreateRequest
import com.otus.otuskotlin.groschenberry.api.v1.models.CIBCreateResponse
import com.otus.otuskotlin.groschenberry.api.v1.models.CIBDebug
import com.otus.otuskotlin.groschenberry.api.v1.models.CIRequestDebugMode
import com.otus.otuskotlin.groschenberry.api.v1.models.CIBRequestDebugStubs
import com.otus.otuskotlin.groschenberry.common.GrschbrContext
import com.otus.otuskotlin.groschenberry.common.models.GrschbrCIBId
import com.otus.otuskotlin.groschenberry.common.models.GrschbrCILock
import com.otus.otuskotlin.groschenberry.common.models.GrschbrCommand
import com.otus.otuskotlin.groschenberry.common.models.GrschbrError
import com.otus.otuskotlin.groschenberry.common.models.GrschbrRequestId
import com.otus.otuskotlin.groschenberry.common.models.GrschbrState
import com.otus.otuskotlin.groschenberry.common.models.GrschbrUserId
import com.otus.otuskotlin.groschenberry.common.models.GrschbrWorkMode
import com.otus.otuskotlin.groschenberry.common.stubs.GrschbrStubs
import com.otus.otuskotlin.groschenberry.mappers.v1.fromTransport
import com.otus.otuskotlin.groschenberry.mappers.v1.toTransportCIB
import com.otus.otuskotlin.groschenberry.mappers.v1.toTransportCreateCIB
import com.otus.otuskotlin.groschenberry.stubs.GrschbrCIBStub
import kotlin.test.assertEquals

class MapperTest {
    @Test
    fun fromTransport() {
        val req = CIBCreateRequest(
            debug = CIBDebug(
                mode = CIRequestDebugMode.STUB,
                stub = CIBRequestDebugStubs.SUCCESS,
            ),
            cib = GrschbrCIBStub.get().toTransportCreateCIB()
        )
        val expected = GrschbrCIBStub.prepareResult {
            id = GrschbrCIBId.NONE
            lock = GrschbrCILock.NONE
            permissionsClient.clear()
        }

        val context = GrschbrContext()
        context.fromTransport(req)

        assertEquals(GrschbrStubs.SUCCESS, context.stubCase)
        assertEquals(GrschbrWorkMode.STUB, context.workMode)
        assertEquals(expected, context.cibRequest)
    }

    @Test
    fun toTransport() {
        val context = GrschbrContext(
            requestId = GrschbrRequestId("1234"),
            command = GrschbrCommand.CREATE,
            cibResponse = GrschbrCIBStub.get(),
            errors = mutableListOf(
                GrschbrError(
                    code = "err",
                    group = "request",
                    field = "title",
                    message = "wrong title",
                )
            ),
            state = GrschbrState.RUNNING,
        )

        val req = context.toTransportCIB() as CIBCreateResponse

        assertEquals(req.cib, GrschbrCIBStub.get().toTransportCIB())
        assertEquals(1, req.errors?.size)
        assertEquals("err", req.errors?.firstOrNull()?.code)
        assertEquals("request", req.errors?.firstOrNull()?.group)
        assertEquals("title", req.errors?.firstOrNull()?.field)
        assertEquals("wrong title", req.errors?.firstOrNull()?.message)
    }
}
