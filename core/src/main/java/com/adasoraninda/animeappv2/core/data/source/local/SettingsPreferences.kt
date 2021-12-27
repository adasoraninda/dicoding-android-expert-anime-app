package com.adasoraninda.animeappv2.core.data.source.local

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.annotation.RequiresApi
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.securepreferences.SecurePreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsPreferences @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val preferences = initPreferences(context)

    fun setTheme(isDarkMode: Boolean) {
        preferences.edit()
            .putBoolean(KEY_THEME, isDarkMode)
            .apply()
    }

    fun getTheme(): Boolean {
        return preferences.getBoolean(KEY_THEME, false)
    }

    private fun initPreferences(context: Context): SharedPreferences {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            jetpackSecurity(context)
        } else {
            securePreferences(context)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun jetpackSecurity(context: Context): SharedPreferences {
        val spec = KeyGenParameterSpec.Builder(
            MasterKey.DEFAULT_MASTER_KEY_ALIAS,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setKeySize(MasterKey.DEFAULT_AES_GCM_MASTER_KEY_SIZE)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .build()

        val masterKey = MasterKey.Builder(context)
            .setKeyGenParameterSpec(spec)
            .build()

        return EncryptedSharedPreferences
            .create(
                context,
                FILE_NAME,
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
    }

    private fun securePreferences(context: Context): SharedPreferences {
        return SecurePreferences(context, PASSWORD, FILE_NAME)
    }

    companion object {
        private const val FILE_NAME = "theme_app"
        private const val PASSWORD = "anime_app_v2"

        private const val KEY_THEME = "THEME"
    }

}