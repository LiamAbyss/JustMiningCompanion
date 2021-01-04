package com.pauk.justminingcompanion.utils

import android.content.Context
import android.content.SharedPreferences
import com.pauk.justminingcompanion.JustMiningApplication

object PreferenceUtils {
    private val sharedPreferences: SharedPreferences
        private get() = JustMiningApplication.getContext().getSharedPreferences(
            Constants.Preferences.SHARED_PREFERENCES_FILE_NAME,
            Context.MODE_PRIVATE
        )

    var key: String?
        get() {
            val prefs = sharedPreferences
            return prefs.getString(Constants.Preferences.PREF_KEY, null)
        }
        set(key) {
            val prefs = sharedPreferences
            prefs.edit().putString(Constants.Preferences.PREF_KEY, key).apply()
        }

}