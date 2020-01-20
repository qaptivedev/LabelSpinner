package com.qaptive.labelspinnerlibrary

import android.widget.BaseAdapter

interface LabelBaseAdapter {

    /**
     * Return text to display when item selected
     */
     fun getDisplayText(position:Int):String?
}