package com.itoys.image.glide

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import com.itoys.image.glide.http.OkHttpUrlLoader
import com.itoys.image.glide.progress.ProgressManager.glideProgressInterceptor
import okhttp3.OkHttpClient
import java.io.InputStream

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 17/04/2023
 * @desc
 */
@GlideModule(glideName = "IToysGlide")
class IToysGlideModule : AppGlideModule() {

    companion object {

        /**
         * 图片缓存文件最大值为100Mb
         */
        private const val IMAGE_DISK_CACHE_MAX_SIZE = 100 * 1024 * 1024

        private const val DISK_CACHE_NAME = "IToysImage"

        /** 把Glide配置方法进行暴露接口 */
        var options: IToysGlideOptions? = null
    }

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        super.applyOptions(context, builder)

        builder.setDiskCache(
            InternalCacheDiskCacheFactory(
                context,
                DISK_CACHE_NAME,
                IMAGE_DISK_CACHE_MAX_SIZE.toLong()
            )
        )

        val calculator = MemorySizeCalculator.Builder(context).build()
        val defaultMemoryCacheSize = calculator.memoryCacheSize
        val defaultBitmapPoolSize = calculator.bitmapPoolSize
        val customMemoryCacheSize = (1.2 * defaultMemoryCacheSize).toInt()
        val customBitmapPoolSize = (1.2 * defaultBitmapPoolSize).toInt()
        builder.setMemoryCache(LruResourceCache(customMemoryCacheSize.toLong()))
        builder.setBitmapPool(LruBitmapPool(customBitmapPoolSize.toLong()))

        options?.applyOptions(context, builder)
    }

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        super.registerComponents(context, glide, registry)
        // 下载进度的实现
        registry.replace(
            GlideUrl::class.java,
            InputStream::class.java,
            OkHttpUrlLoader.Factory(OkHttpClient.Builder().glideProgressInterceptor().build())
        )
        options?.registerComponents(context, glide, registry)
    }

    override fun isManifestParsingEnabled(): Boolean {
        return options?.isManifestParsingEnabled() ?: false
    }
}

/**
 * 把Glide配置方法进行保留, 根据需要去定制
 */
interface IToysGlideOptions {

    fun isManifestParsingEnabled() = false

    fun applyOptions(context: Context, builder: GlideBuilder) { /** 空实现 */ }

    fun registerComponents(context: Context, glide: Glide, registry: Registry) { /** 空实现 */ }
}