# scoda-ddd-starter

[English](README.md) | [中文文档](README-CN.md)

[![Maven Central](https://img.shields.io/maven-central/v/com.scoda/scoda-ddd-starter.svg)](https://search.maven.org/artifact/com.scoda/scoda-ddd-starter)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Java](https://img.shields.io/badge/Java-8+-blue.svg)](https://www.oracle.com/java/)

A Spring Boot starter for Domain-Driven Design (DDD) with CQRS, event-driven architecture, and enterprise best practices. This library helps you quickly build high-cohesion, easily extensible microservice systems.

## Features

- **Layered DDD model**: Clear separation of base, CQRS, and domain layers.
- **CQRS support**: Command/Query bus and handler interfaces for decoupled business logic.
- **Domain events**: Abstractions for domain events and aggregate roots, supporting event-driven design.
- **Unified exception handling**: Standard error codes and exception system.
- **Parameter validation**: Utilities for request and parameter checking.
- **Configurable thread pool**: For async CQRS operations, with support for custom configuration.
- **Spring Boot auto-configuration**: Beans and aspects are auto-registered, supporting override and extension.
- **No business dependencies**: Ready to use out of the box.

## Requirements

- Java 8 or higher
- Spring Boot 2.3.12.RELEASE or higher (Recommended: 2.7.18)
- Maven 3.6+ or Gradle 6+

## Installation

### Maven

```xml
<dependency>
    <groupId>com.trieai.scoda</groupId>
    <artifactId>scoda-ddd-starter</artifactId>
    <version>1.0.2</version>
</dependency>
```

### Gradle

```gradle
implementation 'com.trieai.scoda:scoda-ddd-starter:1.0.1'
```

## Project Structure

```
src/main/java/com/scoda/ddd
├── component/    # Core beans, auto-configuration, CQRS facade, buses, aspects
├── model/
│   ├── base/     # Base capabilities: error, io, constants, validation
│   ├── cqrs/     # CQRS abstractions: Command, Query, handlers, buses
│   └── domain/   # Domain core: events, aggregate roots
├── utils/        # Utility classes (e.g., Snowflake ID generator)
```

## Quick Start

### 1. Add Dependency

Add the dependency to your `pom.xml` as shown in the Installation section above.

### 2. (Optional) Configure Thread Pool

In your `application.yaml`:

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

If not configured, the starter will use default parameters.

### 3. Use CQRS Facade

```java
@Autowired
private BusFacade busFacade;

// Synchronous command
busFacade.sendCommand(command);
// Asynchronous command
busFacade.sendAsyncCommand(command);
// Query
busFacade.sendQuery(query);
```

### 4. Create Your First Command

```java
public class CreateUserCommand extends BaseCommand {
    private String username;
    private String email;
    
    // getters and setters
}

@Component
public class CreateUserCommandHandler implements CommandHandler<CreateUserCommand> {
    @Override
    public void handle(CreateUserCommand command) {
        // Your business logic here
        System.out.println("Creating user: " + command.getUsername());
    }
}
```

### 5. Create Your First Query

```java
public class GetUserQuery extends BaseQuery<User> {
    private String username;
    
    // getters and setters
}

@Component
public class GetUserQueryHandler implements QueryHandler<GetUserQuery, User> {
    @Override
    public User handle(GetUserQuery query) {
        // Your query logic here
        return new User(query.getUsername());
    }
}
```

## Configuration

### Thread Pool Configuration

| Property | Default | Description |
|----------|---------|-------------|
| `scoda.ddd.cqrs.async.core-pool-size` | 8 | Core thread pool size |
| `scoda.ddd.cqrs.async.max-pool-size` | 16 | Maximum thread pool size |
| `scoda.ddd.cqrs.async.queue-capacity` | 100 | Queue capacity |
| `scoda.ddd.cqrs.async.thread-name-prefix` | "cqrs-async-" | Thread name prefix |

## Extension & Customization

- **Thread pool parameters**: Override via `scoda.ddd.cqrs.async.*` configuration.
- **Custom thread pool**: Define your own `@Bean("cqrsAsyncExecutor")` to override the default.
- **Custom BusFacade**: Define your own `@Bean` to override the starter's BusFacade.
- **Command/Query/Event Handlers**: Implement the corresponding interfaces; they will be auto-registered.

## Examples

### Domain Event Example

```java
public class UserCreatedEvent extends AbstractDomainEvent {
    private String username;
    
    public UserCreatedEvent(String username) {
        this.username = username;
    }
    
    // getters
}

@Component
public class UserCreatedEventHandler implements EventHandler<UserCreatedEvent> {
    @Override
    public void handle(UserCreatedEvent event) {
        // Handle user created event
        System.out.println("User created: " + event.getUsername());
    }
}
```

### Error Handling Example

```java
public class UserNotFoundException extends BaseException {
    public UserNotFoundException(String username) {
        super(BaseErrorCode.USER_NOT_FOUND, "User not found: " + username);
    }
}
```

## API Reference

### Core Classes

- `BusFacade`: Main facade for CQRS operations
- `BaseCommand`: Base class for commands
- `BaseQuery`: Base class for queries
- `BaseException`: Base exception class
- `AbstractAggregateRoot`: Base class for aggregate roots
- `AbstractDomainEvent`: Base class for domain events

### Interfaces

- `CommandHandler<T>`: Interface for command handlers
- `QueryHandler<T, R>`: Interface for query handlers
- `EventHandler<T>`: Interface for event handlers

## Migration Guide

### From SNAPSHOT to Release

If you're upgrading from a SNAPSHOT version:

1. Update your dependency version to `1.0.1`
2. No breaking changes are expected
3. Review the changelog for any new features

## Contributing

We welcome contributions! Please see our [Contributing Guide](CONTRIBUTING.md) for details.

### Development Setup

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Submit a pull request

## Changelog

### [1.0.1] - 2024-01-01
- origin version （Initial release）
- CQRS support with command/query buses
- Domain event abstractions
- Spring Boot auto-configuration
- Thread pool configuration support

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Support

- **Documentation**: [GitHub Wiki](https://github.com/chenkangzeng1/scoda-ddd-starter/wiki)
- **Issues**: [GitHub Issues](https://github.com/chenkangzeng1/scoda-ddd-starter/issues)
- **Discussions**: [GitHub Discussions](https://github.com/chenkangzeng1/scoda-ddd-starter/discussions)

## Related Projects

- [Spring Boot](https://spring.io/projects/spring-boot)
- [Domain-Driven Design](https://martinfowler.com/bliki/DomainDrivenDesign.html)

## Acknowledgments

- Spring Boot team for the excellent framework
- Domain-Driven Design community for the architectural patterns

## Version Compatibility

| scoda-ddd-starter Version | Supported Spring Boot Version |
|--------------------------|-------------------------------|
| 1.0.1                    | 2.3.12.RELEASE                |
| 1.0.2                    | 2.7.18                        |
| 1.1.0                    | 3.2.12                        |
