package com.example.a3rd

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)

        val num = 3

        var value = 1
        value = 2

        //num += 2

        println("$value")
        println(+value)

        println(+num)

        var myName = "윤우혁"
        val age: Int = 27
        myName = "Woohyeuk"
        println("var 가변변수, val 불변변수, 나의이름은 $myName")

        var num1 = 1
        var num2: Long = 1111111111111111111
        var num3: Byte = 1
        var num4: Int = 11

        println("num1 $num1, num2 $num2, num3 $num3, num4 $num4")



        var myFloat: Float = 30.2F
        var myDouble: Double = 35.4

        println("Float $myFloat, double $myDouble")

        var myBoolean: Boolean = true

        println("Boolean $myBoolean")

        var myChar1: Char = 'k'
        var myChar2: Char = 'o'
        var myChar3: Char = 't'
        var myChar4: Char = 'l'
        var myChar5: Char = 'i'
        var myChar6: Char = 'n'

        println("Char $myChar1$myChar2$myChar3$myChar4$myChar5$myChar6")

        var myString: String ="asdf"

        println("String $myString")

        var myIntArray: IntArray = intArrayOf(1,2,3,4,5)
        println("myIntArray 3rd value " +myIntArray[2])



    }


}