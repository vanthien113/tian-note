package com.vanthien113.tiannote.presentation.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Standard Base View Model
 */
abstract class BaseViewModel : ViewModel() {
    var isPopBackStack = false
    var errorMessageObs = MutableLiveData<String>()
    var loadingObs = MutableLiveData<Boolean>()

    /**
     * Show error message
     *
     * @param msg to show string message
     */
    protected fun showErrorMessage(msg: String) {
        errorMessageObs.postValue(msg)
    }

    /**
     * Show loading
     */
    protected fun showLoading() {
        loadingObs.postValue(true)
    }

    /**
     * Hide loading
     */
    protected fun hideLoading() {
        loadingObs.postValue(false)
    }
}
