import java.util.Optional;

/** OCP Rule: Checks minimum attendance percentage. */
public class AttendanceRule implements EligibilityRule {
    private final int minAttendance;
    public AttendanceRule(int minAttendance) { this.minAttendance = minAttendance; }

    @Override
    public Optional<String> evaluate(StudentProfile s) {
        if (s.attendancePct < minAttendance)
            return Optional.of("attendance below " + minAttendance);
        return Optional.empty();
    }
}
