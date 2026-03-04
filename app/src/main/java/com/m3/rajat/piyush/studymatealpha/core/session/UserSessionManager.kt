package com.m3.rajat.piyush.studymatealpha.core.session

import com.m3.rajat.piyush.studymatealpha.core.datastore.UserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject
import javax.inject.Singleton

data class UserSession(
    val isLoggedIn: Boolean = false,
    val userId: Int = -1,
    val role: String = "",
    val name: String = "",
    val email: String = ""
)

@Singleton
class UserSessionManager @Inject constructor(
    private val userPreferences: UserPreferences
) {

    val sessionFlow: Flow<UserSession> = combine(
        userPreferences.isLoggedIn,
        userPreferences.loggedInUserId,
        userPreferences.loggedInUserRole,
        userPreferences.loggedInUserName,
        userPreferences.loggedInUserEmail
    ) { isLoggedIn, userId, role, name, email ->
        UserSession(
            isLoggedIn = isLoggedIn,
            userId = userId,
            role = role ?: "",
            name = name ?: "",
            email = email ?: ""
        )
    }

    suspend fun login(userId: Int, role: String, name: String, email: String) {
        userPreferences.saveSession(userId, role, name, email)
    }

    suspend fun logout() {
        userPreferences.clearSession()
    }
}
