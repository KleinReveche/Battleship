package com.revechelizarondo.battleship

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.revechelizarondo.battleship.presentation.components.main.BottomSheetMain
import com.revechelizarondo.battleship.presentation.theme.BattleshipTheme
import com.revechelizarondo.battleship.presentation.theme.UIColorTypes
import org.jetbrains.compose.ui.tooling.preview.Preview

private val textToButtonPadding = 16.dp
private val buttonToButtonPadding = 10.dp
private val buttonTextIconSpacing = 5.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App() {
    BattleshipTheme(
        dynamicColorAndroid = getPlatform().name.contains("Android"),
        uiColorTypes = UIColorTypes.Green,
        darkTheme = true,
        oled = true
    ) {
        var showBottomSheet by remember { mutableStateOf(false) }
        val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)

        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    title = {
                        Text("Battleship")
                    }
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier.padding(
                    0.dp, paddingValues.calculateTopPadding(), 0.dp, 0.dp
                ).fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("Welcome to Battleship!")
                Spacer(Modifier.size(textToButtonPadding))
                Button(
                    onClick = { showBottomSheet = !showBottomSheet }
                ) {
                    Image(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Play"
                    )
                    Spacer(Modifier.size(buttonTextIconSpacing, 0.dp))
                    Text("Play")
                }
                Spacer(Modifier.size(buttonToButtonPadding))
                Button(
                    onClick = { showBottomSheet = !showBottomSheet }
                ) {
                    Image(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Ranking"
                    )
                    Spacer(Modifier.size(buttonTextIconSpacing, 0.dp))
                    Text("Ranking")
                }
                Spacer(Modifier.size(buttonToButtonPadding))
                Button(
                    onClick = { showBottomSheet = !showBottomSheet }
                ) {
                    Image(
                        imageVector = Icons.Default.Info,
                        contentDescription = "About"
                    )
                    Spacer(Modifier.size(buttonTextIconSpacing, 0.dp))
                    Text("About")
                }
            }

            BottomSheetMain(
                showBottomSheet = showBottomSheet,
                sheetState = sheetState,
                onDismiss = { showBottomSheet = false }
            )
        }
    }
}