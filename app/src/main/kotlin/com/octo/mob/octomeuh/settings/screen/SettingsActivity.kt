package com.octo.mob.octomeuh.settings.screen

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.octo.mob.octomeuh.R
import com.octo.mob.octomeuh.settings.injection.SettingsComponent
import com.octo.mob.octomeuh.settings.model.RepetitionModeDescription
import com.octo.mob.octomeuh.settings.presenter.SettingsPresenter
import com.octo.mob.octomeuh.settings.utils.RepetitionModeDialogCreator
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject

class SettingsActivity : AppCompatActivity(), SettingsScreen {

    @Inject
    lateinit var repetitionModeDialogCreator : RepetitionModeDialogCreator

    @Inject
    lateinit var presenter: SettingsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        initToolbar()

        SettingsComponent.init().inject(this)

        durationCellLayout.setOnClickListener { presenter.onDurationChangeRequest() }
        repetitionModeCellLayout.setOnClickListener { presenter.onRepetitionModeChangeRequest() }
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.settings)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onResume() {
        super.onResume()
        presenter.attach(this)
    }

    override fun onPause() {
        super.onPause()
        presenter.detach()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun showVersionNumber(appVersionLabel: String) {
        versionNumberTextView.text = appVersionLabel
    }

    override fun showRepetitionModeSelection(values: Array<RepetitionModeDescription>, selectionIndex: Int) {
        val repetitionModeDialog = repetitionModeDialogCreator.getRepetitionModeDialog(values, selectionIndex, this)
        repetitionModeDialog.show()
    }

    override fun showDurationSelection() {
        val fragment = DurationPickerDialogFragment()
        fragment.onDismissListener = object : DurationPickerDialogFragment.OnDismissListener {
            override fun onDismiss() {
                presenter.onDurationChanged()
            }
        }
        fragment.show(supportFragmentManager, DurationPickerDialogFragment::class.java.simpleName)
    }

    override fun showCurrentRepetitionMode(repetitionModeDescription: RepetitionModeDescription) {
        repetitionModeLabelTextView.setText(repetitionModeDescription.titleRes)
    }

    override fun showCurrentDuration(initialDuration: String) {
        durationTextView.text = initialDuration
    }

}