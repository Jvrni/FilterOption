package com.jvrni.filteroption.view.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.jvrni.filteroption.R
import com.jvrni.filteroption.models.FilterEntity
import com.jvrni.filteroption.models.Item
import com.jvrni.filteroption.models.ItemState

@Composable
fun FilterOption(
    modifier: Modifier = Modifier,
    borderColor: Color,
    borderColorSelected: Color,
    backgroundColor: Color,
    backgroundColorSelected: Color,
    textColor: Color,
    textColorSelected: Color,
    onClearFilter: () -> Unit,
    onSelectFilter: (FilterEntity) -> Unit,
    filtersItems: List<FilterEntity>
) {
    val list = remember { mutableStateOf(filtersItems.map { Item(it) }) }
    val isSelected = list.value.firstOrNull { it.state == ItemState.Selected } != null

    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(10.dp, 0.dp),
        horizontalArrangement = Arrangement.spacedBy(if (isSelected) 0.dp else 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        itemsIndexed(items = list.value) { index, item ->

            if (index == 0) {
                AnimatedVisibility(
                    visible = isSelected,
                    enter = fadeIn(
                        animationSpec = TweenSpec(800)
                    ) + slideInHorizontally(
                        animationSpec = TweenSpec(400, delay = 400),
                        initialOffsetX = { -it }
                    ) + expandHorizontally(
                        animationSpec = TweenSpec(400),
                        expandFrom = Alignment.CenterHorizontally
                    ),
                    exit = fadeOut(
                        animationSpec = TweenSpec(800)
                    ) + slideOutHorizontally(
                        animationSpec = TweenSpec(500)
                    ) + shrinkHorizontally(
                        animationSpec = TweenSpec(800)
                    )
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ClearFilterButton(
                            modifier = Modifier.size(40.dp),
                            borderColor = borderColor,
                            iconColor = textColor,
                            colors = ButtonDefaults.outlinedButtonColors(backgroundColor = backgroundColor),
                            action = {
                                list.value = filtersItems.map { Item(it) }
                                onClearFilter.invoke()
                            }
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                    }
                }
            }

            AnimatedVisibility(
                modifier = Modifier.clip(CircleShape),
                visible = item.isVisible,
                enter = fadeIn(
                    animationSpec = TweenSpec(600, delay = 400)
                ) + expandHorizontally(
                    animationSpec = TweenSpec(500),
                    expandFrom = Alignment.Start
                ),
                exit = fadeOut(
                    animationSpec = TweenSpec(200)
                ) + shrinkHorizontally(
                    animationSpec = TweenSpec(800),
                    shrinkTowards = Alignment.Start
                )
            ) {
                Row(
                    modifier = Modifier.clip(CircleShape),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    when (item.state) {
                        ItemState.Idle -> {
                            FilterOutlinedButton(
                                modifier = modifier,
                                borderColor = borderColor,
                                colors = ButtonDefaults.outlinedButtonColors(backgroundColor = backgroundColor),
                                onClick = {
                                    onSelectFilter.invoke(item.entity)
                                    list.value = list.value.map {
                                        if (it.entity.id == item.entity.id) Item(
                                            item.entity,
                                            isVisible = true,
                                            state = ItemState.Selected
                                        )
                                        else Item(
                                            it.entity,
                                            isVisible = false,
                                            state = ItemState.Idle
                                        )
                                    }
                                }
                            ) {
                                Text(
                                    text = item.entity.value,
                                    color = textColor,
                                )
                            }
                        }
                        ItemState.Selected -> {
                            FilterOutlinedButton(
                                modifier = modifier,
                                borderColor = borderColorSelected,
                                colors = ButtonDefaults.outlinedButtonColors(backgroundColor = backgroundColorSelected),
                                onClick = {}
                            ) {
                                Text(
                                    text = item.entity.value,
                                    color = textColorSelected,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FilterOutlinedButton(
    modifier: Modifier = Modifier,
    borderColor: Color,
    colors: ButtonColors,
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit
) {
    OutlinedButton(
        modifier = modifier.height(46.dp),
        border = BorderStroke(2.dp, borderColor),
        colors = colors,
        shape = CircleShape,
        onClick = onClick,
        content = content
    )
}

@Composable
private fun ClearFilterButton(
    modifier: Modifier,
    borderColor: Color,
    iconColor: Color,
    colors: ButtonColors,
    action: () -> Unit
) {
    Button(
        modifier = modifier,
        onClick = { action.invoke() },
        shape = CircleShape,
        colors = colors,
        border = BorderStroke(2.dp, color = borderColor),
        contentPadding = PaddingValues(10.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_close),
            tint = iconColor,
            contentDescription = null
        )
    }
}
