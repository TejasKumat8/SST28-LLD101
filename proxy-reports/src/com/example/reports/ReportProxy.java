package com.example.reports;

/**
 * ReportProxy implements the Proxy pattern:
 *  1. Access control  — blocks unauthorized users before any real loading.
 *  2. Lazy loading    — RealReport is created only on the first permitted display().
 *  3. Caching         — the same RealReport instance is reused for subsequent calls.
 */
public class ReportProxy implements Report {

    private final String reportId;
    private final String title;
    private final String classification;
    private final AccessControl accessControl = new AccessControl();

    // Lazy: null until the first authorized display()
    private RealReport realReport;

    public ReportProxy(String reportId, String title, String classification) {
        this.reportId = reportId;
        this.title = title;
        this.classification = classification;
    }

    @Override
    public void display(User user) {
        if (!accessControl.canAccess(user, classification)) {
            System.out.println("[ACCESS DENIED] " + user.getName()
                    + " (" + user.getRole() + ") cannot access "
                    + classification + " report '" + title + "'");
            return;
        }

        // Lazy-load and cache
        if (realReport == null) {
            System.out.println("[proxy] first access — creating RealReport for " + reportId);
            realReport = new RealReport(reportId, title, classification);
        } else {
            System.out.println("[proxy] cache hit — reusing RealReport for " + reportId);
        }

        realReport.display(user);
    }
}
