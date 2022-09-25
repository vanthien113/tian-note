package com.vanthien113.tiannote.presentation.modules.home

import androidx.lifecycle.viewModelScope
import com.vanthien113.tiannote.domain.usecases.DeleteNotesUseCase
import com.vanthien113.tiannote.domain.usecases.GetNotesUseCase
import com.vanthien113.tiannote.presentation.base.BaseViewModel
import com.vanthien113.tiannote.presentation.base.ProcessState
import com.vanthien113.tiannote.presentation.mappers.NoteMapper
import com.vanthien113.tiannote.presentation.model.NoteModel
import com.vanthien113.tiannote.shared.utilities.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getNotesUseCase: GetNotesUseCase,
    private val deleteNotesUseCase: DeleteNotesUseCase,
    private val noteMapper: NoteMapper
) : BaseViewModel() {
    val getNoteObs = SingleLiveEvent<ProcessState<List<NoteModel>>>()
    val deleteNoteObs = SingleLiveEvent<ProcessState<NoteModel>>()

    fun getNotes() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                getNotesUseCase.invoke()
            }
                .onSuccess {
                    getNoteObs.postValue(ProcessState.success(noteMapper.transformList(it)))
                }
                .onFailure {
                    getNoteObs.postValue(ProcessState.error(it))
                }
        }
    }

    fun deleteNote(noteModel: NoteModel) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                deleteNotesUseCase.invoke(noteModel.id)
            }
                .onSuccess {
                    deleteNoteObs.postValue(ProcessState.success(noteModel))
                }
                .onFailure {
                    deleteNoteObs.postValue(ProcessState.error(it))
                }
        }
    }
}