package com.itoys.views.snack

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityManager
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import androidx.annotation.ColorInt
import androidx.annotation.IntDef
import androidx.annotation.StringRes
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import com.google.android.material.behavior.SwipeDismissBehavior
import com.itoys.expansion.accessibilityManager
import com.itoys.expansion.doOnClick
import com.itoys.expansion.drawable
import com.itoys.expansion.isBlank
import com.itoys.expansion.layoutInflater
import com.itoys.expansion.then
import com.itoys.utils.BarUtils
import com.itoys.utils.UtilsInitialization
import com.itoys.views.R

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 11/04/2023
 * @desc [TopSnackBar] provides lightweight feedback about an operation. They show a brief message at the
 * top of the screen on mobile. TopSnackBar appear above all other
 * elements on screen and only one can be displayed at a time.
 */
class TopSnackBar {

    companion object {

        /**
         * Show the TopSnackBar from top to down.
         */
        const val APPEAR_FROM_TOP_TO_DOWN = 0

        /**
         * Show the TopSnackBar from top to down.
         */
        const val APPEAR_FROM_BOTTOM_TO_TOP = 1

        /**
         * Show the TopSnackBar indefinitely. This means that the TopSnackBar will be displayed from the time
         * that is shown until either it is dismissed, or another TopSnackBar is shown.
         *
         * @see [setDuration]
         */
        const val LENGTH_INDEFINITE = -2

        /**
         * Show the TopSnackBar for a short period of time.
         *
         * @see [setDuration]
         */
        const val LENGTH_SHORT = -1

        /**
         * Show the TopSnackBar for a long period of time.
         *
         * @see [setDuration]
         */
        const val LENGTH_LONG = 0

        private const val ANIMATION_DURATION: Long = 250
        private const val ANIMATION_FADE_DURATION = 180
        private const val MSG_SHOW = 0
        private const val MSG_DISMISS = 1

        fun make(view: View, message: String, @Duration duration: Int): TopSnackBar {
            val snack = TopSnackBar(findSuitableParent(view), APPEAR_FROM_TOP_TO_DOWN)
            snack.setMessage(message).setDuration(duration)
            return snack
        }

        fun make(view: View, messageRes: Int, @Duration duration: Int): TopSnackBar {
            val snack = TopSnackBar(findSuitableParent(view), APPEAR_FROM_TOP_TO_DOWN)
            snack.setMessage(messageRes).setDuration(duration)
            return snack
        }

        fun make(
            view: View,
            message: String,
            @Duration duration: Int,
            @OverSnackAppearDirection appearDirection: Int
        ): TopSnackBar {
            val snack = TopSnackBar(findSuitableParent(view), appearDirection)
            snack.setMessage(message).setDuration(duration)
            return snack
        }

        private fun findSuitableParent(view: View): ViewGroup? {
            var fallbackView: View? = view
            var fallback: ViewGroup? = null

            do {
                when (fallbackView) {
                    is CoordinatorLayout -> {
                        return view as ViewGroup
                    }

                    is FrameLayout -> {
                        if (view.id == android.R.id.content) {
                            return view as ViewGroup
                        } else {
                            fallback = view as ViewGroup
                        }
                    }
                }

                if (fallbackView != null) {
                    val parent = fallbackView.parent
                    fallbackView = (parent is View).then({ parent as View }, { null })
                }
            } while (fallbackView != null)

            return fallback
        }
    }

    private val mHandler: Handler by lazy {
        Handler(Looper.getMainLooper(), object : Handler.Callback {
            override fun handleMessage(msg: Message): Boolean {
                when (msg.what) {
                    MSG_SHOW -> {
                        (msg.obj as TopSnackBar).showView()
                        return true
                    }

                    MSG_DISMISS -> {
                        (msg.obj as TopSnackBar).hideView(msg.arg1)
                        return true
                    }
                }

                return false
            }
        })
    }

    private val mManagerCallback: SnackBarManager.ICallback = object : SnackBarManager.ICallback {
        override fun show() {
            mHandler.sendMessage(mHandler.obtainMessage(MSG_SHOW, this@TopSnackBar))
        }

        override fun dismiss(event: Int) {
            mHandler.sendMessage(mHandler.obtainMessage(MSG_DISMISS, event, 0, this@TopSnackBar))
        }
    }

    @OverSnackAppearDirection
    private var appearDirection = APPEAR_FROM_TOP_TO_DOWN

    @IntDef(
        APPEAR_FROM_TOP_TO_DOWN,
        APPEAR_FROM_BOTTOM_TO_TOP,
    )
    @Retention(AnnotationRetention.SOURCE)
    annotation class OverSnackAppearDirection

