package com.itoys.depends

/**
 * @author Fanfan.gu <a href="mailto:fanfan.work@outlook.com">Contact me.</a>
 * @date 08/10/2022
 * @desc jetpack depends.
 * @link https://developer.android.com/jetpack
 */
object Jetpacks {

    /** 访问基于 activity 构建的可组合 API。 */
    private const val activity_version = "1.6.0"
    const val activity_ktx = "androidx.activity:activity-ktx:$activity_version"

    /** 让您可以在平台的旧版 API 上访问新 API（很多都符合 Material Design 准则）。 */
    private const val appcompat_version = "1.5.1"
    const val appcompat = "androidx.appcompat:appcompat:$appcompat_version"

    /** 为用户构建自定义的应用内搜索功能。 */
    private const val appsearch_version = "1.1.0-alpha02"
    const val appsearch = "androidx.appsearch:appsearch:$appsearch_version"
    // // Use kapt instead of annotationProcessor if writing Kotlin classes
    const val appsearch_compiler = "androidx.appsearch:appsearch-compiler:$appsearch_version"
    const val appsearch_local = "androidx.appsearch:appsearch-local-storage:$appsearch_version"
    // PlatformStorage is compatible with Android 12+ devices, and offers additional features
    // to LocalStorage.
    const val appsearch_storage = "androidx.appsearch:appsearch-platform-storage:$appsearch_version"

    /** 构建移动相机应用。 */
    private const val camerax_version = "1.2.0-beta02"
    // The following line is optional, as the core library is included indirectly by camera-camera2
    const val camera_core = "androidx.camera:camera-core:$camerax_version"
    const val camera2 = "androidx.camera:camera-camera2:$camerax_version"
    // If you want to additionally use the CameraX Lifecycle library
    const val camera_lifecycle = "androidx.camera:camera-lifecycle:$camerax_version"
    // If you want to additionally use the CameraX VideoCapture library
    const val camera_video = "androidx.camera:camera-video:$camerax_version"
    // If you want to additionally use the CameraX View class
    const val camera_view = "androidx.camera:camera-view:$camerax_version"
    // If you want to additionally add CameraX ML Kit Vision Integration
    const val camera_vision = "androidx.camera:camera-mlkit-vision:$camerax_version"
    // If you want to additionally use the CameraX Extensions library
    const val camera_ext = "androidx.camera:camera-extensions:$camerax_version"

    /** 将您的应用细分为在一个 Activity 中托管的多个独立屏幕。 */
    private const val fragment_version = "1.5.3"
    const val fragment_ktx = "androidx.fragment:fragment-ktx:$fragment_version"
    // Testing Fragments in Isolation
    const val fragment_test = "androidx.fragment:fragment-testing:$fragment_version"

    /** 构建生命周期感知型组件，这些组件可以根据 activity 或 fragment 的当前生命周期状态调整行为。 */
    private const val lifecycle_version = "2.5.1"
    const val view_model_ktx = "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version"
    const val viewModelCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:$lifecycle_version"
    const val live_data_ktx = "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version"
    // Lifecycles only (without ViewModel or LiveData)
    const val lifecycle = "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycle_version"
    // Saved state module for ViewModel
    const val save_state = "androidx.lifecycle:lifecycle-viewmodel-savedstate:$lifecycle_version"
    // Kapt Annotation processor
    const val lifecycle_compiler = "androidx.lifecycle:lifecycle-compiler:$lifecycle_version"

    /** 适用于 Android 的模块化、可自定义 Material Design 界面组件 */
    private const val material_version = "1.6.1"
    const val material = "com.google.android.material:material:$material_version"

    /** 创建、存储和管理由 SQLite 数据库支持的持久性数据。 */
    private const val room_version = "2.4.3"
    const val room_runtime = "androidx.room:room-runtime:$room_version"
    const val room_compiler = "androidx.room:room-compiler:$room_version"
    // To use Kotlin annotation processing tool (kapt)
    const val room_compiler_ktx = "androidx.room:room-compiler:$room_version"
    // To use Kotlin Symbol Processing (KSP)
    const val room_compiler_ksp = "androidx.room:room-compiler:$room_version"
    // optional - Guava support for Room, including Optional and ListenableFuture
    const val room_guava = "androidx.room:room-guava:$room_version"
    // optional - Test helpers
    const val room_test = "androidx.room:room-testing:$room_version"

    /** 调度和执行可延期且基于约束条件的后台任务。 */
    private const val work_version = "2.7.1"
    const val work_runtime_ktx = "androidx.work:work-runtime-ktx:$work_version"
    // optional - Test helpers
    const val work_test = "androidx.work:work-testing:$work_version"
    // optional - Multiprocess support
    const val work_process = "androidx.work:work-multiprocess:$work_version"

    /** 公开元数据，帮助工具开发者和其他开发者了解您的应用代码。 */
    const val annotation = "androidx.annotation:annotation:1.5.0"
    // To use the Java-compatible @Experimental API annotation
    const val annotation_experimental = "androidx.annotation:annotation-experimental:1.3.0"

    /** 通过生物识别特征或设备凭据进行身份验证，以及执行加密操作。 */
    const val biometric = "androidx.biometric:biometric-ktx:1.2.0-alpha05"

    /** 用圆角和阴影实现 Material Design 卡片图案。 */
    const val card_view = "androidx.cardview:cardview:1.0.0"

    /** 降低现有和新的小型集合对内存的影响。 */
    const val collection ="androidx.collection:collection-ktx:1.2.0"

    /** 在当前设备和版本更低的设备上显示表情符号。 */
    private const val emoji2_version = "1.2.0"
    const val emoji2 = "androidx.emoji2:emoji2:$emoji2_version"
    const val emoji2_views = "androidx.emoji2:emoji2-views:$emoji2_version"
    const val emoji2_views_helper = "androidx.emoji2:emoji2-views-helper:$emoji2_version"

    /** 实现网格布局。 */
    const val gridlayout = "androidx.gridlayout:gridlayout:1.0.0"

    /** 在旧版平台上使用动画插值器。 */
    const val interpolator = "androidx.interpolator:interpolator:1.0.0"

    /** 启用您的 Android 应用以评估 JavaScript。 */
    const val java_script_engine = "androidx.javascriptengine:javascriptengine:1.0.0-alpha02"

    /** 在搭载 Android 5 之前版本的设备上部署包含多个 dex 文件的应用。 */
    const val multidex = "androidx.multidex:multidex:2.0.1"

    /** 在您的界面中显示大量数据，同时最大限度减少内存用量。 */
    const val recyclerview = "androidx.recyclerview:recyclerview:1.2.1"
    const val recyclerview_selection = "androidx.recyclerview:recyclerview-selection:1.1.0"

    /** 以可滑动的格式显示视图或 Fragment。 */
    const val viewpager2 = "androidx.viewpager2:viewpager2:1.0.0"
}