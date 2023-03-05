### 模块说明
本项目采用`buildPlugin`来管理app的配置文件、依赖库；
不采用`buildSrc`是因为在`buildSrc`中修改配置后整个项目都需要重新编译，太耗时；

#### depends

`depends`包中包含了app所需要的依赖库，只做了简单区分。

+ `Depends.kt`: 所有的三方依赖都在这里；
+ `Jetpacks.kt`: Android官方推荐的Jetpacks模块都在这里；
+ `Modules.kt`: 项目中所有的Module都可以在这里定义；

### extension

+ `ProjectExtension.kt`: 项目配置扩展；