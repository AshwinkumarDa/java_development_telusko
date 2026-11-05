# Java Features Guide: Java 27 → Java 8 (Reverse Chronological)

> Current as of **5 Nov 2026**. Java 27 reached GA on 15 Sep 2026 (non-LTS, 6 months of updates). The latest LTS is **Java 25**.
> Each feature has: Definition, Description, Purpose/Reason, Benefits, Pros, Cons, Usage, and one runnable example.
> Scope note: the headline JEPs and their key APIs are demonstrated with code; minor JEPs appear in a table per release.

**Status legend:** Final = permanent. Preview = needs `--enable-preview`. Incubator = needs `--add-modules jdk.incubator.*`. Experimental = needs `-XX:+UnlockExperimentalVMOptions`.

**Running preview code (Java 27):**
```bash
javac --release 27 --enable-preview Main.java
java --enable-preview Main
# or single-file:  java --enable-preview --source 27 Main.java
```

## Release Overview

| Version | Released | LTS | Headline |
|---|---|---|---|
| 27 | Sep 2026 | – | Post-quantum TLS, G1 default everywhere, compact headers default, 4 previews |
| 26 | Mar 2026 | – | HTTP/3 client, "final means final", AOT cache for any GC |
| 25 | Sep 2025 | ✅ | Scoped Values, module imports, simple `main`, flexible constructors, KDF |
| 24 | Mar 2025 | – | Stream Gatherers, Class-File API, ML-KEM/ML-DSA |
| 23 | Sep 2024 | – | Markdown Javadoc, generational ZGC default |
| 22 | Mar 2024 | – | FFM API, unnamed variables `_`, multi-file launch |
| 21 | Sep 2023 | ✅ | Virtual threads, sequenced collections, record patterns, pattern switch |
| 20 | Mar 2023 | – | Previews/incubators only |
| 19 | Sep 2022 | – | Previews/incubators only (virtual threads preview) |
| 18 | Mar 2022 | – | Simple Web Server, UTF-8 default |
| 17 | Sep 2021 | ✅ | Sealed classes, RandomGenerator |
| 16 | Mar 2021 | – | Records, `instanceof` patterns |
| 15 | Sep 2020 | – | Text blocks |
| 14 | Mar 2020 | – | Switch expressions, helpful NPEs |
| 13 | Sep 2019 | – | Previews only |
| 12 | Mar 2019 | – | `Collectors.teeing`, compact number format |
| 11 | Sep 2018 | ✅ | HTTP Client, String/Files APIs |
| 10 | Mar 2018 | – | `var` |
| 9 | Sep 2017 | – | Modules, JShell, collection factories, Flow |
| 8 | Mar 2014 | ✅ | Lambdas, Streams, Optional, `java.time` |

---

# JAVA 27 (September 2026)

Nine JEPs: 4 previews, 1 incubator, 4 final/default changes.

### 27.1 Structured Concurrency (JEP 533, 7th Preview)
- **Definition:** An API that treats a group of concurrent subtasks as one unit of work.
- **Description:** `StructuredTaskScope.open()` creates a scope; `fork()` starts subtasks (virtual threads); `join()` waits; a `Joiner` decides success/failure policy.
- **Purpose / Reason:** Plain `ExecutorService` code leaks threads and ignores cancellation when one subtask fails.
- **Benefits:** No thread leaks, automatic cancellation, parent/child relationships visible in thread dumps.
- **Pros:** Readable, reliable, observable concurrent code.
- **Cons:** Still preview; API changed across previews; needs `--enable-preview`.
- **Usage:** Fan-out calls to several services (user + orders + prices) and combine results.

```java
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.StructuredTaskScope.Joiner;
import java.util.concurrent.StructuredTaskScope.Subtask;

public class Dashboard {
    record Page(String user, int orders) {}
    static String user() throws Exception { Thread.sleep(100); return "alice"; }
    static int orders() throws Exception { Thread.sleep(150); return 3; }

    public static void main(String[] args) throws Exception {
        // Default joiner: wait for all, fail if any fails
        try (var scope = StructuredTaskScope.open()) {
            Subtask<String>  u = scope.fork(() -> user());
            Subtask<Integer> o = scope.fork(() -> orders());
            scope.join();
            System.out.println(new Page(u.get(), o.get()));
        }
        // Race: first successful result wins, others are cancelled
        try (var scope = StructuredTaskScope.open(Joiner.<String>anySuccessfulResultOrThrow())) {
            scope.fork(() -> { Thread.sleep(300); return "slow-mirror"; });
            scope.fork(() -> { Thread.sleep(50);  return "fast-mirror"; });
            System.out.println(scope.join());   // fast-mirror
        }
    }
}
```
*Note: shown with the JDK 25 API shape; later previews made small refinements, so check the Javadoc for your build.*

### 27.2 Lazy Constants (JEP 531, 3rd Preview)
- **Definition:** Holders for immutable values that are initialised on first use but treated by the JVM like `final`.
- **Description:** `LazyConstant.of(Supplier)` computes once, thread-safely; `List.ofLazy(n, fn)` / `Map.ofLazy(keys, fn)` do this per element. (Called `StableValue` in JDK 25.)
- **Purpose / Reason:** `final` fields must be set eagerly, which slows startup; double-checked locking is error-prone.
- **Benefits:** Faster startup, thread-safe once-only init, constant-folding performance.
- **Pros:** Replaces holder-class and DCL idioms.
- **Cons:** Preview; API renamed between previews.
- **Usage:** Loggers, config, expensive singletons, lazily built lookup tables.

```java
import java.util.List;

public class App {
    record Config(String url) { static Config load() { System.out.println("loading"); return new Config("db://x"); } }

    private static final LazyConstant<Config> CONFIG = LazyConstant.of(Config::load);
    private static final List<String> NAMES = List.ofLazy(3, i -> "worker-" + i);  // computed per index on demand

    public static void main(String[] args) {
        System.out.println(CONFIG.get());   // "loading" printed once
        System.out.println(CONFIG.get());   // cached
        System.out.println(NAMES.get(2));   // worker-2
    }
}
```

### 27.3 Primitive Types in Patterns, instanceof, switch (JEP 532, 5th Preview)
- **Definition:** Lets pattern matching, `instanceof` and `switch` work with primitive types (`int`, `long`, `boolean`, …).
- **Description:** `case int i when i > 100`, `x instanceof int i` (true only if the conversion is lossless), `switch` over `long`/`float`/`double`/`boolean`.
- **Purpose / Reason:** Patterns were reference-only, which forced boxing and special cases.
- **Benefits:** Uniform language, safe narrowing checks, no manual range checks.
- **Pros:** Less boilerplate, stricter dominance checks catch bugs.
- **Cons:** Preview; subtle exactness rules to learn.
- **Usage:** HTTP status codes, safe `long`→`int` narrowing, numeric parsing.

```java
public class Prim {
    static String http(int code) {
        return switch (code) {
            case 200 -> "OK";
            case int c when c >= 500 -> "Server error " + c;
            case int c when c >= 400 -> "Client error " + c;
            case int c -> "Other " + c;
        };
    }
    public static void main(String[] args) {
        System.out.println(http(503));
        long big = 5_000_000_000L, small = 42L;
        if (big   instanceof int i) System.out.println("fits " + i); else System.out.println("too big");
        if (small instanceof int i) System.out.println("fits " + i);   // fits 42
        boolean flag = true;
        System.out.println(switch (flag) { case true -> "on"; case false -> "off"; });
    }
}
```

### 27.4 PEM Encodings of Cryptographic Objects (JEP 538, 3rd Preview)
- **Definition:** A standard API to encode/decode keys, certificates and CRLs in PEM text format.
- **Description:** `PEMEncoder`, `PEMDecoder`, `PEMRecord`, `DEREncodable`; supports encrypted private keys.
- **Purpose / Reason:** Developers hand-rolled Base64 + header/footer parsing, which is error-prone.
- **Benefits:** Interoperability with OpenSSL, Kubernetes, cloud tooling.
- **Pros:** One-line round-trips; encryption support.
- **Cons:** Preview; API still evolving.
- **Usage:** Loading TLS keys/certs, exporting public keys, key rotation tools.

```java
import java.security.*;
import java.security.PEMDecoder;
import java.security.PEMEncoder;

public class Pem {
    public static void main(String[] args) throws Exception {
        KeyPair kp = KeyPairGenerator.getInstance("Ed25519").generateKeyPair();
        String pub = PEMEncoder.of().encodeToString(kp.getPublic());
        System.out.println(pub);                                   // -----BEGIN PUBLIC KEY----- ...
        PublicKey back = PEMDecoder.of().decode(pub, PublicKey.class);
        System.out.println(back.equals(kp.getPublic()));           // true
        String enc = PEMEncoder.of().withEncryption("s3cret".toCharArray()).encodeToString(kp.getPrivate());
        PrivateKey pk = PEMDecoder.of().withDecryption("s3cret".toCharArray()).decode(enc, PrivateKey.class);
    }
}
```

