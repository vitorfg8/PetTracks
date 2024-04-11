package com.gtihub.vitorfg8.pettracks.ui

import android.graphics.BitmapFactory

fun ByteArray.toBitmap() = BitmapFactory.decodeByteArray(this, 0, size)