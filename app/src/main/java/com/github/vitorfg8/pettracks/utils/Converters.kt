package com.github.vitorfg8.pettracks.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream
import java.util.Date

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun fromBitmap(bitmap: Bitmap): ByteArray {
        val resizedBitmap = resizeBitmap(bitmap)
        val outputStream = ByteArrayOutputStream()
        resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream)
        return outputStream.toByteArray()
    }

    @TypeConverter
    fun toBitmap(byteArray: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }

    /**
     *Resizes a bitmap, ensuring that neither its width nor height exceeds [maxDimension]
     * while maintaining the original aspect ratio.
     *
     * @param bitmap The bitmap to resize.
     * @param maxDimension The maximum allowed dimension (width or height) for the resized bitmap.
     * @return The resized bitmap.
     */
    private fun resizeBitmap(bitmap: Bitmap, maxDimension: Int = 512): Bitmap {
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
}