# 2021年基于Android MVVM框架的前端设计

全文环绕项目是project，且MyApplication3已被废弃

## What's Android MVVM architecture?

![Screenshot](reference.png)

## 核心功能

 - 网络请求 - Retrofit2

 - 存储 - SharePreferences

 - 路由 - ARouter

 - 数据绑定 - DataBinding
 
 - 动态渲染 - EventBus

## 需要添加的配置

- Application的build.grade

``` java
buildscript {
    .....
    dependencies {
        classpath 'com.android.tools.build:gradle:3.5.2'
        classpath 'com.jakewharton:butterknife-gradle-plugin:9.0.0'
    }
}

allprojects {
    repositories {
        .....
        maven { url "https://jitpack.io" }
        maven { url 'https://dl.google.com/dl/android/maven2/' }
        mavenLocal()
    }
}
```

- app的build.grade

``` java
android {
    .....
    defaultConfig {
        .....

        dataBinding {
            enabled = true
        }

        javaCompileOptions {
            annotationProcessorOptions {
                arguments = [AROUTER_MODULE_NAME: project.getName()]
            }
        }
    }
   
}

dependencies {
    .....

    /* 修饰器 */
    implementation 'com.jakewharton:butterknife:8.1.0'
    annotationProcessor 'com.jakewharton:butterknife-compiler:8.1.0'

    /* 网络请求 */
    implementation 'com.zhy:okhttputils:2.6.2'
    implementation "io.reactivex.rxjava2:rxjava:2.1.0"
    implementation "io.reactivex.rxjava2:rxandroid:2.0.1"
    implementation 'com.squareup.retrofit2:retrofit:2.4.0'
    implementation 'com.squareup.retrofit2:adapter-rxjava2:2.3.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.3.0'
    implementation 'com.squareup.okhttp3:logging-interceptor:3.8.1'

    /* 路由 */
    implementation 'com.alibaba:arouter-api:1.4.1'
    annotationProcessor 'com.alibaba:arouter-compiler:1.2.2'
    
    /* 消息总线 */
    implementation 'org.greenrobot:eventbus:3.0.0'

    /* 基本布局 */
    implementation 'com.android.support:recyclerview-v7:30+'
    implementation 'com.android.support:design:30+'
    implementation 'com.android.support:cardview-v7:30+'
    implementation 'com.squareup.picasso:picasso:2.3.2'
    
    /* 第三方UI/UX库 */

    // 过度按钮
    implementation 'com.royrodriguez:transitionbutton:0.2.0'

    // 涟漪背景 -- https://github.com/skyfishjy/android-ripple-background
    implementation 'com.skyfishjy.ripplebackground:library:1.0.1'

    // 电源菜单 -- https://github.com/skydoves/powermenu
    implementation "com.github.skydoves:powermenu:2.1.9"
    
}
```

## 当前进度

 - 已实现了路由path与uri跳转
 - 路由跳转附带的参数可被单向绑定
 - 导入了一些UI/UX库控件
 - 简单实现了动态渲染
 - 可用retrofit2访问到数据

![Screenshot](demo.gif)
