package com.srl.labelspinnerlibrary

import android.content.Context
import android.util.AttributeSet
import android.view.ViewParent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import android.widget.Filterable
import android.widget.ListAdapter
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import com.google.android.material.textfield.TextInputLayout

class AutoCompleteTextInputEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatAutoCompleteTextView(context, attrs, defStyleAttr) {

    override fun onCreateInputConnection(outAttrs: EditorInfo?): InputConnection {
        val ic = super.onCreateInputConnection(outAttrs)
        if (ic != null) {
            val parent: ViewParent = getParent()
            if (parent is TextInputLayout) {
                outAttrs?.hintText = parent.hint
            }
        }
        return ic
    }

    override fun <T : ListAdapter?> setAdapter(adapter: T) where T : Filterable? {
        super.setAdapter(adapter)
    }
}