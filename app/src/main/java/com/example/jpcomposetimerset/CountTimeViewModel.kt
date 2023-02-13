package com.example.jpcomposetimerset

import android.os.CountDownTimer
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

//class CountTimeViewModel : ViewModel() {
//
//    companion object {
//        enum class TimeUnit {
//            SEC, MIN, HOUR
//        }
//    }
//
//    private var countDownTimer: CountDownTimer? = null
//
//    private val _isRunning = MutableLiveData(false)
//    val isRunning: LiveData<Boolean>
//        get() = _isRunning
//
//    private val _seconds = MutableLiveData(0)
//    val seconds: LiveData<Int>
//        get() = _seconds
//
//    private val _minutes = MutableLiveData(0)
//    val minutes: LiveData<Int>
//        get() = _minutes
//
//    private val _hours = MutableLiveData(0)
//    val hours: LiveData<Int>
//        get() = _hours
//}
//
//@ExperimentalAnimationApi
//@Composable
//fun TimerApp(countTimeViewModel: CountTimeViewModel, modifier: Modifier = Modifier) {
//
//    val secs = countTimeViewModel.seconds.observeAsState()
//    val minutes = countTimeViewModel.minutes.observeAsState()
//    val hours = countTimeViewModel.hours.observeAsState()
//
//    val resumed = countTimeViewModel.isRunning.observeAsState()
//    val progress = countTimeViewModel.progress.observeAsState(1f)
//    val timeShow = countTimeViewModel.time.observeAsState(initial = "00:00:00")
//
//    Surface(color = MaterialTheme.colorScheme.background) {
//        val typography = MaterialTheme.typography
//
//        Column(modifier = Modifier.padding()) {
//            Spacer(modifier = Modifier.height(32.dp))
//            Row(
//                modifier = modifier
//                    .fillMaxWidth()
//                    .padding(start = 40.dp, end = 40.dp, top = 10.dp),
//                horizontalArrangement = Arrangement.Center,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Text(
//                    text = "Let's start the countdown!",
//                    fontSize = 24.sp,
//                    style = typography.headlineSmall,
//                    color = Color.White,
//                    fontStyle = FontStyle.Italic
//                )
//            }
//        }
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .wrapContentHeight(),
//            horizontalArrangement = Arrangement.Center
//        ) {
//            Box(Modifier.padding(40.dp), contentAlignment = Alignment.Center) {
//                CircularProgressIndicator(
//                    color = Color.Yellow,
//                    modifier = Modifier.size(250.dp),
//                    progress = progress.value,
//                    strokeWidth = 12.dp
//                )
//                Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
//                    Text(
//                        text = timeShow.value,
//                        color = Color.White
//                    )
//                }
//            }
//        }
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(start = 2.dp, end = 2.dp),
//            horizontalArrangement = Arrangement.SpaceEvenly,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Text(
//                text = CountTimeViewModel.Companion.TimeUnit.HOUR.name,
//                fontSize = 20.sp,
//                color = Color.White,
//                style = typography.bodyLarge
//            )
//            Text(
//                text = CountTimeViewModel.Companion.TimeUnit.MIN.name,
//                fontSize = 20.sp,
//                color = Color.White,
//                style = typography.bodyMedium
//            )
//            Text(
//                text = CountTimeViewModel.Companion.TimeUnit.SEC.name,
//                fontSize = 20.sp,
//                color = Color.White,
//                style = typography.labelMedium
//            )
//        }
//        Row(
//            modifier = modifier
//                .fillMaxWidth()
//                .clip(shape = RoundedCornerShape(4.dp))
//                .padding(12.dp),
//            horizontalArrangement = Arrangement.SpaceEvenly,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            TimerComponent(
//                value = hours.value,
//                timeUnit = CountTimeViewModel.Companion.TimeUnit.HOUR,
//                enabled = resumed.value != true
//            ) {
//                countTimeViewModel.modifyTime(CountTimeViewModel.Companion.TimeUnit.HOUR, it)
//            }
//            Text(text = " : ", fontSize = 36.sp)
//            TimerComponent(
//                value = minutes.value,
//                timeUnit = CountTimeViewModel.Companion.TimeUnit.MIN,
//                enabled = resumed.value != true
//            ) {
//                countTimeViewModel.modifyTime(CountTimeViewModel.Companion.TimeUnit.MIN, it)
//            }
//            Text(text = " : ", fontSize = 36.sp)
//            TimerComponent(
//                value = secs.value,
//                timeUnit = CountTimeViewModel.Companion.TimeUnit.SEC,
//                enabled = resumed.value != true
//            ) {
//                countTimeViewModel.modifyTime(CountTimeViewModel.Companion.TimeUnit.SEC, it)
//            }
//    }
//
//}