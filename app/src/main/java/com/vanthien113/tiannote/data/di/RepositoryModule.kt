package com.vanthien113.tiannote.data.di

import com.vanthien113.tiannote.data.repositories.localrepositories.NoteRepository
import com.vanthien113.tiannote.data.repositories.localrepositories.NoteRepositoryImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindNoteRepository(
        noteRepositoryImp: NoteRepositoryImp
    ): NoteRepository
}
