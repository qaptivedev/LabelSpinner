package com.qaptive.labelspinner

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
        label_spinner.setSelection(1)
        label_spinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(this@MainActivity,"onNothingSelected",Toast.LENGTH_SHORT).show()
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Toast.makeText(this@MainActivity,"position:$position, id:$id",Toast.LENGTH_SHORT).show()
            }

        }
        )
        error_btn.setOnClickListener {
            label_spinner.setError("Error")
            spinner.setError("Error",true)
        }
        clear_btn.setOnClickListener {
            label_spinner.setError(null)
            label_spinner.clearSelection()
            spinner.setError(null)
            spinner.clearSelection()
        }
    }
}
