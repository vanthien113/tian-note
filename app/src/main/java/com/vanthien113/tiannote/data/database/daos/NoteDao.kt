package com.vanthien113.tiannote.data.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.vanthien113.tiannote.data.entities.Note

@Dao
interface NoteDao {
    @Query("SELECT * FROM Note")
    suspend fun getNotes(): List<Note>

    @Insert
    suspend fun storeNote(note: Note)

    @Query("UPDATE Note SET title = :title, content = :content, colorHex = :hex WHERE id =:id ")
    suspend fun updateNote(id: Long, title: String, content: String, hex: String)

    @Query("DELETE FROM Note WHERE id =:noteId")
    suspend fun deleteNote(noteId: Long)
}