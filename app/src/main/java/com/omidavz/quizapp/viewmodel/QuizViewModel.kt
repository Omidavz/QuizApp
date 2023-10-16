package com.omidavz.quizapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.omidavz.quizapp.model.QuestionList
import com.omidavz.quizapp.repository.QuizRepository

class QuizViewModel: ViewModel()  {

    private var repository :QuizRepository = QuizRepository()

    private var questionsLiveData  : LiveData<QuestionList> = repository.getQuestionsFromAPI()


    fun getQuestionsFromLiveData(): LiveData<QuestionList>{
        return questionsLiveData
    }




}