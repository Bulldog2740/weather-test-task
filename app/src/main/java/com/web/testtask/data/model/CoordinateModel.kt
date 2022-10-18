package com.web.testtask.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CoordinateModel(
    val longitude: Double,
    val latitude: Double
): Parcelable