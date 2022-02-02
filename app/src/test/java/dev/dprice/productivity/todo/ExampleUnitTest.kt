package dev.dprice.productivity.todo

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

//import org.junit.Test
//
//import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest : Spec() {

    @Test
    fun addition_isCorrect() {
        context should "User be in the app"
        describe should "and two plus two is added"
        it should "result in 4" with {
            assertEquals(4, 2 + 2)
            2 + 2 equals 4
        }
    }

}

open class Spec {

    val context: String = ""
    val describe: String = ""
    val it: String = ""

    infix fun String.should(test: String) : String {

    }

    infix fun String.with(thing: () -> Unit) : String {

    }

    infix fun cont(test: String) {

    }

    infix fun Any.equals(other: Any?): Boolean = this == other
}