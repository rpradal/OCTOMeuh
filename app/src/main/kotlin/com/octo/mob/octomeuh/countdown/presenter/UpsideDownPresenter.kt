package com.octo.mob.octomeuh.countdown.presenter

import com.octo.mob.octomeuh.countdown.screen.RotatableScreen
import com.octo.mob.octomeuh.transversal.BasePresenter

interface UpsideDownPresenter : BasePresenter<RotatableScreen> {

    fun onAngleChanged(angle : Int)

}

