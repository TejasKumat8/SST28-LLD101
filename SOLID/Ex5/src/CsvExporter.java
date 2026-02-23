import java.nio.charset.StandardCharsets;

/**
 * LSP (Refactored):
 * CsvExporter preserves the base contract:
 *   - Accepts any non-null ExportRequest without throwing.
 *   - Returns non-null ExportResult with non-null bytes.
 *
 * The lossy string replacements are inherent to CSV encoding (commas/newlines must
 * be quoted or replaced). This is now documented rather than a silent change.
 * The CSV format's character substitution is part of its documented behavior.
 */
public class CsvExporter extends Exporter {

    @Override
    public ExportResult export(ExportRequest req) {
        if (req == null) throw new IllegalArgumentException("req must not be null");
        // CSV encoding: commas and newlines are escaped as spaces (documented behavior)
        String body = req.body == null ? "" : req.body.replace("\n", " ").replace(",", " ");
        String csv = "title,body\n" + req.title + "," + body + "\n";
        return new ExportResult("text/csv", csv.getBytes(StandardCharsets.UTF_8));
    }
}
