package com.octo.mob.octomeuh.countdown.screen

import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.os.SystemClock
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.*
import com.octo.mob.octomeuh.R
import com.octo.mob.octomeuh.countdown.injection.CountDownComponent
import com.octo.mob.octomeuh.countdown.model.CountDownValue
import com.octo.mob.octomeuh.countdown.presenter.CountDownPresenter
import com.octo.mob.octomeuh.countdown.presenter.UpsideDownPresenter
import com.octo.mob.octomeuh.countdown.utils.HumanlyReadableDurationsConverter
import com.octo.mob.octomeuh.settings.screen.SettingsActivity
import com.octo.mob.octomeuh.transversal.AnalyticsManager
import kotlinx.android.synthetic.main.activity_countdown.*
import org.jetbrains.anko.browse
import org.jetbrains.anko.email
import javax.inject.Inject

class CountDownActivity : AppCompatActivity(), CountDownScreen, RotatableScreen {

    // ---------------------------------
    // ATTRIBUTES
    // ---------------------------------

    private lateinit var orientationEventListener: UpsideDownEventListener
    lateinit var mediaPlayer: MediaPlayer;

    @Inject
    lateinit var upsideDownPresenter: UpsideDownPresenter

    @Inject
    lateinit var analyticsManager: AnalyticsManager

    @Inject
    lateinit var countDownPresenter: CountDownPresenter

    @Inject
    lateinit var humanDurationConverter: HumanlyReadableDurationsConverter

    // ---------------------------------
    // LIFECYCLE
    // ---------------------------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_countdown)

        setSupportActionBar(toolbar)

        CountDownComponent.init().inject(this)

        mediaPlayer = MediaPlayer.create(this, R.raw.cow_sound)
        orientationEventListener = UpsideDownEventListener(this, upsideDownPresenter)

        initOnClickListeners()
    }

    override fun onResume() {
        super.onResume()
        orientationEventListener.enable()
        countDownPresenter.attach(this)
        upsideDownPresenter.attach(this)
    }

    override fun onStop() {
        orientationEventListener.disable()
        countDownPresenter.detach()
        upsideDownPresenter.detach()
        super.onStop()
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val doneDrawable = ContextCompat.getDrawable(this, R.drawable.ic_done_black_24dp)
        countDownPresenter.getInitialCountDownValue()

        menu?.findItem(countDownPresenter.getInitialCountDownValue().menuResourceId)?.icon = doneDrawable
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.countdown, menu)

        val subMenu = menu?.findItem(R.id.action_edit_countdown_per_atendee)?.subMenu
        inflateSubmenuWithCountdownDurations(subMenu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.action_feedback -> {
                countDownPresenter.onActionFeedbackClicked()
                return true
            }
            R.id.action_settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> changeCountdownDurationIfPossible(item)
        }
    }

    // ---------------------------------
    // INTERFACE IMPLEM
    // ---------------------------------

    override fun setStopAvailability(isStopAvailable: Boolean) {
        stopTimerButton.isEnabled = isStopAvailable
    }

    override fun setStartVisibility(startVisibility: Boolean) {
        startTimerButton.visibility = if (startVisibility) View.VISIBLE else View.GONE
    }

    override fun setNextAttendeeVisibility(nextAtendeeVisibility: Boolean) {
        nextSpeakerButton.visibility = if (nextAtendeeVisibility) View.VISIBLE else View.GONE
    }

    override fun setTimerValue(timerValueInSeconds: Int) {
        timerTextView.text = humanDurationConverter.getCompactReadableStringFromValueInSeconds(timerValueInSeconds)
    }

    override fun switchTimerToFinishedMode() {
        timerTextView.setTextColor(Color.RED)
    }

    override fun switchTimerToNormalMode() {
        timerTextView.setTextColor(ContextCompat.getColor(this, R.color.octo_blue_light))
    }

    override fun startChronometer() {
        elapsedTimeChronometer.base = SystemClock.elapsedRealtime()
        elapsedTimeChronometer.start()
    }

    override fun setSpeakerCounter(speakerCounter: Int) {
        totalSpeakersTextView.text = speakerCounter.toString()
    }

    override fun setFooterVisibility(footerVisibility: Boolean) {
        octoPoweredImageView.visibility = if (footerVisibility) View.INVISIBLE else View.VISIBLE
        detailFooterLayout.visibility = if (footerVisibility) View.VISIBLE else View.INVISIBLE
    }

    override fun setTimerVisibility(timerVisibility: Boolean) {
        logoImageView.visibility = if (timerVisibility) View.GONE else View.VISIBLE
        timerTextView.visibility = if (timerVisibility) View.VISIBLE else View.GONE
    }

    override fun notifyTimerFinished() {
        mediaPlayer.start()
    }

    override fun sendFeedbackEmailAction() {
        analyticsManager.logSendFeedback()
        email(getString(R.string.email_address_feedback_receiver), getString(R.string.feedback_default_subject))
    }

    override fun onUpsideDown() {
        mediaPlayer.start()
    }

    override fun keepAwake(shouldKeepAwake: Boolean) {
        val keepAwakeFlag = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON

        if (shouldKeepAwake) {
            window.addFlags(keepAwakeFlag)
        } else {
            window.clearFlags(keepAwakeFlag)
        }
    }

    // ---------------------------------
    // PRIVATE METHODS
    // ---------------------------------

    private fun initOnClickListeners() {
        startTimerButton.setOnClickListener { countDownPresenter.startTimer() }
        nextSpeakerButton.setOnClickListener { countDownPresenter.onNewSpeakerCountDown() }
        logoImageView.setOnClickListener (LogoClickListener())
        stopTimerButton.setOnClickListener { countDownPresenter.onCancelMeeting() }
        octoPoweredImageView.setOnClickListener {
            analyticsManager.logOctoPoweredClick()
            browse(getString(R.string.url_octo))
        }
    }

    private fun changeCountdownDurationIfPossible(item: MenuItem?): Boolean {
        val selectedValue = CountDownValue.values().find { it.menuResourceId == item?.itemId }

        selectedValue?.let {
            countDownPresenter.setInitialCountDownValue(selectedValue)
            invalidateOptionsMenu()
            true
        }

        return false
    }

    private fun inflateSubmenuWithCountdownDurations(subMenu: SubMenu?) {
        CountDownValue.values().forEach {
            subMenu?.add(Menu.NONE, it.menuResourceId, 0, humanDurationConverter.getReadableStringFromValueInSeconds(it.durationInSeconds))
        }
    }

    // ---------------------------------
    // PRIVATE INNER CLASS
    // ---------------------------------

    private inner class LogoClickListener : View.OnClickListener {

        val valueAnimator = ObjectAnimator.ofFloat(logoImageView, "rotation", 0f, 100f, 0f)

        init {
            valueAnimator.duration = 2000
        }

        override fun onClick(v: View?) {
            analyticsManager.logLogoClick()

            if (!valueAnimator.isRunning) {
                valueAnimator.start()
                mediaPlayer.start()
            }
        }

    }

    private class UpsideDownEventListener(context: Context, val upsideDownPresenter: UpsideDownPresenter) : OrientationEventListener(context) {

        override fun onOrientationChanged(orientation: Int) {
            upsideDownPresenter.onAngleChanged(orientation)
        }
    }
}

