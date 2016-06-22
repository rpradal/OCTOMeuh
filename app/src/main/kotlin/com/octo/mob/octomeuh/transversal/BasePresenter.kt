package com.octo.mob.octomeuh.transversal

interface BasePresenter<Screen> {

    fun attach(screenToAttach: Screen?)

    fun detach()
}


open class BasePresenterImpl<Screen> : BasePresenter<Screen> {

    internal var screen: Screen? = null

    override fun attach(screenToAttach: Screen?) {
        screen = screenToAttach
    }

    override fun detach() {
        screen = null
    }

}
