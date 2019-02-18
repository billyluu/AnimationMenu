package com.billy.animationmenudemo

import android.animation.ObjectAnimator
import android.content.Context
import android.support.design.widget.FloatingActionButton
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.animation_menu.view.*

class AnimationMenu: FrameLayout {
    private val TAG = javaClass.simpleName

    private val END_POSITION = 240
    private val DURATION: Long = 200
    private var isClicked = false

    constructor(context: Context): this(context, null) {
        initLayout(context)
        initMenu()
        Log.i(TAG, "1")
    }
    constructor(context: Context, attrs: AttributeSet?): this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int): super(context, attrs, defStyleAttr) {


        val menuButtonColor: Int
        val icons: MutableList<Int>
        val colors: MutableList<Int>

        if (attrs == null) {
            throw IllegalArgumentException("No buttons icons or colors set")
        }

        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.AnimationMenu, 0, 0)
        val iconArrayId = typedArray.getResourceId(R.styleable.AnimationMenu_button_icons, 0)
        val colorArrayId = typedArray.getResourceId(R.styleable.AnimationMenu_button_colors, 0)

        val iconsIds = resources.obtainTypedArray(iconArrayId)
        try {
            val colorsIds = resources.getIntArray(colorArrayId)
            val buttonsCount = Math.min(iconsIds.length(), colorsIds.size)

            icons = ArrayList(buttonsCount)
            colors = ArrayList(buttonsCount)

            for (i in 0 until buttonsCount) {
                icons.add(iconsIds.getResourceId(i, -1))
                colors.add(colorsIds[i])
            }
        } finally {
            iconsIds.recycle()
        }


        initLayout(context)
        initMenu()
    }


    private fun initLayout(context: Context) {
        LayoutInflater.from(context).inflate(R.layout.animation_menu, this, true)
        setWillNotDraw(true)
        clipChildren = false
        clipToPadding = false
    }

    private fun initMenu() {
        menu_fab.setOnClickListener {
            if (!isClicked) {
                var y = menu_fab.translationY
                var animator = ObjectAnimator.ofFloat(menu_fab, "translationY", y, y - END_POSITION)
                animator.duration = DURATION
                animator.start()
                isClicked = true
                menu_fab.setImageResource(R.drawable.ic_close)
                //menuListener.menuExpand()
                Log.i(TAG, "${menu_fab.x}, ${menu_fab.y - END_POSITION}")
                //initButtons(context, 6, menu_fab.x, menu_fab.y)
            } else {
                var y = menu_fab.translationY
                var animator = ObjectAnimator.ofFloat(menu_fab, "translationY", y, y + END_POSITION)
                animator.duration = DURATION
                animator.start()
                isClicked = false
                menu_fab.setImageResource(R.drawable.ic_menu)
                //menuListener.menuClosed()
            }
        }
    }

    private fun initButtons(context: Context, icons: List<Int>, colors: List<Int>) {

    }
}