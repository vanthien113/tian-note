package com.vanthien113.tiannote.presentation.di

import com.vanthien113.tiannote.presentation.mappers.NoteMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MapperModule {
    @Provides
    @Singleton
    fun provideNoteMapper(): NoteMapper {
        return NoteMapper()
    }
}
