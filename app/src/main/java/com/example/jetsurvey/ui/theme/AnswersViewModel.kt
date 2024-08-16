package com.example.jetsurvey.ui.theme

import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import kotlinx.serialization.Serializable


class AnswersViewModel : ViewModel() {

    var QuesNum  = mutableStateOf(1)

    val SelectedOptions = mutableStateMapOf<Int, String>()

    fun updateoptions(QuesNumber : Int , option : String){

        SelectedOptions[QuesNumber] = option

    }

    fun OnNextQuestion(){
        QuesNum.value++
    }

    fun OnPreviousQuestion(){
        QuesNum.value--
    }

    fun clear(){
        QuesNum.value =1
        SelectedOptions.clear()
    }

}