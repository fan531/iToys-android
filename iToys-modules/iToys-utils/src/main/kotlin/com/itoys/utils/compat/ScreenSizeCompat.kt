package com.itoys.utils.compat

import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.util.DisplayMetrics
import android.util.Size
import android.view.Display
import androidx.annotation.RequiresApi
import androidx.core.hardware.display.DisplayManagerCompat
import com.itoys.expansion.then
import com.itoys.expansion.windowService
import com.itoys.utils.SysUtils

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 30/03/2023
 * @desc compat about screen size.
 */
object ScreenSizeCompat {

    fun screenSize(context: Context): Size {
        return screenApi.getScreenSize(context)
    }

    private val screenApi: Api = (SysUtils.isAndroid11()).then(
        { ApiLiveR() },
        { Api() }
    )

    private open class Api {

        open fun getScreenSize(context: Context): Size {
            val display = context.windowService?.defaultDisplay
            val metrics = (display != null).then(
                { DisplayMetrics().also { display!!.getMetrics(it) } },
                { Resources.getSystem().displayMetrics }
            )

            return Size(metrics.widthPixels, metrics.heightPixels)
        }
    }

    private class ApiLiveR : Api() {

        @RequiresApi(Build.VERSION_CODES.R)
        override fun getScreenSize(context: Context): Size {
            val metrics = context.windowService?.currentWindowMetrics ?: return Size(-1, -1)

            return Size(metrics.bounds.width(), metrics.bounds.height())
        }
    }
}