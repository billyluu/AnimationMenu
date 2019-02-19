package com.billy.animationmenudemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val TAG = javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.i(TAG, "${Math.cos(Math.toRadians(72.0))}")
//        var animationMenu = AnimationMenu1(this, menu_fab, ringView, object : AnimationMenu1.MenuListener{
//            override fun menuExpand() {
//
//            }
//
//            override fun menuClosed() {
//
//            }
//        })




    }
}
