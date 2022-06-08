package br.com.tcc.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.ImageView
import androidx.core.graphics.drawable.RoundedBitmapDrawable
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import java.net.HttpURLConnection
import java.net.URL

object CarregaImagem {

    fun donwload(imagemView: ImageView, url: String, cornerScale: Int) {
        Thread {
            val conn = URL(url).openConnection() as HttpURLConnection

            try {
                val responseCode = conn.responseCode

                if (responseCode == 200) {
                    val inputStream = conn.inputStream
                    val bitmap = BitmapFactory.decodeStream(inputStream)

                    val roundedBitmap = getRoundedBitmap(imagemView, bitmap, cornerScale)
                    imagemView.setImageDrawable(roundedBitmap)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.start()
    }

    private fun getRoundedBitmap(imageView: ImageView, bitmap: Bitmap, cornerScale: Int) : RoundedBitmapDrawable{
        val rbd = RoundedBitmapDrawableFactory.create(imageView.resources, bitmap)
        rbd.cornerRadius = (bitmap.width / cornerScale).toFloat()
        return rbd
    }
}