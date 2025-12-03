package com.hyunjine.nio.d_day

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject

val dDayStartDate = stringPreferencesKey("dDayStartDate")

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class DDayLocalDataSourceImpl @Inject constructor(
    @param:ApplicationContext private val context: Context
): DDayLocalDataSource {
    override suspend fun getStartDate(): Flow<LocalDate> {
        return context.dataStore.data.map { preferences ->
            val dateStr = preferences[dDayStartDate] ?: LocalDate.now().toString()
            LocalDate.parse(dateStr)
        }
    }

    override suspend fun updateStartDate(date: LocalDate) {
        context.dataStore.updateData {
            it.toMutablePreferences().also { preferences ->
                preferences[dDayStartDate] = date.toString()
            }
        }
    }
}