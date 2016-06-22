package com.octo.mob.octomeuh.countdown.presenter

import com.octo.mob.octomeuh.countdown.screen.RotatableScreen
import com.octo.mob.octomeuh.transversal.BasePresenterImpl

class UpsideDownPresenterImpl : UpsideDownPresenter, BasePresenterImpl<RotatableScreen>() {

    // ---------------------------------
    // CONSTANT
    // ---------------------------------

    companion object {
        private val ANGLE_DELTA_FOR_STABLE_POSITION = 10
    }

    // ---------------------------------
    // ATTRIBUTES
    // ---------------------------------

    private var screenState: LastState = LastState.UNDEFINED
    private var counter = 0

    // ---------------------------------
    // INTERFACE IMPLEMENTATION
    // ---------------------------------

    override fun onAngleChanged(angle: Int) {

        when (screenState) {
            LastState.UNDEFINED -> {
                checkIsInUpMode(angle)
                checkIsInDownMode(angle)
            }

            LastState.UP -> {
                checkIsInDownMode(angle)
            }

            LastState.DOWN -> {
                checkIsInUpMode(angle)
            }
        }

        notifyScreenIfNeeded()
    }

    // ---------------------------------
    // PRIVATE METHODS
    // ---------------------------------

    private fun notifyScreenIfNeeded() {
        if (counter == 3) {
            screen?.onUpsideDown()
            counter = 1
        }
    }

    private fun checkIsInDownMode(angle: Int) {
        if (isInDownMode(angle)) {
            screenState = LastState.DOWN
            counter++
        }
    }

    private fun checkIsInUpMode(angle: Int) {
        if (isInUpMode(angle)) {
            screenState = LastState.UP
            counter++
        }
    }

    private fun isInUpMode(angle: Int): Boolean {
        return (angle < ANGLE_DELTA_FOR_STABLE_POSITION || angle > 360 - ANGLE_DELTA_FOR_STABLE_POSITION)
    }

    private fun isInDownMode(angle: Int): Boolean {
        return (angle > 180 - ANGLE_DELTA_FOR_STABLE_POSITION && angle < 180 + ANGLE_DELTA_FOR_STABLE_POSITION)
    }

    // ---------------------------------
    // PRIVATE ENUM
    // ---------------------------------

    private enum class LastState { UNDEFINED, UP, DOWN }

}