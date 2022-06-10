package br.com.tcc.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.LruCache
import android.widget.ImageView
import androidx.core.graphics.drawable.RoundedBitmapDrawable
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import java.net.HttpURLConnection
import java.net.URL

object CarregaImagem {

    private var memoryCache: LruCache<String, Bitmap>

    init {

        val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()

        val cacheSize = maxMemory / 8

        memoryCache = object : LruCache<String, Bitmap>(cacheSize) {
            override fun sizeOf(key: String?, value: Bitmap?): Int {
                return value!!.byteCount / 1024
            }
        }

    }

    fun donwload(imagemView: ImageView, url: String, cornerScale: Int) {
        val imageKey = url

        var bitmap = getBitmapFromMemoryCache(imageKey)?.also {
            val rondedBitmap = getRoundedBitmap(imagemView, it, cornerScale)
            imagemView.setImageDrawable(rondedBitmap)
        } ?: kotlin.run {
            Thread {
                val conn = URL(url).openConnection() as HttpURLConnection

                try {
                    val responseCode = conn.responseCode

                    if (responseCode == 200) {
                        val inputStream = conn.inputStream
                        val bitmap = BitmapFactory.decodeStream(inputStream)

                        val roundedBitmap = getRoundedBitmap(imagemView, bitmap, cornerScale)
                        imagemView.setImageDrawable(roundedBitmap)

                        memoryCache.put(url, bitmap)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }.start()
        }
    }

    private fun getRoundedBitmap(
        imageView: ImageView,
        bitmap: Bitmap,
        cornerScale: Int,
    ): RoundedBitmapDrawable {
        val rbd = RoundedBitmapDrawableFactory.create(imageView.resources, bitmap)
        rbd.cornerRadius = (bitmap.width / cornerScale).toFloat()
        return rbd
    }

    private fun getBitmapFromMemoryCache(imageKey: String): Bitmap? {
        return memoryCache[imageKey]
    }
}