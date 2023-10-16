package com.omidavz.quizapp.retrofit

import com.omidavz.quizapp.model.QuestionList
import retrofit2.Response
import retrofit2.http.GET

interface QuestionsAPI {
    @GET("questionsapi.php")
    suspend fun getQuestions() : Response<QuestionList>

}