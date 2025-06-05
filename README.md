# Spring Boot Boilerplate

A minimal Spring Boot boilerplate project to kickstart your application development. This project includes essential configurations and code samples to get you started quickly with Spring Boot.

## Features

- Spring Boot 3.5.0
- Spring Web MVC
- Spring Data JPA
- Thymeleaf templating
- H2 In-memory database
- Bootstrap 5 for UI
- Basic CRUD functionality
- REST API endpoints
- Web interface
- Unit and integration tests
- Java 21 Virtual Threads support

## Prerequisites

- Java 21 or higher
- Maven 3.6+ or Gradle 7+

## Java Installation and Setup

### Installing Java

#### On macOS (using Homebrew)

```bash
# Install Homebrew if not already installed
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"

# Install OpenJDK 21
brew install openjdk@21

# Get the installed location (you'll need this for jenv later)
ls -la /opt/homebrew/opt/openjdk@21
```

The Homebrew installation will give you instructions on how to add the Java binary to your PATH. It typically looks like:

```bash
# For intel Macs
echo 'export PATH="/usr/local/opt/openjdk@21/bin:$PATH"' >> ~/.zshrc

# For Apple Silicon Macs
echo 'export PATH="/opt/homebrew/opt/openjdk@21/bin:$PATH"' >> ~/.zshrc
```

#### On Ubuntu/Debian

```bash
# Add the repository
sudo apt update
sudo apt install -y software-properties-common
sudo add-apt-repository ppa:openjdk-r/ppa
sudo apt update

# Install OpenJDK 21
sudo apt install -y openjdk-21-jdk

# Verify installation
java -version

# Find Java installation path (for jenv)
update-alternatives --list java
# Example output: /usr/lib/jvm/java-21-openjdk-amd64/bin/java
```

#### On Windows

1. Download OpenJDK or Oracle JDK 21 from their official websites
2. Run the installer and follow the installation wizard
3. Set the JAVA_HOME environment variable:
   - Right-click on 'This PC' or 'My Computer' > Properties > Advanced system settings > Environment Variables
   - Add a new system variable named `JAVA_HOME` with the path to your JDK (e.g., `C:\Program Files\Java\jdk-21`)
   - Edit the `Path` variable and add `%JAVA_HOME%\bin`
4. Verify installation by opening Command Prompt and typing `java -version`
5. Note the installation path for jenv setup (e.g., `C:\Program Files\Java\jdk-21`)

### Using jenv to Manage Multiple Java Versions

