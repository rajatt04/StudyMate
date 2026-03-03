package com.m3.rajat.piyush.studymatealpha.core.util

/**
 * A generic wrapper class for communicating state between layers.
 */
sealed class Resource<out T> {
    data class Success<T>(val data: T) : Resource<T>()
    data class Error(val message: String, val exception: Throwable? = null) : Resource<Nothing>()
    data object Loading : Resource<Nothing>()
}
