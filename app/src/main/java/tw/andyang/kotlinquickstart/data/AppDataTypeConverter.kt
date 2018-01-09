package tw.andyang.kotlinquickstart.data

import android.arch.persistence.room.TypeConverter
import tw.andyang.kotlinquickstart.data.entity.TodoCategory
import tw.andyang.kotlinquickstart.extension.DateExtension.ISO8601
import tw.andyang.kotlinquickstart.extension.toDate
import tw.andyang.kotlinquickstart.extension.toDateString
import java.util.*

class AppDataTypeConverter {

    @TypeConverter
    fun toTodoCategory(todoCategory: String): TodoCategory =
            when (todoCategory) {
                TodoCategory.Family.value -> TodoCategory.Family
                TodoCategory.Friend.value -> TodoCategory.Friend
                TodoCategory.Other.value -> TodoCategory.Other
                else -> TodoCategory.Other
            }

    @TypeConverter
    fun toString(todoCategory: TodoCategory) : String = todoCategory.value

    @TypeConverter
    fun toString(date: Date): String = date.toDateString(ISO8601)

    @TypeConverter
    fun toDate(dateString: String): Date = dateString.toDate()
}