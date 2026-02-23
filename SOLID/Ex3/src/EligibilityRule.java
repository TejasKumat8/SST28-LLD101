import java.util.Optional;

/**
 * OCP: Abstraction for a single eligibility rule.
 * New rules can be added by creating new classes implementing this interface,
 * WITHOUT editing EligibilityEngine.
 */
public interface EligibilityRule {
    /**
     * Returns the failure reason if the student fails this rule,
     * or empty if the student passes.
     */
    Optional<String> evaluate(StudentProfile s);
}
