## module-evn

切换环境

* [env-annotation](env-annotation): 注解, 注解处理器
* [env-iml](env-iml): 具体实现

如何使用

修改`EnvApiConfig.kt`中注解即可, 也可以新增.


运行报错

如果运行过程中找不到`IToysEnvApiConfig.kt`错误, 删除`env-iml\build\generated\ksp`目录后重新运行. 
目前只找到这一种解决方案, 后期在优化.