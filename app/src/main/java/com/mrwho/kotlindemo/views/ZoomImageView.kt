package com.mrwho.kotlindemo.views

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.*
import android.widget.ImageView
import java.util.*

@SuppressLint("AppCompatCustomView")
class ZoomImageView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : ImageView(context, attrs, defStyleAttr), ViewTreeObserver.OnGlobalLayoutListener, View.OnTouchListener, ScaleGestureDetector.OnScaleGestureListener {
    private var isInit: Boolean = false


    private val mMatrix: Matrix

    /**
     * 缩放的最小值
     */
    private var mMinScale: Float = 0.toFloat()
    /**
     * 缩放的中间值
     */
    private var mMidScale: Float = 0.toFloat()
    /**
     * 缩放的最大值
     */
    private var mMaxScale: Float = 0.toFloat()

    /**
     * 多点手势触 控缩放比率分析器
     */
    private val mScaleGestureDetector: ScaleGestureDetector
    /**
     * 记录上一次多点触控的数量
     */
    private var mLastPointereCount: Int = 0

    private var mLastX: Float = 0.toFloat()
    private var mLastY: Float = 0.toFloat()
    private val mTouchSlop: Int
    private var isCanDrag: Boolean = false
    private var isCheckLeftAndRight: Boolean = false
    private var isCheckTopAndBottom: Boolean = false

    //实现双击放大与缩小
    private val mGestureDetector: GestureDetector
    private var isScaleing: Boolean = false
    private val events: List<MotionEvent>
    private var onClickListener: View.OnClickListener? = null
    private var arae_img_id = -1

