package com.octo.mob.octomeuh.countdown.screen

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import com.octo.mob.octomeuh.R
import com.octo.mob.octomeuh.countdown.injection.CountDownComponent
import com.octo.mob.octomeuh.countdown.presenter.RepetitionModeSelectorPresenter
import com.octo.mob.octomeuh.transversal.postDelayed
import javax.inject.Inject

class RepetitionModeDialogFragment() : AppCompatDialogFragment(), RepetitionModeSelectorScreen {

    // ---------------------------------
    // ATTRIBUTES
    // ---------------------------------

    @Inject
    lateinit var repetitionModeSelectorPresenter : RepetitionModeSelectorPresenter

    var loopModeRadioButton : RadioButton? = null
    var stepByStepRadioButton : RadioButton? = null

    // ---------------------------------
    // LIFECYCLE
    // ---------------------------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_Dialog)
        CountDownComponent.init().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.dialogfragment_loop_mode_selector, container);

        dialog.setTitle(getString(R.string.repetition_mode_selector_title));

        bindLoopModeViews(view)
        bindStepByStepViews(view)

        return view;
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        repetitionModeSelectorPresenter.attach(this)
    }

    override fun onStop() {
        super.onStop()
        repetitionModeSelectorPresenter.detach()
    }

    // ---------------------------------
    // INTERFACE IMPLEMENTATION
    // ---------------------------------

    override fun setLoopModeSelected(isSelected: Boolean) {
        loopModeRadioButton?.isChecked = isSelected
    }

    override fun setStepByStepSelected(isSelected: Boolean) {
        stepByStepRadioButton?.isChecked = isSelected
    }

    override fun dismissScreen() {
        postDelayed({ dismiss() }, 500)
    }

    // ---------------------------------
    // PRIVATE METHODS
    // ---------------------------------

    private fun bindStepByStepViews(view: View?) {
        val stepByStepHitBox = view?.findViewById(R.id.stepByStepHitBox)
        stepByStepRadioButton = view?.findViewById(R.id.stepByStepRadioButton) as RadioButton
        stepByStepHitBox?.setOnClickListener {
            repetitionModeSelectorPresenter.onStepByStepSelected()
        }
    }

    private fun bindLoopModeViews(view: View?) {
        val loopModeHitBox = view?.findViewById(R.id.loopModeHitBox)
        loopModeRadioButton = view?.findViewById(R.id.loopModeRadioButton) as RadioButton
        loopModeHitBox?.setOnClickListener {
            repetitionModeSelectorPresenter.onLoopModeSelected()
        }
    }
}

