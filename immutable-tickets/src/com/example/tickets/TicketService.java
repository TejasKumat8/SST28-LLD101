package com.example.tickets;

import java.util.ArrayList;
import java.util.List;

/**
 * Service layer that creates tickets.
 *
 * CURRENT STATE (BROKEN ON PURPOSE):
 * - creates partially valid objects
 * - mutates after creation (bad for auditability)
 * - validation is scattered & incomplete
 *
 * TODO (student):
 * - After introducing immutable IncidentTicket + Builder, refactor this to stop
 * mutating.
 */
public class TicketService {

    public IncidentTicket createTicket(String id, String reporterEmail, String title) {
        // Validation now handled entirely by the Builder
        return IncidentTicket.builder()
                .id(id)
                .reporterEmail(reporterEmail)
                .title(title)
                .priority("MEDIUM")
                .source("CLI")
                .customerVisible(false)
                .tags(List.of("NEW"))
                .build();
    }

    public IncidentTicket escalateToCritical(IncidentTicket t) {
        // Return a new ticket instance with updated priority and tags
        List<String> newTags = new ArrayList<>(t.getTags());
        newTags.add("ESCALATED");
        return t.toBuilder()
                .priority("CRITICAL")
                .tags(newTags)
                .build();
    }

    public IncidentTicket assign(IncidentTicket t, String assigneeEmail) {
        // Validation now handled by Builder
        return t.toBuilder()
                .assigneeEmail(assigneeEmail)
                .build();
    }
}
