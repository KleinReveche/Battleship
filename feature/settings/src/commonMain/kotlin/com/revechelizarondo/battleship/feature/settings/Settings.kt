package com.revechelizarondo.battleship.feature.settings

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.border
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.revechelizarondo.battleship.core.domain.platform.Platforms
import com.revechelizarondo.battleship.core.domain.models.ThemeOptions
import com.revechelizarondo.battleship.core.domain.models.UIColorTypes
import com.revechelizarondo.battleship.core.domain.platform.getPlatform
import com.revechelizarondo.battleship.core.ui.verticalScrollAndDrag
import com.revechelizarondo.battleship.feature.settings.composable.RadioButtonOption
import com.revechelizarondo.battleship.feature.settings.composable.SettingsCard
import org.koin.compose.koinInject
import kotlin.random.Random
import com.revechelizarondo.battleship.core.ui.theme.RetroSpaceColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun Settings(
    returnToMenu: () -> Unit,
    modifier: Modifier
) {
    val vm: SettingsViewModel = koinInject()
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    val isAndroid = getPlatform().name == Platforms.Android

    val retroPurple = Color(0xFF9C27B0)
    val retroPink = Color(0xFFFF4081)
    val retroYellow = Color(0xFFFFEB3B)
    val retroBlue = Color(0xFF2196F3)
    val retroBlack = Color(0xFF121212)

    val gradientBrush = Brush.verticalGradient(
        colors = listOf(
            retroPurple,
            Color(0xFF3F0071)
        )
    )

    val stars = remember {
        List(100) {
            val x = Random.nextFloat()
            val y = Random.nextFloat()
            val size = Random.nextFloat() * 2.5f + 0.5f
            val brightness = Random.nextFloat() * 0.5f + 0.5f
            Star(x, y, size, brightness)
        }
    }

    Scaffold(
        modifier = modifier,
        containerColor = retroBlack,
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(10.dp, shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
                    .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
                    .background(gradientBrush)
                    .padding(vertical = 5.dp)
                    .border(
                        BorderStroke(2.dp, retroPink),
                        shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp)
                    )
            ) {
                TopAppBar(
                    title = {
                        Text(
                            "SETTINGS",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            color = retroYellow
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = returnToMenu) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = "Back",
                                tint = retroPink
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                        titleContentColor = retroYellow
                    )
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(retroBlack)
                .drawBehind {
                    drawStars(stars)
                }
        ) {
            Column(
                modifier = Modifier
                    .verticalScrollAndDrag(scrollState = scrollState, scope = scope)
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(14.dp))

                val retroCardModifier = Modifier
                    .fillMaxWidth(0.92f)
                    .shadow(4.dp, RoundedCornerShape(6.dp))
                    .clip(RoundedCornerShape(6.dp))
                    .background(
                        Brush.linearGradient(
                            colors = listOf(
                                Color(0xFF3F0071),
                                Color(0xFF6A1B9A)
                            )
                        )
                    )
                    .border(
                        BorderStroke(1.dp, retroPink),
                        RoundedCornerShape(6.dp)
                    )
                    .padding(1.dp)

                Box(modifier = retroCardModifier) {
                    SettingsCard(title = "THEME") {
                        val selectedTheme = when (vm.isDarkMode) {
                            true -> ThemeOptions.Dark.name
                            false -> ThemeOptions.Light.name
                            null -> ThemeOptions.System.name
                        }
                        RadioButtonOption(ThemeOptions.System.name, selectedTheme) { vm.setIsDark(it) }
                        RadioButtonOption(ThemeOptions.Light.name, selectedTheme) { vm.setIsDark(it) }
                        RadioButtonOption(ThemeOptions.Dark.name, selectedTheme) { vm.setIsDark(it) }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Box(modifier = retroCardModifier) {
                    SettingsCard(title = "OLED") {
                        val oled = when (vm.isOled) {
                            true -> "Yes"
                            false -> "No"
                        }

                        RadioButtonOption("Yes", oled) { vm.setIsOled(it) }
                        RadioButtonOption("No", oled) { vm.setIsOled(it) }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Box(modifier = retroCardModifier) {
                    SettingsCard(title = "COLOR") {
                        val selectedTheme = vm.selectedTheme?.themeName ?: "Dynamic"

                        if (isAndroid) {
                            RadioButtonOption("Dynamic", selectedTheme) { vm.setTheme(null) }
                        }
                        RadioButtonOption(UIColorTypes.WildViolet.themeName, selectedTheme) {
                            vm.setTheme(UIColorTypes.WildViolet)
                        }
                        RadioButtonOption(UIColorTypes.Yellow.themeName, selectedTheme) {
                            vm.setTheme(UIColorTypes.Yellow)
                        }
                        RadioButtonOption(UIColorTypes.Green.themeName, selectedTheme) {
                            vm.setTheme(UIColorTypes.Green)
                        }
                        RadioButtonOption(UIColorTypes.PixelBlue.themeName, selectedTheme) {
                            vm.setTheme(UIColorTypes.PixelBlue)
                        }
                    }
                }
            }
        }
    }
}

private data class Star(
    val x: Float,
    val y: Float,
    val size: Float,
    val brightness: Float
)

private fun DrawScope.drawStars(stars: List<Star>) {
    stars.forEach { star ->
        val x = star.x * size.width
        val y = star.y * size.height
        val radius = star.size

        val brightness = (star.brightness * 255).toInt()
        val starColor = Color(brightness, brightness, brightness, 255)

        drawCircle(
            color = starColor,
            radius = radius,
            center = Offset(x, y)
        )
    }
}