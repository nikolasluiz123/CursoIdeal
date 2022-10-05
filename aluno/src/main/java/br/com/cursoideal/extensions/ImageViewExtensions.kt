package br.com.cursoideal.extensions

import android.graphics.Bitmap
import android.widget.ImageView
import androidx.core.graphics.drawable.toBitmap
import java.io.ByteArrayOutputStream

fun ImageView.toByteArray() : ByteArray {
    val bitmap = this.drawable.toBitmap()
    val stream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)

    return stream.toByteArray()
}