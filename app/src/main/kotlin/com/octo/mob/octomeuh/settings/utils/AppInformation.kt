package com.octo.mob.octomeuh.settings.utils

import com.octo.mob.octomeuh.BuildConfig

interface AppInformation {
    fun getAppVersionLabel(): String
}

class AppInformationImpl : AppInformation {

    override fun getAppVersionLabel(): String {
        return BuildConfig.VERSION_NAME
    }
}