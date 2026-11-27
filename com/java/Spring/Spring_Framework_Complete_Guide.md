# Complete Guide to the Spring Framework in Java

---

## 1. What is Spring Framework in Java?

The **Spring Framework** is an open-source, lightweight, enterprise-level Java application framework created by **Rod Johnson** in 2003. It was built as a simpler, cleaner, and more flexible alternative to traditional Java Enterprise Edition (Java EE / J2EE) technologies like Enterprise JavaBeans (EJB).

### Why is it called "Lightweight"?
* **Minimal Overhead:** Spring runs in a standard Plain Old Java Object (POJO) environment without requiring heavy application servers (like WebLogic or WebSphere).
* **Non-Invasive:** Your Java classes do not need to extend Spring-specific base classes or implement Spring-specific interfaces to work with the framework.
* **Modular Design:** You only include the specific Spring modules your project needs, keeping memory footprint and dependencies small.

### Core Philosophy
Spring handles the repetitive infrastructure and "plumbing" of enterprise applications (object creation, dependency wiring, database connections, transaction management, and security) so developers can focus purely on **business logic**.

---

## 2. How It Works: Core Architecture & Principles

Spring is built on three foundational software engineering concepts:

### A. Inversion of Control (IoC)
In traditional Java programming, your classes actively instantiate and manage their own dependencies using the `new` keyword. With **IoC**, control is inverted: the **Spring IoC Container** takes responsibility for instantiating, configuring, assembling, and managing the entire lifecycle of application objects.
* Objects managed by the Spring IoC container are called **Spring Beans**.
* **How the Container Works:**
  1. Reads your Java classes (POJOs) and Configuration Metadata (`spring.xml`, Java `@Configuration` classes, or Annotations).
  2. Instantiates the beans in the correct dependency order.
  3. Injects required dependencies into each bean.
  4. Executes lifecycle callbacks (`@PostConstruct`, `InitializingBean`, `@PreDestroy`, `DisposableBean`).
  5. Serves ready-to-use beans to your application and destroys them upon shutdown.

```
[Java POJOs] + [Configuration Metadata (spring.xml / Annotations)]
                            │
                            ▼
                ┌───────────────────────┐
                │  Spring IoC Container │
                │  (ApplicationContext) │
                └───────────────────────┘
                            │
                            ▼
             [Ready-to-Use Application Beans]
```

### B. Dependency Injection (DI)
**Dependency Injection** is the specific design pattern Spring uses to implement IoC. Instead of a class looking up or creating its dependencies, the container "injects" them at runtime.
* **Constructor Injection (Recommended):** Dependencies are passed via class constructors. Enforces immutability (`final` fields) and guarantees required dependencies are never `null`.
* **Setter Injection:** Dependencies are passed via public `setXxx()` methods. Useful for optional dependencies.
* **Field Injection:** Dependencies are injected directly into fields using reflection (`@Autowired`). Easy to write, but harder to unit test outside a container.

### C. Aspect-Oriented Programming (AOP)
Enterprise applications have "cross-cutting concerns"—logic that spans multiple layers of the application (e.g., logging, security checks, performance monitoring, transaction management).
* **How it works:** Instead of duplicating this code inside every service method, AOP isolates it into modular units called **Aspects**.
* At runtime, Spring generates **Dynamic Proxies** (JDK Dynamic Proxies or CGLIB proxies) around your target beans to intercept method calls and execute Aspect logic before, after, or around your business methods.

---

## 3. Key Modules with Details & Code Examples

Spring is organized into ~20 modular components grouped into major layers:

```
┌─────────────────────────────────────────────────────────────┐
│                      Web Layer (MVC / WebFlux)              │
├──────────────────────────────┬──────────────────────────────┤
│      Data Access / ORM       │     Messaging / Integration  │
│  (JDBC, ORM, Transactions)   │       (JMS, OXM, Tasks)      │
├──────────────────────────────┴──────────────────────────────┤
│                   AOP, Aspects, Instrumentation             │
├──────────────────────────────────────────────────────────────┤
│         Core Container (Beans, Core, Context, SpEL)         │
└──────────────────────────────────────────────────────────────┘
```

