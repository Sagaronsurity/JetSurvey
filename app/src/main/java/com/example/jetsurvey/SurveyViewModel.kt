package com.example.jetsurvey

import androidx.lifecycle.ViewModel
import kotlinx.serialization.Serializable


@Serializable
data class Option(
    val value: String,
    val isClicked: Boolean
)

@Serializable
data class Question(
    val quesNum: Int,
    val question: String,
    val type : String,
    val options: List<Option>
)

@Serializable
data class Survey(
    val numOfQues: Int,
    val surveyName: String,
    val isTaken: Boolean,
    val questions: List<Question>
)

@Serializable
data class Surveys(
    val username: String,
    val surveys: List<Survey>
)

@Serializable
data class UserDetails(
    val name: String,
    val mail: String,
    val userSurvey: Surveys
)

class SurveyViewModel : ViewModel() {

    lateinit var Current_User: UserDetails

    val Users = mutableMapOf("Sagar" to Sagar, "Munny" to Munny)

    fun setCurrentUser(UserName: String?) {
        if (UserName != null) {
            Current_User = Users[UserName] ?: throw IllegalArgumentException("User not found")
        }
    }




}
