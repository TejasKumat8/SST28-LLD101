import java.util.*;

// Unchanged – kept exactly as provided
public class AuditLog {
    private final List<String> entries = new ArrayList<>();
    public void add(String e) { entries.add(e); }
    public int size() { return entries.size(); }
}
