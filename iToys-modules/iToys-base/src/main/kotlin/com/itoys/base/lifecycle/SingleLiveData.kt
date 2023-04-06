package com.itoys.base.lifecycle

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.itoys.logcat.LogPriority
import com.itoys.logcat.logcat
import java.util.concurrent.atomic.AtomicBoolean

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 06/04/2023
 * @desc 多个观察者存在时，只有一个Observer能够收到数据更新
 */
class SingleLiveData<T> : MutableLiveData<T>() {

    private val mPending = AtomicBoolean(false)

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        if (hasActiveObservers()) logcat(LogPriority.WARN) { "Multiple observers registered but only one will be notified of changes." }

        super.observe(owner) { t ->
            //如果expect为true，那么将值update为false，方法整体返回true，
            //即当前Observer能够收到更新，后面如果还有订阅者，不能再收到更新通知了
            if (mPending.compareAndSet(true, false)) {
                observer.onChanged(t)
            }
        }
    }

    override fun setValue(value: T?) {
        //AtomicBoolean中设置的值设置为true
        mPending.set(true)
        super.setValue(value)
    }

    /**
     * Used for cases where T is Void, to make calls cleaner.
     */
    @MainThread
    fun call() {
        value = null
    }
}