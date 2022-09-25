package com.vanthien113.tiannote.presentation.modules.home

import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import com.vanthien113.tiannote.R
import com.vanthien113.tiannote.databinding.FragmentHomeBinding
import com.vanthien113.tiannote.presentation.base.BaseMVVMFragment
import com.vanthien113.tiannote.presentation.model.NoteModel
import com.vanthien113.tiannote.presentation.modules.addnote.AddNoteFragment
import com.vanthien113.tiannote.presentation.modules.editnote.EditNoteFragment
import com.vanthien113.tiannote.presentation.modules.splash.SplashFragmentDirections
import com.vanthien113.tiannote.shared.exts.changeVisibility
import com.vanthien113.tiannote.shared.exts.getDrawable
import com.vanthien113.tiannote.shared.exts.setOnDelayClickListener
import com.vanthien113.tiannote.shared.widget.CommonDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseMVVMFragment<FragmentHomeBinding, HomeViewModel>() {
    companion object {
        fun navigateFromSplashScreen(navController: NavController) {
            navController.navigate(SplashFragmentDirections.actionSplashFragmentToHomeFragment())
        }
    }

    private val noteAdapter by lazy {
        NoteAdapter(
            onDeletedClicked = ::onDeletedNote,
            onNoteClicked = ::onNoteClicked
        )
    }

    private val infoDialog by lazy {
        CommonDialog(
            context = requireContext(),
            title = getString(R.string.msg_info),
            positiveText = getString(R.string.thanks),
            positiveBackgroundColor = R.color.colorGreenJungle,
        )
    }

    override fun getViewModelClass(): Class<HomeViewModel> {
        return HomeViewModel::class.java
    }

    override fun getLayoutBinding(): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(layoutInflater)
    }

    override fun initialize() {
        initViews()
    }

    override fun onResume() {
        super.onResume()
        getViewModel().getNotes()
    }

    private fun initViews() {
        getViewBinding().rvNotes.apply {
            adapter = noteAdapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL).apply {
                R.drawable.divider_item_decoration.getDrawable(requireContext())?.let {
                    setDrawable(it)
                }
            })
        }
    }

    override fun registerViewEvent() {
        getViewBinding().btAddNote.setOnDelayClickListener {
            AddNoteFragment.navigateFromHomeScreen(findNavController())
        }
        getViewBinding().btInfo.setOnDelayClickListener {
            infoDialog.show()
        }
    }

    override fun registerViewModelObs() {
        defaultObserve(getViewModel().getNoteObs) {
            getViewBinding().rvNotes.changeVisibility(!it.isNullOrEmpty())
            noteAdapter.setItems(it.orEmpty())
        }

        defaultObserve(getViewModel().deleteNoteObs) {
            it?.let {
                noteAdapter.removeItem(it)
            }
            getViewBinding().rvNotes.changeVisibility(noteAdapter.getItemsData().isNotEmpty())
        }
    }

    private fun onDeletedNote(noteModel: NoteModel) {
        getViewModel().deleteNote(noteModel)
    }

    private fun onNoteClicked(noteModel: NoteModel) {
        EditNoteFragment.navigateFromHomeScreen(findNavController(), noteModel)
    }
}