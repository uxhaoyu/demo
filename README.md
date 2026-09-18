# 图书管理系统后端（Spring Boot + MyBatis）

基于 **Spring Boot + MyBatis + MySQL** 的图书管理系统后端，采用经典分层架构：**Controller → Service → Mapper**，接口统一返回 `code / message / data` 格式，支持图书增删改查与用户注册登录。

## 技术栈

| 技术 | 说明 |
|---|---|
| Java 17 | JDK |
| Spring Boot 4.1.1 | Web 框架 |
| MyBatis 4.0.0 | 持久层框架（SQL 写在 XML，与代码分离） |
| MySQL | 数据库 |

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

2. 修改 `src/main/resources/application.properties` 中的数据库密码。

3. 运行 `DemoApplication`，用 IDEA HTTP Client 或 Postman 调用接口。

## 接口列表

### 图书

| 方法 | 路径 | 说明 |
|---|---|---|
| GET | `/api/books` | 查询所有图书 |
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
