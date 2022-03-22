package ashutosh.jharkhand.regional.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Set(
    val id: String,
    val categoryId: String,
    val topicId: String,
    val number: Int
): Parcelable
