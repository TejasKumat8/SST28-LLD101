package com.example.reports;

/**
 * RealReport is the RealSubject in the Proxy pattern.
 * It does the expensive disk-loading work.
 * Clients should never hold this directly; they should use ReportProxy.
 */
public class RealReport implements Report {

    private final String reportId;
    private final String title;
    private final String classification;
    private String content; // loaded lazily

    public RealReport(String reportId, String title, String classification) {
        this.reportId = reportId;
        this.title = title;
        this.classification = classification;
    }

    @Override
    public void display(User user) {
        if (content == null) {
            content = loadFromDisk();
        }
        System.out.println("REPORT -> id=" + reportId
                + " title=" + title
                + " classification=" + classification
                + " openedBy=" + user.getName());
        System.out.println("CONTENT: " + content);
    }

    public String getClassification() {
        return classification;
    }

    private String loadFromDisk() {
        System.out.println("[disk] loading report " + reportId + " ...");
        try { Thread.sleep(120); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        return "Internal report body for " + title;
    }
}
