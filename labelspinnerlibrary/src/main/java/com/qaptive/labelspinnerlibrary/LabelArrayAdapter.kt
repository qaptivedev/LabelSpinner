package com.qaptive.labelspinnerlibrary

import android.content.Context
import android.widget.ArrayAdapter

class LabelArrayAdapter<T>(
    context: Context,
    resource: Int,
    textViewResourceId: Int=0
) : ArrayAdapter<T>(context, resource, textViewResourceId) ,LabelBaseAdapter{
    override fun getDisplayText(position: Int): String? {
        return getItem(position).toString()
    }
}