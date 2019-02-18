package com.billy.animationmenudemo

import android.animation.ObjectAnimator
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Rect
import android.support.design.widget.FloatingActionButton
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.animation_menu.view.*

class AnimationMenu: FrameLayout {
    private val TAG = javaClass.simpleName

    private val DEFAULT_BUTTON_SIZE = 56
    private val DEFAULT_DISTANCE = DEFAULT_BUTTON_SIZE * 1.5f
    private val DEFAULT_RING_SCALE_RATIO = 1.3f
    private val DEFAULT_CLOSE_ICON_ALPHA = 0.3f

    private val END_POSITION = 240
    private val DURATION: Long = 200
    private var isClicked = false

    private val mButtons = java.util.ArrayList<View>()
    private val mButtonRect = Rect()

    private var mIconMenu: Int = 0
    private var mIconClose: Int = 0
    private var mDurationRing: Int = 0
    private var mLongClickDurationRing: Int = 0
    private var mDurationOpen: Int = 0
    private var mDurationClose: Int = 0
    private var mDesiredSize: Int = 0
    private var mRingRadius: Int = 0
    private var mDistance: Float = 0.toFloat()


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

        mDurationRing = typedArray.getInteger(
            R.styleable.AnimationMenu_duration_ring,
            resources.getInteger(android.R.integer.config_mediumAnimTime)
        )
        mLongClickDurationRing = typedArray.getInteger(
            R.styleable.AnimationMenu_long_click_duration_ring,
            resources.getInteger(android.R.integer.config_longAnimTime)
        )
        mDurationOpen = typedArray.getInteger(
            R.styleable.AnimationMenu_duration_open,
            resources.getInteger(android.R.integer.config_mediumAnimTime)
        )
        mDurationClose = typedArray.getInteger(
            R.styleable.AnimationMenu_duration_close,
            resources.getInteger(android.R.integer.config_mediumAnimTime))

        val density = context.getResources().getDisplayMetrics().density
        val defaultDistance = DEFAULT_DISTANCE * density;
        mDistance = typedArray.getDimension(R.styleable.AnimationMenu_distance, defaultDistance);


        initLayout(context)
        initMenu()
        initButtons(context, icons, colors)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val w = resolveSizeAndState(mDesiredSize, widthMeasureSpec, 0)
        val h = resolveSizeAndState(mDesiredSize, heightMeasureSpec, 0)

        setMeasuredDimension(w, h)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        ringView.setStrokeWidth(mButtonRect.width())
        ringView.setRadius(mRingRadius)

        val lp = ringView.layoutParams as FrameLayout.LayoutParams
        lp.width = right - left
        lp.height = bottom - top
    }


    private fun initLayout(context: Context) {
        LayoutInflater.from(context).inflate(R.layout.animation_menu, this, true)
        setWillNotDraw(true)
        clipChildren = false
        clipToPadding = false

        val density = context.resources.displayMetrics.density
        val buttonSize = DEFAULT_BUTTON_SIZE * density

        mRingRadius = (buttonSize + (mDistance - buttonSize / 2)).toInt()
        mDesiredSize = (mRingRadius * 2 * DEFAULT_RING_SCALE_RATIO).toInt()
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
                Log.i(TAG, "x: ${menu_fab.x}, y:${menu_fab.y}")

                val buttonNumber = mButtons.indexOf(menu_fab) + 1
                val stepAngle = 360f / mButtons.size
                val rOStartAngle = 270 - stepAngle + stepAngle * buttonNumber
                val rStartAngle = if (rOStartAngle > 360) rOStartAngle % 360 else rOStartAngle

                ringView.setStartAngle(rStartAngle)
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

        ringView.x = menu_fab.x
        ringView.y = menu_fab.y - END_POSITION
    }

    private fun initButtons(context: Context, icons: List<Int>, colors: List<Int>) {
        val buttonsCount = Math.min(icons.size, colors.size)
        for (i in 0 until buttonsCount) {
            val button = FloatingActionButton(context)
            button.setImageResource(icons[i])
            button.backgroundTintList = ColorStateList.valueOf(colors[i])
            button.isClickable = true
//            button.setOnClickListener(OnButtonClickListener())
//            button.setOnLongClickListener(OnButtonLongClickListener())
            button.scaleX = 0f
            button.scaleY = 0f
            button.layoutParams =
                FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT)

            addView(button)
            mButtons.add(button)
        }
    }
}