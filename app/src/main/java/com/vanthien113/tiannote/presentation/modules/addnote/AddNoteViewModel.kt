package com.vanthien113.tiannote.presentation.modules.addnote

import androidx.lifecycle.viewModelScope
import com.vanthien113.tiannote.constants.Constants
import com.vanthien113.tiannote.domain.usecases.StoreNotesUseCase
import com.vanthien113.tiannote.domain.usecases.UpdateNotesUseCase
import com.vanthien113.tiannote.presentation.base.BaseViewModel
import com.vanthien113.tiannote.presentation.base.ProcessState
import com.vanthien113.tiannote.shared.utilities.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNoteViewModel @Inject constructor(
    private val storeNotesUseCase: StoreNotesUseCase,
    private val updateNotesUseCase: UpdateNotesUseCase,
) : BaseViewModel() {
    val validateErrorObs = SingleLiveEvent<InputError>()
    val storeNoteObs = SingleLiveEvent<ProcessState<Unit>>()
    var selectedColor: String = Constants.DEFAULT_COLOR

    enum class InputError { EMPTY_TITLE, EMPTY_CONTENT }

    fun validateInput(title: String, content: String): Boolean {
        return when {
            title.isEmpty() -> {
                validateErrorObs.postValue(InputError.EMPTY_TITLE)
                false
            }
            content.isEmpty() -> {
                validateErrorObs.postValue(InputError.EMPTY_CONTENT)
                false
            }
            else -> true
        }
    }

    fun storeNote(title: String, content: String) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                storeNotesUseCase.invoke(title, content, selectedColor)
            }
                .onSuccess {
                    storeNoteObs.postValue(ProcessState.success())
                }
                .onFailure {
                    storeNoteObs.postValue(ProcessState.error(it))
                }
        }
    }

    fun updateNote(id: Long, title: String, content: String) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                updateNotesUseCase.invoke(id, title, content, selectedColor)
            }
                .onSuccess {
                    storeNoteObs.postValue(ProcessState.success())
                }
                .onFailure {
                    storeNoteObs.postValue(ProcessState.error(it))
                }
        }
    }
}