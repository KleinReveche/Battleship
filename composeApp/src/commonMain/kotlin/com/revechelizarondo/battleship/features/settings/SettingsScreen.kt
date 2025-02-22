package com.revechelizarondo.battleship.features.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.revechelizarondo.battleship.features.common.verticalScrollAndDrag
import kotlinx.serialization.Serializable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController) {
    var selectedTheme by remember { mutableStateOf("Light") }
    var selectedOled by remember { mutableStateOf("No") }
    var selectedColor by remember { mutableStateOf("Wild Violet") }
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

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
                RadioButtonOption("Light", selectedTheme) { selectedTheme = it }
                RadioButtonOption("Dark", selectedTheme) { selectedTheme = it }
            }

            Spacer(modifier = Modifier.height(12.dp))

            SettingsCard(title = "OLED") {
                RadioButtonOption("Yes", selectedOled) { selectedOled = it }
                RadioButtonOption("No", selectedOled) { selectedOled = it }
            }

            Spacer(modifier = Modifier.height(12.dp))

            SettingsCard(title = "Color") {
                RadioButtonOption("Wild Violet", selectedColor) { selectedColor = it }
                RadioButtonOption("Dynamic", selectedColor) { selectedColor = it }
                RadioButtonOption("Yellow", selectedColor) { selectedColor = it }
                RadioButtonOption("Green", selectedColor) { selectedColor = it }
                RadioButtonOption("Pixel Blue", selectedColor) { selectedColor = it }
            }
        }
    }
}

@Composable
fun SettingsCard(
    title: String,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(6.dp, shape = RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 6.dp)
            )
            HorizontalDivider(
                modifier = Modifier.padding(bottom = 6.dp),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f)
            )
            content()
        }
    }
}

@Composable
fun RadioButtonOption(text: String, selectedOption: String, onOptionSelected: (String) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        RadioButton(
            selected = selectedOption == text,
            onClick = { onOptionSelected(text) }
        )
        Text(
            text = text,
            modifier = Modifier.padding(start = 8.dp),
            fontSize = 16.sp
        )
    }
}

@Serializable
object SettingsScreenDestination