# 2021年leangoo开发的Android项目

 - 网络请求 - okHttp

 - 懒加载按钮 - KTLoadingButton

 - 上拉刷新 - Taurus

 - 美化控件 - material

## 当下需解决的问题

``` Bash
Error:Error converting bytecode to dex:
Cause: Dex cannot parse version 52 byte code.
This is caused by library dependencies that have been compiled using Java 8 or above.
If you are using the 'java' gradle plugin in a library submodule add 
targetCompatibility = '1.7'
sourceCompatibility = '1.7'
to that submodule's build.gradle file.
```

``` Bash
Error:Execution failed for task ':app:transformClassesWithDexForDebug'.
> com.android.build.api.transform.TransformException: java.lang.RuntimeException: 
> java.lang.RuntimeException: com.android.ide.common.process.ProcessException: 
> java.util.concurrent.ExecutionException: 
> com.android.ide.common.process.ProcessException: Return code 1 for dex process
```

一种说辞
> 由于我导的依赖已经用上了java8和grade 3.0.0以上，而我并没有
>> 当前 API 24
>>> 相关解决:https://github.com/lottie-react-native/lottie-react-native/issues/345

问题已解决
> 文献:https://www.programmersought.com/article/74234168423/

## 心态良好的问题

``` Bash
Caused by:android.view.InflateException: Binary XML file line #22: Binary XML file line #22: Error inflating class ...
Caused by:android.view.InflateException: Binary XML file line #22: Error inflating class
Caused by: java.lang.ClassNotFoundException: Didn't find class ...
```
> 外部库疑似迁移至Androidx以致Api不能被使用？

> 可以通过某种修改达到适配旧版本运行的目的
