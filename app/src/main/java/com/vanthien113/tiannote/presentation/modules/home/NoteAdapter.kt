package com.vanthien113.tiannote.presentation.modules.home

import android.view.LayoutInflater
import android.view.ViewGroup
import com.vanthien113.tiannote.databinding.ItemNoteBinding
import com.vanthien113.tiannote.presentation.base.BaseAdapter
import com.vanthien113.tiannote.presentation.model.NoteModel
import com.vanthien113.tiannote.shared.exts.setOnDelayClickListener
import com.vanthien113.tiannote.shared.utilities.ColorUtils

class NoteAdapter(
    private val onDeletedClicked: (NoteModel) -> Unit,
    private val onNoteClicked: (NoteModel) -> Unit
) : BaseAdapter<ItemNoteBinding, NoteModel>() {
    override fun getLayoutBinding(parent: ViewGroup, viewType: Int): ItemNoteBinding {
        return ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    override fun bind(binding: ItemNoteBinding, item: NoteModel, position: Int) {
        binding.swParent.reset()
        binding.tvTitle.text = item.title
        binding.flDeleteNote.setOnDelayClickListener {
            onDeletedClicked.invoke(item)
        }
        binding.clContentParent.setOnDelayClickListener {
            onNoteClicked.invoke(item)
        }
        binding.clContentParent.setBackgroundColor(ColorUtils.getColorFromHex(item.colorHex))
    }
}