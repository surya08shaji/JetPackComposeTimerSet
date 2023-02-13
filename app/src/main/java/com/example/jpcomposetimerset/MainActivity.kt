package com.example.jpcomposetimerset

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import com.example.jpcomposetimerset.HMSFontInfo.Companion.HMS
import com.example.jpcomposetimerset.HMSFontInfo.Companion.MS
import com.example.jpcomposetimerset.HMSFontInfo.Companion.S
import com.example.jpcomposetimerset.ui.CountdownScreen
import com.example.jpcomposetimerset.ui.theme.JPComposeTimerSetTheme
import kotlinx.coroutines.delay


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JPComposeTimerSetTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {

                        Timer(
                            modifier = Modifier.size(300.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Timer(
    modifier: Modifier = Modifier,
) {

    val context = LocalContext.current

    var totalTime by remember {
        mutableStateOf(0)
    }
    Log.e("totalTime1", totalTime.toString())

    var (hou, min, sec) = remember {
        mutableListOf(0, 0, 0)
    }

    val (editHoursValue, setHoursEditValue) = remember { mutableStateOf("") }
    val (editMinutesValue, setMinutesEditValue) = remember { mutableStateOf("") }
    val (editSecondsValue, setSecondsEditValue) = remember { mutableStateOf("") }
    val numberLength = remember { 2 }


    var visible by remember {
        mutableStateOf(true)
    }

    var size by remember {
        mutableStateOf(IntSize.Zero)
    }
    // create variable for value
    val progress = remember {
        mutableStateOf(1f)
    }
    // create variable for current time
    var currentTime by remember {
        mutableStateOf(totalTime)
    }
    // create variable for isTimerRunning
    var isTimerRunning by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(key1 = currentTime, key2 = isTimerRunning) {
        if (currentTime > 0 && isTimerRunning) {
            delay(100)
            currentTime -= 100


        }
    }
    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "Set Timer")


        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .onSizeChanged {
                    size = it
                }
        ) {


            CircularProgressIndicator(modifier = Modifier.fillMaxSize(), progress = progress.value)


            if (visible) {
                SetTimes(
                    editHoursValue,
                    editMinutesValue,
                    editSecondsValue,
                    setHoursEditValue,
                    setMinutesEditValue,
                    setSecondsEditValue,
                    numberLength,
                    hou,
                    min,
                    sec
                )
            } else {

                CountdownScreen(timeInSec = currentTime) {

                    progress.value =
                        1 - ((totalTime + it).toDouble() / (totalTime * 1000).toDouble()).toFloat()
                }
            }


        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (visible)
                Button(
                    onClick = {
                        if (editHoursValue.isBlank() && editMinutesValue.isBlank() && editSecondsValue.isBlank()) {
                            Toast.makeText(
                                context,
                                "Please Add Time",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {

                            visible = !visible
                            if (visible) {
                                progress.value = 1f

                            } else {

                                hou = if (editHoursValue.isBlank()) 0 else editHoursValue.toInt()
                                min =
                                    if (editMinutesValue.isBlank()) 0 else editMinutesValue.toInt()
                                sec =
                                    if (editSecondsValue.isBlank()) 0 else editSecondsValue.toInt()

                                val times = hou * 60 * 60 +
                                        min * 60 +
                                        sec
                                totalTime = times

                                Log.e("times", times.toString())
                                Log.e("totalTime2", totalTime.toString())

                                if (currentTime <= 0) {
                                    currentTime = totalTime
                                    Log.e("currentTime", currentTime.toString())
                                    isTimerRunning = true
                                } else {
                                    isTimerRunning = !isTimerRunning
                                }
                            }
                        }
                    },
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .size(width = 150.dp, height = 40.dp),

                    ) {
                    Text(
                        text =
                        "Start"

                    )
                }


            Button(
                onClick = {
                    if (!visible) {
                        visible = !visible
                        progress.value = 1f
                        setHoursEditValue("")
                        setMinutesEditValue("")
                        setSecondsEditValue("")
                    }

                }, modifier = Modifier
                    .padding(top = 24.dp)
                    .size(width = 150.dp, height = 40.dp)
            ) {
                Text(text = "Cancel")
            }


        }

    }
}

@Composable
fun ViewTimer(currentTime: Int) {

    val trigger by remember { mutableStateOf(currentTime) }

    val elapsed by animateIntAsState(
        targetValue = trigger * 1000,
        animationSpec = tween(currentTime * 1000, easing = LinearEasing)
    )

    AnimationElapsedTime(elapsed)
}

@Composable
fun AnimationElapsedTime(elapsed: Int) {

    val (hou, min, sec) = remember(elapsed / 1000) {
        val elapsedInSec = elapsed / 1000
        val hou = elapsedInSec / 3600
        val min = elapsedInSec / 60 - hou * 60
        val sec = elapsedInSec % 60
        Triple(hou, min, sec)
    }

    val mills = remember(elapsed) {
        elapsed % 1000
    }

    val onlySec = remember(hou, min) {
        hou == 0 && min == 0
    }

    val (size, labelSize, _) = when {
        hou > 0 -> HMS
        min > 0 -> MS
        else -> S
    }

    val transition = rememberInfiniteTransition()

    val animatedFont by transition.animateFloat(
        initialValue = 1.5f,
        targetValue = 0.8f,
        animationSpec = infiniteRepeatable(tween(500), RepeatMode.Reverse)
    )

    Row {
        if (hou > 0) {
            DisplayTime(
                hou.formatTime(),
                "h",
                fontSize = size,
                labelSize = labelSize
            )
        }
        if (min > 0) {
            DisplayTime(
                min.formatTime(),
                "m",
                fontSize = size,
                labelSize = labelSize
            )
        }
        DisplayTime(
            if (onlySec) sec.toString() else sec.formatTime(),
            if (onlySec) "" else "s",
            fontSize = size * (if (onlySec && sec < 10 && mills != 0) animatedFont else 1f),
            labelSize = labelSize,
            textAlign = if (onlySec) TextAlign.Center else TextAlign.End
        )
    }
}

@Composable
fun RowScope.DisplayTime(
    num: String,
    label: String = "",
    heightLight: Boolean = true,
    fontSize: TextUnit = 14.sp,
    labelSize: TextUnit = 14.sp,
    textAlign: TextAlign = TextAlign.End
) {

    val textColor = if (heightLight) MaterialTheme.colorScheme.primary
    else Color.Unspecified.copy(alpha = 0.5f)

    Text(
        num,
        Modifier
            .weight(1f)
            .align(Alignment.CenterVertically),
        textAlign = textAlign,
        fontSize = fontSize,
//        fontFamily = FontFamily.Cursive,
        color = textColor,
//        style = MaterialTheme.typography.bodyLarge
    )
    if (label.isNotEmpty()) {
        Text(
            modifier = Modifier
                .padding(top = 20.dp)
                .align(Alignment.CenterVertically),
            text = label,
            fontSize = labelSize,
            color = textColor
        )
        Spacer(modifier = Modifier.width(15.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetTimes(
    editHoursValue: String,
    editMinutesValue: String,
    editSecondsValue: String,
    setHoursEditValue: (String) -> Unit,
    setMinutesEditValue: (String) -> Unit,
    setSecondsEditValue: (String) -> Unit,
    numberLength: Int,
    hou: Int,
    min: Int,
    sec: Int
) {
    var h = hou
    var m = min
    var s = sec
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()

    ) {

        TextField(
            value = editHoursValue,
            onValueChange = {
                if (it.length <= numberLength) {
                    setHoursEditValue(it)
                    h = it.toInt()
                }

                Log.e("h", it)
            },
            placeholder = { Text(text = "00") },
            colors = TextFieldDefaults.textFieldColors(
                textColor = MaterialTheme.colorScheme.primary,
                containerColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                cursorColor = Color.Black
            ),
            modifier = Modifier
                .size(width = 80.dp, height = 50.dp)
                .wrapContentWidth()
                .wrapContentHeight(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            )
        )

        Text(text = ":  ", modifier = Modifier.padding(end = 8.dp))

        TextField(
            value = editMinutesValue,
            onValueChange = {
                if (it.length <= numberLength) {
                    setMinutesEditValue(it)
                    m = it.toInt()
                }

            },
            placeholder = { Text(text = "00") },
            colors = TextFieldDefaults.textFieldColors(
                textColor = MaterialTheme.colorScheme.primary,
                containerColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                cursorColor = Color.Black
            ),
            modifier = Modifier
                .size(width = 80.dp, height = 50.dp)
                .wrapContentWidth()
                .wrapContentHeight(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            )
        )

        Text(text = ":  ", modifier = Modifier.padding(end = 8.dp))

        TextField(
            value = editSecondsValue,
            onValueChange = {
                if (it.length <= numberLength) {
                    setSecondsEditValue(it)
                    s = it.toInt()
                }

            },
            placeholder = { Text(text = "00") },
            colors = TextFieldDefaults.textFieldColors(
                textColor = MaterialTheme.colorScheme.primary,
                containerColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                cursorColor = Color.Black
            ),
            modifier = Modifier

                .size(width = 80.dp, height = 50.dp)
                .wrapContentWidth()
                .wrapContentHeight(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            )
        )
    }
}

private fun Int.formatTime() = String.format("%02d", this)

private data class HMSFontInfo(val fontSize: TextUnit, val labelSize: TextUnit, val padding: Dp) {
    companion object {
        val HMS = HMSFontInfo(50.sp, 20.sp, 40.dp)
        val MS = HMSFontInfo(85.sp, 30.sp, 50.dp)
        val S = HMSFontInfo(150.sp, 50.sp, 55.dp)
    }
}
