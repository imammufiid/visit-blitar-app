package com.mufiid.visitblitar.utils.pref

import android.content.Context
import androidx.core.content.edit
import com.mufiid.visitblitar.data.source.local.entity.UserEntity

object UserPref {
    fun getUserData(context: Context): UserEntity? {
        val pref = context.getSharedPreferences("USER", Context.MODE_PRIVATE)
        return UserEntity().apply {
            username = pref.getString("USERNAME", "")
            id = pref.getInt("ID_USER", 0)
        }
    }

    fun setUserData(context: Context, user: UserEntity?) {
        val pref = context.getSharedPreferences("USER", Context.MODE_PRIVATE)
        pref.edit {
            putString("USERNAME", user?.username)
            user?.id?.let { putInt("ID_USER", it) }
        }
    }
}