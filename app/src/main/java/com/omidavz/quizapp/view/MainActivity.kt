package com.omidavz.quizapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.omidavz.quizapp.R
import com.omidavz.quizapp.databinding.ActivityMainBinding
import com.omidavz.quizapp.model.Question
import com.omidavz.quizapp.viewmodel.QuizViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var quizViewModel: QuizViewModel
    lateinit var questionsList: List<Question>

    companion object {
        var result = 0
        var totalQuestions = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        // Resetting the scores
        result = 0
        totalQuestions = 0

        quizViewModel = ViewModelProvider(this).get(QuizViewModel::class.java)


        GlobalScope.launch(Dispatchers.Main) {
            quizViewModel.getQuestionsFromLiveData().observe(this@MainActivity, Observer {
                if (it.size > 0) {
                    questionsList = it

                    binding.apply {
                        txtQuestion.text = "Question 1: ${questionsList[0].question}"
                        radio1.text = questionsList[0].option1
                        radio2.text = questionsList[0].option2
                        radio3.text = questionsList[0].option3
                        radio4.text = questionsList[0].option4
                    }


                }
            })
        }
        // Adding functionality to next btn
        var i = 1
        binding.apply {
            btnNext.setOnClickListener() {
                val selectedOption = radioGroup.checkedRadioButtonId

                if (selectedOption == -1) {
                    // In there is no selection
                    Toast.makeText(this@MainActivity, "Please select one option", Toast.LENGTH_LONG)
                        .show()


                } else {
                    val redBtn = findViewById<View>(selectedOption!!) as RadioButton

                    questionsList.let {
                        if (i < it.size!!) {
                            totalQuestions = it.size


                            if (redBtn.text.toString().equals(it[i - 1].correct_option)) {
                                result++
                                txtResult?.text = "Correct Answer: $result"
                            }

                            // Displaying the nesxt questions
                            txtQuestion.text = "Question ${i + 1}: " + questionsList[i].question
                            radio1.text = it[i].option1
                            radio2.text = it[i].option2
                            radio3.text = it[i].option3
                            radio4.text = it[i].option4

                            // Checking for if it is the last questions
                            if (i == it.size!!.minus(1)) {
                                btnNext.text = "FINISH"
                            }

                            radioGroup?.clearCheck()
                            i++


                        } else {
                            if (redBtn.text.toString().equals(it[i - 1].correct_option)) {
                                result++
                                txtResult?.text = "Correct Answer : $result"
                            } else {

                            }
                            val intent = Intent(this@MainActivity, ResultActivity::class.java)
                            startActivity(intent)
                            finish()
                        }

                    }

                }
            }


        }


    }
}