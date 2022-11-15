package com.azamovhudstc.dictioronaryapp.utils

import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.core.content.ContextCompat
import com.azamovhudstc.dictioronaryapp.R
import com.azamovhudstc.dictioronaryapp.db.app.App

fun String.spannable(query: String,context: Context): SpannableString {
    val span = SpannableString(this)

    val color = ForegroundColorSpan(ContextCompat.getColor(context, R.color.myColoor))
    val startIndex = this.indexOf(query, 0, true)
    if (startIndex > -1) {
        span.setSpan(color, startIndex, startIndex + query.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
    return span
}

// hello
// ll