package com.octo.mob.octomeuh.settings.screen

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.octo.mob.octomeuh.R
import com.octo.mob.octomeuh.settings.injection.SettingsComponent
import com.octo.mob.octomeuh.settings.presenter.DurationPickerPresenter
import kotlinx.android.synthetic.main.dialogfragment_duration_selector.*
import javax.inject.Inject

class DurationPickerDialogFragment : AppCompatDialogFragment(), DurationPickerScreen {

    // ---------------------------------
    // ATTRIBUTES
    // ---------------------------------

    @Inject
    lateinit var durationPickerPresenter : DurationPickerPresenter

    var onDismissListener : OnDismissListener? = null

    // ---------------------------------
    // LIFECYCLE
    // ---------------------------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_Dialog)
        SettingsComponent.init().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.dialogfragment_duration_selector, container)
        dialog.setTitle(getString(R.string.duration_selector_title))
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        validateButton.setOnClickListener { durationPickerPresenter.onDurationSelected(circleDurationPickerView.getValue()) }
        dismissButton.setOnClickListener { dismiss() }
        durationPickerPresenter.attach(this)
    }


    override fun onStop() {
        durationPickerPresenter.detach()
        super.onStop()
    }

    // ---------------------------------
    // INTERFACE IMPLEM
    // ---------------------------------

    override fun setDuration(initialDuration: Int) {
        circleDurationPickerView.setValue(initialDuration.toFloat())
    }

    override fun closeView() {
        onDismissListener?.onDismiss()
        dismiss()
    }

    // ---------------------------------
    // INTERFACE
    // ---------------------------------

    interface OnDismissListener {
        fun onDismiss()
    }

}