package com.adasoraninda.animeappv2.core.data.source.local.enitty

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "anime",indices = [Index(value = ["mal_id"], unique = true)])
data class AnimeEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "mal_id")
    val malId: Int,

    @ColumnInfo(name = "title")
    val title: String? = null,

    @ColumnInfo(name = "score")
    val score: Double? = null,

    @ColumnInfo(name = "image_url")
    val imageUrl: String? = null,

    @ColumnInfo(name = "members")
    val members: Int? = null,

    @ColumnInfo(name = "type")
    val type: String? = null,

    @ColumnInfo(name = "episodes")
    val episodes: Int? = null,

    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean = false,
)