package com.web.testtask.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Coord(
    val lon: Double,
    val lat: Double
): Parcelable