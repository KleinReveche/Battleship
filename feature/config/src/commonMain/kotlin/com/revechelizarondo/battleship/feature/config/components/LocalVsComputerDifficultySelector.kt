package com.revechelizarondo.battleship.feature.config.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.revechelizarondo.battleship.core.domain.models.ComputerDifficulty
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

private val cardSpacing = 8.dp
private val cardPadding = 20.dp
private val horizontalPagerContentPadding = 75.dp

@Composable
fun LocalVsComputerDifficultySelector(
    difficultyCount: Int,
    scope: CoroutineScope,
    pagerState: PagerState
) {
    HorizontalPager(
        modifier = Modifier
            .fillMaxWidth()
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragEnd = {
                        scope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage)
                        }
                    }
                ) { change, dragAmount ->
                    change.consume()
                    scope.launch {
                        pagerState.scrollBy(-dragAmount.x)
                    }
                }
            },
        state = pagerState,
        pageSpacing = cardSpacing,
        verticalAlignment = Alignment.CenterVertically,
        contentPadding = PaddingValues(horizontal = horizontalPagerContentPadding)
    ) { page ->
        val pageOffset =
            ((pagerState.currentPage - (page)) + pagerState.currentPageOffsetFraction).absoluteValue

        OutlinedCard(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
                .graphicsLayer {
                    alpha = lerp(
                        start = 0.5f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )
                }
        ) {
            Column(
                modifier = Modifier.fillMaxSize().padding(cardPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = ComputerDifficulty.entries[page].name
                )
            }
        }
    }

    HorizontalPagerIndicator(
        pageCount = difficultyCount,
        currentPage = pagerState.currentPage,
        targetPage = pagerState.targetPage,
        currentPageOffsetFraction = pagerState.currentPageOffsetFraction,
        scope = scope,
        pagerState = pagerState
    )
}

@Composable
private fun HorizontalPagerIndicator(
    pageCount: Int,
    currentPage: Int,
    targetPage: Int,
    currentPageOffsetFraction: Float,
    modifier: Modifier = Modifier,
    indicatorColor: Color = Color.DarkGray,
    unselectedIndicatorSize: Dp = 16.dp,
    selectedIndicatorSize: Dp = 20.dp,
    indicatorCornerRadius: Dp = 4.dp,
    indicatorPadding: Dp = 4.dp,
    scope: CoroutineScope,
    pagerState: PagerState
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .wrapContentSize()
            .height(selectedIndicatorSize + indicatorPadding * 2)
            .fillMaxWidth()
    ) {

        // draw an indicator for each page
        repeat(pageCount) { page ->
            // calculate color and size of the indicator
            val (color, size) =
                if (currentPage == page || targetPage == page) {
                    // calculate page offset
                    val pageOffset =
                        ((currentPage - page) + currentPageOffsetFraction).absoluteValue
                    // calculate offset percentage between 0.0 and 1.0
                    val offsetPercentage = 1f - pageOffset.coerceIn(0f, 1f)

                    val size =
                        unselectedIndicatorSize + ((selectedIndicatorSize - unselectedIndicatorSize) * offsetPercentage)

                    indicatorColor.copy(
                        alpha = offsetPercentage
                    ) to size
                } else {
                    indicatorColor.copy(alpha = 0.1f) to unselectedIndicatorSize
                }

            // draw indicator
            Box(
                modifier = Modifier
                    .padding(
                        // apply horizontal padding, so that each indicator is same width
                        horizontal = ((selectedIndicatorSize + indicatorPadding * 2) - size) / 2,
                        vertical = size / 4
                    )
                    .clip(RoundedCornerShape(indicatorCornerRadius))
                    .background(color)
                    .width(size)
                    .height(size / 2)
                    .clickable { scope.launch { pagerState.animateScrollToPage(page) } }
            )
        }
    }
}
