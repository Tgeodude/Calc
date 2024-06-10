package com.example.calc

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.calc.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var lastNumber: String = ""
    private var currentOperation: Char = ' '

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Инициализация View Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Инициализация UI элементов
        val buttons = listOf(
            binding.button0, binding.button1, binding.button2, binding.button3,
            binding.button4, binding.button5, binding.button6, binding.button7,
            binding.button8, binding.button9
        )

        // Установка обработчика нажатий для цифровых кнопок
        val numberClickListener = NumberClickListener()
        buttons.forEach { button ->
            button.setOnClickListener(numberClickListener)
        }

        // Обработка нажатия кнопки десятичной точки
        binding.buttonDecimal.setOnClickListener {
            if (!binding.inputEditText.text.contains(".")) {
                binding.inputEditText.append(".")
            }
        }

        // Обработка нажатия кнопки очистки
        binding.buttonClear.setOnClickListener {
            binding.inputEditText.setText("")
            binding.resultTextView.text = "0"
            lastNumber = ""
            currentOperation = ' '
        }

        // Обработка нажатия кнопок операций
        binding.buttonAdd.setOnClickListener { processOperation('+') }
        binding.buttonSubtract.setOnClickListener { processOperation('-') }
        binding.buttonMultiply.setOnClickListener { processOperation('*') }
        binding.buttonDivide.setOnClickListener { processOperation('/') }
        binding.buttonEquals.setOnClickListener { calculateResult() }
    }

    private inner class NumberClickListener : View.OnClickListener {
        override fun onClick(view: View) {
            val button = view as android.widget.Button
            binding.inputEditText.append(button.text)
        }
    }

    private fun processOperation(operation: Char) {
        if (currentOperation != ' ') {
            calculateResult()
        }
        lastNumber = binding.inputEditText.text.toString()
        binding.inputEditText.setText("")
        currentOperation = operation
    }

    private fun calculateResult() {
        if (TextUtils.isEmpty(lastNumber) || TextUtils.isEmpty(binding.inputEditText.text)) {
            return
        }

        val secondNumber = binding.inputEditText.text.toString().toDouble()
        val result = when (currentOperation) {
            '+' -> lastNumber.toDouble() + secondNumber
            '-' -> lastNumber.toDouble() - secondNumber
            '*' -> lastNumber.toDouble() * secondNumber
            '/' -> if (secondNumber != 0.0) lastNumber.toDouble() / secondNumber else Double.NaN
            else -> 0.0
        }
        binding.resultTextView.text = result.toString()
        binding.inputEditText.setText("")
        lastNumber = ""
        currentOperation = ' '
    }
}
