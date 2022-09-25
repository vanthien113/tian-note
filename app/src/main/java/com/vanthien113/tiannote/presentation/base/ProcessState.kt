package com.vanthien113.tiannote.presentation.base

sealed class Status {
    object Loading : Status()
    object Success : Status()
    object Error : Status()
}

data class ProcessState<out T>(
    val status: Status,
    val data: T? = null,
    val throwable: Throwable? = null
) {
    val isLoading = status is Status.Loading
    val isSuccess = status is Status.Success
    val isError = status is Status.Error
    val isFinish: Boolean
        get() = isError || isSuccess

    companion object {
        fun <T> success(data: T? = null): ProcessState<T> {
            return ProcessState(Status.Success, data)
        }

        fun <T> error(throwable: Throwable?): ProcessState<T> {
            return ProcessState(Status.Error, throwable = throwable)
        }

        fun <T> loading(): ProcessState<T> {
            return ProcessState(Status.Loading)
        }
    }
}