### 27.5 Post-Quantum Hybrid Key Exchange for TLS 1.3 (JEP 527, Final)
- **Definition:** TLS 1.3 key exchange combining a classical algorithm with a quantum-resistant one (e.g. X25519 + ML-KEM-768).
- **Description:** Implements hybrid named groups; applications using `javax.net.ssl` get them by default.
- **Purpose / Reason:** "Harvest now, decrypt later" attacks threaten today's recorded traffic.
- **Benefits:** Protection against future quantum attacks with zero code change.
- **Pros:** Automatic; safe because the classical half still protects you.
- **Cons:** Larger handshakes; both peers must support it.
- **Usage:** All HTTPS/TLS clients and servers; optionally pin named groups.

```java
import javax.net.ssl.SSLParameters;
import java.net.http.*;
import java.net.URI;

public class PqTls {
    public static void main(String[] args) throws Exception {
        SSLParameters p = new SSLParameters();
        p.setNamedGroups(new String[] { "X25519MLKEM768", "x25519" });   // optional explicit preference
        HttpClient c = HttpClient.newBuilder().sslParameters(p).build();
        var r = c.send(HttpRequest.newBuilder(URI.create("https://example.com")).build(),
                       HttpResponse.BodyHandlers.ofString());
        System.out.println(r.statusCode());
    }
}
```

### 27.6 G1 Is the Default GC in All Environments (JEP 523, Final)
- **Definition:** The JVM always picks G1 when no collector is specified.
- **Description:** Previously small machines (<2 CPUs or <1792 MB) got Serial GC.
- **Purpose / Reason:** One predictable default; G1 improved enough to replace Serial in small containers.
- **Benefits:** Consistent behaviour across laptops, CI and containers.
- **Pros:** No code change; shorter pauses.
- **Cons:** Slightly higher footprint in tiny containers; use `-XX:+UseSerialGC` to opt out.
- **Usage:** Verify the active collector.

```java
import java.lang.management.ManagementFactory;

public class Gc {
    public static void main(String[] args) {
        ManagementFactory.getGarbageCollectorMXBeans()
            .forEach(b -> System.out.println(b.getName()));   // G1 Young Generation, G1 Old Generation ...
    }
}
```

### 27.7 Compact Object Headers by Default (JEP 534, Final)
- **Definition:** Object headers shrink from 96 to 64 bits on 64-bit JVMs.
- **Description:** Now on by default; disable with `-XX:-UseCompactObjectHeaders`.
- **Purpose / Reason:** Millions of small objects waste memory on headers.
- **Benefits:** Smaller heap, better cache locality, higher deployment density.
- **Pros:** Free memory/CPU win.
- **Cons:** Some tools/agents that assume the old layout may break; limits on very large class hierarchies.
- **Usage:** Check the flag and measure heap.

```bash
java -XX:+PrintFlagsFinal -version | grep UseCompactObjectHeaders
java -Xmx512m -Xlog:gc Alloc            # compare with -XX:-UseCompactObjectHeaders
```
```java
public class Alloc {
    record P(int x, int y) {}
    public static void main(String[] a) {
        var list = new java.util.ArrayList<P>();
        for (int i = 0; i < 5_000_000; i++) list.add(new P(i, i));
        Runtime r = Runtime.getRuntime();
        System.out.println((r.totalMemory() - r.freeMemory()) / 1_048_576 + " MB used");
    }
}
```

### 27.8 Vector API (JEP 537, 12th Incubator)
- **Definition:** API for SIMD vector computations that compile to CPU vector instructions.
- **Description:** `VectorSpecies`, `FloatVector`, `IntVector`, `VectorMask`, `VectorOperators`.
- **Purpose / Reason:** Scalar loops under-use modern CPUs; the JIT can't always auto-vectorise.
- **Benefits:** Large speedups for numeric/AI workloads.
- **Pros:** Portable across x64/AArch64.
- **Cons:** Incubating until Valhalla lands; verbose.
- **Usage:** Image processing, dot products, analytics.

```java
import jdk.incubator.vector.*;

public class Vec {
    static final VectorSpecies<Float> S = FloatVector.SPECIES_PREFERRED;
    static void add(float[] a, float[] b, float[] c) {
        int i = 0, upper = S.loopBound(a.length);
        for (; i < upper; i += S.length())
            FloatVector.fromArray(S, a, i).add(FloatVector.fromArray(S, b, i)).intoArray(c, i);
        for (; i < a.length; i++) c[i] = a[i] + b[i];      // tail
    }
    public static void main(String[] x) {
        float[] a = new float[1000], b = new float[1000], c = new float[1000];
        java.util.Arrays.fill(a, 1f); java.util.Arrays.fill(b, 2f);
        add(a, b, c);
        System.out.println(c[999]);   // 3.0
    }
}
// run: java --add-modules jdk.incubator.vector Vec.java
```

### 27.9 JFR In-Process Data Redaction (JEP 536, Final)
- **Definition:** JDK Flight Recorder redacts sensitive data before it is written to a recording.
- **Description:** Covers command-line arguments and initial values of environment variables and system properties.
- **Purpose / Reason:** Recordings were leaking secrets (passwords in `-D` flags, env tokens).
- **Benefits:** Recordings can be shared with vendors safely.
- **Pros:** Redaction happens in-process, before data leaves the JVM.
- **Cons:** Redacted values reduce diagnostic detail.
- **Usage:** Start a recording and inspect JVM info. See JEP 536 for configuration switches.

```bash
java -XX:StartFlightRecording=filename=rec.jfr,duration=30s -Ddb.password=secret App
jfr print --events jdk.JVMInformation rec.jfr      # jvmArguments should show redacted values
```

**Other Java 27 changes (non-JEP):** updated ML-KEM/ML-DSA private key encodings, faster ML-KEM/ML-DSA/X25519/Ed25519, `jcmd VM.security_properties`, JSON thread-dump number fixes, `HttpServer` routing refinements.

---

# JAVA 26 (March 2026)

### 26.1 HTTP/3 for the HTTP Client (JEP 517, Final)
- **Definition:** `java.net.http.HttpClient` can speak HTTP/3 (QUIC over UDP).
- **Description:** `HttpClient.Version.HTTP_3`; discovery controlled by `HttpOption.H3_DISCOVERY`.
- **Purpose / Reason:** HTTP/3 avoids TCP head-of-line blocking and has faster handshakes.
- **Benefits:** Lower latency on lossy/mobile networks.
- **Pros:** Same API; falls back to HTTP/2 when needed.
- **Cons:** UDP can be blocked by firewalls; server support varies.
- **Usage:** Calling CDNs and modern APIs.

```java
import java.net.URI;
import java.net.http.*;

public class H3 {
    public static void main(String[] args) throws Exception {
        HttpClient client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_3).build();
        HttpRequest req = HttpRequest.newBuilder(URI.create("https://cloudflare.com/")).GET().build();
        HttpResponse<String> res = client.send(req, HttpResponse.BodyHandlers.ofString());
        System.out.println(res.version() + " " + res.statusCode());
    }
}
```

### 26.2 Prepare to Make Final Mean Final (JEP 500, Final)
- **Definition:** Warns when code mutates `final` fields through deep reflection.
- **Description:** `Field.setAccessible(true)` + `set(...)` on a `final` instance field now emits a warning; future releases will deny it.
- **Purpose / Reason:** Mutable "finals" break JIT optimisations and integrity guarantees.
- **Benefits:** Safer, faster programs; prepares the platform for stronger invariants.
- **Pros:** Gradual migration path.
- **Cons:** Breaks serialization/mocking libraries that rely on the hack.
- **Usage:** Audit your dependencies; opt in to strictness with `--illegal-final-field-mutation=deny` (verify the flag in `java --help-extra`).

```java
import java.lang.reflect.Field;

public class FinalTest {
    static class Cfg { private final int port = 8080; int port() { return port; } }
    public static void main(String[] args) throws Exception {
        Cfg c = new Cfg();
        Field f = Cfg.class.getDeclaredField("port");
        f.setAccessible(true);
        f.setInt(c, 9090);           // JDK 26: warning; later: IllegalAccessException
        System.out.println(c.port());
    }
}
```

### 26.3 Ahead-of-Time Object Caching with Any GC (JEP 516, Final)
- **Definition:** The AOT cache (from Project Leyden) can store pre-initialised heap objects for every GC, including ZGC.
- **Description:** Two-step workflow: record, then run with the cache.
- **Purpose / Reason:** Faster JVM startup and warmup.
- **Benefits:** Often 30–40%+ faster startup with no code changes.
- **Pros:** Works with ZGC, G1, Parallel, Serial.
- **Cons:** Cache is tied to the JDK version and classpath; extra build step.
- **Usage:** CI/CD and containers.

