package example.micronaut.controller

import example.micronaut.service.MathService
import example.micronaut.service.MathServiceImpl
import io.kotest.core.spec.style.StringSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.kotest5.MicronautKotest5Extension.getMock
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import kotlin.math.pow
import kotlin.math.roundToInt

@MicronautTest
class MathControllerTest(
    private val mathService: MathService,
    @Client("/") private val client: HttpClient
) : StringSpec({
    "test compute num to square" {
        val mock = getMock(mathService)

        every { mock.compute(any()) } answers {
            firstArg<Int>().toDouble().pow(2).roundToInt()
        }

        forAll(
            row(4, 16),
            row(5, 25)
        ) { a: Int, b: Int ->
            val result = client.toBlocking().retrieve("/math/compute/$a", Int::class.java)
            result shouldBe b
            verify { mock.compute(a) }
        }
    }
}) {

    @MockBean(MathServiceImpl::class)
    fun mathService(): MathService {
        return mockk()
    }
}