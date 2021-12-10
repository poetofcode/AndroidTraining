package com.example.composeexample02.model

sealed class ColorUIEvent(open val item: ColorItem) {
    class ClickEvent(override val item: ColorItem): ColorUIEvent(item)
    class ImageClickEvent(override val item: ColorItem): ColorUIEvent(item)
    class LikeEvent(override val item: ColorItem, val isFavorite: Boolean): ColorUIEvent(item)
}