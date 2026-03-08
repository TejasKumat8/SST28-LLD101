package com.example.reports;

/**
 * Demo for CampusVault using ReportProxy.
 *
 * Expected output:
 * - Student can view PUBLIC report.
 * - Student is denied FACULTY report.
 * - Faculty can view FACULTY report (disk load happens once).
 * - Admin can view ADMIN report (disk load happens once).
 * - Second admin view reuses cached RealReport (no disk load).
 */
public class App {

    public static void main(String[] args) {
        User student = new User("Jasleen", "STUDENT");
        User faculty = new User("Prof. Noor", "FACULTY");
        User admin = new User("Kshitij", "ADMIN");

        // Clients now use ReportProxy (which implements Report interface)
        Report publicReport = new ReportProxy("R-101", "Orientation Plan", "PUBLIC");
        Report facultyReport = new ReportProxy("R-202", "Midterm Review", "FACULTY");
        Report adminReport = new ReportProxy("R-303", "Budget Audit", "ADMIN");

        ReportViewer viewer = new ReportViewer();

        System.out.println("=== CampusVault Demo ===");

        System.out.println("\n-- Student opens PUBLIC report --");
        viewer.open(publicReport, student);

        System.out.println("\n-- Student tries FACULTY report (should be denied) --");
        viewer.open(facultyReport, student);

        System.out.println("\n-- Faculty opens FACULTY report (first time → disk load) --");
        viewer.open(facultyReport, faculty);

        System.out.println("\n-- Faculty opens FACULTY report again (should use cache) --");
        viewer.open(facultyReport, faculty);

        System.out.println("\n-- Admin opens ADMIN report (first time → disk load) --");
        viewer.open(adminReport, admin);

        System.out.println("\n-- Admin opens ADMIN report again (should use cache) --");
        viewer.open(adminReport, admin);
    }
}
