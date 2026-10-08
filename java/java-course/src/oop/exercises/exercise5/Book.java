package oop.exercises.exercise5;

/** A book. Fixed version of the buggy class from the exercise statement. */
public class Book {

    private final String title;      // bug 1 fixed: no longer a public field
    private int pages;

    /**
     * Bug 2 fixed: no {@code void}, so this is a real constructor.
     * Bug 3 fixed: {@code this.title = title} assigns the field, not the parameter to itself.
     */
    public Book(String title, int pages) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("The title is required");
        }
        requirePositivePages(pages);
        this.title = title;
        this.pages = pages;
    }

    public String getTitle() {
        return title;
    }

    public int getPages() {
        return pages;
    }

    /** Bug 4 fixed: the setter validates, so the invariant "pages > 0" always holds. */
    public void setPages(int pages) {
        requirePositivePages(pages);
        this.pages = pages;
    }

    private static void requirePositivePages(int pages) {
        if (pages <= 0) {
            throw new IllegalArgumentException("The page count must be positive");
        }
    }

    @Override
    public String toString() {
        return String.format("Book[title=%s, pages=%d]", title, pages);
    }
}
