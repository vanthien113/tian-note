package com.vanthien113.tiannote.presentation.modules.addnote

import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.vanthien113.tiannote.R
import com.vanthien113.tiannote.databinding.FragmentAddNoteBinding
import com.vanthien113.tiannote.presentation.base.BaseMVVMFragment
import com.vanthien113.tiannote.presentation.modules.home.HomeFragmentDirections
import com.vanthien113.tiannote.shared.exts.changeBackgroundColor
import com.vanthien113.tiannote.shared.exts.setOnDelayClickListener
import com.vanthien113.tiannote.shared.widget.CommonDialog
import com.skydoves.colorpickerview.ColorPickerDialog
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
open class AddNoteFragment : BaseMVVMFragment<FragmentAddNoteBinding, AddNoteViewModel>() {
    companion object {
        fun navigateFromHomeScreen(navController: NavController) {
            navController.navigate(HomeFragmentDirections.actionHomeFragmentToAddNoteFragment())
        }
    }

    protected val saveConfirmDialog by lazy {
        CommonDialog(
            context = requireContext(),
            title = getString(R.string.save_change),
            negativeText = getString(R.string.discard),
            negativeBackgroundColor = R.color.colorRed,
            positiveText = getString(R.string.save),
            positiveBackgroundColor = R.color.colorGreenJungle,
            positiveEvent = ::onSaveNote
        )
    }

    protected val discardDialog by lazy {
        CommonDialog(
            context = requireContext(),
            title = getString(R.string.msg_discard_change),
            negativeText = getString(R.string.discard),
            negativeBackgroundColor = R.color.colorRed,
            negativeEvent = ::onDiscard,
            positiveText = getString(R.string.keep),
            positiveBackgroundColor = R.color.colorGreenJungle,
        )
    }

    private val colorPickerDialog by lazy {
        ColorPickerDialog.Builder(context)
            .setPositiveButton(getString(R.string.select), ColorEnvelopeListener { envelope, _ -> onColorSelected(envelope.hexCode) })
            .setNegativeButton(getString(R.string.cancel)) { dialogInterface, _ -> dialogInterface.dismiss() }
            .show()
    }

    override fun getViewModelClass(): Class<AddNoteViewModel> {
        return AddNoteViewModel::class.java
    }

    override fun getLayoutBinding(): FragmentAddNoteBinding {
        return FragmentAddNoteBinding.inflate(layoutInflater)
    }

    override fun initialize() {
    }

    override fun registerViewEvent() {
        getViewBinding().btSave.setOnDelayClickListener {
            val (title, content) = getTitleAndContent()
            if (getViewModel().validateInput(title, content)) {
                saveConfirmDialog.show()
            }
        }

        getViewBinding().btBack.setOnDelayClickListener {
            val (title, content) = getTitleAndContent()
            if (title.isNotEmpty() || content.isNotEmpty()) {
                discardDialog.show()
            } else {
                onDiscard()
            }
        }

        getViewBinding().btColorPicker.setOnDelayClickListener {
            colorPickerDialog.show()
        }
    }

    override fun registerViewModelObs() {
        getViewModel().validateErrorObs.observe(viewLifecycleOwner) {
            it ?: return@observe
            when (it) {
                AddNoteViewModel.InputError.EMPTY_TITLE -> {
                    CommonDialog(
                        context = requireContext(),
                        title = getString(R.string.msg_empty_title),
                        negativeText = getString(R.string.got_it),
                        negativeBackgroundColor = R.color.colorRed,
                    ).show()
                }
                AddNoteViewModel.InputError.EMPTY_CONTENT -> {
                    CommonDialog(
                        context = requireContext(),
                        title = getString(R.string.msg_empty_content),
                        negativeText = getString(R.string.got_it),
                        negativeBackgroundColor = R.color.colorRed,
                    ).show()
                }
            }
        }

        defaultObserve(getViewModel().storeNoteObs) {
            findNavController().navigateUp()
        }
    }

    protected fun getTitleAndContent(): Pair<String, String> {
        return getViewBinding().etTitle.text.toString() to getViewBinding().etContent.text.toString()
    }

    protected open fun onSaveNote() {
        val (title, content) = getTitleAndContent()
        getViewModel().storeNote(title, content)
    }

    private fun onColorSelected(colorHex: String) {
        getViewModel().selectedColor = colorHex
        getViewBinding().btColorPicker.changeBackgroundColor(colorHex)
    }

    protected fun onDiscard() {
        findNavController().navigateUp()
    }
}