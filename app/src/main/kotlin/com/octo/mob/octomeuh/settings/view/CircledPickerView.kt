package com.octo.mob.octomeuh.settings.view

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Point
import android.graphics.Rect
import android.graphics.RectF
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.view.animation.DecelerateInterpolator
import com.octo.mob.octomeuh.R

/**
 * Duration picker view.
 * Freely adapted from https://github.com/arcadefire/circled-picker
 */
class CircleDurationPickerView : View {

    // ---------------------------------
    // CONSTANTS
    // ---------------------------------

    companion object {
        private val VALUE_THRESHOLD = 270
        private val TURN = 360
        private val HALF = TURN / 2
        private val TURN_THRESHOLD = 300
        private val DURATION = 200
        private val FULL_OPACITY = 255
        private val SECONDS_PER_MINUTE = 60
        private val THREE_QUARTER = 270
        private val ALMOST_TURN = 359.99f
        private val RADIUS_MULTIPLIER = .3f
        private val REMAINDER = 0.01f
    }

    // ---------------------------------
    // ATTRIBUTES
    // ---------------------------------

    private var angleAnimator: ValueAnimator = ValueAnimator()
    private var arcRect: RectF = RectF()
    private var arcInnerRect: RectF = RectF()
    private var shadowRect: RectF = RectF()
    private var shadowInnerRect: RectF = RectF()
    private var paint: Paint = Paint()
    private var path: Path = Path()
    private var textBounds: Rect = Rect()
    private var currentSweep: Float = 0f
    private var currentValue: Float = 0f
    private var lastAngle: Float = 0f
    private var downX: Float = 0f
    private var maxValue: Float = 0f
    private var step: Float = 0f
    private var textSize: Float = 0f
    private var isFilled: Boolean = false
    private var isEmpty: Boolean = false
    private var midX: Int = 0
    private var midY: Int = 0
    private var thickness: Int = 0
    private var innerThickness: Int = 0
    private var touchSlop: Int = 0
    private var radius: Int = 0
    private var innerRadius: Int = 0
    private var lineColor: Int = 0
    private var subLineColor: Int = 0
    private var textColor: Int = 0

    // ---------------------------------
    // CONSTRUCTOR
    // ---------------------------------

    constructor(context: Context) : super(context) {
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(context, attrs)
    }

    // ---------------------------------
    // OVERRIDDEN METHODS
    // ---------------------------------

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val angle = getAngle(Point(event.x.toInt(), event.y.toInt()),
                Point(midX, midY))
        if (isClickable) {
            when (event.action) {
                MotionEvent.ACTION_MOVE -> if (Math.abs(downX - event.x) > touchSlop && !angleAnimator.isRunning) {
                    updateCircle(angle)
                    postInvalidate()
                }
                MotionEvent.ACTION_DOWN -> {
                    downX = event.x
                    isFilled = false
                    isEmpty = false
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> if (Math.abs(downX - event.x) < touchSlop) {
                    animateChange(angle)
                    isFilled = false
                    isEmpty = false
                }
            }
        }
        return true
    }

