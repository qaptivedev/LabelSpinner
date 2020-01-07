package com.srl.labelspinner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.srl.labelspinnerlibrary.LabelSpinner
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val adapter=ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item)
        adapter.add("Hello")
        adapter.add("you")
        adapter.add("TERF")
        spinner.setAdapter(adapter)
//        spinner.setOnItemSelectedListener()
        label_spinner.setAdapter(adapter)
    }
}
