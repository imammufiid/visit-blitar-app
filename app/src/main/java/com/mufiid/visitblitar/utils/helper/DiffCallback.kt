package com.mufiid.visitblitar.utils.helper

import android.provider.ContactsContract
import androidx.recyclerview.widget.DiffUtil
import com.mufiid.visitblitar.data.source.local.entity.TicketEntity

class DiffCallback(private val mOldNoteList: List<TicketEntity>, private val mNewNoteList: List<TicketEntity>) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int = mOldNoteList.size

    override fun getNewListSize(): Int = mNewNoteList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldNoteList[oldItemPosition].id == mNewNoteList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldEmployee = mOldNoteList[oldItemPosition]
        val newEmployee = mNewNoteList[newItemPosition]

        return oldEmployee.id == newEmployee.id && oldEmployee.statusIsComing == newEmployee.statusIsComing
    }
}