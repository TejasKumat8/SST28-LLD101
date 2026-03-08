package com.example.reports;

public class QuickCheck {

    public static void main(String[] args) {
        User student = new User("Riya", "STUDENT");
        User admin = new User("Kshitij", "ADMIN");

        Report adminReport = new ReportProxy("R-303", "Budget Audit", "ADMIN");
        Report facultyReport = new ReportProxy("R-202", "Midterm Review", "FACULTY");

        System.out.println("=== QuickCheck ===");

        // student cannot access faculty report
        facultyReport.display(student);
        System.out.println();

        // admin accesses admin report (first time: disk load)
        adminReport.display(admin);
        System.out.println();

        // admin accesses same proxy again (should NOT reload from disk)
        adminReport.display(admin);
    }
}
