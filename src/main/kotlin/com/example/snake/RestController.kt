package com.example.spring

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.io.File

@RestController
class RestController {

    @GetMapping("/calculate")
    fun calculate(@RequestParam input: String): String {
        val expression = input.filterNot { it.isWhitespace() }.split("(?<=[-+*/])|(?=[-+*/])".toRegex()).toMutableList()

        while (expression.size > 1) {
            var idx = expression.indexOfFirst { it == "*" || it == "/" } - 1
            if (idx < 0) idx = expression.indexOfFirst { it == "+" || it == "-" } - 1

            val num1 = expression.removeAt(idx).toInt()
            val op = expression.removeAt(idx)
            val num2 = expression.removeAt(idx).toInt()

            val result = when(op) {
                "+" -> num1 + num2
                "-" -> num1 - num2
                "*" -> num1 * num2
                "/" -> num1 / num2
                else -> throw Exception()
            }

            expression.add(idx, result.toString())
        }

        return expression.first()
    }

    @GetMapping("/snake")
    fun snake() = File("snake.html").readBytes()
}