package com.itoys.views.toast

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.itoys.views.R

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 13/04/2023
 * @desc Toast status.
 */
enum class ToastyStatus(
    @DrawableRes val icon: Int,
    @ColorRes val tintColor: Int,
    @ColorRes val textColor: Int,
) {
    INFO(
        icon = R.drawable.itoys_views_ic_info_outline_white_24dp,
        tintColor = com.itoys.theme.R.color.theme_colorful_3F51B5,
        textColor = com.itoys.theme.R.color.theme_colorful_white,
    ),

    WARING(
        icon = R.drawable.itoys_views_ic_warning_outline_white_24dp,
        tintColor = com.itoys.theme.R.color.theme_colorful_FFA900,
        textColor = com.itoys.theme.R.color.theme_colorful_white,
    ),

    SUCCESS(
        icon = R.drawable.itoys_views_ic_check_white_24dp,
        tintColor = com.itoys.theme.R.color.theme_colorful_388E3C,
        textColor = com.itoys.theme.R.color.theme_colorful_white,
    ),

    ERROR(
        icon = R.drawable.itoys_views_ic_error_white_24dp,
        tintColor = com.itoys.theme.R.color.theme_colorful_D50000,
        textColor = com.itoys.theme.R.color.theme_colorful_white,
    );
}