[jenv](https://www.jenv.be/) is a great tool for managing multiple Java versions on your system.

#### Installing jenv on macOS

```bash
# Install jenv using Homebrew
brew install jenv

# Add to your shell configuration (~/.zshrc or ~/.bash_profile)
echo 'export PATH="$HOME/.jenv/bin:$PATH"' >> ~/.zshrc
echo 'eval "$(jenv init -)"' >> ~/.zshrc

# Apply changes
source ~/.zshrc

# Enable jenv plugins (optional but recommended)
jenv enable-plugin export
jenv enable-plugin maven
```

#### Installing jenv on Linux

```bash
# Clone jenv repository
git clone https://github.com/jenv/jenv.git ~/.jenv

# Add to your shell configuration (~/.bashrc or ~/.zshrc)
echo 'export PATH="$HOME/.jenv/bin:$PATH"' >> ~/.bashrc
echo 'eval "$(jenv init -)"' >> ~/.bashrc

# Apply changes
source ~/.bashrc

# Enable jenv plugins (optional but recommended)
jenv enable-plugin export
jenv enable-plugin maven
```

#### Finding Java Installations on Your System

Before adding Java to jenv, you need to locate your Java installations:

```bash
# On macOS (shows all Java versions and their locations)
/usr/libexec/java_home -V

# On macOS with Homebrew (specific installation paths)
ls -la /opt/homebrew/opt/openjdk@21/libexec/openjdk.jdk/Contents/Home  # Apple Silicon Mac
ls -la /usr/local/opt/openjdk@21/libexec/openjdk.jdk/Contents/Home     # Intel Mac

# On Linux
update-alternatives --list java   # Shows the Java executable
update-alternatives --list javac  # Shows the Java compiler

# Get the real path of the Java installation directory (Linux)
readlink -f $(which java) | sed 's/\/bin\/java$//'  # Removes the '/bin/java' suffix
```

#### Adding Java versions to jenv

Once you've found the Java home directory (not just the bin directory), add it to jenv:

```bash
# Common macOS Homebrew path (Apple Silicon)
jenv add /opt/homebrew/opt/openjdk@21/libexec/openjdk.jdk/Contents/Home

# Common macOS Homebrew path (Intel)
jenv add /usr/local/opt/openjdk@21/libexec/openjdk.jdk/Contents/Home

# Common Linux path
jenv add /usr/lib/jvm/java-21-openjdk-amd64

# Common Windows path (using WSL or Git Bash)
jenv add /c/Program\ Files/Java/jdk-21
```

#### Managing Java Versions with jenv

```bash
# List all Java versions managed by jenv
jenv versions

# Set a global Java version
jenv global 21.0

# Set a local version for this project
cd /path/to/spring-boot-boilerplate
jenv local 21.0

# Verify the active Java version
jenv version
java -version

# Check jenv is working properly
jenv doctor
```

## Getting Started

### Clone the Repository

```bash
git clone https://github.com/yourusername/spring-boot-boilerplate.git
cd spring-boot-boilerplate
```

### Build and Run

Using Maven:

```bash
mvn clean install
mvn spring-boot:run
```

Or using the Maven wrapper (included in the project):

```bash
./mvnw clean install
./mvnw spring-boot:run
```

Using Java directly:

```bash
mvn clean package
java -jar target/spring-boot-boilerplate-0.0.1-SNAPSHOT.jar
```

### Access the Application

- Web Interface: http://localhost:8080
- Items List: http://localhost:8080/items
- REST API: http://localhost:8080/api/items
- H2 Console: http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:boilerplatedb`
  - User Name: `sa`
  - Password: (leave empty)

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── example/
│   │           └── boilerplate/
│   │               ├── controller/       # Web and REST controllers
│   │               ├── model/            # Entity classes
│   │               ├── repository/       # Data repositories
│   │               ├── service/          # Business logic
│   │               ├── config/           # Configuration classes
│   │               └── BoilerplateApplication.java  # Main class
│   └── resources/
│       ├── static/                       # Static resources (CSS, JS)
│       ├── templates/                    # Thymeleaf templates
│       └── application.properties        # Application configuration
└── test/
    └── java/                             # Test classes
```

## API Endpoints

| Method | URL | Description |
|--------|-----|-------------|
| GET    | /api/items | Get all items |
| GET    | /api/items?activeOnly=true | Get active items only |
| GET    | /api/items/{id} | Get item by ID |
| POST   | /api/items | Create a new item |
| PUT    | /api/items/{id} | Update an existing item |
| DELETE | /api/items/{id} | Delete an item |
| GET    | /api/items/search?query={query} | Search items by name |

## Customization

### Configuration

Edit `src/main/resources/application.properties` to customize:

- Server port
- Database settings
- Logging levels
- Thymeleaf settings

### Adding New Entity

1. Create a new entity class in the `model` package
2. Create a repository interface in the `repository` package
3. Create a service class in the `service` package
4. Create controllers in the `controller` package

## Testing

Run tests using Maven:

```bash
mvn test
```

## What's New in Spring Boot 3.5.0 and Java 21

### Spring Boot 3.5.0 Features
- **Enhanced Virtual Threads Support**: Full optimization for Java 21's lightweight threading model
- **Improved Observability**: Better integration with Micrometer and OpenTelemetry
- **Performance Enhancements**: Faster startup times and reduced memory footprint
- **Updated Dependencies**: Latest versions of Spring Framework 6.2.7, Spring Security 6.5.0, and Hibernate
- **Better Cloud-Native Support**: Enhanced Kubernetes and Docker integration

### Java 21 Benefits
- **Virtual Threads (Project Loom)**: Handle thousands of concurrent tasks with minimal overhead
- **Pattern Matching**: Simplified switch statements and record patterns
- **Improved Z Garbage Collector**: Better memory management and reduced latency
- **Performance Improvements**: Overall runtime optimizations

## Troubleshooting Common Issues

### Java Version Issues

If you encounter errors related to Java version:

```bash
# Check the Java version currently being used
java -version

# If using jenv, make sure the correct version is set
jenv local 21.0
jenv doctor

# Verify Maven is using the correct Java version
mvn -v

# If Maven is using the wrong Java version, try:
JAVA_HOME=$(jenv prefix) mvn -v
```

### jenv Common Issues and Solutions

If you face issues with jenv:

```bash
# If jenv doesn't recognize Java versions properly
jenv rehash

# If changes to Java version aren't reflecting
exec $SHELL -l

# Check jenv setup
jenv doctor

# Manually fix path issues
jenv shell 21.0
echo $JAVA_HOME
```

### Homebrew Java Installation Issues

If you install Java via Homebrew but face issues:

```bash
# Full path to Homebrew OpenJDK executables:
/opt/homebrew/opt/openjdk@21/bin/java  # Apple Silicon Mac
/usr/local/opt/openjdk@21/bin/java     # Intel Mac

# Force relinking
brew link --force openjdk@21
```

### Maven Build Issues

If Maven build fails:

```bash
# Clean the project and try again
mvn clean

# Run with verbose output to see the error details
mvn clean install -X
```

### Application Startup Issues

If the application fails to start:

1. Check the console for error messages
2. Verify the application.properties file has correct configurations
3. Make sure the required ports (8080 by default) are available
4. Ensure you're using Java 21 or higher

### Virtual Threads Configuration

To enable virtual threads in your application, add this to your `application.properties`:

```properties
# Enable virtual threads (available in Java 21+)
spring.threads.virtual.enabled=true
```

## Performance Tips

### Utilizing Virtual Threads

Virtual threads are automatically used for web requests in Spring Boot 3.5.0 with Java 21. For custom async operations:

```java
@Async
public CompletableFuture<String> asyncOperation() {
    // This will run on virtual threads
    return CompletableFuture.completedFuture("result");
}
```

### Memory Optimization

With Java 21's improved garbage collection:

```properties
# JVM options for optimal performance
-XX:+UseZGC
-XX:+UnlockExperimentalVMOptions
```

## License

This project is licensed under the MIT License - see the LICENSE file for details.

# spring-boot-bolierplate
