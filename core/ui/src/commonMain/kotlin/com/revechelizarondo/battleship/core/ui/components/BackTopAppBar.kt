package com.revechelizarondo.battleship.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * A [CenterAlignedTopAppBar] with a back navigation icon.
 * @param modifier The modifier to be applied to the component.
 * @param text The text to be displayed in the app bar.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackTopAppBar(
    modifier: Modifier = Modifier,
    text: String,
    onBackAction: () -> Unit,
) {
    CenterAlignedTopAppBar(
        title = { Text(text) },
        modifier = modifier.background(MaterialTheme.colorScheme.surfaceTint),
        navigationIcon = {
            IconButton(onClick = onBackAction) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
        },
    )
}
