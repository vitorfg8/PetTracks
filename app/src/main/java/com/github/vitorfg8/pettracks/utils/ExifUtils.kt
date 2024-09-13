package com.github.vitorfg8.pettracks.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import androidx.exifinterface.media.ExifInterface
import java.io.IOException
import java.io.InputStream

class ExifUtils(private val context: Context) {

    fun getCorrectedBitmap(imageUri: Uri?): Bitmap? {
        if (imageUri == null) return null
        val bitmap = getBitmapFromUri(imageUri) ?: return null
        return rotateBitmapIfRequired(imageUri, bitmap)
    }

    private fun getBitmapFromUri(imageUri: Uri): Bitmap? {
        var inputStream: InputStream? = null
        return try {
            inputStream = context.contentResolver.openInputStream(imageUri)
            BitmapFactory.decodeStream(inputStream)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        } finally {
            inputStream?.close()
        }
    }

    private fun rotateBitmapIfRequired(imageUri: Uri, bitmap: Bitmap): Bitmap? {
        val rotationDegree = getExifRotationDegrees(imageUri) ?: return bitmap
        return if (rotationDegree != 0) {
            applyRotation(bitmap, rotationDegree)
        } else {
            bitmap
        }
    }

    private fun getExifRotationDegrees(imageUri: Uri): Int? {
        var inputStream: InputStream? = null
        return try {
            inputStream = context.contentResolver.openInputStream(imageUri)
            val exif = ExifInterface(inputStream ?: return null)
            when (exif.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL
            )) {
                ExifInterface.ORIENTATION_ROTATE_90 -> 90
                ExifInterface.ORIENTATION_ROTATE_180 -> 180
                ExifInterface.ORIENTATION_ROTATE_270 -> 270
                else -> 0
            }
        } catch (e: IOException) {
            e.printStackTrace()
            null
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
            null
        } finally {
            inputStream?.close()
        }
    }

    private fun applyRotation(bitmap: Bitmap, rotationDegree: Int): Bitmap? {
        val matrix = Matrix().apply { postRotate(rotationDegree.toFloat()) }
        return try {
            Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
