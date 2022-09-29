package com.vanthien113.tiannote.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vanthien113.tiannote.data.database.daos.NoteDao
import com.vanthien113.tiannote.data.entities.Note

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {
    companion object {
        const val DATABASE_NAME = "note-database"
    }

    abstract fun appDao(): NoteDao
}