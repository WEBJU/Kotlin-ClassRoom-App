package com.example.mathed.student

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.core.content.ContextCompat
import com.example.mathed.Helpers.MyDatabaseHelper
import com.example.mathed.R
import com.example.mathed.data.TAnswer

class TestActivity : AppCompatActivity() {
    private var currentQuestionIndex = 0
    private var isAnswerSelected = false
    private var marks = 0
    private lateinit var questionsAndAnswers: List<Pair<String, List<TAnswer>>>
    private lateinit var questionText: TextView
    private lateinit var questionNumber: TextView
    private lateinit var choice1: TextView
    private lateinit var choice2: TextView
    private lateinit var choice3: TextView
    private lateinit var choice4: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        val nextButton = findViewById<Button>(R.id.next)
        questionText = findViewById(R.id.question_text)
        questionNumber = findViewById(R.id.question_No)
        choice1=findViewById(R.id.question1_choice1)
        choice2 = findViewById(R.id.question1_choice2)
        choice3 = findViewById(R.id.question1_choice3)
        choice4 = findViewById(R.id.question1_choice4)
        val radioGrpBtn = findViewById<RadioGroup>(R.id.question1_choices)

        val dbHelper = MyDatabaseHelper(this)
        questionsAndAnswers = dbHelper.getQuestionsAndAnswers()
        displayQuestion(currentQuestionIndex)


        nextButton.setOnClickListener {
            if (!isAnswerSelected) {
                Toast.makeText(this, "Please select an answer", Toast.LENGTH_SHORT).show()
                return@setOnClickListener // Exit the listener function if no answer is selected
            }

            isAnswerSelected = false
            currentQuestionIndex++

            if (currentQuestionIndex < questionsAndAnswers.size) {
                radioGrpBtn.clearCheck()

                displayQuestion(currentQuestionIndex)

            } else {
                nextButton.text = "Submit"
                nextButton.setOnClickListener {
                    // Handle submission logic
                    Toast.makeText(this, "Submitting...", Toast.LENGTH_SHORT).show()
                    val result = dbHelper.insertTTest(marks)
                    if (result) {

                        Toast.makeText(this, "Test submitted successfully", Toast.LENGTH_SHORT).show()

                        val intent = Intent(this, DashboardActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "Error Submitting. Please Try Again..", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }

        choice1.setOnClickListener {
            handleAnswerSelection(0)
        }
        choice2.setOnClickListener {
            handleAnswerSelection(1)
        }
        choice3.setOnClickListener {
            handleAnswerSelection(2)
        }
        choice4.setOnClickListener {
            handleAnswerSelection(3)
        }

    }

    private fun handleAnswerSelection(answerIndex: Int) {
        isAnswerSelected = true
        val selectedAnswer = questionsAndAnswers[currentQuestionIndex].second[answerIndex]

        if (selectedAnswer.isCorrect) {
            // Add marks for correct answer
            marks++
        }


    }

    private fun displayQuestion(index: Int) {
        val question = questionsAndAnswers[index].first
        val answers = questionsAndAnswers[index].second
        questionText.text = question
        questionNumber.text = "Question "+(index+1).toString()
        choice1.text = answers[0].answerText
        choice2.text = answers[1].answerText
        choice3.text = answers[2].answerText
        choice4.text = answers[3].answerText
    }




}