```bash
java -XX:AOTCacheOutput=app.aot -cp app.jar com.example.Main   # training run
java -XX:AOTCache=app.aot       -cp app.jar com.example.Main   # production run
```

**Other Java 26 changes:** JEP 522 (G1 throughput via less synchronization), JEP 504 (Applet API removed), plus new previews/incubators: 524 PEM (2nd), 525 Structured Concurrency (6th), 526 Lazy Constants (2nd), 529 Vector API (11th), 530 Primitive Patterns (4th).

---

# JAVA 25 (September 2025) — LTS

### 25.1 Scoped Values (JEP 506, Final)
- **Definition:** Immutable, scoped data shared with callees and child threads.
- **Description:** `ScopedValue.newInstance()`, `ScopedValue.where(key, value).run(...)/call(...)`, `get()`, `isBound()`, `orElse()`.
- **Purpose / Reason:** `ThreadLocal` is mutable, leaks, and is costly with millions of virtual threads.
- **Benefits:** Safe context propagation (user, trace id, tenant) with automatic cleanup.
- **Pros:** Immutable, fast, bounded lifetime.
- **Cons:** Read-only; different mental model than `ThreadLocal`.
- **Usage:** Request context in servers.

```java
public class Ctx {
    static final ScopedValue<String> USER = ScopedValue.newInstance();

    static void handle() {
        System.out.println("user=" + USER.get() + ", bound=" + USER.isBound());
    }
    public static void main(String[] args) {
        ScopedValue.where(USER, "alice").run(Ctx::handle);        // user=alice
        System.out.println(USER.orElse("anonymous"));              // outside scope: anonymous
    }
}
```

### 25.2 Module Import Declarations (JEP 511, Final)
- **Definition:** `import module M;` imports all public packages exported by a module.
- **Description:** `import module java.base;` brings in `java.util`, `java.io`, `java.util.stream`, etc.
- **Purpose / Reason:** Long import lists clutter small programs and scripts.
- **Benefits:** Less boilerplate; great for learning and scripting.
- **Pros:** One line replaces dozens.
- **Cons:** Possible name ambiguity (e.g. `List` in `java.awt`), less explicit.
- **Usage:** Scripts, demos, teaching.

```java
import module java.base;

public class Mods {
    public static void main(String[] args) {
        Map<String, List<Integer>> m = Map.of("a", List.of(1, 2, 3));
        System.out.println(m.values().stream().flatMap(List::stream).collect(Collectors.toList()));
    }
}
```

### 25.3 Compact Source Files & Instance Main Methods (JEP 512, Final)
- **Definition:** Programs without a class declaration and with a non-static, no-arg `main`.
- **Description:** `void main()` in a file; `IO.println/print/readln` in `java.lang`.
- **Purpose / Reason:** "Hello World" required `public class`, `static`, `String[]`.
- **Benefits:** Gentle learning curve, quick scripts.
- **Pros:** Can still grow into a normal class.
- **Cons:** Not for large apps; implicit class cannot be referenced from others.
- **Usage:** Teaching, one-off tools.

```java
// Hello.java    →   java Hello.java
void main() {
    String name = IO.readln("Name: ");
    IO.println("Hello, " + name);
}
```

### 25.4 Flexible Constructor Bodies (JEP 513, Final)
- **Definition:** Statements are allowed before `super(...)`/`this(...)` as long as they don't use `this`.
- **Description:** A "prologue" validates or computes arguments.
- **Purpose / Reason:** Workarounds (static helpers) were needed to validate before calling `super`.
- **Benefits:** Fail fast, avoids half-initialised objects, prevents superclass calling overridden methods on uninitialised fields.
- **Pros:** Cleaner validation.
- **Cons:** Prologue cannot read instance fields.
- **Usage:** Argument checks, derived arguments.

```java
class Person { final String name; Person(String n) { this.name = n; } }

class Employee extends Person {
    final int level;
    Employee(String name, int level) {
        if (level < 1) throw new IllegalArgumentException("level");   // before super()
        String clean = name.strip();
        super(clean);
        this.level = level;
    }
}
```

### 25.5 Key Derivation Function API (JEP 510, Final)
- **Definition:** Standard API (`javax.crypto.KDF`) for deriving keys (HKDF, Argon2 later).
- **Description:** `KDF.getInstance("HKDF-SHA256")`, `HKDFParameterSpec` (extract/expand).
- **Purpose / Reason:** Needed for TLS 1.3, hybrid public-key encryption and post-quantum schemes.
- **Benefits:** Provider-agnostic, standard crypto.
- **Pros:** Replaces hand-written HMAC loops.
- **Cons:** Only HKDF built in initially.
- **Usage:** Deriving AES keys from shared secrets.

```java
import javax.crypto.KDF;
import javax.crypto.SecretKey;
import javax.crypto.spec.HKDFParameterSpec;

public class Hkdf {
    public static void main(String[] args) throws Exception {
        byte[] ikm = "shared-secret".getBytes(), salt = "salt".getBytes(), info = "app-v1".getBytes();
        KDF hkdf = KDF.getInstance("HKDF-SHA256");
        SecretKey key = hkdf.deriveKey("AES",
            HKDFParameterSpec.ofExtract().addIKM(ikm).addSalt(salt).thenExpand(info, 32));
        System.out.println(key.getAlgorithm() + " " + key.getEncoded().length);   // AES 32
    }
}
```

**Other Java 25 changes:** JEP 519 Compact Object Headers (product), 521 Generational Shenandoah (product), 509 JFR CPU-time profiling (experimental, Linux), 518/520 JFR sampling and method tracing, 514/515 AOT cache ergonomics and method profiling, 502 Stable Values (preview), 505 Structured Concurrency (5th preview), 507 Primitive patterns (3rd preview), 508 Vector API (10th), 470 PEM (preview), 503 Remove 32-bit x86.

---

# JAVA 24 (March 2025)

### 24.1 Stream Gatherers (JEP 485, Final)
- **Definition:** Custom intermediate operations for Streams via `Stream.gather(Gatherer)`.
- **Description:** Built-ins in `java.util.stream.Gatherers`: `windowFixed`, `windowSliding`, `fold`, `scan`, `mapConcurrent`.
- **Purpose / Reason:** Streams couldn't express windowing, running totals or bounded concurrent mapping.
- **Benefits:** Extensible pipelines; concurrency on virtual threads with a cap.
- **Pros:** Reusable, composable.
- **Cons:** Writing custom `Gatherer`s is advanced.
- **Usage:** Batching, moving averages, parallel I/O.

```java
import java.util.stream.*;

public class Gath {
    public static void main(String[] args) {
        System.out.println(Stream.of(1,2,3,4,5,6,7).gather(Gatherers.windowFixed(3)).toList());   // [[1,2,3],[4,5,6],[7]]
        System.out.println(Stream.of(1,2,3,4).gather(Gatherers.windowSliding(2)).toList());       // [[1,2],[2,3],[3,4]]
        System.out.println(Stream.of(1,2,3,4).gather(Gatherers.scan(() -> 0, Integer::sum)).toList()); // [1,3,6,10]
        System.out.println(Stream.of(1,2,3).gather(Gatherers.fold(() -> "", (s, x) -> s + x)).toList()); // [123]
        System.out.println(Stream.of(1,2,3).gather(Gatherers.mapConcurrent(3, x -> x * x)).toList());    // [1,4,9]
    }
}
```

### 24.2 Class-File API (JEP 484, Final)
- **Definition:** Standard API (`java.lang.classfile`) to parse, generate and transform `.class` files.
- **Description:** `ClassFile.of()`, `ClassModel`, `MethodModel`, transforms and builders.
- **Purpose / Reason:** Frameworks bundled ASM, which lags behind each JDK release.
- **Benefits:** Tooling supports new Java versions on day one.
- **Pros:** Immutable, modern API, in the JDK.
- **Cons:** Low-level; mostly for framework authors.
- **Usage:** Agents, bytecode analysis, code generation.

```java
import java.lang.classfile.*;
import java.nio.file.Path;

public class Cf {
    public static void main(String[] args) throws Exception {
        ClassModel cm = ClassFile.of().parse(Path.of("Cf.class"));
        System.out.println(cm.thisClass().asInternalName());
        for (MethodModel m : cm.methods())
            System.out.println(m.methodName().stringValue() + m.methodType().stringValue());
    }
}
```

