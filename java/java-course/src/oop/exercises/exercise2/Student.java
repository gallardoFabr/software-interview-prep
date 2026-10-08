package oop.exercises.exercise2;

/**
 * A student with a code, a name and exactly four grades between 0 and 20.
 * The class is {final} because constructor 2 calls {registerGrade}, and a
 * subclass could override it before the object is fully built.
 */
public final class Student {

    private static final int GRADE_COUNT = 4;
    private static final double MIN_GRADE = 0;
    private static final double MAX_GRADE = 20;
    private static final double PASSING_AVERAGE = 10.5;

    private final String code;
    private final String name;
    private final double[] grades;     // unregistered grades count as 0

    /** @throws IllegalArgumentException if the code or the name is blank */
    public Student(String code, String name) {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("The code is required");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("The name is required");
        }
        this.code = code;
        this.name = name;
        this.grades = new double[GRADE_COUNT];
    }

    /**
     * Creates a student with all four grades.
     *
    */
    public Student(String code, String name, double[] grades) {
        this(code, name);                                       // reuse constructor 1
        if (grades == null || grades.length != GRADE_COUNT) {   // null check FIRST
            throw new IllegalArgumentException("Exactly " + GRADE_COUNT + " grades are required");
        }
        for (int i = 0; i < grades.length; i++) {
            registerGrade(i, grades[i]);
        }
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    /**
     * Registers a grade.
     *
     * @throws IllegalArgumentException if the index is invalid or the grade is outside [0, 20]
     */
    public void registerGrade(int index, double grade) {
        if (index < 0 || index >= grades.length) {
            throw new IllegalArgumentException("The index must be between 0 and " + (grades.length - 1));
        }
        if (grade < MIN_GRADE || grade > MAX_GRADE) {           // 0 and 20 are valid
            throw new IllegalArgumentException("The grade must be between 0 and 20");
        }
        grades[index] = grade;
    }

    public double average() {
        double sum = 0;
        for (double grade : grades) {
            sum += grade;
        }
        return sum / grades.length;
    }

    public boolean isApproved() {
        return average() >= PASSING_AVERAGE;
    }

    public double bestGrade() {
        double best = grades[0];
        for (double grade : grades) {
            if (grade > best) {
                best = grade;
            }
        }
        return best;
    }
}
