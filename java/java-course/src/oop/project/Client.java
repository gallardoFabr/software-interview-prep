package oop.project;

/** A bank client. The national ID is a String so leading zeros are preserved. */
public class Client {

    private final String nationalId;
    private final String name;
    private final String email;

    /**
     * @throws IllegalArgumentException if the ID is not exactly 8 digits,
     *                                  the name is blank, or the email has no valid format
     */
    public Client(String nationalId, String name, String email) {
        if (!isValidNationalId(nationalId)) {
            throw new IllegalArgumentException("The national ID must have exactly 8 digits");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("The name is required");
        }
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("The email is not valid");
        }
        this.nationalId = nationalId;
        this.name = name;
        this.email = email;
    }

    private static boolean isValidNationalId(String id) {
        return id != null && id.length() == 8;
    }

    /** Simple check: text, one '@', text. */
    private static boolean isValidEmail(String email) {
        return email != null && email.matches("[^@\\s]+@[^@\\s]+");
    }

    public String getNationalId() {
        return nationalId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "Name: " + name + "  ID: " + nationalId + "  Email: " + email;
    }
}