### 24.3 Quantum-Resistant ML-KEM & ML-DSA (JEPs 496, 497, Final)
- **Definition:** NIST-standard post-quantum key encapsulation (ML-KEM) and signatures (ML-DSA).
- **Description:** Works through `KeyPairGenerator`, `KEM`, `Signature`.
- **Purpose / Reason:** Future quantum computers break RSA/ECC.
- **Benefits:** Quantum-safe key exchange and signing in the JDK.
- **Pros:** Standard JCA APIs.
- **Cons:** Larger keys/signatures.
- **Usage:** Hybrid TLS, document signing, key exchange.

```java
import java.security.*;
import javax.crypto.*;

public class Pq {
    public static void main(String[] args) throws Exception {
        // ML-DSA signature
        KeyPair sk = KeyPairGenerator.getInstance("ML-DSA-65").generateKeyPair();
        Signature s = Signature.getInstance("ML-DSA");
        s.initSign(sk.getPrivate()); s.update("hi".getBytes()); byte[] sig = s.sign();
        s.initVerify(sk.getPublic()); s.update("hi".getBytes());
        System.out.println("valid=" + s.verify(sig));

        // ML-KEM key encapsulation
        KeyPair kp = KeyPairGenerator.getInstance("ML-KEM-768").generateKeyPair();
        KEM kem = KEM.getInstance("ML-KEM");
        KEM.Encapsulated enc = kem.newEncapsulator(kp.getPublic()).encapsulate();
        SecretKey shared = kem.newDecapsulator(kp.getPrivate()).decapsulate(enc.encapsulation());
        System.out.println(java.util.Arrays.equals(shared.getEncoded(), enc.key().getEncoded()));  // true
    }
}
```

**Other Java 24 changes:** 491 virtual threads no longer pin on `synchronized`, 483 AOT class loading & linking, 486 Security Manager permanently disabled, 472 prepare to restrict JNI, 450 compact headers (experimental), 404 Generational Shenandoah (experimental), 498 `Unsafe` memory-access warnings, 490 non-generational ZGC removed, 479 Windows 32-bit removed.

---

# JAVA 23 (September 2024)

### 23.1 Markdown Documentation Comments (JEP 467, Final)
- **Definition:** Javadoc comments written in Markdown using `///`.
- **Description:** Supports headings, lists, tables, code fences and `[Link]` references.
- **Purpose / Reason:** HTML+`@tags` Javadoc is hard to read in source.
- **Benefits:** Readable source, familiar syntax.
- **Pros:** Mix with existing tags.
- **Cons:** Older tooling may not render it.
- **Usage:** Any API docs.

```java
/// Computes the **area** of a circle.
///
/// - Uses [Math#PI]
/// - Radius must be non-negative
///
/// ```java
/// double a = Circle.area(2.0);
/// ```
///
/// @param r the radius
/// @return the area
public static double area(double r) { return Math.PI * r * r; }
```

### 23.2 Generational ZGC by Default (JEP 474)
- **Definition:** `-XX:+UseZGC` now uses the generational mode.
- **Description:** Young/old generations; non-generational removed in 24.
- **Purpose / Reason:** Better throughput and memory use with the same ultra-low pauses.
- **Benefits:** Sub-millisecond pauses on large heaps.
- **Pros:** No tuning change.
- **Cons:** Higher CPU than G1 in some workloads.
- **Usage:**
```bash
java -XX:+UseZGC -Xmx16g -Xlog:gc App
```

**Other Java 23 changes:** 455 primitive patterns (1st preview), 466 Class-File API (2nd), 473 Stream Gatherers (2nd), 476 module imports (preview), 477 implicit classes (3rd), 480 Structured Concurrency (3rd), 481 Scoped Values (3rd), 482 flexible constructors (2nd), 469 Vector API (8th), 471 `Unsafe` deprecations.

---

# JAVA 22 (March 2024)

### 22.1 Foreign Function & Memory API (JEP 454, Final)
- **Definition:** Safe, fast access to native code and off-heap memory (replaces JNI).
- **Description:** `Linker`, `SymbolLookup`, `FunctionDescriptor`, `Arena`, `MemorySegment`, `ValueLayout`.
- **Purpose / Reason:** JNI is verbose, unsafe and slow to develop.
- **Benefits:** Call C libraries from pure Java; deterministic memory release.
- **Pros:** No glue C code; `jextract` generates bindings.
- **Cons:** Native access is "restricted" (needs `--enable-native-access`).
- **Usage:** Calling libc, SQLite, OpenSSL; off-heap buffers.

```java
import java.lang.foreign.*;
import java.lang.invoke.MethodHandle;

public class Ffm {
    public static void main(String[] args) throws Throwable {
        Linker linker = Linker.nativeLinker();
        MethodHandle strlen = linker.downcallHandle(
            linker.defaultLookup().find("strlen").orElseThrow(),
            FunctionDescriptor.of(ValueLayout.JAVA_LONG, ValueLayout.ADDRESS));
        try (Arena arena = Arena.ofConfined()) {
            MemorySegment str = arena.allocateFrom("Hello FFM");
            System.out.println((long) strlen.invokeExact(str));   // 9
        }
    }
}
// run: java --enable-native-access=ALL-UNNAMED Ffm.java
```

### 22.2 Unnamed Variables & Patterns `_` (JEP 456, Final)
- **Definition:** `_` marks a variable or pattern you intentionally don't use.
- **Description:** Works in locals, `for`, `catch`, lambdas, `try-with-resources`, record patterns.
- **Purpose / Reason:** Unused names cause warnings and noise.
- **Benefits:** Intent is explicit.
- **Pros:** Cleaner code.
- **Cons:** `_` can't be referenced.
- **Usage:**
```java
record Point(int x, int y) {}

public class Un {
    public static void main(String[] args) {
        int count = 0;
        for (var _ : java.util.List.of("a", "b", "c")) count++;
        try { Integer.parseInt("x"); } catch (NumberFormatException _) { System.out.println("bad"); }
        Object o = new Point(3, 4);
        if (o instanceof Point(int x, _)) System.out.println("x=" + x);
        java.util.Map.of("k", 1).forEach((k, _) -> System.out.println(k));
        System.out.println(count);
    }
}
```

### 22.3 Launch Multi-File Source-Code Programs (JEP 458)
- **Definition:** `java Main.java` can compile and run several source files together.
- **Description:** Other classes are found in the same directory tree.
- **Purpose / Reason:** Bridges single-file scripts and full build tools.
- **Benefits:** Prototype without Maven/Gradle.
- **Pros:** Zero setup.
- **Cons:** No dependency management.
- **Usage:** `java Main.java` where `Main.java` uses `Helper.java` in the same folder.

**Other Java 22 changes:** 423 G1 region pinning, 447 statements before `super()` (preview), 457 Class-File API (preview), 459 String Templates (2nd preview, later removed), 461 Gatherers (preview), 462 Structured Concurrency (2nd), 463 implicit classes (2nd), 464 Scoped Values (2nd), 460 Vector API (7th).

---

# JAVA 21 (September 2023) — LTS

### 21.1 Virtual Threads (JEP 444, Final)
- **Definition:** Lightweight JVM-managed threads, millions possible.
- **Description:** `Thread.ofVirtual()`, `Thread.startVirtualThread()`, `Executors.newVirtualThreadPerTaskExecutor()`.
- **Purpose / Reason:** Platform threads are expensive; async code is hard to read and debug.
- **Benefits:** Thread-per-request style at massive scale; blocking code is fine.
- **Pros:** Simple code, good throughput for I/O-bound apps.
- **Cons:** No gain for CPU-bound work; avoid pooling them; watch `synchronized` pinning (fixed in 24) and `ThreadLocal` misuse.
- **Usage:** HTTP servers, DB/API fan-out.

```java
import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class Virt {
    public static void main(String[] args) throws Exception {
        try (var ex = Executors.newVirtualThreadPerTaskExecutor()) {
            IntStream.range(0, 10_000).forEach(i -> ex.submit(() -> {
                Thread.sleep(Duration.ofSeconds(1));
                return i;
            }));
        }   // waits for all; finishes in ~1s
        Thread t = Thread.ofVirtual().name("worker").start(() -> System.out.println(Thread.currentThread()));
        t.join();
    }
}
```

### 21.2 Sequenced Collections (JEP 431, Final)
- **Definition:** Collections with a defined encounter order get uniform first/last/reverse methods.
- **Description:** New interfaces `SequencedCollection`, `SequencedSet`, `SequencedMap` with `getFirst`, `getLast`, `addFirst`, `addLast`, `removeFirst`, `removeLast`, `reversed`; maps add `firstEntry`, `lastEntry`, `pollFirstEntry`, `putFirst`.
- **Purpose / Reason:** Getting the last element of a `List`, `LinkedHashSet` or `Deque` used different idioms.
- **Benefits:** One API; cheap reverse views.
- **Pros:** Retrofitted into `List`, `Deque`, `LinkedHashSet`, `LinkedHashMap`, `SortedSet`.
- **Cons:** Unmodifiable collections throw on mutators; `reversed()` is a view.
- **Usage:**

```java
import java.util.*;

