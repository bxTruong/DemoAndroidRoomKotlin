package com.truongbx.demoandroidroomkotlin

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Book::class], version = 1, exportSchema = true)
abstract class BookDatabase : RoomDatabase() {
    // để đọc một file có sẵn data: sửa exportSchema=true, còn lưu bình thường để false, các trường dữ liệu trong file phải có notnull vì android room mặc định đã có notnull
    abstract fun userDAO(): BookDAO

    companion object {
        @Volatile
        private var INSTANCE: BookDatabase? = null

        fun getDatabase(context: Context): BookDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BookDatabase::class.java,
                    "book.db"
                ).createFromAsset("book_available.db")
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}