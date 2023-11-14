package com.miu.cs473.assignmentfivve

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.RadioGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var submitButton: Button
    private lateinit var resetButton: Button
    private lateinit var question1RadioGroup: RadioGroup
    private lateinit var question2CheckBoxes: List<CheckBox>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initiazeViews()
        setClickListeners()
    }

    private fun initiazeViews() {
        // Initialize views
        submitButton = findViewById(R.id.submitButton)
        resetButton = findViewById(R.id.resetButton)
        question1RadioGroup = findViewById(R.id.question1RadioGroup)
        question2CheckBoxes = listOf(
            findViewById(R.id.option2_1),
            findViewById(R.id.option2_2),
            findViewById(R.id.option2_3),
            findViewById(R.id.option2_4)
        )
    }

    private fun setClickListeners() {
        submitButton.setOnClickListener { showResultDialog() }
        resetButton.setOnClickListener { resetQuiz() }
    }

    private fun showResultDialog() {
        // Calculate score
        val score = calculateScore()

        // Get current date and time
        val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        val currentTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())

        // Display result in an AlertDialog
        val resultMessage = "Congratulations! You submitted on $currentDate at $currentTime. " +
                "You achieved $score%."
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setMessage(resultMessage)
            .setPositiveButton("OK") { _, _ -> }
            .create()
            .show()
    }

    private fun calculateScore(): Int {
        // Each question carries 50 points
        val scorePerQuestion = 50
        var score = 0

        // Question 1
        val selectedOption1Id = question1RadioGroup.checkedRadioButtonId
        if (selectedOption1Id == R.id.option1) {
            score += scorePerQuestion
        }

        // Question 2
        val correctOptions = setOf(R.id.option2_1, R.id.option2_2)
        if (question2CheckBoxes.filter { it.isChecked }.map { it.id }.toSet() == correctOptions) {
            score += scorePerQuestion
        }

        return score
    }

    private fun resetQuiz() {
        question1RadioGroup.clearCheck()
        question2CheckBoxes.forEach { it.isChecked = false }
    }
}