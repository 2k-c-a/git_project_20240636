package com.example.a3rd

import android.R
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

        var num: Int = 3

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


        var myX: Int = 100
        var myY: Float = myX.toFloat()

        println("Int : " + myX)
        println("Float : " + myY)


        var x : Int = 5
        var y : Int = 2

        println("" + (x+y) +" " +(x-y) + " " + (x/y)+" "  + (x*y) +" "  + (x%y))

        println("x >= y = " + (x >= y))

        println("x <= y = " + (x <= y))

        println("x == y = " + (x == y))

        println("x != y = " + (x != y))

        println("x > y = " + (x > y))

        println("x < y = " + (x < y))



        y += x
        println("y += x, y = " + y)
        y -= x
        println("y -= x, y = " + y)
        y *= x
        println("y *= x, y = " + y)
        y /= x
        println("y /= x, y = " + y)
        y %= x
        println("y %= x, y = " + y)


        println("y++, y = " + ++y)


        println("y--, y = " + --y)


        num = 10

        if (num % 2 == 0) {
            println("num은 짝수")
        }else {
            println("num은 홀수 ")
        }

        num = -10

        if(num>0){
            println("num은 양수")
        }else if(num<0){
            println("num은 음수")
        }else{
            println("num은 0")
        }

        var result : String
        if(num>0){
            if(num%2==0){
                result = "양수이며 짝수"
            }else{
                result = "양수이며 홀수"
            }
        }else{
            if(num%2==0){
                result = "음수이며 짝수"
            }else{
                result = "음수이며 홀수"
            }
        }
        println(result)


        var day : Int = 2
        when (day) {
            1 -> result = "Monday"
            2 -> result = "Tuesday"
            3 -> result = "Wednesday"
            4 -> result = "Thursday"
            5 -> result = "Friday"
            6 -> result = "Saturday"
            7 -> result = "Sunday"
            else -> result = "Invalid day"

        }
        println(result)

        for (i in 5 downTo 1){
            println("for 반복문, 반복변수 i 값 " +i)
        }

        for (i in 5 downTo 1 step 2){
            println("for 반복문, 반복변수 i 값 " +i)
        }

        var numbers = arrayOf(1, 2, 3, 4, 5)
        for (i in numbers){
            if(i%2==1){
                println("for 반복문, 반복변수 i 값 " +i)
            }
        }

        var score :Int = 60
        var attend : Int = 90

        if(attend < 80) println("낙제")
            else{
        if(score>=95) println("A 학점 장학생 선발 대상")
        else if (score>=90) println("A 학점")
        else if (score>=80) println("B 학점")
        else if (score>=70) println("C 학점")
        else if (score<70) println("F 학점")
        }



        for (j in 2..9) {
            for (k in 2..9) {
                print("${k}x${j}=${j * k} \t")
            }
            println()
        }


        for (k in 10..13) {
            for (j in 5..10) {
                print("${k}x${j}=${j * k} \t")
            }
            println()
        }


    }


}