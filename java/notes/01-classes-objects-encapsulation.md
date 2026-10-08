# Lesson 1 — Classes, Objects, Constructors & Encapsulation

> **Track:** Java · **Level:** Beginner → Intermediate · **Time:** 2 days · **JDK:** 17+ (records and pattern matching are used)

This lesson rebuilds the foundations of Object-Oriented Programming (OOP): how to model things with classes, create objects safely with constructors, and protect their state with encapsulation. Lesson 2 continues with inheritance, polymorphism, interfaces and abstract classes.

**How to use this lesson**

1. Read the theory (30 min). Don't memorize it; skim and come back when stuck.
2. Type the guided example yourself. Don't copy and paste.
3. Solve each exercise **before** opening its solution.
4. Build the exam project alone, then compare it with the checklist.

**Where the code lives**

| What | Package | Folder |
|---|---|---|
| Guided examples | `oop.examples` | [`examples`](../java-course/src/oop/examples) |
| Exercises | `oop.exercises` | [`exercises`](../java-course/src/oop/exercises) |
| Exam project | `oop.project` | [`project`](../java-course/src/oop/project) |

---

## Table of contents

1. [Classes and objects](#1-classes-and-objects)
2. [Fields and methods](#2-fields-and-methods)
3. [Constructors](#3-constructors)
4. [Encapsulation](#4-encapsulation)
5. [`static` and `final`](#5-static-and-final)
6. [The `Object` methods: `toString`, `equals`, `hashCode`](#6-the-object-methods-tostring-equals-hashcode)
7. [Records](#7-records)
8. [Validation and exceptions](#8-validation-and-exceptions)
9. [Clean code basics](#9-clean-code-basics)
10. [Common mistakes](#10-common-mistakes)
11. [Guided example: `BankAccount`](#11-guided-example-bankaccount)
12. [Exercises](#12-exercises)
13. [Exam project: Mini Bank](#13-exam-project-mini-bank)
14. [Interview questions](#14-interview-questions)
15. [Cheat sheet](#15-cheat-sheet)
16. [References](#16-references)

---

## 1. Classes and objects

A **class** is a blueprint: it defines the *state* (fields) and the *behavior* (methods) that its objects will have. An **object** is an *instance* of a class, created with `new`.

```java
public class Dog {
    private String name;          // state

    public Dog(String name) {     // constructor
        this.name = name;
    }

    public void bark() {          // behavior
        System.out.println(name + " says woof");
    }
}

Dog a = new Dog("Rex");           // object
Dog b = new Dog("Luna");          // another, independent object
```

### Variables hold references, not objects

```
 Stack                    Heap
┌────────┐              ┌──────────────┐
│ a ─────┼─────────────▶│ Dog{name=Rex}│
├────────┤              └──────────────┘
│ b ─────┼──────┐       ┌──────────────┐
└────────┘      └──────▶│ Dog{name=Luna}│
                        └──────────────┘
```

- `Dog c = a;` copies the **reference**, not the object. `a` and `c` now point to the **same** object.
- `null` means "this variable points to no object". Calling a method on `null` throws a `NullPointerException`.
- `a == b` compares **references** (same object?). `a.equals(b)` compares **content**, but only if the class overrides `equals` (see section 6).

### Java is always pass-by-value

When you pass an argument to a method, Java copies the *value* of the variable. For objects, that value is the **reference**. So a method can modify the object it receives, but it cannot make the caller's variable point somewhere else.

---

## 2. Fields and methods

- **Fields** (instance variables) get default values: `0`, `0.0`, `false`, `null`.
- **Local variables** (inside methods) get **no** default value. The compiler forces you to initialize them.
- **Methods** receive parameters, may return a value, and can read or modify the object's fields.

```java
public class Counter {
    private int value;                 // field → starts at 0

    public void increment() {          // method
        value++;
    }

    public int getValue() {
        return value;
    }
}
```

---

## 3. Constructors

A constructor initializes a new object. It has the **same name as the class** and **no return type**.

| Rule | Detail |
|---|---|
| Default constructor | If you write **no** constructor, Java adds an empty one. |
| It disappears | As soon as you write **any** constructor, the empty one is **not** generated anymore. |
| Overloading | You can have several constructors with different parameters. |
| Chaining | `this(...)` calls another constructor of the same class. It must be the first statement. |
| Validation | Check arguments **before** assigning; throw an exception if they are invalid. |

```java
public class Rectangle {
    private final double width;
    private final double height;

    public Rectangle(double side) {                // square
        this(side, side);                          // chaining
    }

    public Rectangle(double width, double height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Sides must be greater than 0");
        }
        this.width = width;
        this.height = height;
    }
}
```

> ⚠️ **Classic trap:** `public void Rectangle(double w, double h)` is **not** a constructor. The `void` turns it into an ordinary method that happens to share the class name. The code compiles, but `new Rectangle(2, 3)` fails because no matching constructor exists.

### The `this` keyword

- `this.field` refers to the field of the current object. It is needed when a parameter has the same name as a field.
- `this(...)` calls another constructor.
- Without `this`, `title = title;` assigns the parameter to **itself** and the field stays `null`.

---

## 4. Encapsulation

**Encapsulation** means hiding an object's internal state and allowing changes only through methods that enforce the rules. The rules that must always be true for a valid object are called **invariants** (for example: "a balance is never negative").

### Access modifiers

| Modifier | Visible from |
|---|---|
| `private` | The same class only |
| *(none)* — package-private | The same package |
| `protected` | The same package and subclasses |
| `public` | Everywhere |

### Rules of thumb

1. Make **fields `private`** by default.
2. Expose **behavior** (`deposit`, `withdraw`) instead of raw data (`setBalance`). Ask yourself: *does this setter protect an invariant, or does it just open a hole?*
3. Add a setter **only if** the field really needs to change, and validate inside it.
4. Prefer **immutable** objects (all fields `final`, no setters) when the data should not change after creation.
5. Never return an internal mutable object (like an array) directly. Return a copy:

```java
public double[] getGrades() {
    return grades.clone();   // defensive copy: callers can't modify the internal array
}
```

### Why bother?

If `balance` were `public`, any code in the program could write `account.balance = -1000000;`. With encapsulation, the only way to change it is through methods that check the rules, so the bug can only live in **one** place.

---

## 5. `static` and `final`

| Keyword | On a field | On a method | On a class |
|---|---|---|---|
| `static` | One copy shared by **all** objects | Called on the class, no object needed; cannot use `this` | — |
| `final` | Assigned **once** (declaration or constructor) | Cannot be overridden (Lesson 2) | Cannot be extended |

```java
public class MathUtils {
    public static final double TAX_RATE = 0.18;   // constant → UPPER_SNAKE_CASE

    public static double addTax(double price) {
        return price * (1 + TAX_RATE);
    }
}

double total = MathUtils.addTax(100);              // no object needed
```

Important details:

- `final` on a **reference** means the variable cannot point to another object. The object itself can still change. `final` ≠ immutable.
- A `static` counter is global state. It is handy for generating IDs, but it is not thread-safe (use `AtomicInteger` in concurrent code) and makes testing harder.
- A static counter is a good **ID generator**, but a bad **array index** (see the project).

---

## 6. The `Object` methods: `toString`, `equals`, `hashCode`

Every class extends `java.lang.Object`, which provides three methods you will often override.

### `toString`

Returns a readable description. It is called automatically by `System.out.println(obj)` and string concatenation.

### `equals` and `hashCode`

By default, `equals` behaves like `==` (same reference). Override it to compare **content**.

The `equals` contract:

- **Reflexive:** `x.equals(x)` is `true`.
- **Symmetric:** `x.equals(y)` ⇔ `y.equals(x)`.
- **Transitive:** if `x.equals(y)` and `y.equals(z)`, then `x.equals(z)`.
- **Consistent:** same result on repeated calls if nothing changed.
- `x.equals(null)` is `false`.

The `hashCode` contract: **if two objects are equal, they must have the same hash code.** Override both together, or `HashMap` and `HashSet` will misbehave (they use `hashCode` to find the bucket, then `equals` to confirm).

```java
@Override
public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Point other)) return false;      // pattern matching (Java 16+)
    return Double.compare(x, other.x) == 0
        && Double.compare(y, other.y) == 0;
}

@Override
public int hashCode() {
    return Objects.hash(x, y);
}
```

Use `Double.compare` for `double` fields because `==` has edge cases with `NaN` and `-0.0`. If the class is not `final`, think carefully about `instanceof` vs `getClass()`; we cover that when we study inheritance.

---

## 7. Records

A **record** (Java 16+) is a compact way to declare an immutable data carrier.

```java
public record Point(double x, double y) { }
```

Java generates for you: private final fields, the canonical constructor, accessors (`p.x()`, not `getX()`), `equals`, `hashCode` and `toString`.

You can add validation with a **compact constructor** and extra methods:

```java
public record Point(double x, double y) {
    public Point {                                  // compact constructor
        if (Double.isNaN(x) || Double.isNaN(y)) {
            throw new IllegalArgumentException("Coordinates must be numbers");
        }
    }

    public double distanceTo(Point other) {
        return Math.hypot(x - other.x, y - other.y);
    }
}
```

| Use a record when | Use a regular class when |
|---|---|
| The object is just immutable data | The object has mutable state or complex behavior |
| You want `equals`/`hashCode` by content | You need inheritance (records are implicitly `final`) |

---

## 8. Validation and exceptions

Two kinds of failure, two tools:

| Situation | Tool | Example |
|---|---|---|
| The caller passed **invalid input** (a bug in the caller) | Throw `IllegalArgumentException` | `deposit(-50)` |
| The object is in the **wrong state** for this operation | Throw `IllegalStateException` | opening an account in a full bank |
| An **expected business outcome** that is not a bug | Return `boolean` / `Optional` | `withdraw(1000)` with insufficient funds |

Guidelines:

- **Fail fast:** validate at the start of the constructor or method, before changing anything.
- A failed constructor means the object **never exists** in an invalid state.
- Domain classes should **not** print error messages (`System.out.println`). They return a result or throw; the caller decides how to show it.
- Increment shared counters **last**, after all validations pass. Otherwise a failed constructor still consumes an ID.

---

## 9. Clean code basics

| Element | Convention | Example |
|---|---|---|
| Classes, records | `PascalCase`, nouns | `BankAccount` |
| Methods, variables | `camelCase`, methods start with a verb | `calculateAverage()` |
| Boolean methods | Start with `is`, `has`, `can` | `isApproved()` |
| Constants | `UPPER_SNAKE_CASE` | `MAX_ACCOUNTS` |
| Packages | lowercase | `oop.exercises` |

Also:

- **A method does one thing.** `isSquare()` answers a question (returns `boolean`); printing the answer is the caller's job.
- Don't repeat yourself: if two methods share logic, extract a private method.
- Prefer clear names over comments. Use Javadoc (`/** ... */`) for the public API.
- Write identifiers in English, spelled correctly (`Account`, not `Acount`).

---

## 10. Common mistakes

| Mistake | Why it hurts | Fix |
|---|---|---|
| `public void ClassName(...)` | It is a method, not a constructor | Remove `void` |
| `name = name;` | Assigns the parameter to itself | `this.name = name;` |
| `public` fields | Anyone can break invariants | `private` + methods |
| Setter with no validation | Same hole as a public field | Validate or remove the setter |
| Checking `arr.length` before `arr != null` | `NullPointerException` | `arr != null && arr.length == 4` |
| Looping over the whole array when only some slots are filled | `NullPointerException` on empty slots | Keep a `count` and loop to `count` |
| Using a global static counter as an array index | Breaks with a second container | Keep the container's own `count` |
| Overriding `equals` without `hashCode` | Breaks `HashMap` / `HashSet` | Override both |
| Comparing objects with `==` | Compares references | Use `equals` |
| ID stored as `int` when it may start with `0` | Leading zeros are lost | Store it as `String` |
| Printing errors inside domain classes | Mixes logic and presentation | Throw or return a result |

---

## 11. Guided example: `BankAccount`

Create it in `oop.examples`.

```java
package oop.examples;

public class BankAccount {
    private static int totalAccounts = 0;     // shared by all accounts

    private final String owner;               // never changes after creation
    private double balance;

    public BankAccount(String owner) {
        this(owner, 0);                       // constructor chaining
    }

    public BankAccount(String owner, double initialBalance) {
        if (owner == null || owner.isBlank()) {
            throw new IllegalArgumentException("The owner is required");
        }
        if (initialBalance < 0) {
            throw new IllegalArgumentException("The initial balance cannot be negative");
        }
        this.owner = owner;
        this.balance = initialBalance;
        totalAccounts++;                      // incremented LAST, after validation
    }

    public String getOwner() { return owner; }
    public double getBalance() { return balance; }
    public static int getTotalAccounts() { return totalAccounts; }

    public void deposit(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Invalid amount");
        balance += amount;
    }

    public boolean withdraw(double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Invalid amount");
        if (amount > balance) return false;   // expected outcome, not a bug
        balance -= amount;
        return true;
    }

    @Override
    public String toString() {
        return String.format("BankAccount[%s, balance=%.2f]", owner, balance);
    }
}
```

```java
package oop.examples;

public class Main {
    public static void main(String[] args) {
        BankAccount a = new BankAccount("Ana", 500);
        BankAccount b = new BankAccount("Luis");

        a.deposit(200);
        boolean ok = a.withdraw(1000);

        System.out.println(a);                                        // BankAccount[Ana, balance=700.00]
        System.out.println("Withdrawal succeeded: " + ok);            // false
        System.out.println(b);
        System.out.println("Total: " + BankAccount.getTotalAccounts()); // 2

        // a.balance = 999999;   // compile error: balance is private
    }
}
```

**What to notice**

1. `balance` is private: the only way to change it is `deposit` / `withdraw`, which validate.
2. `owner` is `final` and has no setter, because an account's owner never changes.
3. Invalid input throws an exception; insufficient funds returns `false` (an expected outcome).
4. The counter is incremented after validation, so a failed constructor consumes nothing.

---

## 12. Exercises

Create each exercise in `oop.exercises.exerciseN` with its own `Main` to test it. Try it **before** opening the solution.

### Exercise 1 — `Rectangle`

**Statement.** Create an immutable `Rectangle` with `width` and `height`. Both must be greater than 0; otherwise throw `IllegalArgumentException`.

Methods: `area()`, `perimeter()`, `isSquare()` (returns `boolean`), and `toString()`.

<details>
<summary>Solution walkthrough</summary>

Code: [`Rectangle.java`](../java-course/src/oop/exercises/exercise1/Rectangle.java) · [`Main.java`](../java-course/src/oop/exercises/exercise1/Main.java)

- Validate in the constructor **before** assigning, so an invalid rectangle can never exist.
- Fields are `private final`: the object is immutable, so no setters are needed.
- `isSquare()` returns a `boolean` instead of printing. The caller decides what to do with the answer.
- Comparing `double` values with `==` is acceptable here because we compare two values we stored, not the result of a calculation.

</details>

### Exercise 2 — `Student`

**Statement.** Create a `Student` with a `code` (final), a `name`, and an array of **4 grades**.

- Constructor 1: `code` and `name` only. Constructor 2: also receives the 4 grades and **must reuse** constructor 1 with `this(...)`.
- `registerGrade(int index, double grade)`: the grade must be between 0 and 20 **inclusive**; the index must be valid. Otherwise throw `IllegalArgumentException`.
- `average()`, `isApproved()` (average ≥ 10.5), and `bestGrade()`.

<details>
<summary>Hint</summary>

Check `grades != null` **before** `grades.length`. Both ends of the range (0 and 20) are valid grades.

</details>

<details>
<summary>Solution walkthrough</summary>

Code: [`Student.java`](../java-course/src/oop/exercises/exercise2/Student.java) · [`Main.java`](../java-course/src/oop/exercises/exercise2/Main.java)

- Constructor 2 starts with `this(code, name)`, so the initialization logic exists in one place.
- The range check is `grade < 0 || grade > 20` (inclusive limits). The index check is `index < 0 || index >= grades.length`.
- `grades != null && grades.length == 4` has to be in that order: if `grades` is `null`, the second condition would throw a `NullPointerException`.
- Invalid arrays are rejected with an exception instead of being silently ignored.
- Design note: grades that were never registered count as `0` in the average. A real system would track "not graded yet" separately.

</details>

### Exercise 3 — `Product` with stock

**Statement.** Create a `Product` with `name`, `price` and `stock`.

- Reject a blank name, a negative price and a negative stock.
- `sell(int quantity)` returns `boolean`: `false` if there is not enough stock. Reject quantities ≤ 0.
- `restock(int quantity)` adds stock. Reject quantities ≤ 0.
- `inventoryValue()` returns `price * stock`.
- A `static` counter keeps track of how many products were created.

<details>
<summary>Solution walkthrough</summary>

Code: [`Product.java`](../java-course/src/oop/exercises/exercise3/Product.java) · [`Main.java`](../java-course/src/oop/exercises/exercise3/Main.java)

- Without validating `quantity`, `sell(-5)` would **increase** the stock. Encapsulation only works if every method that changes state protects the invariants.
- `sell` returns `false` for "not enough stock" (an expected outcome) and throws for a non-positive quantity (invalid input).
- The static counter is exposed through `getTotalProductsCreated()`. A getter named like the field works, but the `get` prefix keeps it consistent.
- Real systems use `BigDecimal` for money because `double` cannot represent values like `0.10` exactly.

</details>

### Exercise 4 — `Point`, `equals` and `hashCode`

**Statement.** Create an immutable class `Point(x, y)` with `distanceTo(Point other)`, `equals`, `hashCode` and `toString`. In `main`, show that `new Point(1, 2).equals(new Point(1, 2))` is `true` while `==` is `false`. Then write the same thing as a `record` and compare both versions.

<details>
<summary>Solution walkthrough</summary>

Code: [`Point.java`](../java-course/src/oop/exercises/exercise4/Point.java) · [`PointRecord.java`](../java-course/src/oop/exercises/exercise4/PointRecord.java) · [`Main.java`](../java-course/src/oop/exercises/exercise4/Main.java)

- The class is `final`, its fields are `final`, and there are no setters: this is the classic recipe for an immutable class.
- `equals` follows the contract (reflexive, `null`-safe, type check, field comparison). `hashCode` uses the same fields as `equals`.
- `Objects.hash(x, y)` is the short way to write `hashCode`.
- The record version needs **one line** for what the class does in about 30. Records are the right tool for pure data.
- `distanceTo` rejects `null` early with a clear message.

</details>

### Exercise 5 — Find the bugs

**Statement.** This class has **4 problems** (compile errors or design flaws). Find and fix them all, then write a `main` that proves the fixed version works.

```java
public class Book {
    public String title;
    private int pages;

    public void Book(String title, int pages) {
        title = title;
        this.pages = pages;
    }

    public int getPages() { return pages; }
    public void setPages(int p) { pages = p; }
}
```

<details>
<summary>Solution walkthrough</summary>

Code: [`Book.java`](../java-course/src/oop/exercises/exercise5/Book.java)

| # | Problem | Fix |
|---|---|---|
| 1 | `public String title` breaks encapsulation | Make it `private` |
| 2 | `void` turns the constructor into a method, so `new Book("x", 10)` does not compile | Remove `void` |
| 3 | `title = title;` assigns the parameter to itself; the field stays `null` | `this.title = title;` |
| 4 | `setPages` accepts negative values | Validate, or remove the setter |

```java
public class Book {
    private final String title;
    private int pages;

    public Book(String title, int pages) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("The title is required");
        }
        if (pages <= 0) {
            throw new IllegalArgumentException("The page count must be positive");
        }
        this.title = title;
        this.pages = pages;
    }

    public String getTitle() { return title; }
    public int getPages() { return pages; }

    public void setPages(int pages) {
        if (pages <= 0) {
            throw new IllegalArgumentException("The page count must be positive");
        }
        this.pages = pages;
    }
}
```

Lesson: always **compile and run** the fixed version. Bug 2 is invisible until you try to create an object.

</details>

---

## 13. Exam project: Mini Bank

Build it **alone**, without looking at the guided example. Package `oop.project`.

**Classes**

| Class | Responsibilities |
|---|---|
| `Client` | `nationalId` (final, `String`, exactly 8 digits), `name` (not blank), `email` (valid format). |
| `Account` | Auto-generated `number` (static counter), `client`, `balance`. Methods: `deposit`, `withdraw`, `toString`. |
| `Bank` | Stores up to **50 accounts in an array** (no `ArrayList` yet). |

`Bank` methods:

- `openAccount(Client, double initialBalance)` returns the new `Account`.
- `findByNumber(int)` returns the account or `null`.
- `transfer(int origin, int destination, double amount)` returns `boolean`.
- `totalBalance()` sums every balance.
- `printReport()` prints every account.

**Rules**

1. All fields are `private`.
2. Constructors validate with exceptions.
3. A transfer is **consistent**: if it fails, **no** balance changes.
4. The bank handles being full, unknown accounts and invalid amounts without crashing.
5. `Main` demonstrates: 3 accounts, a deposit, one successful transfer, one failed transfer (print its result), a lookup of a non-existent account, and the report.

**Self-check checklist**

- [ ] Does `findByNumber(999)` return `null` instead of crashing?
- [ ] Does `totalBalance()` work with only 3 accounts in a 50-slot array?
- [ ] What happens when you open the 51st account?
- [ ] Does the bank use its **own** counter to place accounts in the array?
- [ ] Can a national ID like `01234567` be accepted?
- [ ] Do domain classes avoid `System.out.println` for errors?
- [ ] Does a failed transfer leave both balances untouched?

<details>
<summary>Solution walkthrough</summary>

Code: [`Client.java`](../java-course/src/oop/project/Client.java) · [`Account.java`](../java-course/src/oop/project/Account.java) · [`Bank.java`](../java-course/src/oop/project/Bank.java) · [`Main.java`](../java-course/src/oop/project/Main.java)

**Key design decisions**

1. **The bank has its own `accountCount`.** The array has 50 slots but usually fewer accounts. Loops must go from `0` to `accountCount`, never to `accounts.length`; otherwise they hit `null` slots and throw `NullPointerException`.

```java
public Account openAccount(Client client, double initialBalance) {
    if (accountCount == accounts.length) {
        throw new IllegalStateException("The bank is full");
    }
    Account account = new Account(client, initialBalance);
    accounts[accountCount++] = account;
    return account;
}

public Account findByNumber(int number) {
    for (int i = 0; i < accountCount; i++) {
        if (accounts[i].getNumber() == number) return accounts[i];
    }
    return null;
}
```

2. **The static counter in `Account` generates IDs, nothing else.** Using `Account.getTotalAccounts() - 1` as an array index couples the bank to global state: a second `Bank`, or an account created elsewhere, breaks it.
3. **The capacity check happens before creating the account**, so a full bank does not consume an account number.
4. **Consistent transfer:** validate everything first, then withdraw, then deposit. Because the amount was already validated as positive, the deposit cannot fail after the withdrawal succeeds.

```java
public boolean transfer(int origin, int destination, double amount) {
    Account from = findByNumber(origin);
    Account to = findByNumber(destination);
    if (from == null || to == null || from == to || amount <= 0) return false;
    if (!from.withdraw(amount)) return false;     // insufficient funds
    to.deposit(amount);
    return true;
}
```

5. **`nationalId` is a `String`.** An `int` loses leading zeros (`01234567` becomes `1234567`), so a valid 8-digit ID would be rejected. Validate with `id.matches("\\d{8}")`.
6. **Email validation:** a simple, honest check is enough here (`contains("@")` with text on both sides). Requiring `.com` rejects valid addresses such as `.pe` or `.edu`.
7. **No printing inside `Account`:** `withdraw` returns `false` for insufficient funds and throws for a non-positive amount. `Main` decides what to show.

</details>

---

## 14. Interview questions

<details>
<summary>What is the difference between a class and an object?</summary>

A class is the blueprint (fields + methods); an object is a concrete instance created with `new`, with its own state.

</details>

<details>
<summary>What happens if you don't write any constructor?</summary>

The compiler adds a public no-argument constructor. As soon as you declare any constructor, that default is no longer generated.

</details>

<details>
<summary>What is the difference between <code>==</code> and <code>equals</code>?</summary>

`==` compares references (is it the same object?). `equals` compares logical content, but only if the class overrides it; otherwise it behaves like `==`.

</details>

<details>
<summary>Why must you override <code>hashCode</code> when you override <code>equals</code>?</summary>

Hash-based collections (`HashMap`, `HashSet`) use `hashCode` to find the bucket and `equals` to confirm. If equal objects have different hash codes, lookups fail even though the object is in the collection.

</details>

<details>
<summary>Is Java pass-by-value or pass-by-reference?</summary>

Always pass-by-value. For objects, the value copied is the reference, so a method can change the object's state but cannot repoint the caller's variable.

</details>

<details>
<summary>Does <code>final</code> make an object immutable?</summary>

No. `final` on a reference variable prevents reassigning the variable. The object can still change unless the class itself is immutable (final fields, no setters, no leaked mutable internals).

</details>

<details>
<summary>Why make fields private?</summary>

To protect invariants (all writes go through validating methods) and to be free to change the internal representation without breaking callers.

</details>

<details>
<summary>What does <code>static</code> mean?</summary>

The member belongs to the class, not to any instance. A static field has a single copy shared by all objects; a static method cannot use `this` or instance fields directly.

</details>

<details>
<summary>When would you use a record instead of a class?</summary>

When the type is only immutable data and you want `equals`, `hashCode`, `toString` and accessors generated. Not when you need mutable state or inheritance.

</details>

<details>
<summary>When do you throw an exception and when do you return <code>false</code>?</summary>

Throw for invalid input or illegal state (a caller bug). Return a result for expected business outcomes, such as a withdrawal with insufficient funds.

</details>

---

## 15. Cheat sheet

```java
public class Example {
    private static int created = 0;                 // shared counter
    public static final int MAX = 10;               // constant

    private final String id;                        // set once
    private int value;

    public Example(String id) { this(id, 0); }      // chaining

    public Example(String id, int value) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("id required");
        if (value < 0) throw new IllegalArgumentException("value must be >= 0");
        this.id = id;
        this.value = value;
        created++;                                  // last
    }

    public String getId() { return id; }
    public int getValue() { return value; }

    @Override public String toString() { return "Example[" + id + "," + value + "]"; }
    @Override public boolean equals(Object o) {
        return o instanceof Example e && id.equals(e.id) && value == e.value;
    }
    @Override public int hashCode() { return java.util.Objects.hash(id, value); }
}
```

---

## 16. References

- Oracle Java Tutorial — [Classes and Objects](https://docs.oracle.com/javase/tutorial/java/javaOO/index.html)
- dev.java — [Learn Java](https://dev.java/learn/)
- [`java.lang.Object` API](https://docs.oracle.com/en/java/javase/24/docs/api/java.base/java/lang/Object.html)
- Oracle — [Record classes](https://docs.oracle.com/en/java/javase/17/language/records.html)
- *Effective Java* (Joshua Bloch): items on `equals`, `hashCode`, `toString`, minimizing mutability

---

**Next lesson →** Lesson 2: Inheritance, polymorphism, interfaces and abstract classes.
