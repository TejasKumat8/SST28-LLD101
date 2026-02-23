import java.util.*;

/**
 * FakeDb now implements StudentRepository so OnboardingService
 * depends on the abstraction, not the concrete class.
 */
public class FakeDb implements StudentRepository {
    private final List<StudentRecord> rows = new ArrayList<>();

    @Override public void save(StudentRecord r) { rows.add(r); }
    @Override public int count() { return rows.size(); }
    public List<StudentRecord> all() { return Collections.unmodifiableList(rows); }
}
