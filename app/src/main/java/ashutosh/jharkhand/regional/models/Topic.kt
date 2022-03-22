package ashutosh.jharkhand.regional.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Topic(
    val id: String,
    val categoryId: String,
    val topicName: String,
    val date: Long
): Parcelable
