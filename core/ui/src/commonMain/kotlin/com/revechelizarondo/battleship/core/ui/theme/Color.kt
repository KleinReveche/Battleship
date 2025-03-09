@file:Suppress("unused")

package com.revechelizarondo.battleship.core.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val pixelBlue = Color(0xFF4285F4)

val RedOrange = Color(0xffffab91)
val RedPink = Color(0xfff48fb1)
val BabyBlue = Color(0xff81deea)
val Violet = Color(0xffcf94da)
val LightGreen = Color(0xffe7ed9b)

val red = Color(0xffEB5757)
val androidGreen = Color(0xFF3DDC84)
val navy = Color(0xFF073042)
val purplish = Color(0xFF880E4F)
val lightBlue = Color(0xFFE0F7FA)
val yellow = Color(0xFFDAA520)

val BlueSky = Color(0xFF4478a9)
val NightSky = Color(0xFF333333)
val BorderColor = Color(0x40000000)

val Ocean = Color(0xFF7FCDFF)

val Fire1 = Color(0xFFFF5A00)
val Fire2 = Color(0xFFFF8A00)
val Fire3 = Color(0xFFFFCE00)
val Fire4 = Color(0xFFFFE808)

object RetroSpaceColors {
    val Purple = Color(0xFF9C27B0)
    val Pink = Color(0xFFFF4081)
    val Yellow = Color(0xFFFFEB3B)
    val Blue = Color(0xFF2196F3)
    val Black = Color(0xFF121212)
    val DeepPurple = Color(0xFF3F0071)

    // Gradients
    val PurpleGradient = Brush.verticalGradient(
        colors = listOf(
            Purple,
            DeepPurple
        )
    )

    val CardGradient = Brush.linearGradient(
        colors = listOf(
            DeepPurple,
            Purple
        )
    )
}