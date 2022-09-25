package com.vanthien113.tiannote.presentation.base

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.viewbinding.ViewBinding

/**
 * The type Base dialog.
 */
abstract class BaseDialog<V : ViewBinding>(context: Context) : Dialog(context) {
    private lateinit var binding: V

    protected abstract fun getLayoutBinding(): V
    protected fun getViewBinding(): V {
        return binding
    }

    protected abstract fun initialize()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // inflated, adding all top-level views to the screen
        binding = getLayoutBinding()
        setContentView(binding.root)
        initialize()
        // cancelable with the BACK key
        setCancelable(false)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
}
