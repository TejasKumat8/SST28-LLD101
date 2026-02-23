import java.util.*;

/**
 * OCP (Refactored):
 * EligibilityEngine holds a list of EligibilityRule objects.
 * Adding a new rule requires ONLY creating a new class and wiring it here –
 * the evaluate logic does NOT change (closed for modification, open for extension).
 *
 * Note: The original logic used else-if, meaning rules short-circuited after the first failure.
 * The refactored engine preserves that behavior with a fail-fast approach *per the original*:
 * the first failing rule stops evaluation (matching original's else-if semantics).
 * This ensures identical output for the given sample.
 */
public class EligibilityEngine {

    private final FakeEligibilityStore store;

    // Rules are evaluated in order; first failure wins (matches original else-if chain)
    private final List<EligibilityRule> rules = List.of(
            new DisciplinaryFlagRule(),
            new CgrRule(8.0),
            new AttendanceRule(75),
            new CreditsRule(20)
    );

    public EligibilityEngine(FakeEligibilityStore store) { this.store = store; }

    public EligibilityEngineResult evaluate(StudentProfile s) {
        for (EligibilityRule rule : rules) {
            Optional<String> reason = rule.evaluate(s);
            if (reason.isPresent()) {
                return new EligibilityEngineResult("NOT_ELIGIBLE", List.of(reason.get()));
            }
        }
        return new EligibilityEngineResult("ELIGIBLE", List.of());
    }

    public void runAndPrint(StudentProfile s) {
        ReportPrinter p = new ReportPrinter();
        EligibilityEngineResult r = evaluate(s);
        p.print(s, r);
        store.save(s.rollNo, r.status);
    }
}

class EligibilityEngineResult {
    public final String status;
    public final List<String> reasons;

    public EligibilityEngineResult(String status, List<String> reasons) {
        this.status = status;
        this.reasons = reasons;
    }
}
