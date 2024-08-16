package com.example.jetsurvey
import android.provider.CalendarContract.Colors
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.jetsurvey.ui.theme.JetSurveyTheme
import com.example.jetsurvey.R
import com.example.jetsurvey.ui.theme.AnswersViewModel
import kotlinx.serialization.Serializable


@Serializable
data class answerscreen(
    val surveyid: Int
)

@Serializable
object screen1


@Serializable
data class homescreen(
    val UserName: String
)


@Serializable
data class surveyscreen(
     val survey : Int
)


@Composable
fun SampleP(
    Question : String,
    Answer : String
){
    JetSurveyTheme {
        Column(Modifier.fillMaxWidth()) {

            Surface(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(8.dp),
                tonalElevation = 8.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Column {
                    Text(
                        text = Question,
                        fontSize = 24.sp,
                        modifier = Modifier
                            .padding(top = 8.dp, start = 8.dp)
                    )
                    Text(text = "Selected Answer",
                        fontSize = 8.sp,
                        modifier = Modifier.padding(start = 10.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(8.dp)

                    ) {
                        Icon(imageVector = Icons.Default.PlayArrow, contentDescription = null,Modifier.size(24.dp))
                        Text(text = Answer,
                            modifier = Modifier.padding(8.dp),
                            fontSize = 18.sp)
                    }
                }
            }
        }


    }
}

@Composable
fun AnswerScreen(viewModel: SurveyViewModel,surveyid: Int,myViewModel: AnswersViewModel) {

    val survey = viewModel.Current_User.userSurvey.surveys[surveyid]
    val items: MutableList<List<String>> = mutableListOf()

    var Answers = myViewModel.SelectedOptions


    for (i in 1..survey.numOfQues) {

        val question = survey.questions[i - 1].question ?: ""
        val answer = Answers[i] ?: "Not Answered"
        val item = listOf(question, answer)
        items.add(item)
    }

    JetSurveyTheme {
        Column(
            modifier = Modifier.padding(top = 64.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Answers")
            }
            LazyColumn {
                items(items){
                    item ->
                    SampleP(Question = item[0], Answer = item[1])
                }
            }

        }
    }

}


@Composable
fun NavController(modifier: Modifier,viewModel: SurveyViewModel,myViewModel: AnswersViewModel) {
    
    val navController = rememberNavController()
    NavHost(navController, startDestination = screen1) {
        composable<homescreen>
        {
            val args = it.toRoute<homescreen>()
            HomeScreen(UserName = args.UserName, navcontroller = navController,viewModel,myViewModel)
        }
        composable<screen1> { LoginScreen(navcontroller = navController)}
        composable<surveyscreen> {
            val args = it.toRoute<surveyscreen>()
            SurveyScreen(surveyid = args.survey,viewModel,navController,myViewModel)
        }
        composable<answerscreen>{
            val args = it.toRoute<answerscreen>()
            AnswerScreen(viewModel = viewModel, surveyid = args.surveyid, myViewModel = myViewModel)
        }


    }
}



@Composable
fun Option(
    name: String,
    isChecked: Boolean,
    onCheckedChange: (String) -> Unit
) {
    Log.d("ana","4")
    val bgColor = if (isChecked) MaterialTheme.colorScheme.surfaceVariant else Color.White
    val borderColor = if (isChecked) MaterialTheme.colorScheme.primary else Color.Transparent
    Surface(
        color = bgColor,
        border = BorderStroke(2.dp, borderColor),
        shape = RoundedCornerShape(8.dp),
        shadowElevation = 8.dp,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = name,
                fontSize = 17.sp,
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
                    .padding(vertical = 16.dp)
            )
            Checkbox(checked = isChecked, onCheckedChange = { onCheckedChange(name) })
        }
    }
}

@Composable
fun Question(
    question: Question,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    Log.d("ana","3")
    Column(modifier = Modifier.padding(16.dp)) {
        Surface(
            color = MaterialTheme.colorScheme.surfaceVariant,
            shape = RoundedCornerShape(8.dp),
            tonalElevation = 8.dp
        ) {
            Text(
                text = question.question,
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(8.dp)
                    .padding(vertical = 16.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = question.type)
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(question.options) { item ->
                Option(
                    name = item.value,
                    isChecked = selectedOption == item.value,
                    onCheckedChange = { onOptionSelected(it) }
                )
            }
        }
    }
}

@Composable
fun TopBar(
    quesNum: Int,
    noOfQues: Int
) {
    Column(
        Modifier
            .padding(16.dp)
            .padding(top = 32.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "$quesNum of $noOfQues",
                Modifier
                    .weight(1f)
                    .wrapContentWidth(Alignment.CenterHorizontally),
                fontSize = 13.sp
            )
            Icon(imageVector = Icons.Default.Close, contentDescription = null)
        }
        Spacer(modifier = Modifier.height(32.dp))
        LinearProgressIndicator(
            progress = quesNum / noOfQues.toFloat(),
            modifier = Modifier
                .fillMaxWidth()
                .height(5.dp),
        )
    }
}

@Composable
fun BottomBar(
    quesNum: Int,
    onClickNext: () -> Unit,
    onClickPrev: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (quesNum > 1) {
            Button(
                onClick = onClickPrev,
                Modifier
                    .weight(1f)
                    .padding(8.dp),
                border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
                colors = ButtonDefaults.buttonColors(
                    Color.White,
                    MaterialTheme.colorScheme.primary
                )
            ) {
                Text(text = "Previous")
            }
        }
        Button(
            onClick = onClickNext,
            Modifier
                .weight(1f)
                .padding(8.dp),
        ) {
            Text(text = "Next")
        }
    }
}


