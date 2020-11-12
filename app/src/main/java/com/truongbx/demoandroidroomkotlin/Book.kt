package com.truongbx.demoandroidroomkotlin

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import java.io.Serializable

@Entity(tableName = "bookData")
data class Book(
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "category") var category: String,
    @ColumnInfo(name = "image") val image: Int
) : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}