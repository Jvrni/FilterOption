package com.jvrni.filteroption.models

data class Item(
    val entity: FilterEntity,
    val isVisible: Boolean = true,
    val state: ItemState = ItemState.Idle
)