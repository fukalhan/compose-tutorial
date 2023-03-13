package com.example.composetutorial

import androidx.annotation.StringRes

sealed class Screen(val route: String, @StringRes val resId: Int, val iconResId: Int) {
    object Counter : Screen("counter", R.string.counter, R.drawable.counter)
    object Chat : Screen("chat", R.string.chat, R.drawable.message)
}
