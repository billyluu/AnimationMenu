package com.billy.animationmenudemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        animation_menu
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
