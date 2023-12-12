package com.example.smscodefield

import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.lukelorusso.codeedittext.CodeEditText


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<CodeEditText>(R.id.cetMyCode).setOnCodeChangedListener { (code, completed) ->
            // the code has changed
            if (completed) {
                // the code has been fully entered (code.length == maxLength)
                println("mylog OnFilled code = ${code}")
            }
        }

//        val areas = listOf(R.id.tvArea1, R.id.tvArea2, R.id.tvArea3, R.id.tvArea4).map {
//            findViewById<TextView>(it)
//        }
//
//        val fieldCode = findViewById<EditText>(R.id.etCode)
//        areas.forEach {
//            it.setOnClickListener {
//                fieldCode.requestFocus()
//                println("mylog ${"Click on area"}")
//
//                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
//                imm.showSoftInput(fieldCode, InputMethodManager.SHOW_IMPLICIT)
//            }
//        }
    }

}