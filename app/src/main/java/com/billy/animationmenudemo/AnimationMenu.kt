package com.billy.animationmenudemo

import android.animation.ObjectAnimator
import android.support.design.widget.FloatingActionButton
import android.util.Log
import android.view.animation.TranslateAnimation

class AnimationMenu(var menu_fab: FloatingActionButton) {
    private val TAG = javaClass.simpleName
    private val position1 = 80
    private val position2 = 160
    private val position3 = 240
    private val duration: Long = 200
    private var isClicked = false

    init {

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