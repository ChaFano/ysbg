# 云尚办公开源项目

## 技术栈

>后端: SpringBoot 、MySQL、Mybatis Plus
>
>前端: VUE、Axios
>


## github 远程推送 443 问题
>1、有的是网络问题 网上一般说是 https 和 http 代理问题，一般需要配置一下，但我的貌似不起作用 
> stackOverflow 博客：https://stackoverflow.com/questions/27087483/how-to-resolve-git-pull-fatal-unable-to-access-https-github-com-empty
>
>2、我遇到的时候解决方案 将远程地址解除关联 git remote rm origin ,再重新关联 git remote add origin 远程地址，再次推送代码成功。
>github官方博客地址: https://docs.github.com/en/get-started/getting-started-with-git/managing-remote-repositories


## 日志

### 2023-8-2
>1、Knife4j 测试文档路径: http://localhost:8800/doc.html
>
>2、数据文件路径: model/resources/static/ guigu-oa.sql
>
>3、初始化项目的基本工程


### 2023-8-3
>1、MybatisPlus 分页插件配置 在启动类路径下生效，公共类下不生效,原因是 idea maven 将 service-util 模块给忽略了
>
>2、系统角色 crud 功能实现
>
>3、全局异常处理,使用的是 Aop 技术, 自定义异常类，在全局异常类里面处理这个异常，在业务中抛出异常便可。
>
>4、前端模板搭建使用 VUE Admin Template 简化版脚手架实现 系统角色页面的增删改查 实现
>
>5、用户管理的 crud  前端实现，给用户分配角色
>

### 2023-8-4
>1、菜单管理 crud 权限分配问题解决，原因是未配置请求导致获取不到数据
>
>2、






