package com.github.vitorfg8.pettracks.utils

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream


fun Uri?.toBitmap(contentResolver: ContentResolver): Bitmap? {
    return this?.let { BitmapFactory.decodeStream(contentResolver.openInputStream(this)) }
}
fun Uri?.toByteArray(context: Context): ByteArray? {
    if (this == null) {
        return null
    }

    var inputStream: InputStream? = null
    try {
        inputStream = context.contentResolver.openInputStream(this)
        if (inputStream != null) {
            val byteBuffer = ByteArrayOutputStream()
            val bufferSize = 1024
            val buffer = ByteArray(bufferSize)
            var len: Int
            while (inputStream.read(buffer).also { len = it } != -1) {
                byteBuffer.write(buffer, 0, len)
            }
            return byteBuffer.toByteArray()
        }
    } catch (e: IOException) {
        e.printStackTrace()
    } finally {
        try {
            inputStream?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    return null
}