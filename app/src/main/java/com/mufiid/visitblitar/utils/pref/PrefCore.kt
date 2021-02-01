package com.mufiid.visitblitar.utils.pref

import android.content.Context
import androidx.core.content.edit

object PrefCore {

    fun clearPrefAuth(context: Context) {
        val pref = context.getSharedPreferences("USER", Context.MODE_PRIVATE)
        pref.edit {
            clear()
        }
    }
}