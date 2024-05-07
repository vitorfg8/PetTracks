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