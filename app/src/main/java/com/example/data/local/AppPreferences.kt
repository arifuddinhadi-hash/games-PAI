package com.example.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "baligh_prefs")

class AppPreferences(private val context: Context) {
    private object Keys {
        val ACTIVE_STUDENT_ID = stringPreferencesKey("active_student_id")
        val IS_MUTED = booleanPreferencesKey("is_muted")
        val TEXT_SCALE = floatPreferencesKey("text_scale")
    }

    val activeStudentId: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[Keys.ACTIVE_STUDENT_ID]
    }

    val isMuted: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[Keys.IS_MUTED] ?: false
    }

    val textScale: Flow<Float> = context.dataStore.data.map { prefs ->
        prefs[Keys.TEXT_SCALE] ?: 1.0f
    }

    suspend fun setActiveStudentId(studentId: String?) {
        context.dataStore.edit { prefs ->
            if (studentId == null) {
                prefs.remove(Keys.ACTIVE_STUDENT_ID)
            } else {
                prefs[Keys.ACTIVE_STUDENT_ID] = studentId
            }
        }
    }

    suspend fun setMuted(muted: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[Keys.IS_MUTED] = muted
        }
    }

    suspend fun setTextScale(scale: Float) {
        context.dataStore.edit { prefs ->
            prefs[Keys.TEXT_SCALE] = scale.coerceIn(0.9f, 1.35f)
        }
    }
}
