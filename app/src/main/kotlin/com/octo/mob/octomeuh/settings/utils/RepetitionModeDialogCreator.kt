package com.octo.mob.octomeuh.settings.utils

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.text.Html
import com.octo.mob.octomeuh.R
import com.octo.mob.octomeuh.settings.model.RepetitionModeDescription
import com.octo.mob.octomeuh.settings.presenter.SettingsPresenter

interface RepetitionModeDialogCreator {
    fun getRepetitionModeDialog(values: Array<RepetitionModeDescription>,
                                selectionIndex: Int,
                                context: Context): Dialog
}

class RepetitionModeDialogCreatorImpl(val settingsPresenter: SettingsPresenter) : RepetitionModeDialogCreator {

    override fun getRepetitionModeDialog(values: Array<RepetitionModeDescription>,
                                         selectionIndex: Int,
                                         context: Context): Dialog {

        val builder = AlertDialog.Builder(context, R.style.AppTheme_Dialog)
        builder.setTitle(R.string.repetition_mode_selector_title)
        val map = values.map { element -> Html.fromHtml(context.resources.getString(element.descriptionRes)) }
        builder.setSingleChoiceItems(map.toTypedArray(), selectionIndex, DurationChoiceListener(values))
        builder.setNegativeButton(android.R.string.cancel, { dialogInterface, i -> })
        return builder.create()
    }

    inner class DurationChoiceListener(val values: Array<RepetitionModeDescription>) : DialogInterface.OnClickListener {
        override fun onClick(dialog: DialogInterface?, position: Int) {
            settingsPresenter.onRepetitionModeSelected(values[position])
            dialog?.dismiss()
        }
    }

}