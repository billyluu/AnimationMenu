package com.billy.animationmenudemo

import android.animation.ObjectAnimator
import android.content.Context
import android.support.design.widget.FloatingActionButton
import android.util.Log
import android.view.ViewGroup
import android.view.animation.TranslateAnimation
import android.widget.Button
import android.widget.FrameLayout

class AnimationMenu(var menu_fab: FloatingActionButton, var btn_layout: FrameLayout) {
    private val TAG = javaClass.simpleName
    private val position1 = 80
    private val position2 = 160
    private val position3 = 240
    private val duration: Long = 200
    private var isClicked = false

    private lateinit var add_btn: Button

    init {
        setMenuFABClick()
        setLayout()
    }

    private fun setLayout() {
        var y = btn_layout.translationY
        Log.i(TAG, "${y}")
        btn_layout.layoutParams.height = 500
        btn_layout.layoutParams.width = 500
        btn_layout.translationY = y - 190f
        Log.i(TAG, "${btn_layout.translationY}")
    }

    private fun setMenuFABClick() {
        menu_fab.setOnClickListener {
            if (!isClicked) {
                var y = menu_fab.translationY
                var animator = ObjectAnimator.ofFloat(menu_fab, "translationY", y, y - position1, y - position2, y - position3)
                animator.duration = duration
                animator.start()
                isClicked = true
                menu_fab.setImageResource(R.drawable.ic_close)
            } else {
                var y = menu_fab.translationY
                var animator = ObjectAnimator.ofFloat(menu_fab, "translationY", y, y + position1, y + position2, y + position3)
                animator.duration = duration
                animator.start()
                isClicked = false
                menu_fab.setImageResource(R.drawable.ic_menu)
            }
        }
    }
}