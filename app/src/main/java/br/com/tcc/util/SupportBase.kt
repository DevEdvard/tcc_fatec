package br.com.tcc.util

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.Point
import android.os.Handler
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.google.gson.Gson
import org.json.JSONObject
import java.io.*
import java.text.NumberFormat
import java.util.HashMap
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import java.util.zip.ZipOutputStream


@SuppressLint("StaticFieldLeak")
internal object SupportBase {

    var Context: android.content.Context? = null

    /**
     * Obtem width/height total da Activity
     * @param activity
     * @return
     */
    fun getXYActivity(activity: Activity): HashMap<String, Int> {
        val hashMap = HashMap<String, Int>()
        val display = activity.windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        val width = size.x
        val height = size.y

        hashMap["width"] = width
        hashMap["height"] = height

        return hashMap
    }
}
