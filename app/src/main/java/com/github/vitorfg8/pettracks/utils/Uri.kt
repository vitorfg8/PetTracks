package com.github.vitorfg8.pettracks.utils

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri


fun Uri?.toBitmap(contentResolver: ContentResolver): Bitmap? {
    return this?.let { BitmapFactory.decodeStream(contentResolver.openInputStream(this)) }
}