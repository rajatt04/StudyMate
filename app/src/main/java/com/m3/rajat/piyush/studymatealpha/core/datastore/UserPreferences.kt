package com.m3.rajat.piyush.studymatealpha.core.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "studymate_preferences")

@Singleton
class UserPreferences @Inject constructor(
    @ApplicationContext private val context: Context
) {

    // Keys
    private object PreferenceKeys {
        val SELECTED_ROLE = stringPreferencesKey("selected_role")
        val REMEMBER_ME_EMAIL = stringPreferencesKey("remember_me_email")
        val REMEMBER_ME_ENABLED = booleanPreferencesKey("remember_me_enabled")
        val LOGGED_IN_USER_ID = intPreferencesKey("logged_in_user_id")
        val LOGGED_IN_USER_ROLE = stringPreferencesKey("logged_in_user_role")
        val LOGGED_IN_USER_NAME = stringPreferencesKey("logged_in_user_name")
        val LOGGED_IN_USER_EMAIL = stringPreferencesKey("logged_in_user_email")
        val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
        val DARK_MODE = booleanPreferencesKey("dark_mode")
    }

    // Selected Role
    val selectedRole: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[PreferenceKeys.SELECTED_ROLE]
    }

    suspend fun saveSelectedRole(role: String) {
        context.dataStore.edit { prefs ->
            prefs[PreferenceKeys.SELECTED_ROLE] = role
        }
    }

    // Remember Me
    val rememberMeEmail: Flow<String?> = context.dataStore.data.map { prefs ->
        if (prefs[PreferenceKeys.REMEMBER_ME_ENABLED] == true) {
            prefs[PreferenceKeys.REMEMBER_ME_EMAIL]
        } else null
    }

    val isRememberMeEnabled: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[PreferenceKeys.REMEMBER_ME_ENABLED] ?: false
    }

    suspend fun saveRememberMe(email: String, enabled: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[PreferenceKeys.REMEMBER_ME_ENABLED] = enabled
            if (enabled) {
                prefs[PreferenceKeys.REMEMBER_ME_EMAIL] = email
            } else {
                prefs.remove(PreferenceKeys.REMEMBER_ME_EMAIL)
            }
        }
    }

    // Session
    val isLoggedIn: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[PreferenceKeys.IS_LOGGED_IN] ?: false
    }

    val loggedInUserId: Flow<Int> = context.dataStore.data.map { prefs ->
        prefs[PreferenceKeys.LOGGED_IN_USER_ID] ?: -1
    }

    val loggedInUserRole: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[PreferenceKeys.LOGGED_IN_USER_ROLE]
    }

    val loggedInUserName: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[PreferenceKeys.LOGGED_IN_USER_NAME]
    }

    val loggedInUserEmail: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[PreferenceKeys.LOGGED_IN_USER_EMAIL]
    }

    suspend fun saveSession(userId: Int, role: String, name: String, email: String) {
        context.dataStore.edit { prefs ->
            prefs[PreferenceKeys.IS_LOGGED_IN] = true
            prefs[PreferenceKeys.LOGGED_IN_USER_ID] = userId
            prefs[PreferenceKeys.LOGGED_IN_USER_ROLE] = role
            prefs[PreferenceKeys.LOGGED_IN_USER_NAME] = name
            prefs[PreferenceKeys.LOGGED_IN_USER_EMAIL] = email
        }
    }

    suspend fun clearSession() {
        context.dataStore.edit { prefs ->
            prefs[PreferenceKeys.IS_LOGGED_IN] = false
            prefs.remove(PreferenceKeys.LOGGED_IN_USER_ID)
            prefs.remove(PreferenceKeys.LOGGED_IN_USER_ROLE)
            prefs.remove(PreferenceKeys.LOGGED_IN_USER_NAME)
            prefs.remove(PreferenceKeys.LOGGED_IN_USER_EMAIL)
        }
    }

    // Dark Mode
    val isDarkMode: Flow<Boolean?> = context.dataStore.data.map { prefs ->
        prefs[PreferenceKeys.DARK_MODE]
    }

    suspend fun setDarkMode(enabled: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[PreferenceKeys.DARK_MODE] = enabled
        }
    }
}
