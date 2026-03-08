package com.example.reports;

/**
 * ReportViewer now works through the Report abstraction (interface).
 * It is decoupled from any concrete implementation.
 */
public class ReportViewer {

    public void open(Report report, User user) {
        report.display(user);
    }
}
