package com.a4ary4n.calendarpoc.utils

import android.content.Context
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.Locale

object AppUtil {
    fun Context.toast(message: String, prevToast: Toast? = null): Toast {
        prevToast?.cancel()
        val newToast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        newToast.show()
        return newToast
    }

    val y4_M2_d2_hh_mm_ss: SimpleDateFormat
        get() = dateFormat("yyyy-MM-dd HH:mm:ss")

    val y4M2d2H2m2s2: SimpleDateFormat
        get() = dateFormat("yyyyMMddHHmmss")

    private fun dateFormat(format: String): SimpleDateFormat {
        return SimpleDateFormat(format, Locale.ENGLISH)
    }
}