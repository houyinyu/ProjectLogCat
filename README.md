# ProjectLogCat
项目内显示日志，可以清除和保存本地

## 使用方式

```Java
    implementation 'com.github.houyinyu:ProjectLogCat:1.4'
```



### Github依赖创建方式

1.在依赖的build.gradle 下添加如下代码
```Java
   id 'maven-publish'
```
和
```Java
   afterEvaluate {
       publishing {
           publications {
               release(MavenPublication) {
                   from components.release
                   groupId = 'com.github.houyinyu'//修改成自己的
                   artifactId = 'logCatLib'//修改成自己的
                   version = '1.0'//修改成自己的
               }
           }
       }
   }
```

2.在根目录的build.gradle下添加如下代码
```Java
      dependencies {
        classpath 'com.github.dcendents:android-maven-gradle-plugin:2.1'
        }

        在新版本的studio中根目录只有plugins，没有buildscript，可直接添加在plugins上面：
        buildscript {
        dependencies {
        classpath 'com.github.dcendents:android-maven-gradle-plugin:2.1'
        }
        }
```

3.创建一个release，然后输入版本号和相关信息

4.然后打开jitpack链接 https://jitpack.io ，输入GitHub项目地址

> 注意：在app下的build.gradle中注释掉签名相关的信息

#### 通过SSH提交到Github
>* 创建SSH KEY
>* 通过SSH克隆到本地
>* 同步代码-提交并推送

#### README在线编写 https://www.zybuluo.com/mdeditor


