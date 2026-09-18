# 图书管理系统（Spring Boot + MyBatis + Redis）

一个完整可运行的图书管理系统，**前后端联调 + Redis 缓存 + 公网部署上线**。

- 前端页面：图书列表 / 新增 / 编辑 / 删除 / 注册 / 登录
- 后端接口：RESTful API，统一返回 `code / message / data` 格式
- 缓存：图书列表走 Redis 缓存，数据变更自动清缓存保证一致性
- 部署：内网穿透上线，**手机浏览器可直接访问**

## 技术栈

| 技术 | 说明 |
|---|---|
| Java 17 | JDK |
| Spring Boot 4.1.1 | Web 框架（IoC / 依赖注入 / 自动配置） |
| MyBatis 4.0.0 | 持久层框架，SQL 写在 XML 与代码分离 |
| MySQL | 关系型数据库 |
| Redis | 缓存（图书列表缓存 + 变更清缓存） |
| HTML / JavaScript | 前端页面（fetch 调用后端接口） |

## 系统架构

```
浏览器 / 手机
    │  HTTP (JSON)
    ▼
BookController / UserController     ← 接收请求、参数绑定（@RequestBody / @PathVariable）
    │
    ▼
BookService / UserService           ← 业务逻辑（缓存查询、注册查重、登录比对）
    │
    ├──► BookMapper / UserMapper    ← MyBatis 持久层
    │        │
    │        ▼
    │      MySQL                     ← 数据存储
    │
    └──► Redis                       ← 图书列表缓存（先查缓存 → miss 查库回写 → 变更清缓存）
```

## 功能清单

- ✅ 图书增删改查（列表 / 按 id 查询 / 新增 / 修改 / 删除）
- ✅ 用户注册（用户名查重）、登录（返回 token）
- ✅ Redis 缓存：图书列表缓存命中直接返回；增删改后清缓存保证一致性
- ✅ 前端页面：原生 HTML + fetch 前后端联调，手机可操作
- ✅ 部署上线：cpolar 内网穿透，公网可访问

## 快速启动

1. 创建数据库并建表：

```sql
CREATE DATABASE library DEFAULT CHARACTER SET utf8mb4;

USE library;

CREATE TABLE book (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    author VARCHAR(50),
    category VARCHAR(50),
    stock INT DEFAULT 0
);

INSERT INTO book (name, author, category, stock) VALUES
('Java编程思想', 'Bruce Eckel', '计算机', 10),
('MySQL必知必会', 'Ben Forta', '计算机', 5),
('活着', '余华', '文学', 8);

CREATE TABLE `user` (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL
);
```

2. 启动 Redis（Windows 版：运行 `redis-server.exe`，端口 6379）。

3. 修改 `src/main/resources/application.properties` 中的数据库密码。

4. 运行 `DemoApplication`，浏览器打开 `http://localhost:8080` 即可使用。

## 接口列表

### 图书

| 方法 | 路径 | 说明 |
|---|---|---|
| GET | `/api/books` | 查询所有图书（走 Redis 缓存） |
| GET | `/api/books/{id}` | 按 id 查询单本图书 |
| POST | `/api/books` | 新增图书（请求体传 JSON） |
| PUT | `/api/books/{id}` | 按 id 修改图书 |
| DELETE | `/api/books/{id}` | 按 id 删除图书 |

### 用户

| 方法 | 路径 | 说明 |
|---|---|---|
| POST | `/api/user/register` | 注册（用户名查重） |
| POST | `/api/user/login` | 登录，成功返回 token |

## 统一返回格式

```json
{
  "code": 200,
  "message": "成功",
  "data": [ ... ]
}
```

`code=200` 成功，`code=500` 失败，`data` 为业务数据（登录成功时是 token）。

## 部署

项目通过 **cpolar 内网穿透**部署上线，公网地址：

```
http://5d40ff8a.r3.cpolar.cn
```

> 注意：cpolar 免费版公网地址每次重启会变化；演示时保持电脑开机、项目与 Redis 运行中、cpolar 隧道开启即可。