    @IntDef(
        LENGTH_INDEFINITE,
        LENGTH_SHORT,
        LENGTH_LONG,
    )
    @Retention(AnnotationRetention.SOURCE)
    annotation class Duration

    private val mContent: Context by lazy { UtilsInitialization.requireApp() }
    private var mParent: ViewGroup? = null
    private var mView: SnackBarLayout? = null
    private var mDuration: Int = 0
    private var mCallback: Callback? = null
    private val mAccessibilityManager: AccessibilityManager? by lazy { mContent.accessibilityManager }

    private constructor(parent: ViewGroup?, @OverSnackAppearDirection appearDirection: Int) {
        mParent = parent

        val layoutInflater = mContent.layoutInflater ?: LayoutInflater.from(mContent)
        mView = layoutInflater.inflate(
            if (appearDirection == APPEAR_FROM_BOTTOM_TO_TOP) {
                R.layout.itoys_view_layout_bottom_snack_bar
            } else {
                R.layout.itoys_view_layout_top_snack_bar
            }, mParent, false
        ) as SnackBarLayout

        if (appearDirection == APPEAR_FROM_TOP_TO_DOWN) {
            setMinHeight(BarUtils.getStatusBarHeight() ,0)
        }
    }

    fun setMessage(message: CharSequence): TopSnackBar {
        mView?.getMessageView()?.text = message
        return this
    }

    fun setMessage(@StringRes messageId: Int): TopSnackBar {
        mView?.getMessageView()?.text = mContent.getString(messageId)
        return this
    }

    fun setMessageColor(@ColorInt color: Int): TopSnackBar {
        mView?.getMessageView()?.setTextColor(color)
        return this
    }

    fun setMessageSize(size: Int): TopSnackBar {
        mView?.getMessageView()?.setTextSize(TypedValue.COMPLEX_UNIT_SP, size.toFloat())
        return this
    }

    fun setAction(@StringRes actionText: Int, block: () -> Unit): TopSnackBar {
        return setAction(mContent.getString(actionText), block)
    }

    fun setAction(actionText: CharSequence, block: () -> Unit): TopSnackBar {
        mView?.getActionView()?.visibility = (actionText.isBlank()).then(View.GONE, View.VISIBLE)
        mView?.getActionView()?.doOnClick(block)
        return this
    }

    fun setActionColor(@ColorInt color: Int): TopSnackBar {
        mView?.getActionView()?.setTextColor(color)
        return this
    }

    fun setActionSize(size: Int): TopSnackBar {
        mView?.getActionView()?.setTextSize(TypedValue.COMPLEX_UNIT_SP, size.toFloat())
        return this
    }

    fun setColors(@ColorInt color: Int): TopSnackBar {
        setMessageColor(color)
        setActionColor(color)
        return this
    }

    fun setDuration(@Duration duration: Int): TopSnackBar {
        mDuration = duration
        return this
    }

    @Duration
    fun getDuration(): Int {
        return mDuration
    }

    fun addIcon(icon: Drawable?): TopSnackBar {
        mView?.getMessageView()?.apply {
            setCompoundDrawablesWithIntrinsicBounds(
                icon, null, null, null
            )
        }
        return this
    }

    fun addLoadingIcon(
        left: Boolean = true, right: Boolean = false
    ): TopSnackBar {
        return addLoadingIcon(
            mContent.drawable(com.itoys.theme.R.drawable.theme_icon_loading_24), left, right
        )
    }

    fun addLoadingIcon(
        loadingIcon: Drawable?, left: Boolean = true, right: Boolean = false
    ): TopSnackBar {
        val animator = ObjectAnimator.ofInt(loadingIcon, "level", 0, 1000)
        animator.duration = 1000
        animator.interpolator = LinearInterpolator()
        animator.repeatCount = ValueAnimator.INFINITE
        animator.repeatMode = ValueAnimator.RESTART

        if (left) {
            mView?.getMessageView()
                ?.setCompoundDrawablesWithIntrinsicBounds(loadingIcon, null, null, null)
        }

        if (right) {
            mView?.getMessageView()
                ?.setCompoundDrawablesWithIntrinsicBounds(null, null, loadingIcon, null)
        }

        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
                /** 空实现 */
            }

            override fun onAnimationEnd(animation: Animator) {
                mCallback?.onShown(this@TopSnackBar)
                SnackBarManager.INSTANCE.onShown(mManagerCallback)
            }

            override fun onAnimationCancel(animation: Animator) {
                /** 空实现 */
            }