    init {

        scaleType = ImageView.ScaleType.MATRIX
        mMatrix = Matrix()
        mScaleGestureDetector = ScaleGestureDetector(context, this)
        mGestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onDoubleTap(e: MotionEvent): Boolean {
                if (isScaleing || scale >= mMaxScale)
                    return true
                isScaleing = true
                val x = e.x
                val y = e.y

                if (scale < mMidScale) {
                    postDelayed(AutoScaleRunnable(mMidScale, x, y), 16)
                } else {
                    postDelayed(AutoScaleRunnable(mMinScale, x, y), 16)
                }

                return true
            }

            override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
                if (onClickListener != null) {
                    onClickListener!!.onClick(this@ZoomImageView)
                    return true
                }
                return false
            }
        })
        setOnTouchListener(this)
        mTouchSlop = ViewConfiguration.get(context).scaledTouchSlop
        events = ArrayList<MotionEvent>()
    }


    private inner class AutoScaleRunnable(
            /**
             * 要缩放的目标值
             */
            private val mTargetScale: Float, private val x: Float //缩放的中心点x
            , private val y: Float //缩放的中心点y
    ) : Runnable {
        private var tmpScale: Float = 0.toFloat()

        private val BIGGER = 1.07f
        private val SMALL = 0.93f

        init {

            if (scale < mTargetScale) {
                tmpScale = BIGGER
            } else {
                tmpScale = SMALL
            }
        }

        override fun run() {
            mMatrix.postScale(tmpScale, tmpScale, x, y)
            checkBorderAndCenterWhenScale()
            imageMatrix = mMatrix


            val currentScale = scale
            if (tmpScale > 1.0f && currentScale < mTargetScale || tmpScale < 1.0f && currentScale > mTargetScale) {
                postDelayed(this, 16)
            } else {
                val scale = mTargetScale / currentScale
                mMatrix.postScale(scale, scale, x, y)
                checkBorderAndCenterWhenScale()
                imageMatrix = mMatrix
                isScaleing = false
            }
        }
    }


    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        viewTreeObserver.addOnGlobalLayoutListener(this)
    }

    @SuppressLint("NewApi")
    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        viewTreeObserver.removeOnGlobalLayoutListener(this)
    }

    override fun onGlobalLayout() {
        if (drawable == null || width == 0 || height == 0) return
        if (!isInit) {
            val width = width
            val height = height
            val screenWeight = height * 1.0f / width

            val imageH = drawable.intrinsicHeight // 图片高度
            val imageW = drawable.intrinsicWidth // 图片宽度
            val imageWeight = imageH * 1.0f / imageW
            if (screenWeight >= imageWeight) {
                var scale = 1.0f
                if (imageW > width && imageH < height) {
                    scale = width * 1.0f / imageW //根据宽度缩放
                }

                if (imageH > height && imageW < width) {
                    scale = height * 1.0f / imageH //根据高度缩放
                }

                if (imageH > height && imageW > width) {
                    scale = Math.min(width * 1.0f / imageW, height * 1.0f / imageH)
                }

                if (imageH < height && imageW < width) {
                    scale = Math.min(width * 1.0f / imageW, height * 1.0f / imageH)
                    log("min scale:" + scale)
                }


                mMinScale = scale
                mMidScale = mMinScale * 2
                mMaxScale = mMinScale * 4

                val dx = getWidth() / 2 - imageW / 2
                val dy = getHeight() / 2 - imageH / 2

                mMatrix.postTranslate(dx.toFloat(), dy.toFloat())
                mMatrix.postScale(mMinScale, mMinScale, (width / 2).toFloat(), (height / 2).toFloat())
            } else {

                val scale = width * 1.0f / imageW
                mMaxScale = scale
                mMidScale = mMaxScale / 2
                mMinScale = mMaxScale / 4

                mMatrix.postScale(mMaxScale, mMaxScale, 0f, 0f)
            }

            imageMatrix = mMatrix
            isInit = true
        }
    }

    fun reSetState() {
        isInit = false
        tag = null
        mMatrix.reset()
    }

    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        if (mGestureDetector.onTouchEvent(motionEvent))
            return true

        //将触摸事件传递给ScaleGestureDetector
        if (motionEvent.pointerCount > 1)
            mScaleGestureDetector.onTouchEvent(motionEvent)


        var x = 0f
        var y = 0f

        val pointerCount = motionEvent.pointerCount

        for (i in 0..pointerCount - 1) {
            x += motionEvent.getX(i)
            y += motionEvent.getY(i)
        }

        x /= pointerCount.toFloat()
        y /= pointerCount.toFloat()

        if (mLastPointereCount != pointerCount) {
            isCanDrag = false
            mLastX = x
            mLastY = y
        }

        mLastPointereCount = pointerCount

        when (motionEvent.action) {
            MotionEvent.ACTION_DOWN -> {
                val rectF = matrixRectF
                if (rectF.width() > width + 0.01f || rectF.height() > height + 0.01f) {
                    if (rectF.right != width.toFloat() && rectF.left != 0f) {
                        try {
                            parent.requestDisallowInterceptTouchEvent(true)
                        } catch (e: Exception) {
                            log(e.toString())
                        }

                    }
                }
            }
            MotionEvent.ACTION_MOVE -> {

                var dx = x - mLastX
                var dy = y - mLastY

                if (!isCanDrag) {
                    isCanDrag = isMoveAction(dx, dy)
                }

                if (isCanDrag) {
                    val rectF = matrixRectF

                    if (drawable != null) {
                        isCheckTopAndBottom = true
                        isCheckLeftAndRight = isCheckTopAndBottom

                        if (rectF.width() <= width) {
                            isCheckLeftAndRight = false
                            dx = 0f
                        }

                        if (rectF.height() <= height) {
                            isCheckTopAndBottom = false
                            dy = 0f
                        }

                        mMatrix.postTranslate(dx, dy)
                        checkBorderWhenTranslate()
                        imageMatrix = mMatrix
                    }
                }
                mLastX = x
                mLastY = y

                val rect = matrixRectF
                if (rect.width() > width + 0.01f || rect.height() > height + 0.01f) {
                    if (rect.right != width.toFloat() && rect.left != 0f) {
                        try {
                            parent.requestDisallowInterceptTouchEvent(true)
                        } catch (e: Exception) {
                            log(e.toString())
                        }

                    }
                }
            }
            MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> {
                mLastPointereCount = 0
            }
        }
        return true
    }


    private fun checkBorderWhenTranslate() {
        val rectF = matrixRectF

        var deltaX = 0f
        var deltaY = 0f

        val width = width
        val height = height

        if (rectF.top > 0 && isCheckTopAndBottom) {
            deltaY = -rectF.top
        }

        if (rectF.bottom < height && isCheckTopAndBottom) {
            deltaY = height - rectF.bottom
        }


        if (rectF.left > 0 && isCheckLeftAndRight) {
            deltaX = -rectF.left
        }

        if (rectF.right < width && isCheckLeftAndRight) {
            deltaX = width - rectF.right
        }

        mMatrix.postTranslate(deltaX, deltaY)
        imageMatrix = mMatrix
    }


    private fun isMoveAction(dx: Float, dy: Float): Boolean {
        return Math.sqrt((dx * dx + dy * dy).toDouble()) > mTouchSlop
    }

    override fun onScale(detector: ScaleGestureDetector): Boolean {

        var scaleFactor = detector.scaleFactor//获取用户手势判断出来的缩放值
        val scale = scale

        /**
         * 没有图片
         */
        if (drawable == null) return true

        //缩放范围控制
        if (scale < mMaxScale && scaleFactor > 1.0f || scale > mMinScale && scaleFactor < 1.0f) {
            if (scaleFactor * scale < mMinScale) {
                scaleFactor = mMinScale / scale
            }

            if (scale * scaleFactor > mMaxScale) {
                scaleFactor = mMaxScale / scale
            }

            mMatrix.postScale(scaleFactor, scaleFactor, detector.focusX, detector.focusY)
            checkBorderAndCenterWhenScale()
            imageMatrix = mMatrix
        }
        return true
    }


    private fun checkBorderAndCenterWhenScale() {
        val rectF = matrixRectF

        var deltaX = 0f
        var deltaY = 0f

        val width = width
        val height = height

        if (rectF.width() >= width) {
            if (rectF.left > 0)
                deltaX = -rectF.left
            if (rectF.right < width)
                deltaX = width - rectF.right
        }

        if (rectF.height() >= height) {
            if (rectF.top > 0)
                deltaY = 0f
            if (rectF.bottom < height)
                deltaY = height - rectF.bottom
        }

        if (rectF.width() < width) {
            deltaX = width / 2f - rectF.right + rectF.width() / 2
        }

        if (rectF.height() < height) {
            deltaY = height / 2f - rectF.bottom + rectF.height() / 2
        }

        mMatrix.postTranslate(deltaX, deltaY)
        imageMatrix = mMatrix
    }


    private val matrixRectF: RectF
        get() {
            val rectF = RectF()
            val drawable = drawable

            if (drawable != null) {
                rectF.set(0f, 0f, drawable.intrinsicWidth.toFloat(), drawable.intrinsicHeight.toFloat())
                mMatrix.mapRect(rectF)
            }

            return rectF
        }

    override fun onScaleBegin(scaleGestureDetector: ScaleGestureDetector): Boolean {
        return true //缩放开始,返回true 用于接收后续时间
    }

    override fun onScaleEnd(scaleGestureDetector: ScaleGestureDetector) {

    }


    private val scale: Float
        get() {
            val values = FloatArray(9)
            mMatrix.getValues(values)
            return values[Matrix.MSCALE_X]
        }

    override fun setImageBitmap(bm: Bitmap) {
        reSetState()
        super.setImageBitmap(bm)
    }

    override fun setImageResource(resId: Int) {
        reSetState()
        super.setImageResource(resId)
    }

    override fun setOnClickListener(l: View.OnClickListener?) {
        this.onClickListener = l
    }


    fun placeholder(resID: Int) {
        this.arae_img_id = resID
    }

    companion object {

        private val TAG = "ZoomImageView"


        private val IS_DEBUG = true

        fun log(value: String) {
            if (IS_DEBUG)
                Log.w(TAG, value)
        }
    }
}