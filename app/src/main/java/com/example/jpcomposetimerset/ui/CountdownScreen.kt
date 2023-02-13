/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.jpcomposetimerset.ui

import android.util.Log
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jpcomposetimerset.DisplayTime
import com.example.jpcomposetimerset.ui.HMSFontInfo.Companion.HMS
import com.example.jpcomposetimerset.ui.HMSFontInfo.Companion.MS
import com.example.jpcomposetimerset.ui.HMSFontInfo.Companion.S

@Composable
fun CountdownScreen(
    timeInSec: Int,
    value:(Int)->Unit
) {

    var trigger by remember { mutableStateOf(timeInSec) }

    val elapsed by animateIntAsState(
        targetValue = trigger * 1000,
        animationSpec = tween(timeInSec * 1000, easing = LinearEasing)
    )

    LaunchedEffect(key1 = elapsed ){
        Log.e("progress", "CountdownScreen: $elapsed" )
        value.invoke(elapsed)
    }

    DisposableEffect(Unit) {
        trigger = 0
        onDispose { }
    }

    Column(
        Modifier
            .fillMaxHeight()
            .padding(start = 10.dp, end = 10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Box {
            AnimationElapsedTime(elapsed)

        }

        Spacer(modifier = Modifier.size(55.dp))

    }
}

@Composable
private fun BoxScope.AnimationElapsedTime(elapsed: Int) {

    val (hou, min, sec) = remember(elapsed / 1000) {
        val elapsedInSec = elapsed / 1000
        val hou = elapsedInSec / 3600
        val min = elapsedInSec / 60 - hou * 60
        val sec = elapsedInSec % 60
        Triple(hou, min, sec)
    }

    val onlySec = remember(hou, min) {
        hou == 0 && min == 0
    }


    val (_, _, padding) = when {
        hou > 0 -> HMS
        min > 0 -> MS
        else -> S
    }

    Row(
        Modifier
            .align(Alignment.Center)
            .padding(start = padding, end = padding, top = 10.dp, bottom = 10.dp)
    ) {
        if (hou > 0) {
            DisplayTime(
                hou.formatTime(),
                "h",
                fontSize = 14.sp,
                labelSize = 14.sp
            )
        }
        if (min > 0) {
            DisplayTime(
                min.formatTime(),
                "m",
                fontSize = 14.sp,
                labelSize = 14.sp
            )
        }
        DisplayTime(
            if (onlySec) sec.toString() else sec.formatTime(),
            if (onlySec) "" else "s",
            fontSize = 14.sp,
            labelSize = 14.sp,
            textAlign = if (onlySec) TextAlign.Center else TextAlign.End
        )
    }

}

@Preview
@Composable
fun DisplayPreview() {
    CountdownScreen(1000){

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
