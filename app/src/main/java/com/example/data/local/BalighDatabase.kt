package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [
        StudentSessionEntity::class,
        StudentAnswerEntity::class,
        FormativeResultEntity::class,
        SummativeResultEntity::class,
        ActivityLogEntity::class,
        ReflectionEntity::class,
        TeacherSettingsEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class BalighDatabase : RoomDatabase() {
    abstract fun balighDao(): BalighDao

    companion object {
        @Volatile
        private var INSTANCE: BalighDatabase? = null

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE teacher_settings ADD COLUMN offlineOnlyMode INTEGER NOT NULL DEFAULT 0")
            }
        }

        fun getInstance(context: Context): BalighDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BalighDatabase::class.java,
                    "petualangan_baligh_db"
                )
                    .addMigrations(MIGRATION_1_2)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
