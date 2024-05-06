package com.github.vitorfg8.pettracks.utils

import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.painter.Painter

fun ByteArray.toPainter(fallback: Painter = ColorPainter(Color.Transparent)): Painter {
    val imageBitmap = this.toImageBitmap()
    return if (imageBitmap != null) {
        BitmapPainter(imageBitmap)
    } else {
        fallback
    }
}

private fun ByteArray?.toImageBitmap(): ImageBitmap? {
    if (this == null) {
        return null
    }
    return try {
        BitmapFactory.decodeByteArray(this, 0, size).asImageBitmap()
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}