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


        var rectF = RectF(100f, 200f, 300f, 400f)
        canvas!!.drawArc(rectF, 0f, 360f, true, mPaint)




    }

}