public class Seq {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>(List.of(1, 2, 3));
        list.addFirst(0); list.addLast(4);
        System.out.println(list.getFirst() + " " + list.getLast() + " " + list.reversed());  // 0 4 [4,3,2,1,0]

        LinkedHashMap<String,Integer> m = new LinkedHashMap<>();
        m.put("b", 2); m.putFirst("a", 1); m.putLast("c", 3);
        System.out.println(m.firstEntry() + " " + m.lastEntry() + " " + m.reversed());
        SequencedSet<String> s = new LinkedHashSet<>(List.of("x", "y"));
        System.out.println(s.reversed());   // [y, x]
    }
}
```

### 21.3 Pattern Matching for switch (JEP 441, Final)
- **Definition:** `switch` that matches on types, guards and `null`.
- **Description:** Type patterns, `when` guards, `case null`, exhaustiveness over sealed types.
- **Purpose / Reason:** `if/else instanceof` chains are verbose and non-exhaustive.
- **Benefits:** Compiler-checked completeness; no default needed for sealed hierarchies.
- **Pros:** Concise polymorphism without visitors.
- **Cons:** Order matters (dominance); learning curve.
- **Usage:**

```java
sealed interface Shape permits Circle, Square, Rect {}
record Circle(double r) implements Shape {}
record Square(double s) implements Shape {}
record Rect(double w, double h) implements Shape {}

public class Sw {
    static double area(Shape s) {
        return switch (s) {
            case Circle c -> Math.PI * c.r() * c.r();
            case Square q -> q.s() * q.s();
            case Rect r when r.w() == r.h() -> r.w() * r.w();
            case Rect r -> r.w() * r.h();
        };
    }
    static String describe(Object o) {
        return switch (o) {
            case null -> "null!";
            case Integer i when i > 10 -> "big int";
            case Integer i -> "int";
            case String str -> "string " + str.length();
            default -> "other";
        };
    }
    public static void main(String[] a) { System.out.println(area(new Rect(2, 3)) + " " + describe(42)); }
}
```

### 21.4 Record Patterns (JEP 440, Final)
- **Definition:** Deconstruct records directly in patterns, including nested.
- **Description:** `if (o instanceof Point(int x, int y))`, nested `Line(Point(var x1, var y1), Point p2)`.
- **Purpose / Reason:** Accessor calls after a type check are noisy.
- **Benefits:** Declarative data extraction.
- **Pros:** Works with generics and `var`.
- **Cons:** Records only.
- **Usage:**

```java
record Point(int x, int y) {}
record Line(Point from, Point to) {}

public class Rp {
    public static void main(String[] args) {
        Object o = new Line(new Point(0, 0), new Point(3, 4));
        if (o instanceof Line(Point(var x1, var y1), Point(var x2, var y2))) {
            System.out.println(Math.hypot(x2 - x1, y2 - y1));   // 5.0
        }
    }
}
```

**Other Java 21 changes:** 439 Generational ZGC, 452 KEM API, 430 String Templates (preview, later removed), 443 unnamed patterns (preview), 445 unnamed classes (preview), 446 Scoped Values (preview), 453 Structured Concurrency (preview), 442 FFM (3rd preview), 448 Vector API (6th), 449 deprecate Windows 32-bit, 451 prepare to disallow dynamic agent loading.

---

# JAVA 20 (March 2023) and JAVA 19 (September 2022)

Transitional releases: everything was preview or incubator. Their features became final later.

| Feature | Java 19 | Java 20 | Became final in |
|---|---|---|---|
| Virtual Threads | Preview | 2nd Preview | 21 |
| Structured Concurrency | Incubator | 2nd Incubator | Still preview (27: 7th) |
| Scoped Values | – | Incubator | 25 |
| Record Patterns | Preview | 2nd Preview | 21 |
| Pattern Matching for switch | 3rd Preview | 4th Preview | 21 |
| FFM API | Preview | 2nd Preview | 22 |
| Vector API | 4th Incubator | 5th Incubator | Still incubating |

---

# JAVA 18 (March 2022)

### 18.1 Simple Web Server (JEP 408)
- **Definition:** Minimal static file HTTP server, as a command (`jwebserver`) and API.
- **Description:** `SimpleFileServer`, `HttpHandlers`, `Request`, `Filter` in `com.sun.net.httpserver`.
- **Purpose / Reason:** Quick local serving of files for prototyping/testing.
- **Benefits:** No third-party dependency.
- **Pros:** One command; embeddable.
- **Cons:** GET/HEAD only, no TLS/auth; not for production.
- **Usage:**
```bash
jwebserver -p 9000 -d /tmp/site
```
```java
import com.sun.net.httpserver.SimpleFileServer;
import com.sun.net.httpserver.SimpleFileServer.OutputLevel;
import java.net.InetSocketAddress;
import java.nio.file.Path;

public class Web {
    public static void main(String[] args) {
        var server = SimpleFileServer.createFileServer(
            new InetSocketAddress(8080), Path.of("."), OutputLevel.VERBOSE);
        server.start();   // http://localhost:8080
    }
}
```

### 18.2 UTF-8 by Default (JEP 400)
- **Definition:** Default charset is UTF-8 on every platform.
- **Description:** `Charset.defaultCharset()` returns UTF-8; `file.encoding` is no longer OS-dependent.
- **Purpose / Reason:** Same program behaved differently on Windows vs Linux.
- **Benefits:** Portable text I/O.
- **Pros:** Fewer mojibake bugs.
- **Cons:** Legacy files in other encodings need explicit charsets.
- **Usage:**
```java
import java.nio.charset.*;
import java.nio.file.*;

public class Utf {
    public static void main(String[] args) throws Exception {
        System.out.println(Charset.defaultCharset());                 // UTF-8
        Files.writeString(Path.of("t.txt"), "héllo ✓");               // UTF-8 regardless of OS
        System.out.println(Files.readString(Path.of("t.txt"), StandardCharsets.UTF_8));
    }
}
```

**Other Java 18 changes:** 413 `@snippet` in Javadoc, 416 reflection via method handles, 418 internet-address resolution SPI, 420 pattern switch (2nd preview), 417 Vector API (3rd), 419 FFM (2nd incubator), 421 finalization deprecated for removal.

---

# JAVA 17 (September 2021) — LTS

### 17.1 Sealed Classes (JEP 409, Final)
- **Definition:** Classes/interfaces that restrict which types may extend or implement them.
- **Description:** `sealed`, `permits`, subclasses must be `final`, `sealed` or `non-sealed`.
- **Purpose / Reason:** Model closed hierarchies (algebraic data types) safely.
- **Benefits:** Exhaustive `switch`, better domain modelling.
- **Pros:** Compiler-enforced design.
- **Cons:** Less open for extension; subclasses must be in same module/package.
- **Usage:**
```java
sealed interface Payment permits Card, Upi, Cash {}
record Card(String number) implements Payment {}
record Upi(String id) implements Payment {}
final class Cash implements Payment {}

public class Sealed {
    static String pay(Payment p) {
        return switch (p) {            // exhaustive on Java 21+, no default
            case Card c -> "card " + c.number();
            case Upi u  -> "upi " + u.id();
            case Cash c -> "cash";
        };
    }
    public static void main(String[] a) { System.out.println(pay(new Upi("a@bank"))); }
}
```

### 17.2 Enhanced Pseudo-Random Number Generators (JEP 356)
- **Definition:** Unified `RandomGenerator` interface and algorithm factory.
- **Description:** `RandomGenerator`, `RandomGeneratorFactory`, `SplittableGenerator`, `JumpableGenerator`; algorithms like `L64X128MixRandom`.
- **Purpose / Reason:** `Random`, `ThreadLocalRandom`, `SplittableRandom` had no common type.
- **Benefits:** Swap algorithms without code change; better parallel streams.
- **Pros:** Modern, statistically better generators.
- **Cons:** Many algorithm names.
- **Usage:**
```java
import java.util.random.*;

