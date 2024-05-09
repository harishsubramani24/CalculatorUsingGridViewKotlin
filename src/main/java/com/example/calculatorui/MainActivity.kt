package com.example.calculatorui
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.GridView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var gridView: GridView
    private lateinit var display: TextView
    private var isOperatorClicked = false
    private var currentNumber = StringBuilder()
    private var firstOperand = 0.0
    private var secondOperand = 0.0
    private lateinit var currentOperator: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gridView = findViewById(R.id.gridView)
        display = findViewById(R.id.display)

        val buttons = listOf(
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "C", "0", "=", "+"
        )

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, buttons)
        gridView.adapter = adapter

        gridView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val buttonText = buttons[position]
            handleButtonClick(buttonText)
        }
    }

    private fun handleButtonClick(buttonText: String) {
        when {
            buttonText.isNumber() -> handleNumber(buttonText)
            buttonText == "C" -> clearDisplay()
            buttonText == "=" -> calculateResult()
            else -> handleOperator(buttonText)
        }
    }

    private fun String.isNumber(): Boolean {
        return this.toIntOrNull() != null
    }

    private fun handleNumber(number: String) {
        if (isOperatorClicked) {
            currentNumber.clear()
            isOperatorClicked = false
        }
        currentNumber.append(number)
        display.text = currentNumber.toString()
    }

    private fun handleOperator(operator: String) {
        if (currentNumber.isNotEmpty()) {
            firstOperand = currentNumber.toString().toDouble()
            currentOperator = operator
            isOperatorClicked = true
        }
    }

    private fun calculateResult() {
        if (currentNumber.isNotEmpty() && !isOperatorClicked) {
            secondOperand = currentNumber.toString().toDouble()
            currentNumber.clear()

            val result = when (currentOperator) {
                "+" -> firstOperand + secondOperand
                "-" -> firstOperand - secondOperand
                "*" -> firstOperand * secondOperand
                "/" -> firstOperand / secondOperand
                else -> 0.0
            }

            display.text = result.toString()
            currentNumber.append(result)
            firstOperand = result
            secondOperand = 0.0
        }
    }

    private fun clearDisplay() {
        currentNumber.clear()
        display.text = ""
    }
}
