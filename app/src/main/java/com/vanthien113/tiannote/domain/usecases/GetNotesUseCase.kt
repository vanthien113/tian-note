package com.vanthien113.tiannote.domain.usecases

import com.vanthien113.tiannote.data.entities.Note
import com.vanthien113.tiannote.data.repositories.localrepositories.NoteRepository
import javax.inject.Inject

class GetNotesUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) {
    suspend fun invoke(): List<Note> {
        return noteRepository.getNotes()
    }
}