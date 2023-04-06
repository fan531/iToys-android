## iToys-kit

使用:
```groovy
1. 添加依赖
// 开发环境
debugImplementation(Modules.iToysKit(project))
// 发布环境
releaseImplementation(Modules.iToysKitNoop(project))

2. 初始化

// 集成 itoys-kit, 已区分开发模式和发布模式, 开发模式可定义些功能
IToysKit.Builder(application = this).build()
```


添加, 删除kit
修改`assets`目录下`tool_panels.json`文件

修改后同步在`ToolPanelUriParse.kt`增加相应跳转
