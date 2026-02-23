import java.nio.charset.StandardCharsets;

/**
 * LSP (Refactored):
 * PdfExporter documents its constraint explicitly.
 * The throw is intentional and documented — callers using the safe() wrapper
 * in Main are protected. No silent surprises.
 *
 * The content size limit is now documented in the class contract,
 * making it possible for callers to adapt without needing instanceof.
 */
public class PdfExporter extends Exporter {

    /** Maximum body length supported by this exporter. */
    public static final int MAX_BODY_LENGTH = 20;

    /**
     * @throws IllegalArgumentException if req.body length exceeds MAX_BODY_LENGTH (documented)
     */
    @Override
    public ExportResult export(ExportRequest req) {
        if (req == null) throw new IllegalArgumentException("req must not be null");
        if (req.body != null && req.body.length() > MAX_BODY_LENGTH) {
            throw new IllegalArgumentException("PDF cannot handle content > 20 chars");
        }
        String fakePdf = "PDF(" + req.title + "):" + req.body;
        return new ExportResult("application/pdf", fakePdf.getBytes(StandardCharsets.UTF_8));
    }
}
