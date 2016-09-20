package com.octo.mob.octomeuh.settings.screen

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.Html
import android.view.MenuItem
import com.octo.mob.octomeuh.R
import com.octo.mob.octomeuh.settings.injection.SettingsComponent
import com.octo.mob.octomeuh.settings.model.RepetitionModeDescription
import com.octo.mob.octomeuh.settings.presenter.SettingsPresenter
import kotlinx.android.synthetic.main.activity_settings.*
import javax.inject.Inject

class SettingsActivity : AppCompatActivity(), SettingsScreen {

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

    override fun showRepetitionModeSelection(values: Array<RepetitionModeDescription>, selectionIndex: Int) {
        val builder = AlertDialog.Builder(this, R.style.AppTheme_Dialog)
        builder.setTitle(R.string.repetition_mode_selector_title)
        val map = values.map { element -> Html.fromHtml(resources.getString(element.descriptionRes)) }
        builder.setSingleChoiceItems(map.toTypedArray(), selectionIndex) {
            dialog, which ->
            presenter.onRepetitionModeSelected(values[which])
            dialog.dismiss()
        }
        builder.setNegativeButton(android.R.string.cancel, { dialogInterface, i -> })
        builder.create().show()
    }

    override fun showDurationSelection() {
    }

    override fun showCurrentRepetitionMode(repetitionModeDescription: RepetitionModeDescription) {
        repetitionModeLabelTextView.setText(repetitionModeDescription.titleRes)
    }
}