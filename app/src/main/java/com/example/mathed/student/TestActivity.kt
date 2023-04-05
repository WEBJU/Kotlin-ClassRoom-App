package com.example.mathed.student

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.core.content.ContextCompat
import com.example.mathed.Helpers.MarksDialogFragment
import com.example.mathed.Helpers.MyDatabaseHelper
import com.example.mathed.R
import com.example.mathed.data.TAnswer

class TestActivity : AppCompatActivity() {
    private var currentQuestionIndex = 0
    private var isAnswerSelected = false
    private var marks = 0
    private var choiceId=-1
    private lateinit var questionsAndAnswers: List<Pair<String, List<TAnswer>>>
    private lateinit var randomizedChoices: List<TAnswer>

    private lateinit var questionText: TextView
    private lateinit var questionNumber: TextView
    private lateinit var choice1: TextView
    private lateinit var choice2: TextView
    private lateinit var choice3: TextView
    private lateinit var choice4: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        val nextButton = findViewById<TextView>(R.id.next)
        questionText = findViewById(R.id.question_text)
        questionNumber = findViewById(R.id.question_No)
        choice1 = findViewById(R.id.question1_choice1)
        choice2 = findViewById(R.id.question1_choice2)
        choice3 = findViewById(R.id.question1_choice3)
        choice4 = findViewById(R.id.question1_choice4)
        val radioGrpBtn = findViewById<RadioGroup>(R.id.question1_choices)

        val dbHelper = MyDatabaseHelper(this)
        questionsAndAnswers = dbHelper.getQuestionsAndAnswers()
        displayQuestion(currentQuestionIndex)


        nextButton.setOnClickListener {
            if (choiceId == -1) {
                Toast.makeText(this, "Please select an answer", Toast.LENGTH_SHORT).show()
                return@setOnClickListener // Exit the listener function if no answer is selected
            }
            if (currentQuestionIndex == 8) {
                nextButton.text = "SUBMIT"
            }
            handleAnswerSelection(choiceId)
            isAnswerSelected = false
            currentQuestionIndex++

            if (currentQuestionIndex < questionsAndAnswers.size) {
                radioGrpBtn.clearCheck()
                choiceId =-1

                displayQuestion(currentQuestionIndex)

            } else {


                val result = dbHelper.insertTTest(marks)
                if (result) {
                    val dialogFragment = MarksDialogFragment(marks)
                    dialogFragment.show(supportFragmentManager, "marksDialog")
//                    Toast.makeText(this, "Test submitted successfully", Toast.LENGTH_SHORT).show()

//                    val intent = Intent(this, DashboardActivity::class.java)
//                    startActivity(intent)
//                    finish()
                } else {
                    Toast.makeText(this, "Error Submitting. Please Try Again..", Toast.LENGTH_SHORT)
                        .show()
                }

            }

        }

        choice1.setOnClickListener {
            choiceId=0
//            handleAnswerSelection(0)
        }
        choice2.setOnClickListener {
            choiceId=1
//            handleAnswerSelection(1)
        }
        choice3.setOnClickListener {
            choiceId=2
//            handleAnswerSelection(2)
        }
        choice4.setOnClickListener {
            choiceId=3
//            handleAnswerSelection(3)
        }

    }

    private fun handleAnswerSelection(answerIndex: Int) {
        isAnswerSelected = true
        val selectedAnswer = randomizedChoices[answerIndex]


        if (selectedAnswer.isCorrect) {
            // Add marks for correct answer
            marks++
        }


    }

    private fun displayQuestion(index: Int) {
        val question = questionsAndAnswers[index].first
        val answers = questionsAndAnswers[index].second

        // Filter out the correct answer
        val correctAnswer = answers.find { it.isCorrect }!!
        val otherAnswers = answers.filter { it != correctAnswer }

        // Shuffle the other answers and take 3 more
        val shuffledAnswers = otherAnswers.shuffled().take(3)

        // Concatenate the correct answer with the shuffled subset of answers
        val choices = listOf(correctAnswer) + shuffledAnswers

        // Shuffle the choices again to randomize the order
        randomizedChoices = choices.shuffled()
        questionText.text = question
        questionNumber.text = "Question " + (index + 1).toString()
        choice1.text = randomizedChoices[0].answerText
        choice2.text = randomizedChoices[1].answerText
        choice3.text = randomizedChoices[2].answerText
        choice4.text = randomizedChoices[3].answerText
    }


}