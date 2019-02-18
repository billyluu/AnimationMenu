package com.billy.animationmenudemo

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.drawable.Drawable
import android.support.design.widget.FloatingActionButton
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import kotlin.math.E

class AnimationMenu1(var context: Context, var menu_fab: FloatingActionButton, var ringView: RingView, var menuListener: MenuListener) {
    private val TAG = javaClass.simpleName
    private val END_POSITION = 240
    private val DURATION: Long = 200
    private var isClicked = false
    private var layout: FrameLayout

    private var btns = ArrayList<View>()

    private var menuXY = FloatArray(2)

    init {
        layout = LayoutInflater.from(context).inflate(R.layout.animation_menu, null, false) as FrameLayout
        setMenuFABClick()
        Log.i(TAG, "ringView:${ringView.x}, ${ringView.y}")

    }

    private fun setMenuFABClick() {
        menu_fab.setOnClickListener {
            if (!isClicked) {
                var y = menu_fab.translationY
                var animator = ObjectAnimator.ofFloat(menu_fab, "translationY", y, y - END_POSITION)
                animator.duration = DURATION
                animator.start()
                isClicked = true
                menu_fab.setImageResource(R.drawable.ic_close)
                menuListener.menuExpand()
                Log.i(TAG, "${menu_fab.x}, ${menu_fab.y - END_POSITION}")
                initButtons(context, 6, menu_fab.x, menu_fab.y)
            } else {
                var y = menu_fab.translationY
                var animator = ObjectAnimator.ofFloat(menu_fab, "translationY", y, y + END_POSITION)
                animator.duration = DURATION
                animator.start()
                isClicked = false
                menu_fab.setImageResource(R.drawable.ic_menu)
                menuListener.menuClosed()
            }
        }


    }

    private fun initButtons(context: Context, count: Int, centerX: Float, centerY: Float) {
        for (i in 0..(count - 1)) {
            var fab = FloatingActionButton(context)
            fab.x = centerX
            fab.y = centerY
            fab.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            btns.add(fab)
        }
    }


//    private fun (x: Float, y: Float) {
//        menuXY[0] = x
//        menuXY[1] = y
//    }







    interface MenuListener {
        fun menuExpand()
        fun menuClosed()
    }


}