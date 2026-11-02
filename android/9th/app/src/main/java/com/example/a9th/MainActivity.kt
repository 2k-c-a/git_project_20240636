
package com.example.a9th

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    lateinit var button: Button
    lateinit var num1: TextView
    lateinit var num2: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById(R.id.button2)
        num1 = findViewById(R.id.num1)
        num2 = findViewById(R.id.num2)
    }

    fun onButtonClick(view: View) {

        // 두 TextView의 숫자 가져오기
        val number1 = num1.text.toString().toInt()
        val number2 = num2.text.toString().toInt()

        // 두 수의 합 계산
        val sum = number1 + number2

        // Toast 메시지 출력
        Toast.makeText(
            this,
            "두 수의 합은 $sum 입니다.",
            Toast.LENGTH_SHORT
        ).show()
    }
}
