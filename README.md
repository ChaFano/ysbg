# 云尚办公开源项目

## 技术栈

>后端: SpringBoot + MyBatisPlus + SpringSecurity + Redis + Activiti+ MySQL
>
>前端: vue-admin-template + Node.js + Npm + Vue + ElementUI + Axios
>


## github 远程推送 443 问题
>1、有的是网络问题 网上一般说是 https 和 http 代理问题，一般需要配置一下，但我的貌似不起作用 
> stackOverflow 博客：https://stackoverflow.com/questions/27087483/how-to-resolve-git-pull-fatal-unable-to-access-https-github-com-empty
>
>2、我遇到的时候解决方案 将远程地址解除关联 git remote rm origin ,再重新关联 git remote add origin 远程地址，再次推送代码成功。
>github官方博客地址: https://docs.github.com/en/get-started/getting-started-with-git/managing-remote-repositories

## 工具

> 1、yml语法检测网站 : https://www.yamllint.com

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
>2、角色管理 后端实现，前端调试通过

### 2023-8-5
>1、加入 spring security 组件做授权与认证
>
>AuthenticationManager 在校验用户身份时需要以下组件
>>
>>1、一个实现了 UserDetailsService 接口的类，用于加载用户的详细信息。
>>
>>2、一个实现了 PasswordEncoder 接口的类，用于对用户密码进行加密。
>>
>>3、一个实现了 AuthenticationProvider 接口的类，包含了具体的用户身份认证逻辑。
>
>
>
>2、WebSecurityConfig 配置 (extends WebSecurityConfigurerAdapter)
>> 1、configure(HttpSecurity http) 这是配置的关键，决定哪些接口开启防护，哪些接口绕过防护
>>
>> 2、configure(AuthenticationManagerBuilder auth) 加密认证的算法配置
>>
>> 3、configure(WebSecurity web) 配置哪些请求不用拦截，一般包含一些资源图片，接口文档地址等
>
>3、自定义加密组件 CustomMd5PasswordEncoder 类
>
>4、UserDetailsServiceImpl (service-oa) 重写 loadUserByUsername(String username) 方法 
>
>5、TokenLoginFilter(登录过滤器，继承UsernamePasswordAuthenticationFilter，对用户名密码进行登录校验)
>
>6、TokenAuthenticationFilter(认证解析token过滤器)
>
>7、问题：权限分配未生效 除了超级管理员外，普通用户不能登录 
>
> 原因 findListByUserId(Long userId) 该方法执行报错  Invalid bound statement (not found),
>
>排除 sql 语句错误 ，pom 打包资源文件 .xml 过滤掉的错误 ，具体原因还没有找出来
>
>原因没有加配置( mapper-locations: classpath:com/atguigu/auth/mapper/xml/*.xml ),其中耗时一下午 😭 





