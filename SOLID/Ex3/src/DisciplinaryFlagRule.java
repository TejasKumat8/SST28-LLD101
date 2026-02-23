import java.util.Optional;

/** OCP Rule: Checks disciplinary flag. */
public class DisciplinaryFlagRule implements EligibilityRule {
    @Override
    public Optional<String> evaluate(StudentProfile s) {
        if (s.disciplinaryFlag != LegacyFlags.NONE)
            return Optional.of("disciplinary flag present");
        return Optional.empty();
    }
}
