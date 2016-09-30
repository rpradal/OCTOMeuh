package com.octo.mob.octomeuh.transversal

inline fun <T, R> withNullable(receiver: T?, block: T.() -> R): R? =  receiver?.block()
