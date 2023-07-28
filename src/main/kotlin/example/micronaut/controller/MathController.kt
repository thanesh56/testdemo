package example.micronaut.controller

import example.micronaut.service.MathService
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get

@Controller("/math")
class MathController internal constructor(private var mathService: MathService) {

    @Get(uri = "/compute/{number}", processes = [MediaType.TEXT_PLAIN])
    internal fun compute(number: Int): String {
        println("compute()::called and number: $number")
        return mathService.compute(number).toString()
    }
}