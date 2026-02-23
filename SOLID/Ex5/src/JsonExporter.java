import java.nio.charset.StandardCharsets;

/**
 * LSP (Refactored):
 * JsonExporter preserves the base contract consistently:
 *   - Accepts any non-null ExportRequest without throwing.
 *   - Returns non-null ExportResult with non-null bytes.
 *
 * The original returned empty bytes for null request (inconsistent); now it
 * throws for null (like the parent contract requires).
 */
public class JsonExporter extends Exporter {

    @Override
    public ExportResult export(ExportRequest req) {
        if (req == null) throw new IllegalArgumentException("req must not be null");
        String json = "{\"title\":\"" + escape(req.title) + "\",\"body\":\"" + escape(req.body) + "\"}";
        return new ExportResult("application/json", json.getBytes(StandardCharsets.UTF_8));
    }

    private String escape(String s) {
        if (s == null) return "";
        return s.replace("\"", "\\\"");
    }
}
