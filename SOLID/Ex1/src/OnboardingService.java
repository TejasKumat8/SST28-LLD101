import java.util.*;

/**
 * SRP (Refactored):
 * OnboardingService ONLY orchestrates the workflow.
 * It delegates parsing → RawInputParser
 *              validation → StudentValidator
 *              persistence → StudentRepository (interface, not FakeDb directly)
 *              printing → ConfirmationPrinter
 *
 * FakeDb now implements StudentRepository so existing FakeDb still works.
 */
public class OnboardingService {

    private final StudentRepository repo;
    private final RawInputParser parser = new RawInputParser();
    private final StudentValidator validator = new StudentValidator();
    private final ConfirmationPrinter printer = new ConfirmationPrinter();

    public OnboardingService(StudentRepository repo) { this.repo = repo; }

    public void registerFromRawInput(String raw) {
        System.out.println("INPUT: " + raw);

        Map<String, String> kv = parser.parse(raw);

        String name    = kv.getOrDefault("name", "");
        String email   = kv.getOrDefault("email", "");
        String phone   = kv.getOrDefault("phone", "");
        String program = kv.getOrDefault("program", "");

        List<String> errors = validator.validate(name, email, phone, program);
        if (!errors.isEmpty()) {
            printer.printErrors(errors);
            return;
        }

        String id = IdUtil.nextStudentId(repo.count());
        StudentRecord rec = new StudentRecord(id, name, email, phone, program);
        repo.save(rec);

        printer.printSuccess(id, repo.count(), rec);
    }
}
