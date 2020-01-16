package com.qaptive.labelspinnerlibrary

import android.content.res.Resources
import android.graphics.Rect
import android.view.View

object ViewUtils {

    fun View.isVisibleForUser(): Boolean {
        if (!isShown) {
            return false
        }
        val actualPosition = Rect()
        this.getGlobalVisibleRect(actualPosition)
        val screen = Rect(
            0,
            0,
            Resources.getSystem().displayMetrics.widthPixels,
            Resources.getSystem().displayMetrics.heightPixels
        )
        return actualPosition.intersect(screen)
    }
}