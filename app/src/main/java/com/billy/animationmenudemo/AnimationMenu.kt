package com.billy.animationmenudemo

import android.animation.ObjectAnimator
import android.support.design.widget.FloatingActionButton
import android.util.Log

class AnimationMenu(var menu_fab: FloatingActionButton) {
    private val TAG = javaClass.simpleName
    private var isClicked = false

    init {

        menu_fab.setOnClickListener {
            if (!isClicked) {
                var y = menu_fab.translationY
                var animator = ObjectAnimator.ofFloat(menu_fab, "translationY", y, y - 80, y - 160, y - 240)
                animator.duration = 200
                animator.start()
                isClicked = true
                menu_fab.setImageResource(R.drawable.ic_close)
            } else {
                var y = menu_fab.translationY
                var animator = ObjectAnimator.ofFloat(menu_fab, "translationY", y, y + 80, y + 160, y + 240)
                animator.duration = 200
                animator.start()
                isClicked = false
                menu_fab.setImageResource(R.drawable.ic_menu)
            }

        }
    }


}