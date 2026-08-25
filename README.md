# Xiaoyi博客系统 (XiaoyiBlogSystem)

基于 Java Swing 的桌面端博客管理系统，通过 JDBC 连接 MySQL 数据库，提供用户注册登录、个人中心、博客管理、系统公告和开发者介绍等功能。

## 功能特性

- **用户注册** — 输入账号密码注册新用户，自动创建用户信息记录
- **用户登录** — 账号密码校验，登录后进入系统主界面
- **博客系统主界面** — 系统导航中心，可跳转至各功能页面
- **个人中心** — 包含三个子功能：
  - 用户账号管理（密码、绑定手机号、账号状态）
  - 用户个人信息（昵称、性别、个人简介）
  - 用户所有博客（博客编号、标题、正文、发布日期、标签）
- **系统公告** — 查看系统发布的公告信息
- **开发者介绍** — 展示开发者姓名、照片、简介和联系电话

## 技术栈

| 技术 | 说明 |
|------|------|
| Java | 核心开发语言 |
| Swing | GUI 桌面界面框架 |
| MySQL 8.x | 后端数据库 |
| JDBC (MySQL Connector/J 8.4.0) | 数据库连接驱动 |

## 项目结构

```
个人博客系统/
├── src/
│   ├── APP/
│   │   └── APP.java                      # 程序入口
│   ├── DatabaseConnection/
│   │   └── DatabaseConnection.java       # 数据库操作层（连接、增删改查）
│   ├── UI/
│   │   ├── LoginJFrame.java              # 登录界面
│   │   ├── RegisterFrame.java            # 注册界面
│   │   ├── SystemMainJFrame.java         # 博客系统主界面
│   │   ├── UserJFrame.java               # 用户个人中心
│   │   ├── AnnouncementFrame.java        # 系统公告界面
│   │   └── DeveloperJFrame.java          # 开发者介绍界面
│   └── TOOL/
│       ├── SetBackground.java            # 背景图片工具类
│       ├── BackToLoginMenuItem.java       # 「返回登录界面」菜单项
│       └── BackToMainMenuItem.java        # 「返回主界面」菜单项
├── ConnectMySQL/
│   └── mysql-connector-j-8.4.0.jar       # MySQL JDBC 驱动
├── background.jpg                         # 界面背景图片
├── photo.png                             # 开发者照片
└── XiaoyiBlogSystem.iml                  # IntelliJ IDEA 模块文件
```

## 页面导航流程

```
登录界面
  ├──→ 注册界面 ──返回──→ 登录界面
  └──→ 博客系统主界面
        ├──→ 个人中心
        │     ├── 用户账号管理
        │     ├── 用户个人信息
        │     └── 用户所有博客
        ├──→ 系统公告 ──返回──→ 主界面
        └──→ 开发者介绍 ──返回──→ 主界面
```

各页面均可通过菜单栏返回上一级页面。

## 数据库设计

数据库名：`db_blog`，包含以下数据表：

| 表名 | 说明 | 主要字段 |
|------|------|----------|
| `tb_user` | 用户账号表 | `user_number`（账号）、`user_password`（密码）、`user_phoneNumber`（手机号）、`user_static`（账号状态） |
| `tb_userInfo` | 用户信息表 | `user_number`（账号）、`userInfo_name`（昵称）、`userInfo_gander`（性别）、`userInfo_profile`（简介） |
| `tb_blog` | 博客表 | `blog_number`（编号）、`blog_title`（标题）、`blog_text`（正文）、`blog_insertTime`（发布时间）、`user_number`（作者账号） |
| `tb_label` | 标签表 | `label_number`（编号）、`label_name`（标签名） |
| `tb_relation_blog_label` | 博客-标签关联表 | `blog_number`、`label_number` |
| `tb_announcement` | 系统公告表 | `announcement_title`（标题）、`announcement_text`（内容）、`announcement_date`（日期） |
| `tb_developer` | 开发者信息表 | `developer_name`（姓名）、`developer_photo`（照片路径）、`developer_profile`（简介）、`developer_phoneNumber`（电话） |

### 建表示例

```sql
CREATE DATABASE IF NOT EXISTS db_blog DEFAULT CHARACTER SET utf8mb4;
USE db_blog;

-- 用户账号表
CREATE TABLE tb_user (
    user_number      VARCHAR(50)  PRIMARY KEY,
    user_password    VARCHAR(100) NOT NULL,
    user_phoneNumber VARCHAR(20),
    user_static      VARCHAR(20)
);

-- 用户信息表
CREATE TABLE tb_userInfo (
    user_number       VARCHAR(50)  PRIMARY KEY,
    userInfo_name     VARCHAR(50),
    userInfo_gander    VARCHAR(10),
    userInfo_profile  VARCHAR(255)
);

-- 博客表
CREATE TABLE tb_blog (
    blog_number     VARCHAR(50)  PRIMARY KEY,
    blog_title      VARCHAR(200) NOT NULL,
    blog_text       TEXT,
    blog_insertTime DATETIME,
    user_number     VARCHAR(50),
    FOREIGN KEY (user_number) REFERENCES tb_user(user_number)
);

-- 标签表
CREATE TABLE tb_label (
    label_number VARCHAR(50) PRIMARY KEY,
    label_name   VARCHAR(50)
);

-- 博客-标签关联表
CREATE TABLE tb_relation_blog_label (
    blog_number  VARCHAR(50),
    label_number VARCHAR(50),
    PRIMARY KEY (blog_number, label_number),
    FOREIGN KEY (blog_number)  REFERENCES tb_blog(blog_number),
    FOREIGN KEY (label_number) REFERENCES tb_label(label_number)
);

-- 系统公告表
CREATE TABLE tb_announcement (
    announcement_title VARCHAR(200),
    announcement_text  TEXT,
    announcement_date  VARCHAR(50)
);

-- 开发者信息表
CREATE TABLE tb_developer (
    developer_name        VARCHAR(50),
    developer_photo       VARCHAR(255),
    developer_profile     VARCHAR(255),
    developer_phoneNumber VARCHAR(20)
);
```

## 运行环境

- **JDK** 8 或以上
- **MySQL** 8.x
- **MySQL Connector/J** 8.4.0（已包含在 `ConnectMySQL/` 目录中）
- **IntelliJ IDEA**（推荐，项目使用 IDEA 工程文件）

## 快速开始

### 1. 创建数据库

执行上方的建表 SQL 脚本，创建 `db_blog` 数据库及所有数据表。

### 2. 配置数据库连接

修改 `src/DatabaseConnection/DatabaseConnection.java` 中的数据库连接参数：

```java
private static String url = "jdbc:mysql://localhost:3306/db_blog?useSSL=true&serverTimezone=UTC";
private static String user = "root";
private static String password = "你的MySQL密码";
```

### 3. 添加 JDBC 驱动

在 IntelliJ IDEA 中，将 `ConnectMySQL/mysql-connector-j-8.4.0.jar` 添加为项目依赖库：

`File → Project Structure → Libraries → + → Java → 选择 jar 文件`

### 4. 编译运行

运行 `src/APP/APP.java` 的 `main` 方法即可启动程序，首先进入登录界面。

## 安全说明

本项目为学习阶段作品，以下安全问题在投入使用前需修复：

- **数据库密码硬编码** — `DatabaseConnection.java` 中明文写死了数据库账号密码，生产环境应改用环境变量或配置文件读取
- **密码明文存储** — 用户注册时密码以明文写入数据库，应改为哈希存储（如 BCrypt）
- **密码明文显示** — 用户个人中心会以表格形式展示用户密码，应移除该展示项

## License

本项目仅供学习交流使用。