    override fun onDraw(canvas: Canvas) {
        setCirclesBoundingBoxes()
        drawBackground(canvas)
        drawShadow(canvas)
        drawPickerCircle(canvas)
        drawCenteredText(canvas)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        midX = View.MeasureSpec.getSize(widthMeasureSpec) / 2
        midY = View.MeasureSpec.getSize(heightMeasureSpec) / 2

        if (midX < midY) {
            radius = midX
        } else {
            radius = midY
        }

        if (textSize == 0f) {
            textSize = radius * RADIUS_MULTIPLIER
        }
        innerRadius = radius - thickness

        with(arcRect) {
            left = (midX - radius).toFloat()
            top = (midY - radius).toFloat()
            right = (midX + radius).toFloat()
            bottom = (midY + radius).toFloat()
        }

        with(arcInnerRect) {
            left = (midX - innerRadius).toFloat()
            top = (midY - innerRadius).toFloat()
            right = (midX + innerRadius).toFloat()
            bottom = (midY + innerRadius).toFloat()
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    // ---------------------------------
    // PUBLIC METHODS
    // ---------------------------------

    fun setValue(value: Float) {
        val newAngle = value * TURN / maxValue
        animateChange(newAngle)
    }

    fun getValue() = currentValue.toInt()

    // ---------------------------------
    // PRIVATE METHODS
    // ---------------------------------

    private fun updateCircle(angle: Float) {
        currentSweep = angle

        if (currentSweep - lastAngle < -VALUE_THRESHOLD && !isFilled && !isEmpty) {
            isFilled = true
        } else if (currentSweep - lastAngle > VALUE_THRESHOLD && !isEmpty && !isFilled) {
            isEmpty = true
        }

        if (currentSweep < TURN && currentSweep > TURN_THRESHOLD && isFilled) {
            isFilled = false
        } else if (currentSweep < SECONDS_PER_MINUTE && currentSweep > 0 && isEmpty) {
            isEmpty = false
        }

        if (isFilled) {
            currentSweep = ALMOST_TURN
        } else if (isEmpty) {
            currentSweep = 0f
        } else {
            lastAngle = currentSweep
        }

        currentValue = getMultiply((currentSweep + REMAINDER) * maxValue / TURN).toFloat()
    }

    private fun animateChange(finalAngle: Float) {
        if (angleAnimator.isRunning) {
            angleAnimator.end()
        }

        with(angleAnimator) {
            setFloatValues(lastAngle, finalAngle)
            duration = DURATION.toLong()
            interpolator = DecelerateInterpolator()
            addUpdateListener { animation ->
                currentSweep = animation.animatedValue as Float
                currentValue = getMultiply(currentSweep * maxValue / TURN).toFloat()
                postInvalidate()
            }
            addListener(ValueAnimationListener())
            start()
        }
    }

    private fun drawBackground(canvas: Canvas) {
        canvas.drawColor(Color.TRANSPARENT)
        paint.reset()
        paint.isAntiAlias = true
        paint.alpha = FULL_OPACITY
    }

    private fun getMultiply(value: Float): Int {
        return (value - value % step).toInt()
    }

    private fun drawCenteredText(canvas: Canvas) {
        with(paint) {
            reset()
            alpha = FULL_OPACITY
            shader = null
            color = textColor
        }
        paint.textSize = textSize

        val centerLabel = getMinuteLabel() + getSecondsLabel()
        paint.getTextBounds(centerLabel, 0, centerLabel.length, textBounds)
        val halfWidth = textBounds.width().toFloat() / 2
        val textVerticalOffset = midY + textBounds.height().toFloat() / 2
        canvas.drawText(centerLabel, midX - halfWidth, textVerticalOffset, paint)
    }

    private fun drawPickerCircle(canvas: Canvas) {
        with(path) {
            reset()
            arcTo(arcRect, THREE_QUARTER.toFloat(), currentSweep)
            arcTo(arcInnerRect, THREE_QUARTER + currentSweep, -currentSweep)
            close()
        }
        paint.color = lineColor

        canvas.drawPath(path, paint)
    }

    private fun drawShadow(canvas: Canvas) {
        path.arcTo(shadowRect, 0f, ALMOST_TURN)
        path.arcTo(shadowInnerRect, ALMOST_TURN, -ALMOST_TURN)
        path.close()
        paint.color = subLineColor
        canvas.drawPath(path, paint)
    }

    private fun getMinuteLabel(): String {
        val minutes = (currentValue / SECONDS_PER_MINUTE).toInt()
        return if (minutes == 0) "" else "${minutes}min "
    }

    private fun getSecondsLabel(): String {
        val minutes = (currentValue / SECONDS_PER_MINUTE).toInt()
        val seconds = (currentValue - minutes * SECONDS_PER_MINUTE).toInt()
        return "${padWithZeroIfNeeded(seconds)}s"
    }

    private fun init(context: Context, attrs: AttributeSet) {
        extractArguments(attrs, context)

        lastAngle = currentSweep
        val viewConfiguration = ViewConfiguration.get(context)
        touchSlop = viewConfiguration.scaledTouchSlop

        isClickable = true
    }

    private fun extractArguments(attrs: AttributeSet, context: Context) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleDurationPickerView)

        with(typedArray) {
            lineColor = getColor(R.styleable.CircleDurationPickerView_lineColor, ContextCompat.getColor(context, R.color.defaultLineColor))
            subLineColor = getColor(R.styleable.CircleDurationPickerView_subLineColor, ContextCompat.getColor(context, R.color.defaultSubLineColor))
            textColor = getColor(R.styleable.CircleDurationPickerView_textColor, ContextCompat.getColor(context, R.color.defaultLineColor))
            step = getInteger(R.styleable.CircleDurationPickerView_step, 1).toFloat()
            maxValue = getInteger(R.styleable.CircleDurationPickerView_maxValue, 100).toFloat()
            textSize = getDimensionPixelSize(R.styleable.CircleDurationPickerView_textSize, 0).toFloat()
            thickness = getDimensionPixelSize(R.styleable.CircleDurationPickerView_outerThickness, convertDpToPixel(context, 5f).toInt())
            innerThickness = thickness - getDimensionPixelSize(R.styleable.CircleDurationPickerView_innerThickness, convertDpToPixel(context, 1f).toInt())
        }

        typedArray?.recycle()
    }

    private fun setCirclesBoundingBoxes() {
        path.reset()

        // Set the shadow circle's bounding box
        with(shadowRect) {
            left = (midX - radius + innerThickness).toFloat()
            top = (midY - radius + innerThickness).toFloat()
            right = (midX + radius - innerThickness).toFloat()
            bottom = (midY + radius - innerThickness).toFloat()
        }

        // Set the picker circle's bounding box
        with(shadowInnerRect) {
            left = midX - innerRadius - innerThickness.toFloat()
            top = midY - innerRadius - innerThickness.toFloat()
            right = midX + innerRadius + innerThickness.toFloat()
            bottom = midY + innerRadius + innerThickness.toFloat()
        }
    }

    private fun convertDpToPixel(context: Context, densityPixel: Float): Float {
        val resources = context.resources
        val metrics = resources.displayMetrics
        return densityPixel * (metrics.densityDpi / 160f)
    }

    private fun getAngle(target: Point, origin: Point): Float {
        var angle = Math.toDegrees(Math.atan2((target.x - origin.x).toDouble(), (target.y - origin.y).toDouble())).toFloat() + HALF
        if (angle < 0) {
            angle += TURN.toFloat()
        }
        return TURN - angle
    }

    private fun padWithZeroIfNeeded(value: Int): String {
        return if (value > 9) value.toString() else "0" + value
    }

    // ---------------------------------
    // PRIVATE INNER CLASS
    // ---------------------------------

    private inner class ValueAnimationListener() : AnimatorListener {
        override fun onAnimationCancel(animation: Animator) {
            lastAngle = currentSweep
        }

        override fun onAnimationEnd(animation: Animator) {
            lastAngle = currentSweep
        }

        override fun onAnimationRepeat(animation: Animator) {
        }

        override fun onAnimationStart(animation: Animator) {
        }
    }
}