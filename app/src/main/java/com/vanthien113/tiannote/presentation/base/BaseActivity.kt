package com.vanthien113.tiannote.presentation.base

import android.graphics.Color
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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

    /**
     * Edge-to-edge is enforced from targetSdk 35, so the system bars can no longer be colored
     * (window.statusBarColor is ignored). Draw behind transparent bars instead, fill [root] with
     * [backgroundColor] so the area behind them keeps the app color, and pad [root] by the
     * system bar / display cutout / keyboard insets so content stays clear of them.
     */
    protected fun setupEdgeToEdge(root: View, @ColorRes backgroundColor: Int) {
        // Dark style = light status/navigation bar icons, matching the dark app background
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.dark(Color.TRANSPARENT),
        )
        root.setBackgroundColor(ContextCompat.getColor(this, backgroundColor))
        ViewCompat.setOnApplyWindowInsetsListener(root) { view, insets ->
            val bars = insets.getInsets(
                WindowInsetsCompat.Type.systemBars() or
                    WindowInsetsCompat.Type.displayCutout() or
                    WindowInsetsCompat.Type.ime()
            )
            view.setPadding(bars.left, bars.top, bars.right, bars.bottom)
            WindowInsetsCompat.CONSUMED
        }
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