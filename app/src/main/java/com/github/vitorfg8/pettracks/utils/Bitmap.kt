package com.github.vitorfg8.pettracks.utils

import android.graphics.Bitmap
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.painter.Painter

@Composable
fun Bitmap?.asPainter(fallback: Painter = ColorPainter(Color.Transparent)): Painter {
    return this?.let { BitmapPainter(it.asImageBitmap()) } ?: fallback
}

/**
 *Resizes a bitmap, ensuring that neither its width nor height exceeds [maxDimension]
 * while maintaining the original aspect ratio.
 *
 * @param bitmap The bitmap to resize.
 * @param maxDimension The maximum allowed dimension (width or height) for the resized bitmap.
 * @return The resized bitmap.
 */
fun resizeBitmap(bitmap: Bitmap, maxDimension: Int = 512): Bitmap {
    val width = bitmap.width
    val height = bitmap.height

    if (width <= maxDimension && height <= maxDimension) {
        return bitmap
    }

    val scaleFactor = if (width > height) {
        maxDimension / width.toFloat()
    } else {
        maxDimension / height.toFloat()
    }
    val newWidth = (width * scaleFactor).toInt()
    val newHeight = (height * scaleFactor).toInt()

    return Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true)
}