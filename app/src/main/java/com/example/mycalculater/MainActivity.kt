package com.example.mycalculater

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.ArithmeticException


class MainActivity : AppCompatActivity() {

    var lastNumeric: Boolean = false
    var lastDot: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //val tvInput: TextView = findViewById(R.id.tvInput)
    }

    fun onDecimalPoint(view: View) {
        if (lastNumeric && !lastDot) {  //have number without "." duplicate
            tvInput.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    fun onClear(view: View) {   //clear all textview
        tvInput.text = ""
        lastNumeric = false
        lastDot = false
    }

    fun onDigit(view: View) {
        if (tvInput.text == "0") {  //clear number "0" default
            tvInput.text = ""
        }
        tvInput.append((view as Button).text)
        lastNumeric = true
    }

    private fun isOperatorAdded(value: String): Boolean {
        return if (value.startsWith("-")) {
            false
        } else {
            value.contains("/") || value.contains("*")
                    || value.contains("+") || value.contains("-")
        }
    }

    fun onOperator(view: View) {
        if (lastNumeric && !isOperatorAdded(tvInput.text.toString())) {
            tvInput.append((view as Button).text)
            lastNumeric = false
            lastDot = false
        }
    }

    fun onEqual(view: View) {
        if (lastNumeric) {

            //if prefix is minus, let's start index at [1] to avoid first number(after split) is operator
            var tvValue = tvInput.text.toString()
            var prefixValue = ""
            try {

                //avoid null pointer exception
                //example: -33 - 5. If prefix still "-", split function will delimiter the Number
                //Left and the Number Right by "-" operator. In this case, the Number Right is 33
                //and the Number Left is Null --> App will be crashed
                if (tvValue.startsWith("-")) {
                    prefixValue = "-"
                    tvValue = tvValue.substring(1)
                }

                //split number and operator
                if (tvValue.contains("-")) {
                    val splitValue = tvValue.split("-")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    tvInput.text = (one.toDouble() - two.toDouble()).toString()
                } else if (tvValue.contains("+")) {
                    val splitValue = tvValue.split("+")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    tvInput.text = (one.toDouble() + two.toDouble()).toString()
                } else if (tvValue.contains("*")) {
                    val splitValue = tvValue.split("*")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    tvInput.text = (one.toDouble() * two.toDouble()).toString()
                } else if (tvValue.contains("/")) {
                    val splitValue = tvValue.split("/")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    tvInput.text = (one.toDouble() / two.toDouble()).toString()
                }

            } catch (e: ArithmeticException) {
                e.printStackTrace()
            }
        }
    }

}