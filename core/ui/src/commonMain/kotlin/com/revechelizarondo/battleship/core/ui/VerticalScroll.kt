package com.revechelizarondo.battleship.core.ui

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun Modifier.verticalScrollAndDrag(
    scrollState: ScrollState,
    enabled: Boolean = true,
    flingBehavior: FlingBehavior? = null,
    reverseScrolling: Boolean = false,
    scope: CoroutineScope,
) = this.then(
    verticalScroll(
        state = scrollState,
        enabled = enabled,
        flingBehavior = flingBehavior,
        reverseScrolling = reverseScrolling
    ).then(
        pointerInput(Unit) {
            detectDragGestures { change, dragAmount ->
                scope.launch {
                    scrollState.scrollBy(
                        -dragAmount.y
                    )
                }
                change.consume()
            }
        }
    )
)