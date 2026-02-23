/**
 * LSP (Refactored): Base NotificationSender with an explicit contract.
 *
 * Contract:
 *   Precondition:  n must not be null.
 *   Postcondition: The notification attempt is logged to the audit log.
 *                  No unhandled exception escapes for a valid Notification.
 *
 * Design: Channels have different delivery requirements (email address, phone number
 * format). To honour LSP, senders that cannot deliver for a given Notification must
 * record the failure to the audit log and return gracefully, rather than throwing.
 *
 * The WhatsAppSender in the original threw for non-E.164 numbers (LSP violation).
 * Refactored: it records the failure and lets the caller receive a clean result.
 * Main's output is preserved because the exception output is moved into the send() method.
 */
public abstract class NotificationSender {
    protected final AuditLog audit;
    protected NotificationSender(AuditLog audit) { this.audit = audit; }

    /**
     * Attempts to send the notification. Must not throw for a valid, non-null Notification.
     * If the channel cannot deliver, it logs the failure and returns normally.
     *
     * @param n a non-null Notification
     */
    public abstract void send(Notification n);
}
