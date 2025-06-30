import org.junit.Test
import com.otus.otuskotlin.groschenberry.api.v1.models.CIBCreateRequest
import com.otus.otuskotlin.groschenberry.api.v1.models.CIBCreateResponse
import com.otus.otuskotlin.groschenberry.api.v1.models.CIBDebug
import com.otus.otuskotlin.groschenberry.api.v1.models.CIRequestDebugMode
import com.otus.otuskotlin.groschenberry.api.v1.models.CIBRequestDebugStubs
import com.otus.otuskotlin.groschenberry.api.v1.models.CIDCreateRequest
import com.otus.otuskotlin.groschenberry.api.v1.models.CIDCreateResponse
import com.otus.otuskotlin.groschenberry.api.v1.models.CIDDebug
import com.otus.otuskotlin.groschenberry.api.v1.models.CIDRequestDebugStubs
import com.otus.otuskotlin.groschenberry.common.GrschbrContext
import com.otus.otuskotlin.groschenberry.common.models.GrschbrCIId
import com.otus.otuskotlin.groschenberry.common.models.GrschbrCILock
import com.otus.otuskotlin.groschenberry.common.models.GrschbrCommand
import com.otus.otuskotlin.groschenberry.common.models.GrschbrError
import com.otus.otuskotlin.groschenberry.common.models.GrschbrRequestId
import com.otus.otuskotlin.groschenberry.common.models.GrschbrState
import com.otus.otuskotlin.groschenberry.common.models.GrschbrWorkMode
import com.otus.otuskotlin.groschenberry.common.stubs.GrschbrStubs
import com.otus.otuskotlin.groschenberry.mappers.v1.fromTransport
import com.otus.otuskotlin.groschenberry.mappers.v1.toTransportCIB
import com.otus.otuskotlin.groschenberry.mappers.v1.toTransportCID
import com.otus.otuskotlin.groschenberry.mappers.v1.toTransportCreateCIB
import com.otus.otuskotlin.groschenberry.mappers.v1.toTransportCreateCID
import com.otus.otuskotlin.groschenberry.stubs.GrschbrCIBStub
import com.otus.otuskotlin.groschenberry.stubs.GrschbrCIDStub
import kotlin.test.assertEquals

class MapperTest {
    @Test
    fun cibFromTransport() {
        val req = CIBCreateRequest(
            debug = CIBDebug(
                mode = CIRequestDebugMode.STUB,
                stub = CIBRequestDebugStubs.SUCCESS,
            ),
            cib = GrschbrCIBStub.get().toTransportCreateCIB()
        )
        val expected = GrschbrCIBStub.prepareResult {
            id = GrschbrCIId.NONE
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
    fun cibToTransport() {
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

        val res = context.toTransportCIB() as CIBCreateResponse

        assertEquals(res.cib, GrschbrCIBStub.get().toTransportCIB())
        assertEquals(1, res.errors?.size)
        assertEquals("err", res.errors?.firstOrNull()?.code)
        assertEquals("request", res.errors?.firstOrNull()?.group)
        assertEquals("title", res.errors?.firstOrNull()?.field)
        assertEquals("wrong title", res.errors?.firstOrNull()?.message)
    }

    @Test
    fun cidFromTransport() {
        val req = CIDCreateRequest(
            debug = CIDDebug(
                mode = CIRequestDebugMode.STUB,
                stub = CIDRequestDebugStubs.SUCCESS,
            ),
            cid = GrschbrCIDStub.get().toTransportCreateCID()
        )
        val expected = GrschbrCIDStub.prepareResult {
            id = GrschbrCIId.NONE
            lock = GrschbrCILock.NONE
            permissionsClient.clear()
        }

        val context = GrschbrContext()
        context.fromTransport(req)

        assertEquals(GrschbrStubs.SUCCESS, context.stubCase)
        assertEquals(GrschbrWorkMode.STUB, context.workMode)
        assertEquals(expected, context.cidRequest)
    }

    @Test
    fun cidToTransport() {
        val context = GrschbrContext(
            requestId = GrschbrRequestId("1234"),
            command = GrschbrCommand.CREATE,
            cidResponse = GrschbrCIDStub.get(),
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

        val res = context.toTransportCID() as CIDCreateResponse

        assertEquals(res.cid, GrschbrCIDStub.get().toTransportCID())
        assertEquals(1, res.errors?.size)
        assertEquals("err", res.errors?.firstOrNull()?.code)
        assertEquals("request", res.errors?.firstOrNull()?.group)
        assertEquals("title", res.errors?.firstOrNull()?.field)
        assertEquals("wrong title", res.errors?.firstOrNull()?.message)
    }
}
