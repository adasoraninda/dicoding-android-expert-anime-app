package com.adasoraninda.animeappv2.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.adasoraninda.animeappv2.core.data.source.local.enitty.AnimeEntity

@Database(entities = [AnimeEntity::class], version = 1, exportSchema = false)
abstract class AnimeDatabase : RoomDatabase() {
    abstract val animeDao: AnimeDao
}