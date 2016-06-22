package com.octo.mob.octomeuh.countdown.model

import android.support.annotation.IdRes
import com.octo.mob.octomeuh.R

enum class CountDownValue(val durationInSeconds: Int, @IdRes val menuResourceId: Int) {

    THIRTY_SECONDS(             30, R.id.menu_countdown_30secs),
    FORTYFIVE_SECONDS(          45, R.id.menu_countdown_45secs),
    ONE_MINUTE(                 60, R.id.menu_countdown_1min),
    ONE_MINUTE_THIRTY_SECONDS(  90, R.id.menu_countdown_1min30secs),
    TWO_MINUTES(               120, R.id.menu_countdown_2min);

}