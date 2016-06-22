package com.octo.mob.octomeuh.transversal

import android.os.Handler

fun postDelayed(delayedCall : () -> Unit, delayInMilli : Long) {
    val handler = Handler()
    handler.postDelayed(delayedCall, delayInMilli)
}

inline fun <T, R> withNullable(receiver: T?, block: T.() -> R): R? =  receiver?.block()
