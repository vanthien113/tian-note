package com.vanthien113.tiannote.domain.usecases

import com.vanthien113.tiannote.data.repositories.localrepositories.NoteRepository
import javax.inject.Inject

class DeleteNotesUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) {
    suspend fun invoke(noteId: Long) {
        return noteRepository.deleteNote(noteId)
    }
}