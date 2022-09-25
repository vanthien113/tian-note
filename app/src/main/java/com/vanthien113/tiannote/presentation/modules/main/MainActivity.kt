package com.vanthien113.tiannote.presentation.modules.main

import com.vanthien113.tiannote.databinding.ActivityMainBinding
import com.vanthien113.tiannote.presentation.base.BaseMVVMActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseMVVMActivity<ActivityMainBinding, MainViewModel>() {
    override fun getViewModelClass(): Class<MainViewModel> {
        return MainViewModel::class.java
    }

    override fun getLayoutBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initialize() {
    }

    override fun registerViewEvent() {
    }

    override fun registerViewModelObs() {
    }
}