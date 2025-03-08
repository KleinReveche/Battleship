package com.revechelizarondo.battleship.core.ui.components

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import com.revechelizarondo.battleship.core.domain.platform.Platforms
import com.revechelizarondo.battleship.core.domain.platform.getPlatform

/**
 * CompositionLocal to provide drag target information.
 */
internal val LocalDragTargetInfo = compositionLocalOf { DragTargetInfo() }

/**
 * A composable that provides a screen where draggable items can be placed.
 *
 * @param modifier Modifier to be applied to the Box.
 * @param content The content to be displayed inside the draggable screen.
 */
@Composable
fun DraggableScreen(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    val state = remember { DragTargetInfo() }
    CompositionLocalProvider(
        LocalDragTargetInfo provides state
    ) {
        Box(modifier = modifier.fillMaxSize()) {
            content()
            if (state.isDragging) {
                var targetSize by remember { mutableStateOf(IntSize.Zero) }
                Box(
                    modifier = Modifier
                        .graphicsLayer {
                            val offset = (state.dragPosition + state.dragOffset)
                            scaleX = 1.3f
                            scaleY = 1.3f
                            alpha = if (targetSize == IntSize.Zero) 0f else .9f
                            translationX = offset.x.minus(targetSize.width / 2)
                            translationY = offset.y.minus(targetSize.height / 2)
                        }
                        .onGloballyPositioned {
                            targetSize = it.size
                        }
                ) {
                    state.draggableComposable?.invoke()
                }
            }
        }
    }
}

/**
 * A composable that makes its content draggable.
 *
 * @param T The type of data to be dragged.
 * @param modifier Modifier to be applied to the Box.
 * @param dataToDrop The data to be dropped when dragging ends.
 * @param startDragging Callback to be invoked when dragging starts.
 * @param stopDragging Callback to be invoked when dragging stops.
 * @param content The content to be displayed inside the draggable area.
 */
@Composable
fun <T> DraggableContent(
    modifier: Modifier = Modifier,
    dataToDrop: T,
    startDragging: () -> Unit,
    stopDragging: () -> Unit,
    additionalModifier: (Modifier.() -> Modifier) = { this },
    content: @Composable (() -> Unit)
) {
    var currentPosition by remember { mutableStateOf(Offset.Zero) }
    val currentState = LocalDragTargetInfo.current

    Box(
        modifier = modifier
            .onGloballyPositioned {
                currentPosition = it.localToWindow(Offset.Zero)
            }
            .pointerInput(Unit) {
                val platform = getPlatform().name
                if (platform != Platforms.Android) {
                    detectDragGestures(
                        onDragStart = {
                            startDragging()
                            currentState.dataToDrop = dataToDrop
                            currentState.isDragging = true
                            currentState.dragPosition = currentPosition + it
                            currentState.draggableComposable = content
                        },
                        onDrag = { change, dragAmount ->
                            change.consume()
                            currentState.dragOffset += Offset(dragAmount.x, dragAmount.y)
                        },
                        onDragEnd = {
                            stopDragging()
                            currentState.isDragging = false
                            currentState.dragOffset = Offset.Zero
                        },
                        onDragCancel = {
                            stopDragging()
                            currentState.dragOffset = Offset.Zero
                            currentState.isDragging = false
                        }
                    )
                } else {
                    detectDragGesturesAfterLongPress(
                        onDragStart = {
                            startDragging()
                            currentState.dataToDrop = dataToDrop
                            currentState.isDragging = true
                            currentState.dragPosition = currentPosition + it
                            currentState.draggableComposable = content
                        },
                        onDrag = { change, dragAmount ->
                            change.consume()
                            currentState.dragOffset += Offset(dragAmount.x, dragAmount.y)
                        },
                        onDragEnd = {
                            stopDragging()
                            currentState.isDragging = false
                            currentState.dragOffset = Offset.Zero
                        },
                        onDragCancel = {
                            stopDragging()
                            currentState.dragOffset = Offset.Zero
                            currentState.isDragging = false
                        }
                    )
                }
            }
            .additionalModifier()
    ) {
        content()
    }
}

/**
 * A composable that defines a drop zone for draggable items.
 *
 * @param T The type of data to be dropped.
 * @param modifier Modifier to be applied to the Box.
 * @param content The content to be displayed inside the drop zone.
 */
@Suppress("UNCHECKED_CAST")
@Composable
fun <T> DropItemZone(
    modifier: Modifier = Modifier,
    content: @Composable (BoxScope.(isInBound: Boolean, data: T?) -> Unit)
) {
    val dragInfo = LocalDragTargetInfo.current
    val dragPosition = dragInfo.dragPosition
    val dragOffset = dragInfo.dragOffset
    var isCurrentDropTarget by remember { mutableStateOf(false) }

    Box(
        modifier = modifier.onGloballyPositioned {
            it.boundsInWindow().let { rect ->
                isCurrentDropTarget = rect.contains(dragPosition + dragOffset)
            }
        }
    ) {
        val data =
            if (isCurrentDropTarget && !dragInfo.isDragging) dragInfo.dataToDrop as T? else null
        content(isCurrentDropTarget, data)
    }
}

/**
 * A class that holds information about the current drag target.
 */
internal class DragTargetInfo {
    var isDragging: Boolean by mutableStateOf(false)
    var dragPosition by mutableStateOf(Offset.Zero)
    var dragOffset by mutableStateOf(Offset.Zero)
    var draggableComposable by mutableStateOf<(@Composable () -> Unit)?>(null)
    var dataToDrop by mutableStateOf<Any?>(null)
}