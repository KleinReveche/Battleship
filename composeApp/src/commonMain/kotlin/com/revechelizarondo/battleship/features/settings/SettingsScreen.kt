package com.revechelizarondo.battleship.features.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.revechelizarondo.battleship.domain.models.ThemeOptions
import com.revechelizarondo.battleship.domain.models.UIColorTypes
import com.revechelizarondo.battleship.features.common.verticalScrollAndDrag
import com.revechelizarondo.battleship.features.settings.composable.RadioButtonOption
import com.revechelizarondo.battleship.features.settings.composable.SettingsCard
import com.revechelizarondo.battleship.getPlatform
import kotlinx.serialization.Serializable
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController) {
    val vm: SettingsScreenViewModel = koinInject()
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    val isAndroid = getPlatform().name.startsWith("Android")

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(10.dp, shape = RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
                    .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
                    .background(Color(0xFF6A1B9A))
                    .padding(vertical = 5.dp)
            ) {
                TopAppBar(
                    title = { Text("Settings") },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = "Back",
                                tint = Color.White
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                        titleContentColor = Color.White
                    )
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .verticalScrollAndDrag(scrollState = scrollState, scope = scope)
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Spacer(modifier = Modifier.height(19.dp))

            SettingsCard(title = "Theme") {
                val selectedTheme = when (vm.isDarkMode) {
                    true -> ThemeOptions.Dark.name
                    false -> ThemeOptions.Light.name
                    null -> ThemeOptions.System.name
                }
                RadioButtonOption(ThemeOptions.System.name, selectedTheme) { vm.setIsDark(it) }
                RadioButtonOption(ThemeOptions.Light.name, selectedTheme) { vm.setIsDark(it) }
                RadioButtonOption(ThemeOptions.Dark.name, selectedTheme) { vm.setIsDark(it) }
            }

            Spacer(modifier = Modifier.height(12.dp))

            SettingsCard(title = "OLED") {
                val oled = when (vm.isOled) {
                    true -> "Yes"
                    false -> "No"
                }

                RadioButtonOption("Yes", oled) { vm.setIsOled(it) }
                RadioButtonOption("No", oled) { vm.setIsOled(it) }
            }

            Spacer(modifier = Modifier.height(12.dp))

            SettingsCard(title = "Color") {
                val selectedTheme = vm.selectedTheme?.themeName ?: "Dynamic"

                if (isAndroid) {
                    RadioButtonOption("Dynamic", selectedTheme) { vm.setTheme(null) }
                }
                RadioButtonOption(UIColorTypes.WildViolet.themeName, selectedTheme) {
                    vm.setTheme(
                        UIColorTypes.WildViolet
                    )
                }
                RadioButtonOption(UIColorTypes.Yellow.themeName, selectedTheme) {
                    vm.setTheme(
                        UIColorTypes.Yellow
                    )
                }
                RadioButtonOption(UIColorTypes.Green.themeName, selectedTheme) {
                    vm.setTheme(
                        UIColorTypes.Green
                    )
                }
                RadioButtonOption(UIColorTypes.PixelBlue.themeName, selectedTheme) {
                    vm.setTheme(
                        UIColorTypes.PixelBlue
                    )
                }
            }
        }
    }
}

@Serializable
object SettingsScreenDestination