@Composable
fun Survey(
    survey: Survey,
    quesNum: Int,
    selectedOptions: Map<Int, String>,
    onOptionSelected: (Int, String) -> Unit,
    onNextQuestion: () -> Unit,
    onPrevQuestion: () -> Unit
) {
    Log.d("ana","2")
    Scaffold(
        topBar = {
            TopBar(quesNum, survey.numOfQues)
        },
        bottomBar = {
            BottomBar(
                quesNum = quesNum,
                onClickNext = onNextQuestion,
                onClickPrev = onPrevQuestion
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            Question(
                question = survey.questions[quesNum - 1],
                selectedOption = selectedOptions[quesNum] ?: "",
                onOptionSelected = { option -> onOptionSelected(quesNum, option) }
            )
        }
    }
}


@Composable
fun SurveyScreen(surveyid: Int,viewModel: SurveyViewModel,navcontroller: NavController, myViewModel: AnswersViewModel) {

    var quesNum = myViewModel.QuesNum.value

    val selectedOptions = myViewModel.SelectedOptions

    val survey = viewModel.Current_User.userSurvey.surveys[surveyid]

    Survey(
        survey = survey,
        quesNum = quesNum,
        selectedOptions = selectedOptions,
        onOptionSelected = { questionNum, option -> myViewModel.updateoptions(questionNum,option) },
        onNextQuestion = {
            if (quesNum < survey.numOfQues) {
                myViewModel.OnNextQuestion()

            }
            else if(quesNum == survey.numOfQues){
                navcontroller.navigate(answerscreen(surveyid))
            }
        },
        onPrevQuestion = {
            if (quesNum > 1) {
                myViewModel.OnPreviousQuestion()

            }
        }
    )
}




@Composable
fun HomeScreen(
    UserName : String,
    navcontroller : NavController,
    viewModel: SurveyViewModel,
    myViewModel: AnswersViewModel
) {

    viewModel.setCurrentUser(UserName)

    JetSurveyTheme {
        Surface(
            Modifier.fillMaxSize(),

        ) {
            Column(
               Modifier.padding(8.dp)
            ) {
                Image(painter = painterResource(id = R.drawable.logo),
                    contentDescription = null, modifier = Modifier.padding(top = 64.dp))

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Welcome Back,",
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.secondary
                )
                if (viewModel.Current_User != null) {
                    Text(text = viewModel.Current_User.name, fontSize = 30.sp, color = MaterialTheme.colorScheme.primary)
                }
                Spacer(Modifier.height(32.dp))
                Surface(

                    border = BorderStroke(2.dp, Color.Transparent),
                    shape = RoundedCornerShape(8.dp),
                    shadowElevation = 18.dp,

                    ) {
                    Column(
                        Modifier.padding(8.dp)
                    ) {
                        Text(text = "Your Survey's", fontSize = 30.sp)
                        Spacer(modifier = Modifier.height(16.dp))
                        LazyColumn {
                            if (viewModel.Current_User != null) {
                                items(viewModel.Current_User.userSurvey.surveys) { item ->
                                    Surface(
                                        Modifier
                                            .padding(8.dp)
                                            .height(100.dp),
                                        border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
                                        shape = RoundedCornerShape(8.dp),
                                        tonalElevation = 8.dp,
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = item.surveyName,
                                                Modifier
                                                    .weight(1f)
                                                    .padding(start = 8.dp),
                                                fontSize = 30.sp
                                            )
                                            IconButton(onClick = {
                                                myViewModel.clear()
                                                navcontroller.navigate(surveyscreen(viewModel.Current_User.userSurvey.surveys.indexOf(item))) }) {
                                                Icon(
                                                    imageVector = Icons.Default.ArrowForward,
                                                    contentDescription = null,
                                                    Modifier
                                                        .size(64.dp)
                                                        .padding(end = 16.dp)
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}





@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navcontroller: NavController
){
    var UserName by remember { mutableStateOf("") }
    var Password by remember { mutableStateOf("") }


    JetSurveyTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(32.dp)
                ) {
                    Image(painter = painterResource(id = R.drawable.logo),
                        contentDescription = null)
                    Text(text = "Better  Survey  with  JetPack  Compose",
                        fontSize = 16.sp,
                        color = Color.Gray)
                }


            Spacer(modifier = Modifier.height(64.dp))
            Surface(
                border = BorderStroke(2.dp, MaterialTheme.colorScheme.surfaceVariant),
                shape = RoundedCornerShape(8.dp),
                shadowElevation = 18.dp,
                modifier = Modifier
                    .padding(8.dp)
                    .height(400.dp)
            ) {
                Column(
                    modifier = Modifier.padding(8.dp)
                ) {
                    OutlinedTextField(value = UserName,
                        onValueChange = { UserName = it} ,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        label = { Text(text = "UserName") },
                        singleLine = true,
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = Color.Gray,
                            focusedLabelColor = MaterialTheme.colorScheme.primary,
                            unfocusedLabelColor = Color.Gray
                        ))
                    Spacer(modifier = Modifier.height(2.dp))
                    OutlinedTextField(value = Password,
                        onValueChange = { Password = it} ,
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        label = { Text(text = "Password") },
                        singleLine = true,
                        visualTransformation = PasswordVisualTransformation(),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = Color.Gray,
                            focusedLabelColor = MaterialTheme.colorScheme.primary,
                            unfocusedLabelColor = Color.Gray
                        ))

                    Button(onClick = {
                        navcontroller.navigate( homescreen(UserName = UserName ) )
                                     },
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp)) {
                        Text(text = "Login")
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {

                        ClickableText(text = AnnotatedString("Don't Have an Account? Sign-Up"),
                            style = TextStyle(fontSize = 16.sp),
                            onClick = { }
                        )


                    }
                }
            }
        }
    }
}

