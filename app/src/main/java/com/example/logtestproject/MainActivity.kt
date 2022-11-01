package com.example.logtestproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.logtestproject.log_test.GenerateValueFiles
import com.hyy.logcat.DragViewUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DragViewUtils.addDrag(main_rootLayout, this)
    }
}