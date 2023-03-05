## iToys

### 目录结构

``` text
.Android Application
├── app             # app example;
├── buildPlugin     # 项目配置模块;
├── module-env      # 环境切换模块;
├── module-lib      # lib 模块;
└── module-login    # 登录模块;
```

### 使用说明

#### 资源

> color、icon资源统一放到`modules-lib:lib-theme`中。

* color命名方式：`color_色值`，例如：`color_388e3c`。注意：色值需要小写。
* icon命名方式：`theme_icon_作用/功能`，例如：`theme_icon_back`。

### 使用的三方库

* 动态申请权限: 