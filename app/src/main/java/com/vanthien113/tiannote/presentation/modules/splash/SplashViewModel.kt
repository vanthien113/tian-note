package com.vanthien113.tiannote.presentation.modules.splash

import androidx.lifecycle.viewModelScope
import com.vanthien113.tiannote.presentation.base.BaseViewModel
import com.vanthien113.tiannote.shared.utilities.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : BaseViewModel() {
    val navigateHomeObs = SingleLiveEvent<Boolean>()

    fun navigateToHome() {
        viewModelScope.launch(Dispatchers.IO) {
            delay(2000)
            navigateHomeObs.postValue(true)
        }
    }
}