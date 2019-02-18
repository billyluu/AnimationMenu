package com.billy.animationmenudemo

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout

class RingView: View {
    private val TAG = javaClass.simpleName


    private val mPaint: Paint
    private val mPath = Path()

    private var mAngle: Float = 0.toFloat()
    private var mStartAngle: Float = 0.toFloat()
    private var mRadius: Int = 0


    constructor(context: Context): this(context, null)
    constructor(context: Context, attrs: AttributeSet?): this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int): super(context, attrs, defStyleAttr)

    init {
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaint.style = Paint.Style.STROKE
        mPaint.isAntiAlias = true
        mPaint.strokeCap = Paint.Cap.ROUND
        mPaint.color = Color.BLACK
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (!mPath.isEmpty) {
            canvas!!.save()
            canvas.translate((width / 2).toFloat(), (height / 2).toFloat())
            canvas.drawPath(mPath, mPaint)
            canvas.restore()
        }
    }

    override fun setAlpha(alpha: Float) {
        super.setAlpha(alpha)

        mPaint.alpha = (255 * alpha).toInt()
        invalidate()
    }

    override fun getAlpha(): Float {
        return (mPaint.alpha / 255).toFloat()

    }

    fun setStartAngle(startAngle: Float) {
        mStartAngle = startAngle
        mAngle = 0f

        val sw = mPaint.strokeWidth * 0.5f
        val radius = mRadius - sw

        mPath.reset()
        val x = Math.cos(Math.toRadians(startAngle.toDouble())).toFloat() * radius
        val y = Math.sin(Math.toRadians(startAngle.toDouble())).toFloat() * radius
        mPath.moveTo(x, y)
    }

    fun setStrokeColor(color: Int) {
        mPaint.color = color
    }

    fun setStrokeWidth(width: Int) {
        mPaint.strokeWidth = width.toFloat()
    }

    fun setRadius(radius: Int) {
        mRadius = radius
    }
}