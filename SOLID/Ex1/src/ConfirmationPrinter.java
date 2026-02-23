/**
 * SRP: Responsible ONLY for printing confirmation output to console.
 * Does not contain any business logic.
 */
public class ConfirmationPrinter {

    public void printSuccess(String id, int totalStudents, StudentRecord rec) {
        System.out.println("OK: created student " + id);
        System.out.println("Saved. Total students: " + totalStudents);
        System.out.println("CONFIRMATION:");
        System.out.println(rec);
    }

    public void printErrors(java.util.List<String> errors) {
        System.out.println("ERROR: cannot register");
        for (String e : errors) System.out.println("- " + e);
    }
}
