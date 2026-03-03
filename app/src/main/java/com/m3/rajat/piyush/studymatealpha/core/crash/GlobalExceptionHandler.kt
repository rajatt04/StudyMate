package com.m3.rajat.piyush.studymatealpha.core.crash

import android.util.Log

/**
 * Global uncaught exception handler that logs the crash before
 * delegating to the default handler. In production, this would
 * also log to a crash reporting service like Firebase Crashlytics.
 */
class GlobalExceptionHandler private constructor(
    private val defaultHandler: Thread.UncaughtExceptionHandler?
) : Thread.UncaughtExceptionHandler {

    override fun uncaughtException(thread: Thread, throwable: Throwable) {
        try {
            Log.e(TAG, "Uncaught exception in thread ${thread.name}", throwable)
            // TODO: Log to Crashlytics if integrated
            // FirebaseCrashlytics.getInstance().recordException(throwable)
        } catch (e: Exception) {
            // Prevent recursive crash
        } finally {
            defaultHandler?.uncaughtException(thread, throwable)
        }
    }

    companion object {
        private const val TAG = "GlobalExceptionHandler"

        fun install() {
            val defaultHandler = Thread.getDefaultUncaughtExceptionHandler()
            Thread.setDefaultUncaughtExceptionHandler(
                GlobalExceptionHandler(defaultHandler)
            )
        }
    }
}
