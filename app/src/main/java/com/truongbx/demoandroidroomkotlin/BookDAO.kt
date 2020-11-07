package com.truongbx.demoandroidroomkotlin

import androidx.room.*

@Dao
interface BookDAO {
    @Query("SELECT * from bookData")
    suspend fun getAllBook(): List<Book>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: Book)

    @Update
    suspend fun updateBook(book: Book)

    @Delete
    suspend fun deleteBook(book: Book)
}