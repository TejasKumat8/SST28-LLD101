import java.util.Optional;

/** OCP Rule: Checks minimum CGR threshold. */
public class CgrRule implements EligibilityRule {
    private final double minCgr;
    public CgrRule(double minCgr) { this.minCgr = minCgr; }

    @Override
    public Optional<String> evaluate(StudentProfile s) {
        if (s.cgr < minCgr) return Optional.of("CGR below " + minCgr);
        return Optional.empty();
    }
}
