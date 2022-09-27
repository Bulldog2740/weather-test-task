package com.web.testtask.data.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Images(
    var original: Original? = null,
    @SerializedName("preview_gif")
    var previewGif: PreviewGif? = null,
): Parcelable