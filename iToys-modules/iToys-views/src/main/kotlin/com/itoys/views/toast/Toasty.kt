package com.itoys.views.toast

import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.annotation.StringRes
import com.itoys.expansion.backgroundExt
import com.itoys.expansion.color
import com.itoys.expansion.drawable
import com.itoys.expansion.layoutInflater
import com.itoys.expansion.tint9PatchDrawable
import com.itoys.expansion.tintIcon
import com.itoys.views.R
import com.itoys.views.databinding.ItoysViewsLayoutToastyBinding

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 09/04/2023
 * @desc toasty
 */
object Toasty {

    private var currentTypeface = Typeface.create("sans-serif", Typeface.NORMAL)

    // sp
    private var toastTextSize: Int = 14

    private var tintIcon: Boolean = false
    private var allowQueue: Boolean = true
    private var toastGravity: Int = Gravity.CENTER
    private var xOffset: Int = 0
    private var yOffset: Int = 0
    private var supportDarkTheme: Boolean = true
    private var isRTL: Boolean = false
    private var lastToast: Toast? = null

    const val LENGTH_SHORT: Int = Toast.LENGTH_SHORT
    const val LENGTH_LONG: Int = Toast.LENGTH_LONG

    fun normal(
        context: Context,
        @StringRes message: Int,
        icon: Drawable? = null,
        duration: Int = LENGTH_SHORT,
    ): Toast {
        return normalWithDarkThemeSupport(context, context.getString(message), icon, duration)
    }

    fun normal(
        context: Context,
        message: CharSequence,
        icon: Drawable? = null,
        duration: Int = LENGTH_SHORT,
    ): Toast {
        return normalWithDarkThemeSupport(context, message, icon, duration)
    }

    fun info(
        context: Context,
        @StringRes message: Int,
        duration: Int = LENGTH_SHORT,
    ): Toast {
        val infoIcon = context.drawable(R.drawable.itoys_views_ic_info_outline_white_24dp)

        return custom(
            context = context,
            message = context.getString(message),
            icon = infoIcon,
            tintColor = context.color(com.itoys.theme.R.color.theme_colorful_3F51B5),
            textColor = context.color(com.itoys.theme.R.color.theme_colorful_white),
            withIcon = infoIcon != null,
            duration = duration,
            shouldTint = true
        )
    }

    fun info(
        context: Context,
        message: CharSequence,
        duration: Int = LENGTH_SHORT,
    ): Toast {
        val infoIcon = context.drawable(R.drawable.itoys_views_ic_info_outline_white_24dp)

        return custom(
            context = context,
            message = message,
            icon = infoIcon,
            tintColor = context.color(com.itoys.theme.R.color.theme_colorful_3F51B5),
            textColor = context.color(com.itoys.theme.R.color.theme_colorful_white),
            withIcon = infoIcon != null,
            duration = duration,
            shouldTint = true
        )
    }

    fun warning(
        context: Context,
        @StringRes message: Int,
        duration: Int = LENGTH_SHORT,
    ): Toast {
        val warningIcon = context.drawable(R.drawable.itoys_views_ic_warning_outline_white_24dp)

        return custom(
            context = context,
            message = context.getString(message),
            icon = warningIcon,
            tintColor = context.color(com.itoys.theme.R.color.theme_colorful_FFA900),
            textColor = context.color(com.itoys.theme.R.color.theme_colorful_white),
            withIcon = warningIcon != null,
            duration = duration,
            shouldTint = true
        )
    }

    fun warning(
        context: Context,
        message: CharSequence,
        duration: Int = LENGTH_SHORT,
    ): Toast {
        val warningIcon = context.drawable(R.drawable.itoys_views_ic_warning_outline_white_24dp)

        return custom(
            context = context,
            message = message,
            icon = warningIcon,
            tintColor = context.color(com.itoys.theme.R.color.theme_colorful_FFA900),
            textColor = context.color(com.itoys.theme.R.color.theme_colorful_white),
            withIcon = warningIcon != null,
            duration = duration,
            shouldTint = true
        )
    }

    fun success(
        context: Context,
        @StringRes message: Int,
        duration: Int = LENGTH_SHORT,
    ): Toast {
        val successIcon = context.drawable(R.drawable.itoys_views_ic_check_white_24dp)

        return custom(
            context = context,
            message = context.getString(message),
            icon = successIcon,
            tintColor = context.color(com.itoys.theme.R.color.theme_colorful_388E3C),
            textColor = context.color(com.itoys.theme.R.color.theme_colorful_white),
            withIcon = successIcon != null,
            duration = duration,
            shouldTint = true
        )
    }

    fun success(
        context: Context,
        message: CharSequence,
        duration: Int = LENGTH_SHORT,
    ): Toast {
        val successIcon = context.drawable(R.drawable.itoys_views_ic_check_white_24dp)

        return custom(
            context = context,
            message = message,
            icon = successIcon,
            tintColor = context.color(com.itoys.theme.R.color.theme_colorful_388E3C),
            textColor = context.color(com.itoys.theme.R.color.theme_colorful_white),
            withIcon = successIcon != null,
            duration = duration,
            shouldTint = true
        )
    }

    fun error(
        context: Context,
        @StringRes message: Int,
        duration: Int = LENGTH_SHORT,
    ): Toast {
        val errorIcon = context.drawable(R.drawable.itoys_views_ic_error_white_24dp)

        return custom(
            context = context,
            message = context.getString(message),
            icon = errorIcon,
            tintColor = context.color(com.itoys.theme.R.color.theme_colorful_D50000),
            textColor = context.color(com.itoys.theme.R.color.theme_colorful_white),
            withIcon = errorIcon != null,
            duration = duration,
            shouldTint = true
        )
    }

