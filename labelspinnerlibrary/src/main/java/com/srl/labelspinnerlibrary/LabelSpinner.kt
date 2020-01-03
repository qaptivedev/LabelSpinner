package com.srl.labelspinnerlibrary

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout

class LabelSpinner : RelativeLayout {

    constructor(context: Context) : super(context) {
        init(context, null,null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs,null)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(context, attrs,defStyle)
    }

    fun init(context: Context, attrs: AttributeSet?, defStyle: Int?)
    {
        inflate(context,R.layout.lable_spinner_layout,this)
    }
}