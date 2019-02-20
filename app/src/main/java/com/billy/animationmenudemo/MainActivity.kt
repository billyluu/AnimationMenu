package com.billy.animationmenudemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.animation_menu.*
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {
    private val TAG = javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        animation_menu.setSubButtonClick(object : AnimationMenu.OnSubButtonClick {
            override fun onClick(name: String) {
                toast(name)
            }
        })
    }
}
