package example.micronaut.service

import jakarta.inject.Singleton

@Singleton
class MathServiceImpl : MathService {
    override fun compute(num: Int): Int {
        println("MathServiceImpl::cumpute is called and num: $num")
        return num * 4
    }
}