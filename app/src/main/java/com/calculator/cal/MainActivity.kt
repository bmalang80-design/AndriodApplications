 package com.calculator.cal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.text.DecimalFormat
import java.util.*

 enum class CalculatorMode{
    None, Add, Subtract, Multiply, Divide
}
class MainActivity : AppCompatActivity() {
    var lastButtonWasMode = false // declared variable that check if any arithmetic function is click
    var currentMode = CalculatorMode.None// instantiate CalculatorMode class
    var labelString = ""// hold the entered values
    var savedNum = 0// save value temporally


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupCalculator()
    }
    fun setupCalculator(){
        val allButtons = arrayOf(button0, button1, button2, button3, button4, button5, button6, button7, button8, button9)
        for(i in allButtons.indices){
            allButtons[i].setOnClickListener { didPressedNumber(i) }
        }
        buttonPlus.setOnClickListener { changeMode(CalculatorMode.Add) }
        buttonMinus.setOnClickListener { changeMode(CalculatorMode.Subtract) }
        buttonMultiply.setOnClickListener { changeMode(CalculatorMode.Multiply) }
        buttonDivide.setOnClickListener { changeMode(CalculatorMode.Divide) }
        buttonEquals.setOnClickListener { didPressEquals() }
        buttonc.setOnClickListener { didPressClear() }
    }
    fun didPressEquals(){
        if (lastButtonWasMode){
            return
        }
        val labelInt  = labelString.toInt()
        when(currentMode){
            CalculatorMode.Add -> savedNum += labelInt
            CalculatorMode.Subtract -> savedNum -= labelInt
            CalculatorMode.Multiply -> savedNum *= labelInt
            CalculatorMode.Divide -> {
                if (labelInt == 0){
                    didPressClear()
                    textView.text = "Cannot divide by zero"

                }else{
                    savedNum /= labelInt
                }
            }
            CalculatorMode.None -> return
        }
        currentMode = CalculatorMode.None
        labelString = "$savedNum"
        updateText()
        lastButtonWasMode = true
    }
    fun didPressClear(){
         lastButtonWasMode = false
         currentMode = CalculatorMode.None
         labelString = ""
        savedNum = 0
        textView.text = "0"
    }
    fun updateText(){
        //make sure entered number is not more than 8
        if(labelString.length > 8){
            didPressClear()
            textView.text = "Too Large"
            return
        }
        val labelInt  = labelString.toInt()
        if (currentMode == CalculatorMode.None){
            savedNum = labelInt
        }
        textView.text = DecimalFormat("#,###").format(labelInt)
    }
    fun changeMode(mode:CalculatorMode){
        if (savedNum == 0){
            return
        }
        currentMode = mode
        lastButtonWasMode = true

    }
    fun didPressedNumber(num:Int){
        val strVal = num.toString()

        if (lastButtonWasMode){
            lastButtonWasMode = false
            labelString = "0"
        }

        labelString = "$labelString$strVal"
        updateText()
    }
}
