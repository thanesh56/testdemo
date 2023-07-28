package example.micronaut.service

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.kotest5.MicronautKotest5Extension.getMock
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlin.math.pow
import kotlin.math.roundToInt

@MicronautTest
class MathMockServiceTest(private val mathService: MathService) : BehaviorSpec({
    given("test compute num to square") {
        `when`("the mock is provided") {
            val mock = getMock(mathService)
            every { mock.compute(any()) } answers {
                firstArg<Int>().toDouble().pow(2).roundToInt()
            }
            then("the mock implementation is used") {
                mock.compute(3) shouldBe 9
                verify { mock.compute(3) }
            }
        }
    }
}) {
    @MockBean(MathServiceImpl::class)
    fun mathService(): MathService {
        return mockk()
    }
}