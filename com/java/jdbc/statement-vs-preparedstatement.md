# Difference Between Statement and PreparedStatement in Java JDBC

In Java JDBC (`java.sql`), **`Statement`** is used for static, one-time SQL queries without parameters, whereas **`PreparedStatement`** (a subinterface of `Statement`) is used for precompiled, parameterized SQL queries that execute repeatedly and safely.

---

## Summary Comparison Table

| Feature | `Statement` | `PreparedStatement` |
| :--- | :--- | :--- |
| **1. Compilation & Caching** | Compiled every time it executes. | Precompiled once and cached by the database. |
| **2. Parameter Handling** | No placeholders; relies on string concatenation. | Uses `?` placeholders with type-safe setters. |
| **3. Security (SQL Injection)** | Vulnerable to SQL injection attacks. | Protects against SQL injection automatically. |
| **4. Performance (Repeated Runs)** | Slower when running the same query multiple times. | Faster for repeated execution or batch loops. |
| **5. Binary Data (BLOB/CLOB)** | Difficult to insert or read binary streams. | Built-in methods like `setBinaryStream()`. |
| **6. Best Use Case** | DDL commands (`CREATE`, `ALTER`, `DROP`). | DML operations (`SELECT`, `INSERT`, `UPDATE`, `DELETE`). |

---

## Detailed Differences with Code Examples

### 1. Compilation & Query Caching
* **`Statement`:** The database parses, compiles, and creates an execution plan every single time `executeQuery()` or `executeUpdate()` is called.
* **`PreparedStatement`:** The SQL template is sent to the database upon creation (`prepareStatement()`), where it is precompiled and cached for reuse.

#### Example:
```java
// Statement: Database compiles the SQL string on every call
Statement stmt = connection.createStatement();
stmt.executeUpdate("UPDATE accounts SET balance = 500 WHERE id = 101");
stmt.executeUpdate("UPDATE accounts SET balance = 600 WHERE id = 102"); // Recompiled again

// PreparedStatement: Database compiles the SQL template only once
PreparedStatement pstmt = connection.prepareStatement("UPDATE accounts SET balance = ? WHERE id = ?");
pstmt.setDouble(1, 500);
pstmt.setInt(2, 101);
pstmt.executeUpdate();

pstmt.setDouble(1, 600);
pstmt.setInt(2, 102);
pstmt.executeUpdate(); // Reuses cached execution plan
```

---

### 2. Parameter Handling
* **`Statement`:** Does not support dynamic parameters. Values must be hardcoded or concatenated directly into the SQL string, which becomes messy with quotes and dates.
* **`PreparedStatement`:** Supports `?` placeholders and provides type-safe setter methods (`setInt()`, `setString()`, `setDate()`, etc.).

#### Example:
```java
String name = "O'Connor"; // Contains a single quote that breaks string concatenation
int age = 28;

// Statement: Requires manual escaping and error-prone concatenation
Statement stmt = connection.createStatement();
String sql1 = "INSERT INTO users (name, age) VALUES ('" + name.replace("'", "''") + "', " + age + ")";
stmt.executeUpdate(sql1);

// PreparedStatement: Clean placeholders; driver handles escaping and formatting automatically
PreparedStatement pstmt = connection.prepareStatement("INSERT INTO users (name, age) VALUES (?, ?)");
pstmt.setString(1, name);
pstmt.setInt(2, age);
pstmt.executeUpdate();
```

---

### 3. Security (SQL Injection Prevention)
* **`Statement`:** Highly vulnerable to **SQL Injection** because concatenated user input is interpreted as executable SQL syntax.
* **`PreparedStatement`:** Prevents **SQL Injection** because bound parameters are treated strictly as literal data values, never as executable SQL commands.

#### Example:
```java
// Malicious user input attempting SQL Injection
String userInput = "admin' OR '1'='1";

// Statement: UNSAFE - Query becomes: SELECT * FROM users WHERE username = 'admin' OR '1'='1'
Statement stmt = connection.createStatement();
ResultSet rs1 = stmt.executeQuery("SELECT * FROM users WHERE username = '" + userInput + "'");

// PreparedStatement: SAFE - Database searches for the literal username "admin' OR '1'='1"
PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
pstmt.setString(1, userInput);
ResultSet rs2 = pstmt.executeQuery();
```

---

### 4. Performance in Loops & Batch Processing
* **`Statement`:** Slower when executing similar queries in a loop because each iteration sends a brand-new SQL string to be parsed.
* **`PreparedStatement`:** Significantly faster in loops and batch operations because only the parameter values are transmitted and bound per iteration.

#### Example:
```java
List<String> cities = List.of("Pune", "Mumbai", "Delhi");

// Statement: Parses and executes 3 distinct SQL strings
Statement stmt = connection.createStatement();
for (String city : cities) {
    stmt.executeUpdate("INSERT INTO locations (city_name) VALUES ('" + city + "')");
}

// PreparedStatement: Precompiles once and executes efficiently as a batch
PreparedStatement pstmt = connection.prepareStatement("INSERT INTO locations (city_name) VALUES (?)");
for (String city : cities) {
    pstmt.setString(1, city);
    pstmt.addBatch();
}
pstmt.executeBatch();
```

---

### 5. Handling Binary Data (BLOB / CLOB)
* **`Statement`:** Cannot directly pass binary streams (such as images, PDFs, or large files) into an SQL string.
* **`PreparedStatement`:** Provides dedicated stream methods like `setBinaryStream()` and `setBlob()` to store binary files cleanly.

#### Example:
```java
File imageFile = new File("profile.png");
FileInputStream fis = new FileInputStream(imageFile);

// Statement: Not practical for raw binary streams

// PreparedStatement: Easily binds binary file streams to a BLOB column
PreparedStatement pstmt = connection.prepareStatement("INSERT INTO avatars (user_id, photo) VALUES (?, ?)");
pstmt.setInt(1, 42);
pstmt.setBinaryStream(2, fis, (int) imageFile.length());
pstmt.executeUpdate();
```

---

### 6. Best Use Cases (DDL vs. DML)
* **`Statement`:** Ideal for Data Definition Language (**DDL**) statements (`CREATE`, `ALTER`, `DROP`) or static queries that run once without user input.
* **`PreparedStatement`:** Ideal for Data Manipulation Language (**DML**) statements (`SELECT`, `INSERT`, `UPDATE`, `DELETE`) in application logic.

#### Example:
```java
// Statement: Used for static DDL schema creation (placeholders are not allowed in DDL)
Statement stmt = connection.createStatement();
stmt.execute("CREATE TABLE IF NOT EXISTS products (id INT PRIMARY KEY, title VARCHAR(100))");

// PreparedStatement: Used for dynamic DML record insertion
PreparedStatement pstmt = connection.prepareStatement("INSERT INTO products (id, title) VALUES (?, ?)");
pstmt.setInt(1, 1);
pstmt.setString(2, "Mechanical Keyboard");
pstmt.executeUpdate();
```
