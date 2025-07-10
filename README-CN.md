# scoda-ddd-starter

[English](README.md) | [中文文档](README-CN.md)

[![Maven Central](https://img.shields.io/maven-central/v/com.scoda/scoda-ddd-starter.svg)](https://search.maven.org/artifact/com.scoda/scoda-ddd-starter)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Java](https://img.shields.io/badge/Java-8+-blue.svg)](https://www.oracle.com/java/)

一个基于Spring Boot的领域驱动设计（DDD）启动器，支持CQRS、事件驱动架构和企业级最佳实践。该库帮助您快速构建高内聚、易扩展的微服务系统。

## 特性

- **分层DDD模型**: 清晰分离基础层、CQRS层和领域层
- **CQRS支持**: 命令/查询总线和处理器接口，实现解耦的业务逻辑
- **领域事件**: 领域事件和聚合根的抽象，支持事件驱动设计
- **统一异常处理**: 标准错误码和异常系统
- **参数验证**: 请求和参数检查工具
- **可配置线程池**: 用于异步CQRS操作，支持自定义配置
- **Spring Boot自动配置**: Bean和切面自动注册，支持覆盖和扩展
- **无业务依赖**: 开箱即用

## 系统要求

- Java 8 或更高版本
- Spring Boot 2.3.12.RELEASE 或更高版本
- Maven 3.6+ 或 Gradle 6+

## 安装

### Maven

```xml
<dependency>
    <groupId>com.trieai.scoda</groupId>
    <artifactId>scoda-ddd-starter</artifactId>
    <version>1.0.1</version>
</dependency>
```

### Gradle

```gradle
implementation 'com.trieai.scoda:scoda-ddd-starter:1.0.1'
```

## 项目结构

```
src/main/java/com/scoda/ddd
├── component/    # 核心Bean、自动配置、CQRS门面、总线、切面
├── model/
│   ├── base/     # 基础能力：错误处理、IO、常量、验证
│   ├── cqrs/     # CQRS抽象：命令、查询、处理器、总线
│   └── domain/   # 领域核心：事件、聚合根
├── utils/        # 工具类（如雪花ID生成器）
```

## 快速开始

### 1. 添加依赖

在您的 `pom.xml` 中添加依赖，如安装部分所示。

### 2. （可选）配置线程池

在您的 `application.yaml` 中：

```yaml
scoda:
  ddd:
    cqrs:
      async:
        core-pool-size: 8
        max-pool-size: 16
        queue-capacity: 100
        thread-name-prefix: "cqrs-async-"
```

如果未配置，启动器将使用默认参数。

### 3. 使用CQRS门面

```java
@Autowired
private BusFacade busFacade;

// 同步命令
busFacade.sendCommand(command);
// 异步命令
busFacade.sendAsyncCommand(command);
// 查询
busFacade.sendQuery(query);
```

### 4. 创建您的第一个命令

```java
public class CreateUserCommand extends BaseCommand {
    private String username;
    private String email;
    
    // getter和setter方法
}

@Component
public class CreateUserCommandHandler implements CommandHandler<CreateUserCommand> {
    @Override
    public void handle(CreateUserCommand command) {
        // 您的业务逻辑
        System.out.println("创建用户: " + command.getUsername());
    }
}
```

### 5. 创建您的第一个查询

```java
public class GetUserQuery extends BaseQuery<User> {
    private String username;
    
    // getter和setter方法
}

@Component
public class GetUserQueryHandler implements QueryHandler<GetUserQuery, User> {
    @Override
    public User handle(GetUserQuery query) {
        // 您的查询逻辑
        return new User(query.getUsername());
    }
}
```

## 配置

### 线程池配置

| 属性 | 默认值 | 描述 |
|------|--------|------|
| `scoda.ddd.cqrs.async.core-pool-size` | 8 | 核心线程池大小 |
| `scoda.ddd.cqrs.async.max-pool-size` | 16 | 最大线程池大小 |
| `scoda.ddd.cqrs.async.queue-capacity` | 100 | 队列容量 |
| `scoda.ddd.cqrs.async.thread-name-prefix` | "cqrs-async-" | 线程名称前缀 |

## 扩展和自定义

- **线程池参数**: 通过 `scoda.ddd.cqrs.async.*` 配置覆盖
- **自定义线程池**: 定义您自己的 `@Bean("cqrsAsyncExecutor")` 来覆盖默认配置
- **自定义BusFacade**: 定义您自己的 `@Bean` 来覆盖启动器的BusFacade
- **命令/查询/事件处理器**: 实现相应的接口；它们将被自动注册

## 示例

### 领域事件示例

```java
public class UserCreatedEvent extends AbstractDomainEvent {
    private String username;
    
    public UserCreatedEvent(String username) {
        this.username = username;
    }
    
    // getter方法
}

@Component
public class UserCreatedEventHandler implements EventHandler<UserCreatedEvent> {
    @Override
    public void handle(UserCreatedEvent event) {
        // 处理用户创建事件
        System.out.println("用户已创建: " + event.getUsername());
    }
}
```

### 错误处理示例

```java
public class UserNotFoundException extends BaseException {
    public UserNotFoundException(String username) {
        super(BaseErrorCode.USER_NOT_FOUND, "用户未找到: " + username);
    }
}
```

## API参考

### 核心类

- `BusFacade`: CQRS操作的主要门面
- `BaseCommand`: 命令的基类
- `BaseQuery`: 查询的基类
- `BaseException`: 基础异常类
- `AbstractAggregateRoot`: 聚合根的基类
- `AbstractDomainEvent`: 领域事件的基类

### 接口

- `CommandHandler<T>`: 命令处理器接口
- `QueryHandler<T, R>`: 查询处理器接口
- `EventHandler<T>`: 事件处理器接口

## 迁移指南

### 从SNAPSHOT到发布版本

如果您正在从SNAPSHOT版本升级：

1. 将依赖版本更新为 `1.0.1`
2. 预计不会有破坏性变更
3. 查看更新日志了解新功能

## 贡献

我们欢迎贡献！请查看我们的[贡献指南](CONTRIBUTING.md)了解详情。

### 开发设置

1. Fork仓库
2. 创建功能分支
3. 进行更改
4. 为新功能添加测试
5. 提交拉取请求

## 更新日志

### [1.0.1] - 2024-01-01
- 首个正式版本（初始发布）
- 支持CQRS的命令/查询总线
- 领域事件抽象
- Spring Boot自动配置
- 线程池配置支持

## 许可证

本项目采用MIT许可证 - 查看[LICENSE](LICENSE)文件了解详情。

## 支持

- **文档**: [GitHub Wiki](https://github.com/chenkangzeng1/scoda-ddd-starter/wiki)
- **问题**: [GitHub Issues](https://github.com/chenkangzeng1/scoda-ddd-starter/issues)
- **讨论**: [GitHub Discussions](https://github.com/chenkangzeng1/scoda-ddd-starter/discussions)

## 相关项目

- [Spring Boot](https://spring.io/projects/spring-boot)
- [领域驱动设计](https://martinfowler.com/bliki/DomainDrivenDesign.html)

## 致谢

- Spring Boot团队提供的优秀框架
- 领域驱动设计社区的架构模式 

## 版本兼容性

| scoda-ddd-starter 版本 | 支持的 Spring Boot 版本      |
|-----------------------|------------------------------|
| 1.0.1                 | 2.3.12.RELEASE               |
| 1.0.2                 | 2.7.18                       |
| 1.1.0                 | 3.2.12                       |