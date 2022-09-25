package com.vanthien113.tiannote.presentation.mappers

import com.vanthien113.tiannote.data.entities.Note
import com.vanthien113.tiannote.presentation.base.BaseMapper
import com.vanthien113.tiannote.presentation.model.NoteModel

class NoteMapper : BaseMapper<Note, NoteModel>() {
    override fun transform(entity: Note): NoteModel {
        return NoteModel(
            id = entity.id,
            time = entity.time,
            title = entity.title,
            content = entity.content,
            colorHex = entity.colorHex
        )
    }
}