package com.vanthien113.tiannote.domain.usecases

import com.vanthien113.tiannote.data.entities.Note
import com.vanthien113.tiannote.data.repositories.localrepositories.NoteRepository
import javax.inject.Inject

class StoreNotesUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) {
    suspend fun invoke(title: String, content: String, colorHex: String) {
        return noteRepository.storeNote(
            Note(
                time = System.currentTimeMillis(),
                title = title,
                content = content,
                colorHex = colorHex
            )
        )
    }
}