### 3.1 Core Container (`spring-core`, `spring-beans`, `spring-context`, `spring-expression`)
* **Details:** The heart of the framework. Provides the IoC container (`BeanFactory` and `ApplicationContext`), dependency injection, internationalization (i18n), event propagation, and Spring Expression Language (SpEL).
* **Example:**
```java
public class MessageService {
    public String getMessage() {
        return "Hello from Spring Core Container!";
    }
}

// Retrieving the bean from ApplicationContext
ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
MessageService service = context.getBean("messageService", MessageService.class);
System.out.println(service.getMessage());
```

### 3.2 AOP & Aspects (`spring-aop`, `spring-aspects`)
* **Details:** Provides method-interception capabilities to cleanly decouple cross-cutting concerns like logging and auditing from core business logic.
* **Example:**
```java
@Aspect
@Component
public class LoggingAspect {
    @Before("execution(* com.example.service.*.*(..))")
    public void logBeforeMethod(JoinPoint joinPoint) {
        System.out.println("[AUDIT LOG] Executing: " + joinPoint.getSignature().getName());
    }
}
```

### 3.3 Data Access / Integration (`spring-jdbc`, `spring-tx`, `spring-orm`)
* **Details:** Abstracts away tedious JDBC boilerplate (opening/closing connections, handling checked `SQLExceptions`), provides declarative transaction management (`@Transactional`), and integrates with ORM frameworks like Hibernate/JPA.
* **Example:**
```java
public class UserRepository {
    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int countUsers() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM users", Integer.class);
    }
}
```

### 3.4 Spring Web (`spring-web`, `spring-webmvc`, `spring-webflux`)
* **Details:** Provides a full-featured Model-View-Controller (MVC) framework for building traditional web apps and RESTful APIs using `DispatcherServlet`, as well as `WebFlux` for reactive, non-blocking web applications.
* **Example:**
```java
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @GetMapping("/{id}")
    public ResponseEntity<String> getOrder(@PathVariable("id") int id) {
        return ResponseEntity.ok("Order details for ID: " + id);
    }
}
```

### 3.5 Spring Security (Ecosystem Module)
* **Details:** A powerful, customizable authentication and access-control framework that protects web endpoints and method invocations against CSRF, session fixation, and unauthorized access.
* **Example:**
```java
@Service
public class AccountService {
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteAccount(Long userId) {
        System.out.println("Account " + userId + " deleted by Admin.");
    }
}
```

### 3.6 Spring Test (`spring-test`)
* **Details:** Supports unit and integration testing of Spring components with JUnit and TestNG, caching the `ApplicationContext` across tests and providing `MockMvc` for testing web controllers without starting an HTTP server.
* **Example:**
```java
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:spring.xml"})
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Test
    public void testOrderCreation() {
        assertNotNull(orderService);
    }
}
```

---

## 4. Core Spring APIs: How to Use, How They Work & When to Use

Spring provides hundreds of classes and interfaces. Below are the foundational APIs across the core framework layers:

