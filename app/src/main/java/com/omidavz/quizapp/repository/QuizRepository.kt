package com.omidavz.quizapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.omidavz.quizapp.model.QuestionList
import com.omidavz.quizapp.retrofit.QuestionsAPI
import com.omidavz.quizapp.retrofit.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QuizRepository  {

    var questionsAPI : QuestionsAPI

   init {
       questionsAPI = RetrofitInstance().getRetrofitInstance().create(QuestionsAPI::class.java)
   }


    fun getQuestionsFromAPI() : LiveData<QuestionList>{

        var data = MutableLiveData<QuestionList>()

        var questionList : QuestionList

        CoroutineScope(Dispatchers.IO).launch {

            var response = questionsAPI.getQuestions()

            if (response != null ){

                questionList = response.body()!!

                data.postValue(questionList)

            }


        }

        return data




    }









}