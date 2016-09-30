package com.octo.mob.octomeuh.transversal

import android.os.Build
import android.text.Html
import android.text.Spanned

inline fun <T, R> withNullable(receiver: T?, block: T.() -> R): R? = receiver?.block()

fun String.toHtml(): Spanned {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    } else {
        Html.fromHtml(this)
    }
}
