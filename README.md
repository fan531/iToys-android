## iToys

### 目录结构

``` text
.Android Application
├── app             # app example;
├── iToys-build     # 项目配置模块;
├── iToys-env       # 环境切换模块;
├── iToys-login     # 登录模块;
└── iToys-modules   # lib 模块;
```

### 使用说明

#### 资源

> color、icon资源统一放到`modules-lib:lib-theme`中。

* color命名方式：`color_色值`，例如：`color_388e3c`。注意：色值需要小写。
* icon命名方式：`theme_icon_作用/功能`，例如：`theme_icon_back`。

### 使用的三方库

* [AutoSize](https://github.com/JessYanCoding/AndroidAutoSize): 今日头条屏幕适配方案终极版，一个极低成本的 Android 屏幕适配方案
* [ImmersionBar](https://github.com/gyf-dev/ImmersionBar): android 4.4以上沉浸式状态栏和沉浸式导航栏管理
* [TitleBar](https://github.com/getActivity/TitleBar): Android 标题栏框架，从此布局属性不用记
* [SmartRefreshLayout](https://github.com/scwang90/SmartRefreshLayout): Android智能下拉刷新框架，支持越界回弹、越界拖动，具有极强的扩展性，集成了几十种炫酷的Header和 Footer

### TODO list

 - [ ] 网络请求、数据存储
 - [ ] 缺省页
 - [ ] 图片选择器
 - [ ] 完善工具类、扩展
 - [ ] 其他