            override fun onAnimationRepeat(animation: Animator) {
                /** 空实现 */
            }
        })
        animator.start()
        return this
    }

    fun setBackgroundColor(@ColorInt color: Int): TopSnackBar {
        mView?.setBackgroundColor(color)
        return this
    }

    fun setCallback(callback: Callback): TopSnackBar {
        this.mCallback = callback
        return this
    }

    /**
     * Show the [TopSnackBar]
     */
    fun show() {
        SnackBarManager.INSTANCE.show(mDuration, mManagerCallback)
    }

    fun dismiss() {
        dispatchDismiss(Callback.DISMISS_EVENT_MANUAL)
    }

    private fun dispatchDismiss(@Callback.DismissEvent event: Int) {
        SnackBarManager.INSTANCE.dismiss(mManagerCallback, event)
    }

    fun isShown(): Boolean {
        return SnackBarManager.INSTANCE.isCurrent(mManagerCallback)
    }

    fun isShownOrQueued(): Boolean {
        return SnackBarManager.INSTANCE.isCurrentOrNext(mManagerCallback)
    }

    fun showView() {
        if (mView?.parent == null) {
            val layoutParams = mView?.layoutParams
            if (layoutParams is CoordinatorLayout.LayoutParams) {
                val behavior = Behavior()
                behavior.setStartAlphaSwipeDistance(0.1f)
                behavior.setEndAlphaSwipeDistance(0.6f)
                behavior.setSwipeDirection(SwipeDismissBehavior.SWIPE_DIRECTION_START_TO_END)
                behavior.listener = object : SwipeDismissBehavior.OnDismissListener {
                    override fun onDismiss(view: View?) {
                        view?.visibility = View.GONE
                        dispatchDismiss(Callback.DISMISS_EVENT_SWIPE)
                    }

                    override fun onDragStateChanged(state: Int) {
                        when (state) {
                            SwipeDismissBehavior.STATE_DRAGGING, SwipeDismissBehavior.STATE_SETTLING -> {
                                SnackBarManager.INSTANCE.cancelTimeout(mManagerCallback)
                            }

                            SwipeDismissBehavior.STATE_IDLE -> {
                                SnackBarManager.INSTANCE.restoreTimeout(mManagerCallback)
                            }
                        }
                    }
                }

                layoutParams.behavior = behavior
                layoutParams.setMargins(0, 30, 0, 0)
            }

            mParent?.addView(mView)
        }

        if (mView != null && ViewCompat.isLaidOut(mView!!)) {
            showViewWithAnimate()
        } else {
            mView?.setOnLayoutChangeListener(object : SnackBarLayout.OnLayoutChangeListener {
                override fun onLayoutChange(
                    view: View, left: Int, top: Int, right: Int, bottom: Int
                ) {
                    mView?.setOnLayoutChangeListener(null)

                    showViewWithAnimate()
                }
            })
        }

        mView?.setOnAttachStateChangeListener(object : SnackBarLayout.OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(view: View) {
                /** 空实现 */
            }

            override fun onViewDetachedFromWindow(view: View) {
                if (isShownOrQueued()) {
                    mHandler.post { onViewHidden(Callback.DISMISS_EVENT_MANUAL) }
                }
            }
        })
    }

    fun hideView(@Callback.DismissEvent event: Int) {
        if (mAccessibilityManager?.isEnabled == false && mView?.visibility == View.VISIBLE) {
            animateViewOut(event)
        } else {
            onViewHidden(event)
        }
    }

    private fun setMinHeight(stateBarHeight: Int, actionBarHeight: Int): TopSnackBar {
        when (appearDirection) {
            APPEAR_FROM_TOP_TO_DOWN -> {
                if (stateBarHeight > 0 || actionBarHeight > 0) {
                    mView?.setPadding(0, stateBarHeight, 0, 0)
                    mView?.minimumHeight = stateBarHeight + actionBarHeight
                }
            }

            else -> {
                if (stateBarHeight > 0 || actionBarHeight > 0) {
                    mView?.minimumHeight = actionBarHeight
                } else {
                    mView?.minimumHeight = BarUtils.getActionBarHeight()
                    SnackUtils.setMargins(mView, 0, BarUtils.getStatusBarHeight(), 0, 0)
                }
            }
        }
        return this
    }

    private fun onViewShown() {
        SnackBarManager.INSTANCE.onShown(mManagerCallback)
        mCallback?.onShown(this@TopSnackBar)
    }

    private fun onViewHidden(event: Int) {
        SnackBarManager.INSTANCE.onDismissed(mManagerCallback)
        mCallback?.onDismissed(this@TopSnackBar, event)
        val parent = mView?.parent
        if (parent is ViewGroup) {
            parent.removeView(mView)
        }
    }

    private fun showViewWithAnimate() {
        if (mAccessibilityManager?.isEnabled == false) {
            animateViewIn()
        } else {
            onViewShown()
        }
    }

    private fun animateViewIn() {
        val anim: Animation = if (appearDirection == APPEAR_FROM_TOP_TO_DOWN) {
            AnimationUtils.getAnimationInFromTopToDown(mContent)
        } else {
            AnimationUtils.getAnimationInFromBottomToTop(mContent)
        }

        anim.interpolator = AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR
        anim.duration = ANIMATION_DURATION
        anim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
                /** 空实现 */
            }

            override fun onAnimationEnd(animation: Animation?) {
                onViewShown()
            }

            override fun onAnimationRepeat(animation: Animation?) {
                /** 空实现 */
            }
        })
        mView?.startAnimation(anim)
    }

    private fun animateViewOut(event: Int) {
        val anim: Animation = if (appearDirection == APPEAR_FROM_TOP_TO_DOWN) {
            AnimationUtils.getAnimationOutFromTopToDown(mContent)
        } else {
            AnimationUtils.getAnimationOutFromBottomToTop(mContent)
        }

        anim.interpolator = AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR
        anim.duration = ANIMATION_DURATION
        anim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
                /** 空实现 */
            }

            override fun onAnimationEnd(animation: Animation?) {
                onViewHidden(event)
            }

            override fun onAnimationRepeat(animation: Animation?) {
                /** 空实现 */
            }
        })
        mView?.startAnimation(anim)
    }

    internal inner class Behavior : SwipeDismissBehavior<SnackBarLayout>() {

        override fun canSwipeDismissView(view: View): Boolean {
            return view is SnackBarLayout
        }

        override fun onInterceptTouchEvent(
            parent: CoordinatorLayout, child: SnackBarLayout, event: MotionEvent
        ): Boolean {
            if (parent.isPointInChildBounds(child, event.x.toInt(), event.y.toInt())) {
                when (event.actionMasked) {
                    MotionEvent.ACTION_DOWN -> {
                        SnackBarManager.INSTANCE.cancelTimeout(mManagerCallback)
                    }

                    MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                        SnackBarManager.INSTANCE.restoreTimeout(mManagerCallback);
                    }
                }
            }

            return super.onInterceptTouchEvent(parent, child, event)
        }
    }

    /**
     * Callback class for [TopSnackBar] instances.
     *
     * @see TopSnackBar.setCallback
     */
    abstract class Callback {

        companion object {
            /**
             * Indicates that the [TopSnackBar] was dismissed via a swipe.
             */
            const val DISMISS_EVENT_SWIPE = 0

            /**
             * Indicates that the [TopSnackBar] was dismissed via an action click.
             */
            const val DISMISS_EVENT_ACTION = 1

            /**
             * Indicates that the [TopSnackBar] was dismissed via a timeout.
             */
            const val DISMISS_EVENT_TIMEOUT = 2

            /**
             * Indicates that the [TopSnackBar] was dismissed via a call to [.dismiss].
             */
            const val DISMISS_EVENT_MANUAL = 3

            /**
             * Indicates that the [TopSnackBar] was dismissed from a new being shown.
             */
            const val DISMISS_EVENT_CONSECUTIVE = 4
        }

        @IntDef(
            DISMISS_EVENT_SWIPE,
            DISMISS_EVENT_ACTION,
            DISMISS_EVENT_TIMEOUT,
            DISMISS_EVENT_MANUAL,
            DISMISS_EVENT_CONSECUTIVE
        )
        @Retention(AnnotationRetention.SOURCE)
        annotation class DismissEvent

        /**
         * Called when the given [TopSnackBar] is visible.
         *
         * @param [snackBar] The snackbar which is now visible.
         * @see TopSnackBar.show
         */
        fun onShown(snackBar: TopSnackBar?) {}

        /**
         * Called when the given [TopSnackBar] has been dismissed, either through a time-out,
         * having been manually dismissed, or an action being clicked.
         *
         * @param snackBar The snackbar which has been dismissed.
         * @param event        The event which caused the dismissal. One of either:
         * [.DISMISS_EVENT_SWIPE], [.DISMISS_EVENT_ACTION],
         * [.DISMISS_EVENT_TIMEOUT], [.DISMISS_EVENT_MANUAL] or
         * [.DISMISS_EVENT_CONSECUTIVE].
         * @see TopSnackBar.dismiss
         */
        fun onDismissed(snackBar: TopSnackBar?, @DismissEvent event: Int) {}
    }
}