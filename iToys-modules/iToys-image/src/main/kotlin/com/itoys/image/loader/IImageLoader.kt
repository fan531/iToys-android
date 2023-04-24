package com.itoys.image.loader

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.RawRes

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 22/04/2023
 * @desc 图片 loader 接口
 */
interface IImageLoader {

    /**
     * 加载本地图片
     */
    fun loadImage(context: Context, @DrawableRes @RawRes drawableId: Int)
}