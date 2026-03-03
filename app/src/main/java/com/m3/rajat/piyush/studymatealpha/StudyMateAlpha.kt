package com.m3.rajat.piyush.studymatealpha

import android.app.Application
import com.google.android.material.color.DynamicColors
import com.m3.rajat.piyush.studymatealpha.core.crash.GlobalExceptionHandler
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class StudyMateAlpha : Application() {
    override fun onCreate() {
        super.onCreate()
        GlobalExceptionHandler.install()
        DynamicColors.applyToActivitiesIfAvailable(this)
    }
}
