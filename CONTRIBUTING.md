# Contributing to scoda-ddd-starter

Thank you for your interest in contributing to scoda-ddd-starter! This document provides guidelines for contributing to this project.

## Code of Conduct

By participating in this project, you agree to abide by our Code of Conduct.

## How Can I Contribute?

### Reporting Bugs

- Use the GitHub issue tracker
- Include a clear and descriptive title
- Provide detailed steps to reproduce the bug
- Include your environment details (Java version, Spring Boot version, etc.)
- Include any relevant error messages or stack traces

### Suggesting Enhancements

- Use the GitHub issue tracker
- Describe the enhancement clearly
- Explain why this enhancement would be useful
- Provide examples of how it would be used

### Pull Requests

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Make your changes
4. Add tests for new functionality
5. Ensure all tests pass
6. Commit your changes (`git commit -m 'Add some amazing feature'`)
7. Push to the branch (`git push origin feature/amazing-feature`)
8. Open a Pull Request

## Development Setup

### Prerequisites

- Java 8 or higher
- Maven 3.6+
- Git

### Building the Project

```bash
# Clone the repository
git clone https://github.com/your-username/scoda-ddd-starter.git
cd scoda-ddd-starter

# Build the project
mvn clean install
```

### Running Tests

```bash
# Run all tests
mvn test

# Run tests with coverage
mvn test jacoco:report
```

## Coding Standards

### Java Code Style

- Follow Java naming conventions
- Use meaningful variable and method names
- Add Javadoc comments for public APIs
- Keep methods small and focused
- Use appropriate access modifiers

### Code Formatting

- Use 4 spaces for indentation
- Use UTF-8 encoding
- Remove trailing whitespace
- End files with a newline

### Commit Messages

- Use clear and descriptive commit messages
- Start with a verb in present tense (e.g., "Add", "Fix", "Update")
- Keep the first line under 50 characters
- Add more details in the body if needed

## Testing Guidelines

- Write unit tests for new functionality
- Ensure test coverage is maintained or improved
- Use descriptive test method names
- Test both positive and negative scenarios
- Mock external dependencies

## Documentation

- Update README.md if you add new features
- Add Javadoc comments for new public APIs
- Update examples if needed
- Keep documentation up to date

## Review Process

1. All pull requests will be reviewed by maintainers
2. Address any feedback or requested changes
3. Once approved, your changes will be merged

## Questions?

If you have questions about contributing, please open an issue or contact the maintainers.

Thank you for contributing to scoda-ddd-starter! 