| API / Interface / Class | Layer / Package | How It Works | How to Use | When to Use |
| :--- | :--- | :--- | :--- | :--- |
| **`BeanFactory`** | `org.springframework.beans.factory` | The root IoC interface. Uses **lazy initialization**—it only instantiates a bean when `getBean()` is explicitly called. | `BeanFactory factory = new XmlBeanFactory(...);` (Legacy) | Memory-constrained environments or mobile/embedded devices where startup speed and low RAM are critical. |
| **`ApplicationContext`** | `org.springframework.context` | Extends `BeanFactory`. Uses **eager initialization** for singleton beans at startup. Adds AOP, event publishing, i18n, and resource loading. | `ApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");` | **Standard choice (99% of apps).** Use in all enterprise, web, and standalone applications. |
| **`ClassPathXmlApplicationContext`** | `org.springframework.context.support` | Loads XML bean definitions from the Java classpath (e.g., `src/main/resources`). | `new ClassPathXmlApplicationContext("spring.xml")` | Standalone applications configured via XML located inside the project's classpath. |
| **`FileSystemXmlApplicationContext`** | `org.springframework.context.support` | Loads XML bean definitions from an absolute or relative file system path outside the JAR/classpath. | `new FileSystemXmlApplicationContext("/opt/config/spring.xml")` | When configuration files must reside externally on a server disk for ops teams to edit without rebuilding. |
| **`AnnotationConfigApplicationContext`** | `org.springframework.context.annotation` | Scans `@Component` classes and parses `@Configuration` / `@Bean` Java classes without XML. | `new AnnotationConfigApplicationContext(AppConfig.class)` | Modern, pure-Java Spring applications without XML configuration. |
| **`BeanPostProcessor`** | `org.springframework.beans.factory.config` | Hook interface called before and after every bean's initialization (`postProcessBeforeInitialization` / `After`). | Implement interface and register as a bean in the container. | Customizing beans globally (e.g., wrapping beans in custom proxies or validating properties). |
| **`FactoryBean<T>`** | `org.springframework.beans.factory` | Special factory object managed by Spring that produces complex target objects via `getObject()`. | Implement `FactoryBean<MyComplexObject>` and register it. | Instantiating complex third-party objects that cannot be constructed with simple `new` calls. |
| **`JdbcTemplate`** | `org.springframework.jdbc.core` | Executes SQL queries, manages connection acquisition/release, and translates `SQLException` into unchecked `DataAccessException`. | `jdbcTemplate.query("SELECT ...", rowMapper)` | Lightweight relational database access when a full ORM (like Hibernate) is overkill. |
| **`PlatformTransactionManager`** | `org.springframework.transaction` | Strategy interface that abstracts transaction boundaries (`getTransaction`, `commit`, `rollback`) across JDBC, JPA, or JMS. | Configured as a bean and triggered via `@Transactional` or `<tx:advice>`. | Any operation performing multiple database writes that must succeed or fail atomically. |
| **`DispatcherServlet`** | `org.springframework.web.servlet` | Front Controller pattern. Intercepts all incoming HTTP requests, consults `HandlerMapping`, invokes `@Controller`, and resolves views. | Registered in `web.xml` or `WebApplicationInitializer`. | Building any Spring MVC web application or REST API. |
| **`RestTemplate` / `WebClient`** | `org.springframework.web.client` | Synchronous (`RestTemplate`) or non-blocking reactive (`WebClient`) HTTP clients for consuming external REST APIs. | `webClient.get().uri("/users").retrieve()...` | Communicating with external microservices or third-party REST APIs. |
| **`ApplicationEventPublisher`** | `org.springframework.context` | Implements the Observer pattern to broadcast decoupled events to `@EventListener` beans inside the container. | `publisher.publishEvent(new OrderPlacedEvent(this, order));` | Decoupling side effects (e.g., sending an email after an order is saved). |

---

## 5. Explaining the `spring.xml` File

Before Java annotations became widespread, `spring.xml` (often named `applicationContext.xml` or `beans.xml`) was the primary blueprint telling the Spring IoC container **which classes to instantiate**, **what values/dependencies to inject**, and **how long those objects should live**.

### Anatomy of a `spring.xml` File

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="
           http://www.springframework.org/schema/beans
           https://www.springframework.org/schema/beans/spring-beans.xsd
           http://www.springframework.org/schema/context
           https://www.springframework.org/schema/context/spring-context.xsd">

    <!-- 1. Enable annotation support alongside XML if desired -->
    <context:annotation-config />

    <!-- 2. Simple Bean Definition with Lifecycle Hooks -->
    <bean id="databaseConfig" class="com.example.DatabaseConfig"
          scope="singleton"
          init-method="connect"
          destroy-method="disconnect">
        <!-- Injecting Primitive / String Literals -->
        <property name="url" value="jdbc:mysql://localhost:3306/shop" />
        <property name="maxConnections" value="10" />
    </bean>

    <!-- 3. Constructor Injection using <constructor-arg> -->
    <bean id="paymentGateway" class="com.example.StripePaymentGateway">
        <constructor-arg name="apiKey" value="sk_live_987654321" />
        <constructor-arg name="timeoutMs" value="5000" />
    </bean>

    <!-- 4. Setter Injection using <property> and 'ref' -->
    <bean id="orderService" class="com.example.OrderService">
        <property name="paymentGateway" ref="paymentGateway" />
        <property name="databaseConfig" ref="databaseConfig" />
    </bean>