public class Rng {
    public static void main(String[] args) {
        RandomGenerator g = RandomGenerator.of("L64X128MixRandom");
        System.out.println(g.nextInt(1, 100) + " " + g.nextDouble());
        RandomGeneratorFactory.all().map(RandomGeneratorFactory::name).sorted().limit(5).forEach(System.out::println);
        var f = RandomGeneratorFactory.<RandomGenerator.SplittableGenerator>of("L64X128MixRandom");
        System.out.println(f.create(42L).nextInt(10));   // reproducible by seed
    }
}
```

**Other Java 17 changes:** 403 strong encapsulation of JDK internals, 406 pattern switch (1st preview), 412 FFM (incubator), 414 Vector API (2nd), 415 context-specific deserialization filters, 411 Security Manager deprecated, 398 Applet API deprecated, 306 always-strict floating point, 382 macOS rendering pipeline.

---

# JAVA 16 (March 2021)

### 16.1 Records (JEP 395, Final)
- **Definition:** Compact immutable data carriers.
- **Description:** `record Point(int x, int y) {}` generates constructor, accessors, `equals`, `hashCode`, `toString`. Supports compact constructors and static methods.
- **Purpose / Reason:** POJOs/DTOs required huge boilerplate (or Lombok).
- **Benefits:** Concise, immutable, value semantics.
- **Pros:** Works with pattern matching and serialization.
- **Cons:** Cannot extend classes; fields are final.
- **Usage:**
```java
public class Rec {
    record User(String name, int age) {
        User {                                   // compact constructor: validation
            if (age < 0) throw new IllegalArgumentException("age");
            name = name.strip();
        }
        static User of(String n) { return new User(n, 0); }
        String label() { return name + " (" + age + ")"; }
    }
    public static void main(String[] a) {
        var u = new User(" Bob ", 30);
        System.out.println(u + " " + u.label() + " " + u.equals(new User("Bob", 30)));
    }
}
```

### 16.2 Pattern Matching for instanceof (JEP 394, Final)
- **Definition:** Test and cast in one step.
- **Description:** `if (o instanceof String s && s.length() > 3)`; binding is flow-scoped.
- **Purpose / Reason:** Redundant explicit casts.
- **Benefits:** Safer and shorter.
- **Pros:** Works with `&&` conditions.
- **Cons:** Scope rules can surprise beginners.
- **Usage:**
```java
public class Inst {
    static String f(Object o) {
        if (o instanceof String s && s.length() > 3) return s.toUpperCase();
        if (!(o instanceof Integer i)) return "other";
        return "int " + (i + 1);      // i in scope here
    }
    public static void main(String[] a) { System.out.println(f("hello") + " " + f(5) + " " + f(2.0)); }
}
```

### 16.3 `Stream.toList()` and `mapMulti`
- **Definition:** Terminal shortcut returning an unmodifiable list; `mapMulti` emits zero or more elements per input.
- **Description:** Replaces `collect(Collectors.toList())` when mutability isn't needed.
- **Purpose / Reason:** Verbose collectors for the most common case.
- **Benefits:** Shorter; immutable result.
- **Pros:** Allows `null` elements (unlike `List.of`).
- **Cons:** Returned list is unmodifiable.
- **Usage:**
```java
import java.util.stream.*;

