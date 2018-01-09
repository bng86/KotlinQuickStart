package tw.andyang.kotlinquickstart.data.entity

import android.arch.persistence.room.*
import io.reactivex.Flowable
import java.util.*

@Entity(tableName = "todo")
data class Todo(
        @ColumnInfo(name = "message")
        val message: String,
        @ColumnInfo(name = "todo_category")
        val todoCategory: TodoCategory = TodoCategory.Other,
        @ColumnInfo(name = "created")
        var created: Date = Date(),
        @PrimaryKey()
        val id: String = UUID.randomUUID().toString()
)

sealed class TodoCategory(val value: String, val display: String) {
    object Family : TodoCategory("family", "家人")
    object Friend : TodoCategory("friend", "朋友")
    object Other : TodoCategory("other", "其他")
}

@Dao
abstract class TodoDao {
    @Query("SELECT * FROM todo")
    abstract fun findAllTodo(): Flowable<List<Todo>>

    @Insert
    abstract fun insertTodo(todo: Todo)

    @Delete
    abstract fun deleteTodo(todo: Todo)
}