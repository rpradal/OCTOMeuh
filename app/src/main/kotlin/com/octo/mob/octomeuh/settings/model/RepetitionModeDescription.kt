package com.octo.mob.octomeuh.settings.model

import android.support.annotation.StringRes
import com.octo.mob.octomeuh.R
import com.octo.mob.octomeuh.countdown.model.RepetitionMode

enum class RepetitionModeDescription(val repetitionMode: RepetitionMode,
                                     @StringRes val descriptionRes: Int,
                                     @StringRes val titleRes: Int) {
    LOOP(RepetitionMode.LOOP, R.string.loop_mode_description, R.string.loop_mode_title),
    STEP_BY_STEP(RepetitionMode.STEP_BY_STEP, R.string.step_by_step_mode_description, R.string.step_by_step_mode_title)
}
