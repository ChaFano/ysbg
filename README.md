# 云尚办公开源项目

## 技术栈

>后端: SpringBoot + MyBatisPlus + SpringSecurity + Redis + Activiti + MySQL
>
>前端: vue-admin-template + Node.js + Npm + Vue + ElementUI + Axios
>


## github 远程推送 443 问题
>1、有的是网络问题 网上一般说是 https 和 http 代理问题，一般需要配置一下，但我的貌似不起作用 
> stackOverflow 博客：https://stackoverflow.com/questions/27087483/how-to-resolve-git-pull-fatal-unable-to-access-https-github-com-empty
>
>2、我遇到的时候解决方案 将远程地址解除关联 git remote rm origin ,再重新关联 git remote add origin 远程地址，再次推送代码成功。
>github官方博客地址: https://docs.github.com/en/get-started/getting-started-with-git/managing-remote-repositories
>
>3、很多时候确实是网络的问题，等一哈又可以上传了

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
>

### 2023-8-6 整合 work flow (工作流引擎) activiti

>1、熟悉工作引擎基本开发流程
>
>2、tomcat 部署 work flow 画图工具
>
>3、工作流画图工具使用 
>
>4、操作流程
>> 1、启动流程实列
>>
>> 2、查询任务
>>
>> 3、处理任务
>>
>>4、查询已处理任务
>>
>>5、其他详细操作
>
>

### 2023-8-7 审批设置 和 审批管理

>1、审批甚至 CRUD 实现
>
>2、审批管理实现一部分
>
>3、问题 ( mapper-locations: classpath:com/atguigu/*/mapper/xml/*.xml ) 解决 auth和process 下xml 找不到会报错。
> 

### 2023-8-8 前端审批

> 1、整合前端审批内容 和后端接口
>
> 2、启动审批页面、启动审批流程、待处理审批
>
> 3、审批发起、待处理、已处理、 测试通过 准备回顾知识点 写笔记。

### 2023-8-9 项目回顾

>1、回顾公共方法的封装 泛型、枚举的使用，jwt 、中间件辅助配置类的使用
>
>2、对持久层 model 设计理解 BaseEntity 封装公共属性
>
>3、对构建树形菜单的理解 

```sql
WITH RECURSIVE MenuTree AS (
  SELECT 
    id, parent_id, name, type, path, component, perms, icon, sort_value, status
  FROM sys_menu
  WHERE parent_id = 0
  
  UNION ALL
  
  SELECT 
    m.id, m.parent_id, m.name, m.type, m.path, m.component, m.perms, m.icon, m.sort_value, m.status
  FROM sys_menu m
  INNER JOIN MenuTree mt ON m.parent_id = mt.id
)
SELECT 
  id, parent_id, name, type, path, component, perms, icon, sort_value, status
FROM MenuTree;

```

``` java 
/**
     * 使用递归方法建菜单
     * @param sysMenuList
     * @return
     */
    public static List<SysMenu> buildTree(List<SysMenu> sysMenuList) {
        List<SysMenu> trees = new ArrayList<>();
        for(SysMenu sysMenu : sysMenuList){
            if(sysMenu.getParentId().longValue() == 0){
                trees.add(findChildren(sysMenu,sysMenuList));
            }
        }
        return trees;
    }

    /**
     * 递归查找子节点
     * @param treeNodes
     * @return
     */
    private static SysMenu findChildren(SysMenu sysMenu, List<SysMenu> treeNodes){

        sysMenu.setChildren(new ArrayList<SysMenu>());

        for(SysMenu it : treeNodes){
            if(sysMenu.getId().longValue() == it.getParentId().longValue()){
                if(sysMenu.getChildren() == null){
                    sysMenu.setChildren(new ArrayList<>());
                }
                sysMenu.getChildren().add(findChildren(it,treeNodes));
            }
        }
        return sysMenu;

    }

```

>4、mybatis 里面自动映射实体类使用 resultMap 可以不用写数据 直接指定 实体类地址

```
    <resultMap id="sysMenuMap" type="com.atguigu.model.system.SysMenu" autoMapping="true">
        
    </resultMap>


    <!-- 根据用户id 查询该用户的菜单权限 -->
    <select id="findListByUserId" resultMap="sysMenuMap">
        select distinct
            m.id,m.parent_id,m.name,m.type,m.path,m.component,m.perms,
            m.icon,m.sort_value,m.status,m.create_time,m.update_time,m.is_deleted

        from sys_menu m
                 inner join sys_role_menu rm on rm.menu_id = m.id
                 inner join sys_user_role ur on ur.role_id = rm.role_id

        where ur.user_id=#{userId}
          and m.status= 1
          and rm.is_deleted=0
          and ur.is_deleted=0
          and m.is_deleted=0
    </select>
```




>5、Mybatis plus 中 LambdaQueryWrapper 的使用方式，使用 lambda 表达式 可以使用方法引用的写法 ，减少 QueryWrapper中数据库字段匹配写法。
> 
>6、对菜单处理重新理解、对角色分配重新理解、用户管理 
>
>7、UserDetailsService loadUserByUsername(username) 它是用来根据用户名加载用户信息的方法。在用户进行登录认证时，
>Spring Security 会调用 loadUserByUsername() 方法来获取用户的详细信息，包括用户名、密码和权限等，以便进行后续的认证和授权操作。 
>
>8、ThreadLocal 是 Java 中的一个类，用于在多线程环境下存储线程局部变量
>
>ThreadLocal 的主要作用包括：
 
>线程隔离： 每个线程都拥有自己的线程局部变量，这些变量在其他线程中是不可见的，从而实现了线程之间的隔离。
 
> 线程数据共享： 在多线程环境中，有些情况下需要共享某些数据，但又希望每个线程都拥有自己的副本，不会被其他线程修改影响。ThreadLocal 提供了一种方式来实现这种共享，每个线程可以独立地修改自己的副本，而不会影响其他线程。
 
>线程上下文传递： 在某些情况下，需要在线程之间传递一些上下文信息，如用户身份、请求信息等。使用 ThreadLocal 可以方便地将这些上下文信息绑定到当前线程，以供后续的处理使用。
 
> 减少同步： 在多线程环境中，如果多个线程共享同一个变量，可能会涉及到同步操作，而使用 ThreadLocal 可以避免这种同步，从而减少线程竞争和锁的使用。
>
>9、审批流程数据库表理解 
>
>10、 工作流部署流程基本理解 api 使用还不是很了解 需要去学 Activiti 框架再理解
>

### 2023-8-10 activity 理解

>1、对流程的部署、启动、查询、处理 有基本了解
>
>上述 四个步骤 部署、启动、查询、处理 对于一个 工作流任务 的基本处理
>
> 部署 一般由管理员操作
>
> 启动 一般由员工发起申请
>
> 查询和处理 一般由员工的上级领导 查看和批复
>
>2、流程定义的查询 、删除 接口的了解
>
>3、Service 接口的了解
>
>   RepositoryService | activiti的资源管理类 
>   
>   RuntimeService    | activiti的流程运行管理类 
>
>   TaskService       | activiti的任务管理类     
>
>   HistoryService    | activiti的历史管理类     
>
>   ManagerService    | activiti的引擎管理类     
>
>4、历史流程数据查询 查询员工参与的流程、查询流程执行到那一步等