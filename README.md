# 资源库管理系统 (Resource Library System)
## 项目简介
资源库管理系统是一个基于Spring Boot 3.4.1开发的RESTful API服务，用于管理各类资源的存储、检索和权限控制。系统实现了用户认证、角色管理、权限控制等功能，采用JWT进行身份验证。

## 技术栈
+ **后端框架**: Spring Boot 3.4.1
+ **数据库**: MySQL
+ **ORM**: MyBatis
+ **认证授权**: Sa-Token + JWT
+ **API文档**: (待添加，建议使用Swagger/OpenAPI)
+ **日志**: Log4j2
+ **项目构建**: Maven

## 核心功能
### 1. 用户管理
+ 用户注册
+ 用户登录（JWT认证）
+ 用户信息管理
+ 密码修改

### 2. 角色权限管理
+ 角色的CRUD操作
+ 权限分配
+ 基于注解的权限控制
+ 多角色支持

### 3. 资源管理
+ 资源上传
+ 资源检索
    - 关键词搜索
    - 分类搜索
    - 作者搜索
    - 时间范围搜索
+ 资源更新
+ 资源软删除

## API接口说明
### 用户相关接口
```plain
POST   /api/resources/sign          # 用户注册
POST   /api/resources/login         # 用户登录
GET    /api/resources/userInfo      # 获取用户信息
GET    /api/resources/user/details  # 获取用户详细信息
PUT    /api/resources/user/update   # 更新用户信息
PUT    /api/resources/user/password # 修改密码
```

### 角色管理接口
```plain
GET    /api/resources/public/getRoles    # 获取所有角色
GET    /api/resources/roles/{roleId}     # 获取角色详情
POST   /api/resources/roles              # 创建角色
PUT    /api/resources/roles/{roleId}     # 更新角色
DELETE /api/resources/roles/{roleId}     # 删除角色
```

### 资源管理接口
```plain
POST   /api/resources/admin/add          # 添加资源
GET    /api/resources/admin/delete/{id}  # 删除资源
PUT    /api/resources/admin/update       # 更新资源
GET    /api/resources/public/get         # 获取资源列表
GET    /api/resources/public/search      # 综合搜索
```

## 安全特性
1. **认证机制**
    - 基于Sa-Token的JWT认证
    - Token过期机制
    - 密码加密存储（待实现）
2. **权限控制**
    - 基于注解的权限控制
    - 角色基础的访问控制
    - API访问权限控制
3. **跨域配置**
    - 支持CORS
    - 预检请求处理

## 项目结构
```plain
src/main/java/top/yuxs/resourcelibrarysystem/
├── annotation        # 自定义注解
├── config           # 配置类
├── controller       # 控制器
├── DTO             # 数据传输对象
├── exception       # 异常处理
├── interceptor     # 拦截器
├── mapper          # MyBatis映射接口
├── pojo            # 实体类
└── service         # 业务逻辑层
```

## 开发环境要求
+ JDK 17+
+ Maven 3.9+
+ MySQL 8.0+
+ IDE推荐: IntelliJ IDEA

## 部署说明
1. **环境准备**
    - 安装JDK 17
    - 安装MySQL 8.0
    - 配置Maven环境
2. **配置修改**
    - 修改`application.yaml`中的数据库连接信息
    - 配置日志路径（如需要）
    - 配置跨域设置（如需要）
3. **数据库初始化**
    - 执行数据库建表脚本（待补充）
    - 初始化基础数据（待补充）
4. **应用启动**

```bash
mvn clean package
java -jar target/ResourceLibrarySystem-1.0.0-BETA.jar
```

## 待优化项目
1. 添加API文档（Swagger/OpenAPI）
2. 完善密码加密机制
3. 添加缓存层
4. 完善日志系统
5. 添加单元测试
6. 完善异常处理机制
7. 添加数据验证
8. 实现文件上传功能

## 维护者
+ Yu DongXing (yuxs2022@163.com)

## 许可证
[待补充]

