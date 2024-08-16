package com.example.jetsurvey

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.jetsurvey.ui.theme.AnswersViewModel
import com.example.jetsurvey.ui.theme.JetSurveyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val viewmodel : SurveyViewModel by viewModels()
        val myViewModel : AnswersViewModel by viewModels()
        setContent {
            JetSurveyTheme {

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                         Log.d("ana","Inside Main Activity")
                          NavController(Modifier.padding(innerPadding),viewmodel,myViewModel)
                }
            }
        }
    }
}