</beans>
```

### Key XML Elements & Attributes Explained
* **`<beans>` (Root Tag):** Contains XML Namespace (`xmlns`) and Schema Location (`xsi:schemaLocation`) declarations so Spring can validate the XML structure.
* **`<bean>`:** Registers a single Java class with the IoC container.
  * `id` / `name`: Unique identifier used to retrieve or reference the bean.
  * `class`: Fully qualified class name (e.g., `com.example.OrderService`).
  * `scope`: Controls object lifecycle (`singleton` = one shared instance per container; `prototype` = new instance every time `getBean()` is called).
  * `init-method` / `destroy-method`: Custom methods executed right after dependency injection and right before container shutdown.
  * `lazy-init="true"`: Delays bean creation until it is first requested, even in an `ApplicationContext`.
* **`<constructor-arg>`:** Passes arguments into the class constructor (`value` for primitives/Strings, `ref` for pointing to another `<bean>`).
* **`<property>`:** Calls the corresponding Java setter method (e.g., `name="paymentGateway"` calls `setPaymentGateway(...)`).

---

## 6. Autowiring Using `spring.xml`

### What is XML Autowiring?
**Autowiring** is a feature where the Spring container automatically resolves and injects collaborating beans into a target bean **without requiring explicit `<constructor-arg>` or `<property>` tags** in `spring.xml`. You enable it per bean using the `autowire` attribute: `<bean id="..." class="..." autowire="mode" />`.

### How It Initializes Classes Under the Hood
1. **Bean Definition Parsing:** Spring parses `spring.xml` and notes the `autowire` attribute on each `BeanDefinition`.
2. **Instantiation:**
   * For `byName` and `byType`, Spring first invokes the class's **default no-arg constructor** via Java Reflection (`Class.getDeclaredConstructor().newInstance()`).
   * For `constructor`, Spring inspects all public constructors, matches parameter types against available beans in the container, and invokes the matching **parameterized constructor**.
3. **Property Population:** For `byName` and `byType`, Spring inspects the bean's `PropertyDescriptor`s (public `setXxx()` methods), searches the `ApplicationContext` for matching beans, and invokes the setter methods via reflection.

---

### All 5 XML Autowiring Modes (Use Cases & Examples)

#### 1. `autowire="no"` (Default)
* **How it works:** Autowiring is disabled. All dependencies must be explicitly wired using `<property ref="..." />` or `<constructor-arg ref="..." />`.
* **When to use:** Large enterprise XML files where explicit wiring makes dependencies 100% obvious and readable to developers.
* **Example:**
```xml
<bean id="engine" class="com.example.V8Engine" />
<bean id="car" class="com.example.Car" autowire="no">
    <property name="engine" ref="engine" />
</bean>
```

#### 2. `autowire="byName"`
* **How it works:** Spring looks at the **setter method name** of the target bean (e.g., `setEngine` -> property name `engine`) and searches `spring.xml` for a `<bean>` whose `id` or `name` matches `engine`. Uses **Setter Injection**.
* **When to use:** When you have multiple beans of the same type (e.g., two `NotificationService` beans: `emailService` and `smsService`) and want to match specifically by bean ID.
* **Example:**
```java
public class NotificationManager {
    private NotificationService emailService; // Setter name matches bean id="emailService"
    public void setEmailService(NotificationService emailService) {
        this.emailService = emailService;
    }
}
```
```xml
<bean id="emailService" class="com.example.EmailService" />
<bean id="smsService" class="com.example.SmsService" />

