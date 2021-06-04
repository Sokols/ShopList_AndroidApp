package pl.sokols.shoppinglist.data.database

import androidx.room.TypeConverter
import java.util.*

/**
 * Converters module created for correct management of dates.
 */
class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}