# 校园论坛

前后端分离的校园论坛系统：邮箱注册登录、发帖与富文本编辑、点赞收藏、多级评论回复、消息提醒。

## 技术栈

**后端**：Spring Boot 3.1 · Spring Security + JWT · MyBatis-Plus · MySQL 8 · Redis · RabbitMQ · MinIO · Java 17
**前端**：Vue 3（`<script setup>`）· Vite 4 · Pinia · vue-router 4 · Element Plus · Axios · Quill 富文本

## 功能特性

- **账号与安全**：邮箱验证码注册 / 重置密码（RabbitMQ 异步发信）、JWT 登录与自动续期、修改密码与邮箱、IP 限流
- **用户资料**：详细资料（性别、QQ、微信、手机号、简介）+ 逐项**隐私开关**，未公开字段对他人显示为“已隐藏或未填写”
- **帖子**：类型筛选、无限滚动分页列表、置顶帖、富文本发帖 / 编辑（图片上传、字数校验）、帖子详情、我的收藏列表
- **互动**：点赞与收藏（Redis 缓冲 + 延迟批量落库）、按用户回显状态、点赞 / 收藏数展示
- **评论**：发表、分页展示、回复评论（引用摘要）、删除自己的评论、评论限流
- **消息提醒**：被评论 / 被回复时生成通知，顶栏红点 + 抽屉列表，点击跳转并标记已读
- **其他**：首页天气与定位（和风天气）、图片上传至 MinIO 并由 `/images/**` 代理输出


### 环境依赖

| 组件 | 端口 | 说明 |
| --- | --- | --- |
| JDK 17 / Maven | — | 后端 |
| Node.js 18+ | 5173 | 前端 Vite |
| MySQL 8 | 3306 | 数据库名 `test` |
| Redis | 6379 | 缓存 / 限流 / 点赞缓冲 |
| RabbitMQ | 5672 / 15672 | 邮件验证码 |
| MinIO | 9000 / 9090 | 对象存储，Bucket 名称 `study` |

### 1. 数据库

主要数据表：`db_account`（账号）、`db_account_details`（详细资料）、`db_account_privacy`（隐私开关）、`db_image_store`（图片记录）、`db_topic`（帖子）、`db_topic_type`（类型）、`db_topic_comment`（评论）、`db_topic_interact_like` / `db_topic_interact_collect`（点赞 / 收藏关系）、`db_notification`（通知）。

### 2. 中间件

```bash
docker run -d --name redis -p 6379:6379 redis:latest
docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management
# MinIO：启动后需手动创建 Bucket `study`（官方镜像已下架，可自行准备服务）
```

## 配置说明

| 配置项 | 说明 |
| --- | --- |
| `spring.datasource.*` | MySQL 连接 |
| `spring.mail.username` / `password` | 邮箱账号与**授权码**（不是登录密码） |
| `spring.rabbitmq.*` | RabbitMQ 连接（邮件队列 `mail`） |
| `spring.security.jwt.key` / `expire` | JWT 密钥与有效期（小时） |
| `spring.web.flow.period` / `limit` / `block` | IP 限流：周期（秒）/ 次数 / 封禁时长（秒） |
| `spring.minio.endpoint` / `username` / `password` | MinIO 地址与账号 |
| `spring.weather.key` / `host` | 和风天气 API Key 与专属 API Host |


## 项目结构

```
my-project-backend/    # config 配置 · controller 接口 · entity 实体与 VO
                       # filter 过滤器 · mapper 持久层 · service 业务 · utils 工具
my-project-frontend/   # src/net Axios 封装 · src/store Pinia · src/router 路由
                       # src/components 通用组件与编辑器 · src/views 页面
```

## 说明
个人学习项目