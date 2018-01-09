package tw.andyang.kotlinquickstart.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import tw.andyang.kotlinquickstart.App
import tw.andyang.kotlinquickstart.data.entity.Todo
import tw.andyang.kotlinquickstart.data.entity.TodoDao

@Database(entities = arrayOf(Todo::class), version = 1, exportSchema = false)
@TypeConverters(AppDataTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
}

object AppDatabaseImpl {
    private const val DATABASE_NAME = "kotlin-quick-start"
    fun instance() = Room.databaseBuilder(App.instance(), AppDatabase::class.java, DATABASE_NAME).allowMainThreadQueries().build()
}