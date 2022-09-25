package com.vanthien113.tiannote.presentation.base

import android.widget.Toast
import androidx.fragment.app.Fragment

/**
 * The type Base fragment.
 */
abstract class BaseFragment : Fragment() {
    /**
     * Toast shown for short period of time
     * @param message - String
     */
    protected fun showToast(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    /**
     * Toast shown for short period of time
     */
    protected fun showToast(message: Int) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
