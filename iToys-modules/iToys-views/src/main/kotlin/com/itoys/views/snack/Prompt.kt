package com.itoys.views.snack

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.itoys.views.R

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 10/04/2023
 * @desc
 */
enum class Prompt(
    @DrawableRes val icon: Int,
    @ColorRes val backgroundColor: Int,
) {

    SUCCESS(
        R.drawable.itoys_views_ic_check_white_24dp,
        R.color.theme_colorful_388E3C
    ),
    ERROR(
        R.drawable.itoys_views_ic_error_white_24dp,
        R.color.theme_colorful_D50000
    ),
    WARNING(
        R.drawable.itoys_views_ic_warning_outline_white_24dp,
        R.color.theme_colorful_FFA900
    );
}