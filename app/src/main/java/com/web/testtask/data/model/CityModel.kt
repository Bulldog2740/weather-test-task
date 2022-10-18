package com.web.testtask.data.model

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "city_table")
data class CityModel(
    @PrimaryKey(autoGenerate = true)
    var primaryKey: Int,
    val id: Int,
    val name: String,
    val state: String,
    val country: String,
    @Embedded
val coord: CoordinateModel = CoordinateModel(0.0, 0.0)
) : Parcelable