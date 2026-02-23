/**
 * SRP / DIP: Abstraction for student persistence.
 * OnboardingService depends on this interface, not on FakeDb directly.
 */
public interface StudentRepository {
    void save(StudentRecord r);
    int count();
}
