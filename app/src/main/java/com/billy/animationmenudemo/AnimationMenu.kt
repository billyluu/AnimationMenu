package com.billy.animationmenudemo

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.ComponentCallbacks
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Rect
import android.support.design.widget.FloatingActionButton
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.OvershootInterpolator
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.animation_menu.view.*
import kotlin.math.E

class AnimationMenu: FrameLayout {
    private val TAG = javaClass.simpleName

    private val DEFAULT_BUTTON_SIZE = 56
    private val DEFAULT_DISTANCE = DEFAULT_BUTTON_SIZE * 1.5f
    private val DEFAULT_RING_SCALE_RATIO = 1.3f

    private val END_POSITION = 240
    private val DURATION: Long = 200
    private var isOpen = false
    private var isAnimating = false

    private val mButtons = java.util.ArrayList<View>()

    private var mDurationRing: Int = 0
    private var mLongClickDurationRing: Int = 0
    private var mDurationOpen: Int = 0
    private var mDurationClose: Int = 0
    private var mDesiredSize: Int = 0
    private var mRingRadius: Int = 0
    private var mDistance: Float = 0.toFloat()

    private var menuXY = FloatArray(2)

    private lateinit var onSubButtonClick: OnSubButtonClick

    constructor(context: Context): this(context, null) {
        initLayout(context)
        initMenu()
    }
    constructor(context: Context, attrs: AttributeSet?): this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int): super(context, attrs, defStyleAttr) {
        val icons: MutableList<Int>
        val colors: MutableList<Int>
        val names: MutableList<String>

        if (attrs == null) {
            throw IllegalArgumentException("No buttons icons or colors set")
        }

        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.AnimationMenu, 0, 0)
        val iconArrayId = typedArray.getResourceId(R.styleable.AnimationMenu_button_icons, 0)
        val colorArrayId = typedArray.getResourceId(R.styleable.AnimationMenu_button_colors, 0)
        val btnNameArrayId = typedArray.getResourceId(R.styleable.AnimationMenu_button_name, 0)

        val iconsIds = resources.obtainTypedArray(iconArrayId)
        try {
            val colorsIds = resources.getIntArray(colorArrayId)
            val buttonsCount = Math.min(iconsIds.length(), colorsIds.size)
            val namesArray = resources.getStringArray(btnNameArrayId)

            icons = ArrayList(buttonsCount)
            colors = ArrayList(buttonsCount)
            names = ArrayList(buttonsCount)


            for (i in 0 until buttonsCount) {
                icons.add(iconsIds.getResourceId(i, iconArrayId))
                colors.add(colorsIds[i])
                names.add(namesArray[i])
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
        val defaultDistance = DEFAULT_DISTANCE * density
        mDistance = typedArray.getDimension(R.styleable.AnimationMenu_distance, defaultDistance)


        initLayout(context)
        initMenu()

        menuXY[0] = menu_fab.x
        menuXY[1] = menu_fab.y

        initButtons(context, icons, colors, names)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val w = resolveSizeAndState(mDesiredSize, widthMeasureSpec, 0)
        val h = resolveSizeAndState(mDesiredSize, heightMeasureSpec, 0)

        setMeasuredDimension(w, h)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

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
            if (isAnimating) {
                return@setOnClickListener
            }

            val animator = if (!isOpen) {
                menuOpenAnimation()
            } else {
                menuCloseAnimation()

            }

            animator.start()
        }
    }

    private fun initButtons(context: Context, icons: List<Int>, colors: List<Int>, names: List<String>) {
        val buttonsCount = Math.min(icons.size, colors.size)
        for (i in 0 until buttonsCount) {
            val button = FloatingActionButton(context)
            button.setImageResource(icons[i])
            button.tag = names[i]
            button.backgroundTintList = ColorStateList.valueOf(colors[i])
            button.isClickable = true
            button.setOnClickListener(OnButtonClickListener)
//            button.setOnLongClickListener(OnButtonLongClickListener())
            button.scaleX = 0.8f
            button.scaleY = 0.8f
            button.x = menuXY[0]
            button.y = menuXY[1] - END_POSITION
            button.layoutParams =
                FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT)

            addView(button)
            mButtons.add(button)
        }
    }

    private var OnButtonClickListener = View.OnClickListener {
        this.onSubButtonClick.onClick(it.tag.toString())
    }



    private fun menuOpenAnimation(): Animator {
        menu_fab.setImageResource(R.drawable.ic_close)
        val y = menu_fab.translationY
        val animator = ObjectAnimator.ofFloat(menu_fab, "translationY", y, y - END_POSITION)
        animator.duration = DURATION


        val btnAnimator = ValueAnimator.ofFloat(0f, mDistance)
        btnAnimator.duration = 450
        btnAnimator.addUpdateListener {
            val value = it.animatedValue as Float
            val angle = 360f / mButtons.size
            setButtonsAnimation(menu_fab.x, menu_fab.y, angle, value)
        }

        val result = AnimatorSet()
        result.playTogether(animator, btnAnimator)
        result.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {
                isAnimating = true
            }

            override fun onAnimationEnd(animation: Animator?) {
                isAnimating = false
                isOpen = true
            }

            override fun onAnimationCancel(animation: Animator?) {

            }

            override fun onAnimationRepeat(animation: Animator?) {

            }
        })

        return result
    }

    private fun menuCloseAnimation(): Animator {

        val btnAnimator = ValueAnimator.ofFloat(mDistance, 0f)
        btnAnimator.duration = DURATION
        btnAnimator.addUpdateListener {
            val value = it.animatedValue as Float
            val angle = 360f / mButtons.size
            setButtonsAnimation(menu_fab.x, menu_fab.y, angle, value)
        }

        menu_fab.setImageResource(R.drawable.ic_menu)
        val y = menu_fab.translationY
        val animator = ObjectAnimator.ofFloat(menu_fab, "translationY", y, y + END_POSITION)
        animator.duration = DURATION



        val result = AnimatorSet()
        result.play(btnAnimator).before(animator)
        result.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {
                isAnimating = true
            }

            override fun onAnimationEnd(animation: Animator?) {
                isAnimating = false
                isOpen = false
                hideButtons()

            }

            override fun onAnimationCancel(animation: Animator?) {

            }

            override fun onAnimationRepeat(animation: Animator?) {

            }
        })

        return result
    }

    private fun setButtonsAnimation(centerX: Float, centerY: Float, angleStep: Float, offset: Float) {
        var i = 0
        val cnt = mButtons.size
        while (i < cnt) {
            val angle = angleStep * i - 90

            val x = Math.cos(Math.toRadians(angle.toDouble())).toFloat() * offset
            val y = Math.sin(Math.toRadians(angle.toDouble())).toFloat() * offset
            val button = mButtons[i]

            button.x = centerX + x
            button.y = centerY + y
            button.scaleX = 0.8f
            button.scaleY = 0.8f
            i++
        }
    }



    private fun hideButtons() {
        var i = 0
        val cnt = mButtons.size
        while (i < cnt) {
            val button = mButtons[i]
            button.scaleX = 0f
            button.scaleY = 0f
            i++
        }
    }

    fun setSubButtonClick(onSubButtonClick: OnSubButtonClick) {
        this.onSubButtonClick = onSubButtonClick
    }

    interface OnSubButtonClick {
        fun onClick(name: String)
    }
}