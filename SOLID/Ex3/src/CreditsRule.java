import java.util.Optional;

/** OCP Rule: Checks minimum earned credits. */
public class CreditsRule implements EligibilityRule {
    private final int minCredits;
    public CreditsRule(int minCredits) { this.minCredits = minCredits; }

    @Override
    public Optional<String> evaluate(StudentProfile s) {
        if (s.earnedCredits < minCredits)
            return Optional.of("credits below " + minCredits);
        return Optional.empty();
    }
}