public class Tl {
    public static void main(String[] args) {
        var evens = Stream.of(1, 2, 3, 4).filter(n -> n % 2 == 0).toList();      // [2, 4]
        var dup = Stream.of(1, 2).<Integer>mapMulti((n, sink) -> { sink.accept(n); sink.accept(n * 10); }).toList();
        System.out.println(evens + " " + dup);                                     // [2, 4] [1,10,2,20]
    }
}
```

**Other Java 16 changes:** Unix-domain sockets (JEP 380), Vector API (incubator), Foreign Linker API (incubator), elastic metaspace, strong encapsulation by default, value-based class warnings, sealed (2nd preview).

---

# JAVA 15 (September 2020)

### 15.1 Text Blocks (JEP 378, Final)
- **Definition:** Multi-line string literals with `"""`.
- **Description:** Incidental indentation is stripped; escapes `\s`, `\<newline>`; `String.formatted()`, `stripIndent()`, `translateEscapes()`.
- **Purpose / Reason:** JSON/SQL/HTML strings needed ugly concatenation and escaping.
- **Benefits:** Readable embedded languages.
- **Pros:** No escaping of quotes/newlines.
- **Cons:** Indentation rules need care.
- **Usage:**
```java
public class Tb {
    public static void main(String[] args) {
        String json = """
            {
              "name": "%s",
              "age": %d
            }
            """.formatted("Ann", 30);
        String sql = """
            SELECT id, name \
            FROM users WHERE active = true
            """;
        System.out.print(json + sql);
    }
}
```

**Other Java 15 changes:** Sealed classes (preview), Records (2nd preview), pattern `instanceof` (2nd preview), hidden classes (371), ZGC and Shenandoah production-ready, EdDSA signatures (339), biased locking deprecated, Nashorn removed.

---

# JAVA 14 (March 2020)

### 14.1 Switch Expressions (JEP 361, Final)
- **Definition:** `switch` that yields a value, with arrow cases.
- **Description:** `case A, B ->`, `yield` for blocks, no fall-through, exhaustive.
- **Purpose / Reason:** Old `switch` was verbose and bug-prone.
- **Benefits:** Concise, safer.
- **Pros:** Compile-time exhaustiveness on enums.
- **Cons:** Mixing old/new styles not allowed.
- **Usage:**
```java
public class Se {
    enum Day { MON, TUE, SAT, SUN }
    public static void main(String[] args) {
        Day d = Day.SAT;
        int len = switch (d) {
            case MON, TUE -> 8;
            case SAT, SUN -> { int w = 2; yield w; }
        };
        String kind = switch (len) { case 8 -> "work"; default -> "rest"; };
        System.out.println(len + " " + kind);
    }
}
```

### 14.2 Helpful NullPointerExceptions (JEP 358)
- **Definition:** NPE messages say exactly which expression was null.
- **Description:** On by default from Java 15.
- **Purpose / Reason:** `NullPointerException` at line X was ambiguous.
- **Benefits:** Faster debugging.
- **Pros:** No code changes.
- **Cons:** Slight message-computation cost (only on failure).
- **Usage:**
```java
public class Npe {
    static class A { B b; } static class B { String s; }
    public static void main(String[] args) {
        A a = new A();
        System.out.println(a.b.s.length());
        // Cannot read field "s" because "a.b" is null
    }
}
```

**Other Java 14 changes:** Records (preview), pattern `instanceof` (preview), text blocks (2nd preview), JFR event streaming (349), NUMA-aware G1, ZGC on macOS/Windows, CMS removed.

---

# JAVA 13 (September 2019)

Mostly previews: text blocks (1st preview), switch expressions (2nd preview). Final changes: dynamic CDS archives (350), ZGC returns unused memory (351), reimplemented legacy Socket API (353).

---

# JAVA 12 (March 2019)

### 12.1 `Collectors.teeing`, `CompactNumberFormat`, `String.indent/transform`
- **Definition:** Small API additions for collecting two results at once, short number display, and string manipulation.
- **Description:** `teeing(c1, c2, merger)`; `NumberFormat.getCompactNumberInstance`; `indent(n)`, `transform(fn)`.
- **Purpose / Reason:** Avoid streaming twice; localise "1K/1M"; chain string transforms.
- **Benefits:** Single pass; i18n support.
- **Pros:** Tiny, useful.
- **Cons:** Niche.
- **Usage:**
```java
import java.text.NumberFormat;
import java.util.*;
import java.util.stream.*;

public class J12 {
    record Stats(long count, double avg) {}
    public static void main(String[] args) {
        Stats s = Stream.of(4, 8, 15).collect(Collectors.teeing(
            Collectors.counting(), Collectors.averagingInt(i -> i), Stats::new));
        System.out.println(s);                                                          // Stats[count=3, avg=9.0]
        var f = NumberFormat.getCompactNumberInstance(Locale.US, NumberFormat.Style.SHORT);
        System.out.println(f.format(1_250_000));                                        // 1M
        System.out.print("a\nb".indent(2));
        System.out.println("hello".transform(String::toUpperCase));
    }
}
```

**Other Java 12 changes:** Switch expressions (preview), Shenandoah GC (experimental), default CDS archive, `Files.mismatch`, G1 abortable mixed collections.

---

# JAVA 11 (September 2018) — LTS

### 11.1 Standard HTTP Client (JEP 321)
- **Definition:** Modern HTTP/1.1 + HTTP/2 client in `java.net.http`.
- **Description:** `HttpClient`, `HttpRequest`, `HttpResponse`, `BodyHandlers`, async via `CompletableFuture`, WebSocket.
- **Purpose / Reason:** `HttpURLConnection` is outdated and clumsy.
- **Benefits:** Fluent builder, async, HTTP/2.
- **Pros:** No third-party library needed.
- **Cons:** Less feature-rich than OkHttp/Apache (interceptors, etc.).
- **Usage:**
```java
import java.net.URI;
import java.net.http.*;
import java.time.Duration;

public class Http {
    public static void main(String[] args) throws Exception {
        HttpClient c = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(5)).build();
        HttpRequest r = HttpRequest.newBuilder(URI.create("https://httpbin.org/get"))
            .header("Accept", "application/json").GET().build();
        System.out.println(c.send(r, HttpResponse.BodyHandlers.ofString()).statusCode());
        c.sendAsync(r, HttpResponse.BodyHandlers.ofString())
            .thenApply(HttpResponse::body).thenAccept(b -> System.out.println(b.length())).join();
    }
}
```

### 11.2 New String / Files / Predicate APIs and `var` in lambdas
- **Definition:** Convenience methods and syntax additions.
- **Description:** `isBlank`, `strip`, `stripLeading/Trailing`, `lines`, `repeat`; `Files.readString/writeString`; `Predicate.not`; `(var x, var y) ->`.
- **Purpose / Reason:** Common chores required utility libraries.
- **Benefits:** Unicode-aware trimming, simpler file I/O.
- **Pros:** Small and intuitive.
- **Cons:** `Files.readString` loads the full file.
- **Usage:**
```java
import java.nio.file.*;
import java.util.function.*;
import java.util.stream.*;

public class J11 {
    public static void main(String[] args) throws Exception {
        System.out.println("  ".isBlank() + " [" + " hi ".strip() + "] " + "ab".repeat(3));
        "a\nb\nc".lines().forEach(System.out::println);
        Path p = Files.writeString(Path.of("n.txt"), "x\n\ny\n");
        System.out.println(Files.readString(p).lines().filter(Predicate.not(String::isBlank)).collect(Collectors.toList()));
        BiFunction<Integer, Integer, Integer> add = (var a, var b) -> a + b;
        System.out.println(add.apply(2, 3));
    }
}
```

### 11.3 Launch Single-File Source Programs (JEP 330)
- **Definition:** Run a `.java` file directly.
- **Description:** In-memory compile and run; supports shebang scripts.
- **Purpose / Reason:** Quick scripts without `javac`.
- **Benefits:** Python-like workflow.
- **Pros:** Zero ceremony.
- **Cons:** One file only until Java 22.
- **Usage:**
```bash
java Hello.java arg1 arg2
```

**Other Java 11 changes:** TLS 1.3, Epsilon no-op GC, ZGC (experimental), Flight Recorder open-sourced, `Optional.isEmpty`, nest-based access, Java EE/CORBA modules removed.

---

# JAVA 10 (March 2018)

### 10.1 Local-Variable Type Inference `var` (JEP 286)
- **Definition:** The compiler infers the type of local variables.
- **Description:** `var` works for locals, `for`, try-with-resources; not fields, parameters or return types. Also: `List.copyOf`, `Collectors.toUnmodifiableList/Set/Map`, `Optional.orElseThrow()`.
- **Purpose / Reason:** Remove redundant type repetition (`Map<String,List<Integer>> m = new HashMap<String,List<Integer>>()`).
- **Benefits:** Less noise.
- **Pros:** Still statically typed.
- **Cons:** Can hurt readability when the right side is unclear.
- **Usage:**
```java
import java.util.*;
import java.util.stream.*;

public class V {
    public static void main(String[] args) {
        var map = new HashMap<String, List<Integer>>();
        map.computeIfAbsent("a", k -> new ArrayList<>()).add(1);
        for (var e : map.entrySet()) System.out.println(e.getKey() + "=" + e.getValue());
        var copy = List.copyOf(map.get("a"));
        var un = Stream.of(1, 2).collect(Collectors.toUnmodifiableList());
        System.out.println(copy + " " + un + " " + Optional.of(5).orElseThrow());
    }
}
```

**Other Java 10 changes:** Parallel full GC for G1, application class-data sharing, experimental Graal JIT, thread-local handshakes, heap allocation on alternative memory devices.

---

# JAVA 9 (September 2017)

### 9.1 Java Platform Module System (JPMS, JEP 261)
- **Definition:** Strong encapsulation and explicit dependencies via `module-info.java`.
- **Description:** `module`, `requires`, `exports`, `opens`, `uses/provides`; `jlink`, `jdeps`.
- **Purpose / Reason:** Classpath hell and an unwieldy monolithic JDK.
- **Benefits:** Smaller custom runtimes, hidden internals.
- **Pros:** Reliable configuration; `jlink` images.
- **Cons:** Migration pain; reflection needs `opens`.
- **Usage:**
```java
// module-info.java
module com.example.app {
    requires java.net.http;
    requires com.example.lib;
    exports com.example.app.api;
    opens com.example.app.model to com.fasterxml.jackson.databind;
}
```
```bash
jlink --module-path $JAVA_HOME/jmods:mods --add-modules com.example.app --output runtime
```

### 9.2 JShell (JEP 222)
- **Definition:** Interactive Java REPL.
- **Description:** Evaluate expressions and snippets with `/vars`, `/methods`, `/exit`.
- **Purpose / Reason:** Experimenting required a full class.
- **Benefits:** Instant feedback.
- **Pros:** Great for learning/APIs.
- **Cons:** Not for large code.
- **Usage:**
```text
$ jshell
jshell> int x = 5
jshell> List.of(1,2,3).stream().mapToInt(i -> i).sum()
$2 ==> 6
```

### 9.3 Collection Factory Methods + Stream/Optional/Interface Improvements
- **Definition:** Immutable collection literals and richer Stream/Optional APIs; private interface methods.
- **Description:** `List.of`, `Set.of`, `Map.of`, `Map.ofEntries`; `takeWhile`, `dropWhile`, `ofNullable`, `iterate(seed, hasNext, next)`; `Optional.ifPresentOrElse`, `or`, `stream`; `private` methods in interfaces.
- **Purpose / Reason:** Verbose collection setup; missing stream operations.
- **Benefits:** Concise, immutable by default.
- **Pros:** Null-hostile, compact.
- **Cons:** `List.of` rejects `null`; unmodifiable.
- **Usage:**
```java
import java.util.*;
import java.util.stream.*;

public class J9 {
    interface Greeter {
        default String hi(String n) { return prefix() + n; }
        private String prefix() { return "Hello, "; }
    }
    public static void main(String[] args) {
        var list = List.of(1, 2, 3, 10, 2);
        System.out.println(list.stream().takeWhile(i -> i < 10).toList());       // [1,2,3] (toList needs 16+)
        System.out.println(list.stream().dropWhile(i -> i < 10).count());
        System.out.println(Stream.iterate(1, i -> i < 50, i -> i * 2).collect(Collectors.toList()));
        Optional.empty().ifPresentOrElse(System.out::println, () -> System.out.println("empty"));
        System.out.println(Optional.<String>empty().or(() -> Optional.of("fallback")).get());
        System.out.println(Map.of("a", 1, "b", 2).size() + " " + new Greeter(){}.hi("Bob"));
    }
}
```

### 9.4 Reactive Streams `Flow` API
- **Definition:** JDK interfaces for reactive streams with back-pressure.
- **Description:** `Flow.Publisher`, `Subscriber`, `Subscription`, `Processor`; `SubmissionPublisher`.
- **Purpose / Reason:** Standardise interoperability for RxJava/Reactor/Akka.
- **Benefits:** Back-pressure standard.
- **Pros:** In the JDK.
- **Cons:** Low-level; frameworks are richer.
- **Usage:**
```java
import java.util.concurrent.*;

public class Fl {
    public static void main(String[] args) throws Exception {
        try (var pub = new SubmissionPublisher<Integer>()) {
            pub.subscribe(new Flow.Subscriber<>() {
                Flow.Subscription s;
                public void onSubscribe(Flow.Subscription s) { this.s = s; s.request(1); }
                public void onNext(Integer i) { System.out.println("got " + i); s.request(1); }
                public void onError(Throwable t) {}
                public void onComplete() { System.out.println("done"); }
            });
            for (int i = 1; i <= 3; i++) pub.submit(i);
        }
        Thread.sleep(300);
    }
}
```

### 9.5 Process API Improvements
- **Definition:** Manage and inspect OS processes.
- **Description:** `ProcessHandle.current()`, `pid()`, `info()`, `children()`, `onExit()`.
- **Purpose / Reason:** Previously limited to `Process` with no PID.
- **Benefits:** Monitoring and cleanup.
- **Pros:** Portable.
- **Cons:** Info availability varies by OS.
- **Usage:**
```java
public class Proc {
    public static void main(String[] args) throws Exception {
        ProcessHandle me = ProcessHandle.current();
        System.out.println(me.pid() + " " + me.info().command().orElse("?"));
        Process p = new ProcessBuilder("sleep", "1").start();
        p.toHandle().onExit().thenAccept(h -> System.out.println("exited " + h.pid())).join();
    }
}
```

**Other Java 9 changes:** G1 as default GC, multi-release JARs, `StackWalker`, HTTP/2 client (incubator), try-with-resources on effectively final variables, `@SafeVarargs` on private methods, unified JVM logging, compact strings.

---

# JAVA 8 (March 2014) — LTS

### 8.1 Lambda Expressions, Functional Interfaces, Method References
- **Definition:** Anonymous functions assignable to interfaces with a single abstract method.
- **Description:** `(a, b) -> a + b`, `@FunctionalInterface`, `java.util.function` (`Function`, `Predicate`, `Supplier`, `Consumer`, `BiFunction`, `UnaryOperator`), method refs (`String::length`, `System.out::println`, `ArrayList::new`).
- **Purpose / Reason:** Anonymous inner classes were verbose; needed for functional style and streams.
- **Benefits:** Concise code, behaviour as parameters.
- **Pros:** Foundation for modern Java.
- **Cons:** Stack traces harder; captured variables must be effectively final.
- **Usage:**
```java
import java.util.*;
import java.util.function.*;

public class Lam {
    @FunctionalInterface interface Calc { int apply(int a, int b); }
    public static void main(String[] args) {
        Calc add = (a, b) -> a + b;
        Function<String, Integer> len = String::length;
        Predicate<String> empty = String::isEmpty;
        Supplier<List<String>> sup = ArrayList::new;
        Consumer<String> out = System.out::println;
        BiFunction<Integer, Integer, Integer> mul = (a, b) -> a * b;
        UnaryOperator<String> up = String::toUpperCase;
        out.accept(add.apply(2, 3) + " " + len.apply("java") + " " + empty.test("") + " " + sup.get() + " " + mul.apply(3, 4) + " " + up.apply("x"));
        List<String> names = new ArrayList<>(List.of("bob", "al", "christine"));
        names.sort(Comparator.comparing(String::length).reversed());
        names.forEach(System.out::println);
    }
}
```

### 8.2 Stream API
- **Definition:** Declarative, lazy pipelines over collections and other sources.
- **Description:** `stream()`, `filter`, `map`, `flatMap`, `sorted`, `distinct`, `limit`, `reduce`, `collect`, `Collectors.groupingBy/partitioningBy/joining/summarizingInt`, `parallelStream`, `IntStream`.
- **Purpose / Reason:** Loops mix "what" and "how"; hard to parallelise.
- **Benefits:** Readable data processing; easy parallelism.
- **Pros:** Composable, lazy.
- **Cons:** Overhead on small data; debugging; streams are single-use.
- **Usage:**
```java
import java.util.*;
import java.util.stream.*;

public class Str {
    record Emp(String name, String dept, int sal) {}   // record needs Java 16+; use a class on 8
    public static void main(String[] args) {
        List<Emp> es = List.of(new Emp("a", "IT", 100), new Emp("b", "IT", 200), new Emp("c", "HR", 150));
        System.out.println(es.stream().collect(Collectors.groupingBy(Emp::dept, Collectors.summingInt(Emp::sal))));
        System.out.println(es.stream().filter(e -> e.sal() > 120).map(Emp::name).collect(Collectors.joining(",")));
        System.out.println(es.stream().mapToInt(Emp::sal).summaryStatistics());
        System.out.println(es.stream().collect(Collectors.partitioningBy(e -> e.sal() >= 150)));
        System.out.println(IntStream.rangeClosed(1, 5).reduce(1, (a, b) -> a * b));    // 120
        System.out.println(Stream.of(List.of(1, 2), List.of(3)).flatMap(List::stream).collect(Collectors.toList()));
    }
}
```

### 8.3 Optional
- **Definition:** Container that may or may not hold a non-null value.
- **Description:** `of`, `ofNullable`, `empty`, `map`, `flatMap`, `filter`, `orElse`, `orElseGet`, `orElseThrow`, `ifPresent`.
- **Purpose / Reason:** `null` returns cause NPEs.
- **Benefits:** Explicit absence in the API.
- **Pros:** Chainable.
- **Cons:** Not for fields/params; small allocation cost.
- **Usage:**
```java
import java.util.Optional;

public class Opt {
    static Optional<String> find(int id) { return id == 1 ? Optional.of("Alice") : Optional.empty(); }
    public static void main(String[] args) {
        System.out.println(find(1).map(String::toUpperCase).orElse("none"));
        System.out.println(find(2).orElseGet(() -> "default"));
        find(1).filter(n -> n.startsWith("A")).ifPresent(System.out::println);
        System.out.println(Optional.ofNullable((String) null).isPresent());
    }
}
```

### 8.4 Default and Static Interface Methods
- **Definition:** Interfaces can contain method bodies.
- **Description:** `default` methods are inherited; `static` helper methods live on the interface.
- **Purpose / Reason:** Evolve interfaces (e.g. add `List.sort`, `Collection.stream`) without breaking implementers.
- **Benefits:** Backward compatibility; mixin-like reuse.
- **Pros:** Enables the Streams retrofit.
- **Cons:** Diamond conflicts must be resolved with `A.super.m()`.
- **Usage:**
```java
public class Def {
    interface Vehicle {
        default String start() { return "Vehicle started"; }
        static Vehicle of() { return new Vehicle() {}; }
    }
    interface Electric { default String start() { return "Electric started"; } }
    static class Tesla implements Vehicle, Electric {
        public String start() { return Vehicle.super.start() + " + " + Electric.super.start(); }
    }
    public static void main(String[] args) {
        System.out.println(Vehicle.of().start() + " | " + new Tesla().start());
    }
}
```

### 8.5 Date/Time API (`java.time`)
- **Definition:** Immutable, thread-safe date/time classes (JSR-310).
- **Description:** `LocalDate`, `LocalTime`, `LocalDateTime`, `ZonedDateTime`, `Instant`, `Duration`, `Period`, `DateTimeFormatter`, `ChronoUnit`.
- **Purpose / Reason:** `Date`/`Calendar` were mutable, confusing and not thread-safe.
- **Benefits:** Clear, fluent, time-zone aware.
- **Pros:** Immutable; ISO-8601 by default.
- **Cons:** Legacy API interop needed.
- **Usage:**
```java
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Dt {
    public static void main(String[] args) {
        LocalDate d = LocalDate.of(2026, 11, 5).plusMonths(1);
        ZonedDateTime z = ZonedDateTime.of(LocalDateTime.of(2026, 11, 5, 9, 30), ZoneId.of("Asia/Kolkata"));
        System.out.println(d + " " + d.getDayOfWeek() + " " + z.withZoneSameInstant(ZoneId.of("UTC")));
        System.out.println(ChronoUnit.DAYS.between(LocalDate.of(2026, 1, 1), d) + " " + Period.between(LocalDate.of(2000, 1, 1), d));
        System.out.println(Duration.ofMinutes(135) + " " + d.format(DateTimeFormatter.ofPattern("dd MMM yyyy")));
        System.out.println(Instant.now().truncatedTo(ChronoUnit.SECONDS));
    }
}
```

### 8.6 CompletableFuture
- **Definition:** Composable asynchronous computation.
- **Description:** `supplyAsync`, `thenApply`, `thenCompose`, `thenCombine`, `allOf`, `anyOf`, `exceptionally`, `handle`, `join`.
- **Purpose / Reason:** `Future.get()` blocks and can't be chained.
- **Benefits:** Non-blocking pipelines; error handling.
- **Pros:** Rich composition.
- **Cons:** Callback chains get complex; use virtual threads/structured concurrency for new code.
- **Usage:**
```java
import java.util.concurrent.CompletableFuture;

public class Cf8 {
    public static void main(String[] args) {
        CompletableFuture<String> user = CompletableFuture.supplyAsync(() -> "alice");
        CompletableFuture<Integer> orders = CompletableFuture.supplyAsync(() -> 3);
        System.out.println(user.thenCombine(orders, (u, o) -> u + ":" + o).join());
        System.out.println(CompletableFuture.supplyAsync(() -> 1 / 0).exceptionally(ex -> -1).join());
        CompletableFuture.allOf(user, orders).thenRun(() -> System.out.println("all done")).join();
    }
}
```

**Other Java 8 changes:** `Base64`, `StringJoiner`, `String.join`, repeatable and type annotations, `Arrays.parallelSort`, `Map.computeIfAbsent/merge/getOrDefault`, `LongAdder`, `StampedLock`, Metaspace replaces PermGen, Nashorn JavaScript engine (removed in 15), `-XX:+UseStringDeduplication`.

---

## Quick Recommendations

| Goal | Use |
|---|---|
| Stable production baseline | Java 25 LTS (or 21 LTS) |
| I/O-heavy scalability | Virtual threads (21) + Scoped Values (25); Structured Concurrency when you accept preview |
| Cleaner domain models | Records (16) + sealed (17) + pattern switch/record patterns (21) |
| Faster startup | AOT cache (24–26) |
| Memory/GC wins | Compact headers (25 opt-in, 27 default), Generational ZGC (23) |
| Quantum-safe security | ML-KEM/ML-DSA (24), hybrid TLS (27) |

*Sources for Java 27 details: Oracle/Inside.java "The Arrival of Java 27" (15 Sep 2026). Java 26 and earlier details are from the JEP index; confirm exact preview API shapes in the Javadoc for your JDK build.*
