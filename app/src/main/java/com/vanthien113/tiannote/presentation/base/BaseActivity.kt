package com.vanthien113.tiannote.presentation.base

import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.vanthien113.tiannote.shared.widget.LoadingDialog

abstract class BaseActivity : AppCompatActivity() {
    protected val loadingDialog by lazy { LoadingDialog(this) }

    /**
     * Toast shown for short period of time
     *
     * @param message - String
     */
    protected fun showToast(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    /**
     * Toast shown for short period of time
     *
     * @param message - int
     */
    protected fun showToast(message: Int) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    protected fun changeStatusBarColor(color: Int) {
        window?.statusBarColor = ContextCompat.getColor(this, color)
    }

    /**
     * Called to process touch screen events.
     *
     * @param event the touch screen event
     * @return boolean Return true if this event was consumed.
     */
    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        val v = currentFocus
        if (v is EditText) {
            val scoops = IntArray(2)
            v.getLocationOnScreen(scoops)
            val x = event.rawX + v.getLeft() - scoops[0]
            val y = event.rawY + v.getTop() - scoops[1]
            if (event.action == MotionEvent.ACTION_UP
                && (x < v.getLeft() || x >= v.getRight() || y < v.getTop() || y > v.getBottom())
            ) {
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
            }
        }
        return super.dispatchTouchEvent(event)
    }

    fun showLoading() {
        loadingDialog.show()
    }

    fun hideLoading() {
        loadingDialog.dismiss()
    }
}