package com.fadhil.finalsubmission.storage.pref

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys


class PreferenceDataSource {
    companion object {
        const val USER_TOKEN = "user_token"
        private var prefs: SharedPreferences? = null

        @Volatile
        private var instance: PreferenceDataSource? = null
        private val LOCK = Any()

        operator fun invoke(context: Context): PreferenceDataSource =
            instance ?: synchronized(LOCK) {
                instance ?: buildHelper(context).also {
                    instance = it

                }
            }

        private fun buildHelper(context: Context): PreferenceDataSource {
            val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
            val mainKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec)
            val sharePrefFile = "sharedPref"
            prefs = EncryptedSharedPreferences.create(
                sharePrefFile,
                mainKeyAlias,
                context,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
            return PreferenceDataSource()
        }

    }

    /**
     * Function to save auth token
     */
    fun saveAuthToken(token: String) {
        prefs?.edit(commit = true) {
            putString(USER_TOKEN, token)
        }
    }




    fun fetchAuthToken(): String? {
        return prefs?.getString(USER_TOKEN, "")
    }

    fun deleteDataAuth() {
        prefs?.edit(commit = true) {
            remove(USER_TOKEN)
        }
    }

}