<!-- Matches 'emailService' because of setEmailService() -->
<bean id="notificationManager" class="com.example.NotificationManager" autowire="byName" />
```

#### 3. `autowire="byType"`
* **How it works:** Spring inspects the **data type** of each setter parameter and searches the container for a bean assignable to that type. Uses **Setter Injection**.
* **Caveat:** If **zero** matching beans exist, the property stays `null` (unless `dependency-check` is set). If **more than one** bean of that type exists, Spring throws `NoUniqueBeanDefinitionException` unless one bean is marked `primary="true"` or excluded via `autowire-candidate="false"`.
* **When to use:** When there is strictly one implementation of a dependency interface/class in your container.
* **Example:**
```java
public class CheckoutService {
    private TaxCalculator taxCalculator;
    public void setTaxCalculator(TaxCalculator taxCalculator) {
        this.taxCalculator = taxCalculator;
    }
}
```
```xml
<!-- Bean ID does NOT need to match property name; only the class type matters -->
<bean id="gstCalculator2026" class="com.example.IndiaGstCalculator" />
<bean id="checkoutService" class="com.example.CheckoutService" autowire="byType" />
```

#### 4. `autowire="constructor"`
* **How it works:** Works identically to `byType`, except it inspects **constructor arguments** instead of setter methods. Spring selects the constructor with the most arguments that can be satisfied by beans in the container.
* **When to use:** When your class uses **Constructor Injection** (best practice for mandatory dependencies and `final` fields) and you want XML to resolve arguments automatically.
* **Example:**
```java
public class ReportGenerator {
    private final DataSource dataSource;
    public ReportGenerator(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
```
```xml
<bean id="mysqlDataSource" class="com.example.MysqlDataSource" />
<bean id="reportGenerator" class="com.example.ReportGenerator" autowire="constructor" />
```

#### 5. `autowire="autodetect"` *(Deprecated in Spring 3.0+)*
* **How it works:** Spring first attempts `constructor` autowiring if a parameterized constructor exists; if no suitable constructor exists, it falls back to `byType` setter autowiring.
* **When to use:** Legacy Spring 2.x codebases only. Do not use in modern Spring versions.

### Limitations of XML Autowiring
1. **Primitives & Strings cannot be autowired:** `int`, `boolean`, `String`, and `Class` properties must still be injected manually using `<property value="..." />`.
2. **Explicit overrides Autowiring:** Any explicit `<property>` or `<constructor-arg>` tag always overrides autowiring.
3. **Reduced Visibility:** Overusing `byType` or `byName` in massive XML files makes it harder to trace dependencies without IDE tooling.

---

## 7. Important Concepts Often Missed

To have a complete mastery of the Spring Framework, keep these critical concepts in mind:

1. **Spring Bean Scopes (Beyond Singleton):**
   * `singleton` (Default): One instance per Spring IoC container. *(Warning: Must be stateless to remain thread-safe!)*
   * `prototype`: A brand-new instance every time the bean is requested.
   * `request` / `session` / `application` / `websocket`: Web-aware scopes tied to HTTP lifecycle.
2. **Full Bean Lifecycle Pipeline:**
   * Instantiation $ightarrow$ Populate Properties (DI) $ightarrow$ `BeanNameAware` / `ApplicationContextAware` $ightarrow$ `BeanPostProcessor.postProcessBeforeInitialization` $ightarrow$ `@PostConstruct` / `InitializingBean.afterPropertiesSet()` / `init-method` $ightarrow$ `BeanPostProcessor.postProcessAfterInitialization` (AOP Proxies created here) $ightarrow$ Bean Ready $ightarrow$ `@PreDestroy` / `DisposableBean.destroy()` / `destroy-method`.
3. **Circular Dependencies:**
   * If Bean A needs Bean B in its constructor, and Bean B needs Bean A in its constructor, Spring throws `BeanCurrentlyInCreationException`. Setter injection or `@Lazy` can break the cycle, though refactoring the design is preferred.
4. **Java-Based & Annotation Configuration (`@Configuration`, `@Bean`, `@ComponentScan`, `@Autowired`):**
   * While `spring.xml` teaches the underlying mechanics clearly, modern Spring applications use `@Configuration` classes and `@Component` stereotypes (`@Service`, `@Repository`, `@Controller`) for type-safe, compile-time checked wiring.
5. **Spring Framework vs. Spring Boot:**
   * **Spring Framework** is the core engine (DI, AOP, MVC, JDBC).
   * **Spring Boot** is an opinionated wrapper built *on top* of the Spring Framework that provides **Auto-Configuration**, **Starter POMs**, and **Embedded Servers** (Tomcat/Jetty) so you don't have to write boilerplate XML or Java setup code.

---

## 8. Real-Life Runnable Example: E-Commerce Order Processing System

Below is a complete, self-contained, runnable Maven/Java example demonstrating **IoC**, **Constructor & Setter DI**, **`spring.xml` configuration**, **XML Autowiring (`byName`, `byType`, `constructor`)**, and **Bean Lifecycle callbacks**.

### Project Structure
```text
spring-ecommerce-demo/
├── pom.xml
└── src/
    └── main/
        ├── java/com/example/shop/
        │   ├── PaymentGateway.java
        │   ├── UpiPaymentGateway.java
        │   ├── CardPaymentGateway.java
        │   ├── InventoryService.java
        │   ├── EmailNotificationService.java
        │   ├── OrderService.java
        │   └── MainApp.java
        └── resources/
            └── spring.xml
```

### Step 1: `pom.xml` (Dependencies)
```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.example</groupId>
    <artifactId>spring-ecommerce-demo</artifactId>
    <version>1.0.0</version>

    <properties>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
        <spring.version>6.1.14</spring.version>
    </properties>

    <dependencies>
        <!-- Spring Context includes Core, Beans, Context, and SpEL -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context</artifactId>
            <version>${spring.version}</version>
        </dependency>
    </dependencies>
</project>
```

### Step 2: Java Classes (`src/main/java/com/example/shop/`)

#### `PaymentGateway.java` (Interface for Loose Coupling)
```java
package com.example.shop;

public interface PaymentGateway {
    boolean processPayment(String orderId, double amount);
}
```

#### `UpiPaymentGateway.java` (Implementation 1)
```java
package com.example.shop;

public class UpiPaymentGateway implements PaymentGateway {
    private String upiProvider;

    public void setUpiProvider(String upiProvider) {
        this.upiProvider = upiProvider;
    }

    @Override
    public boolean processPayment(String orderId, double amount) {
        System.out.println("[Payment - UPI via " + upiProvider + "] Paid INR " + amount + " for Order: " + orderId);
        return true;
    }
}
```

#### `CardPaymentGateway.java` (Implementation 2)
```java
package com.example.shop;

public class CardPaymentGateway implements PaymentGateway {
    @Override
    public boolean processPayment(String orderId, double amount) {
        System.out.println("[Payment - Credit Card] Paid INR " + amount + " for Order: " + orderId);
        return true;
    }
}
```

#### `InventoryService.java` (Demonstrates Lifecycle Callbacks & Constructor DI)
```java
package com.example.shop;

public class InventoryService {
    private final String warehouseLocation;

    public InventoryService(String warehouseLocation) {
        this.warehouseLocation = warehouseLocation;
    }

    public void init() {
        System.out.println("[Lifecycle - Init] Connected to Warehouse Inventory at: " + warehouseLocation);
    }

    public boolean reserveStock(String item, int quantity) {
        System.out.println("[Inventory] Reserved " + quantity + "x '" + item + "' from " + warehouseLocation);
        return true;
    }

    public void cleanup() {
        System.out.println("[Lifecycle - Destroy] Closing Warehouse Inventory connection.");
    }
}
```

#### `EmailNotificationService.java` (Will be Autowired `byType`)
```java
package com.example.shop;

public class EmailNotificationService {
    public void sendOrderConfirmation(String customerEmail, String orderId) {
        System.out.println("[Email] Sent confirmation for Order " + orderId + " to " + customerEmail);
    }
}
```

#### `OrderService.java` (Main Business Service combining Constructor & Setter Injection)
```java
package com.example.shop;

public class OrderService {
    // Injected via Constructor
    private final InventoryService inventoryService;

    // Injected via Setter (Autowiring)
    private PaymentGateway upiPaymentGateway;        // Matched 'byName'
    private EmailNotificationService emailService;   // Matched 'byType'

    public OrderService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    public void setUpiPaymentGateway(PaymentGateway upiPaymentGateway) {
        this.upiPaymentGateway = upiPaymentGateway;
    }

    public void setEmailService(EmailNotificationService emailService) {
        this.emailService = emailService;
    }

    public void placeOrder(String orderId, String item, int qty, double price, String customerEmail) {
        System.out.println("
--- Processing New Order: " + orderId + " ---");
        if (inventoryService.reserveStock(item, qty)) {
            boolean paid = upiPaymentGateway.processPayment(orderId, price * qty);
            if (paid) {
                emailService.sendOrderConfirmation(customerEmail, orderId);
                System.out.println("--- Order " + orderId + " Completed Successfully! ---
");
            }
        }
    }
}
```

### Step 3: `src/main/resources/spring.xml`
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="
           http://www.springframework.org/schema/beans
           https://www.springframework.org/schema/beans/spring-beans.xsd">

    <!-- 1. Two implementations of PaymentGateway -->
    <bean id="upiPaymentGateway" class="com.example.shop.UpiPaymentGateway">
        <property name="upiProvider" value="BHIM / Razorpay" />
    </bean>

    <bean id="cardPaymentGateway" class="com.example.shop.CardPaymentGateway" />

    <!-- 2. InventoryService with Constructor Arg + Init/Destroy Lifecycle Methods -->
    <bean id="inventoryService" class="com.example.shop.InventoryService"
          init-method="init"
          destroy-method="cleanup">
        <constructor-arg value="Pune Fulfillment Center" />
    </bean>

    <!-- 3. EmailNotificationService (Single bean of this type in container) -->
    <bean id="notifier" class="com.example.shop.EmailNotificationService" />

    <!-- 4. OrderService demonstrating:
         - Constructor Injection for InventoryService
         - Autowire 'byName' for 'upiPaymentGateway' (resolves ambiguity between UPI & Card!)
         - Explicit wiring (or autowire='byType') for EmailNotificationService -->
    <bean id="orderService" class="com.example.shop.OrderService" autowire="byName">
        <constructor-arg ref="inventoryService" />
        <property name="emailService" ref="notifier" />
    </bean>

</beans>
```

### Step 4: `MainApp.java` (Bootstraps the Spring Container)
```java
package com.example.shop;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainApp {
    public static void main(String[] args) {
        // 1. Start the Spring IoC Container using spring.xml
        ConfigurableApplicationContext context =
                new ClassPathXmlApplicationContext("spring.xml");

        // 2. Fetch the fully wired OrderService bean from the container
        OrderService orderService = context.getBean("orderService", OrderService.class);

        // 3. Execute real-world business logic
        orderService.placeOrder(
                "ORD-2026-101",
                "Mechanical Keyboard",
                2,
                4500.00,
                "customer@example.com"
        );

        // 4. Gracefully close the container to trigger destroy-method callbacks
        context.close();
    }
}
```

### Expected Console Output
```text
[Lifecycle - Init] Connected to Warehouse Inventory at: Pune Fulfillment Center

--- Processing New Order: ORD-2026-101 ---
[Inventory] Reserved 2x 'Mechanical Keyboard' from Pune Fulfillment Center
[Payment - UPI via BHIM / Razorpay] Paid INR 9000.0 for Order: ORD-2026-101
[Email] Sent confirmation for Order ORD-2026-101 to customer@example.com
--- Order ORD-2026-101 Completed Successfully! ---

[Lifecycle - Destroy] Closing Warehouse Inventory connection.
```