    fun error(
        context: Context,
        message: CharSequence,
        duration: Int = LENGTH_SHORT,
    ): Toast {
        val errorIcon = context.drawable(R.drawable.itoys_views_ic_error_white_24dp)

        return custom(
            context = context,
            message = message,
            icon = errorIcon,
            tintColor = context.color(com.itoys.theme.R.color.theme_colorful_D50000),
            textColor = context.color(com.itoys.theme.R.color.theme_colorful_white),
            withIcon = errorIcon != null,
            duration = duration,
            shouldTint = true
        )
    }

    private fun normalWithDarkThemeSupport(
        context: Context,
        message: CharSequence,
        icon: Drawable? = null,
        duration: Int = LENGTH_SHORT,
    ): Toast {
        return withDarkTheme(context, message, icon, duration)
    }

    private fun withLightTheme(
        context: Context,
        message: CharSequence,
        icon: Drawable?,
        duration: Int,
    ): Toast {
        return custom(
            context = context,
            message = message,
            icon = icon,
            tintColor = context.color(com.itoys.theme.R.color.theme_colorful_white),
            textColor = context.color(com.itoys.theme.R.color.theme_colorful_353A3E),
            duration = duration,
            withIcon = icon != null,
            shouldTint = true,
        )
    }

    private fun withDarkTheme(
        context: Context,
        message: CharSequence,
        icon: Drawable?,
        duration: Int,
    ): Toast {
        return custom(
            context = context,
            message = message,
            icon = icon,
            tintColor = context.color(com.itoys.theme.R.color.theme_colorful_353A3E),
            textColor = context.color(com.itoys.theme.R.color.theme_colorful_white),
            duration = duration,
            withIcon = icon != null,
            shouldTint = true,
        )
    }

    fun custom(
        context: Context,
        message: CharSequence,
        icon: Drawable?,
        @ColorInt tintColor: Int,
        @ColorInt textColor: Int,
        duration: Int,
        withIcon: Boolean,
        shouldTint: Boolean,
    ): Toast {
        val currentToast = Toast.makeText(context, "", duration)
        val layoutInflater = context.layoutInflater ?: LayoutInflater.from(context)
        val toastLayoutBinding = ItoysViewsLayoutToastyBinding.inflate(layoutInflater)
        toastLayoutBinding.viewsToastyRoot.backgroundExt(
            if (shouldTint) {
                context.tint9PatchDrawable(R.drawable.itoys_views_toast_frame)?.tintIcon(tintColor)
            } else {
                context.drawable(R.drawable.itoys_views_toast_frame)
            }
        )

        if (withIcon) {
            toastLayoutBinding.viewsToastyIcon.visibility = View.VISIBLE
            if (isRTL) {
                toastLayoutBinding.viewsToastyRoot.layoutDirection = View.LAYOUT_DIRECTION_RTL
            }

            toastLayoutBinding.viewsToastyIcon.backgroundExt(
                if (tintIcon) {
                    icon?.tintIcon(tintColor)
                } else {
                    icon
                }
            )
        }

        toastLayoutBinding.viewsToastyText.apply {
            text = message
            setTextColor(textColor)
            setTypeface(currentTypeface, Typeface.NORMAL)
            setTextSize(TypedValue.COMPLEX_UNIT_SP, toastTextSize.toFloat())
        }

        currentToast.view = toastLayoutBinding.root

        if (!allowQueue) {
            lastToast?.cancel()
            lastToast = currentToast
        }

        currentToast.setGravity(toastGravity, xOffset, yOffset)
        return currentToast
    }

    class Config {

        companion object {
            val INSTANCE = Config()
        }

        private var typeface = currentTypeface
        private var textSize = Toasty.toastTextSize
        private var tintIcon = Toasty.tintIcon
        private var allowQueue = Toasty.allowQueue
        private var toastGravity = Toasty.toastGravity
        private var xOffset = Toasty.xOffset
        private var yOffset = Toasty.yOffset
        private var supportDarkTheme = Toasty.supportDarkTheme
        private var isRTL = Toasty.isRTL

        fun setToastTypeface(typeface: Typeface): Config {
            this.typeface = typeface
            return this
        }

        fun setTextSize(textSize: Int): Config {
            this.textSize = textSize
            return this
        }

        fun tintIcon(tintIcon: Boolean): Config {
            this.tintIcon = tintIcon
            return this
        }

        fun allowQueue(allowQueue: Boolean): Config {
            this.allowQueue = allowQueue
            return this
        }

        fun setGravity(gravity: Int): Config {
            this.toastGravity = gravity
            return this
        }

        fun supportDarkTheme(supportDarkTheme: Boolean): Config {
            this.supportDarkTheme = supportDarkTheme
            return this
        }

        fun setRTL(isRTL: Boolean): Config {
            this.isRTL = isRTL
            return this
        }

        fun build() {
            currentTypeface = typeface
            Toasty.toastTextSize = textSize
            Toasty.tintIcon = tintIcon
            Toasty.allowQueue = allowQueue
            Toasty.toastGravity = toastGravity
            Toasty.xOffset = xOffset
            Toasty.yOffset = yOffset
            Toasty.supportDarkTheme = supportDarkTheme
            Toasty.isRTL = isRTL
        }
    }
}