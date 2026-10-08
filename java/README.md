# Java Track

A hands-on Java course for people who already know the basics and want to rebuild solid foundations for software internships and interviews.

Every lesson follows the same cycle:

1. **Theory** with links to the official documentation
2. **Guided examples**
3. **Exercises** (statement first, then a solution with a walkthrough)
4. **Exam project** to build on your own

## Lessons

| # | Lesson | Notes | Code |
|---|---|---|---|
| 1 | Classes, objects, constructors, encapsulation | [notes](notes/01-classes-objects-encapsulation.md) | [`oop`](java-course/src/oop) |
| 2 | Inheritance, polymorphism, interfaces, abstract classes | *coming soon* | |
| 3 | Collections | *coming soon* | |
| 4 | Exceptions | *coming soon* | |
| 5 | Generics, lambdas, streams | *coming soon* | |

## Project layout

```
java/
├── README.md
├── notes/                  ← theory, exercise statements, walkthroughs
└── java-course/            ← NetBeans (Ant) project
    └── src/
        └── oop/
            ├── examples/
            ├── exercises/
            └── project/
```

## How to run the code

**NetBeans:** open the `java-course` folder as a project, then run any `Main.java` with `Shift + F6`.

**Terminal** (JDK 17 or newer):

```bash
cd java-course/src
javac oop/examples/*.java
java oop.examples.Main
```

## Tips for learners

- Try each exercise **before** opening its solution.
- Compile and run your code, even for "obvious" fixes.
- If a solution differs from yours, ask *why*, not just *which is right*.
