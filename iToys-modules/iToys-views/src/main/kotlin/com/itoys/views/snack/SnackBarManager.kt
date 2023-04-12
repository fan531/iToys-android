package com.itoys.views.snack

import android.os.Handler
import android.os.Looper
import android.os.Message
import com.google.android.material.snackbar.Snackbar
import java.lang.ref.WeakReference

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 11/04/2023
 * @desc
 */
class SnackBarManager {

    companion object {
        private const val MSG_TIMEOUT = 0
        private const val SHORT_DURATION_MS = 1500
        private const val LONG_DURATION_MS = 2750

        val INSTANCE: SnackBarManager by lazy { SnackBarManager() }
    }

    private val mLock: Any = Any()

    private val mHandler: Handler by lazy {
        Handler(Looper.getMainLooper(), object : Handler.Callback {
            override fun handleMessage(msg: Message): Boolean {
                when (msg.what) {
                    MSG_TIMEOUT -> {
                        handleTimeout(msg.obj as SnackBarRecord)
                        return true
                    }
                }

                return false
            }
        })
    }

    private var mCurrentSnackBar: SnackBarRecord? = null
    private var mNextSnackBar: SnackBarRecord? = null

    fun show(duration: Int, callback: ICallback) {
        synchronized(mLock) {
            when {
                isCurrent(callback) -> {
                    // Means that the callback is already in the queue. We'll just update the duration
                    // If this is the SnackBar currently being shown, call re-schedule it's timeout
                    mCurrentSnackBar?.duration = duration
                    mHandler.removeCallbacksAndMessages(mCurrentSnackBar)
                }

                isSnackBarLocked(mNextSnackBar, callback) -> {
                    mNextSnackBar?.duration = duration
                }

                else -> {
                    // Else, we need to create a new record and queue it
                    mNextSnackBar = SnackBarRecord(duration, callback)
                }
            }

            if (mCurrentSnackBar != null && cancelSnackBarLocked(
                    mCurrentSnackBar,
                    Snackbar.Callback.DISMISS_EVENT_CONSECUTIVE
                )
            ) {
                // If we currently have a SnackBar, try and cancel it and wait in line
                return
            } else {
                mCurrentSnackBar = null
                showNextSnackBarLocked()
            }
        }
    }

    private fun showNextSnackBarLocked() {
        mNextSnackBar?.apply {
            mCurrentSnackBar = this
            mNextSnackBar = null

            val callback = mCurrentSnackBar?.weakReference?.get()
            callback?.show()
            if (callback == null) {
                mCurrentSnackBar = null
            }
        }
    }

    private fun isSnackBarLocked(snackBarRecord: SnackBarRecord?, callback: ICallback): Boolean {
        return snackBarRecord != null && snackBarRecord.isSnackBar(callback)
    }

    private fun cancelSnackBarLocked(snackBarRecord: SnackBarRecord?, event: Int): Boolean {
        val callback = snackBarRecord?.weakReference?.get()
        if (callback != null) {
            mHandler.removeCallbacksAndMessages(snackBarRecord)
            callback.dismiss(event)
            return true
        }

        return false
    }

    fun dismiss(callback: ICallback, event: Int) {
        synchronized(mLock) {
            when {
                isCurrent(callback) -> {
                    cancelSnackBarLocked(mCurrentSnackBar, event)
                }

                isSnackBarLocked(mNextSnackBar, callback) -> {
                    cancelSnackBarLocked(mNextSnackBar, event)
                }

                else -> {/*空实现*/}
            }
        }
    }

    fun onDismissed(callback: ICallback) {
        synchronized(mLock) {
            if (isCurrent(callback)) {
                mCurrentSnackBar = null
                showNextSnackBarLocked()
            }
        }
    }

    fun onShown(callback: ICallback) {
        synchronized(mLock) {
            if (isCurrent(callback)) {
                scheduleTimeoutLocked(mCurrentSnackBar)
            }
        }
    }

    fun cancelTimeout(callback: ICallback) {
        synchronized(mLock) {
            if (isCurrent(callback)) {
                mHandler.removeCallbacksAndMessages(mCurrentSnackBar)
            }
        }
    }

    fun restoreTimeout(callback: ICallback) {
        synchronized(mLock) {
            if (isCurrent(callback)) {
                scheduleTimeoutLocked(mCurrentSnackBar)
            }
        }
    }

    fun isCurrent(callback: ICallback): Boolean {
        synchronized(mLock) {
            return isSnackBarLocked(mCurrentSnackBar, callback)
        }
    }

    fun isCurrentOrNext(callback: ICallback): Boolean {
        synchronized(mLock) {
            return isCurrent(callback) || isSnackBarLocked(mNextSnackBar, callback)
        }
    }

    private fun scheduleTimeoutLocked(record: SnackBarRecord?) {
        if (record?.duration == Snackbar.LENGTH_INDEFINITE) return

        record?.let {
            val durationMs = when {
                it.duration > 0 -> {
                    it.duration
                }

                it.duration == Snackbar.LENGTH_SHORT -> {
                    SHORT_DURATION_MS
                }

                else -> LONG_DURATION_MS
            }

            mHandler.removeCallbacksAndMessages(record)
            mHandler.sendMessageDelayed(
                Message.obtain(mHandler, MSG_TIMEOUT, record),
                durationMs.toLong()
            )
        }

    }

    private fun handleTimeout(record: SnackBarRecord) {
        synchronized(mLock) {
            if (mCurrentSnackBar == record || mNextSnackBar == record) {
                cancelSnackBarLocked(record, Snackbar.Callback.DISMISS_EVENT_TIMEOUT);
            }
        }
    }

    private class SnackBarRecord(
        var duration: Int,
        callback: ICallback,
    ) {

        val weakReference: WeakReference<ICallback>

        init {
            weakReference = WeakReference(callback)
        }

        fun isSnackBar(callback: ICallback?): Boolean {
            return callback != null && weakReference.get() == callback
        }
    }

    interface ICallback {
        fun show()

        fun dismiss(event: Int)
    }
}