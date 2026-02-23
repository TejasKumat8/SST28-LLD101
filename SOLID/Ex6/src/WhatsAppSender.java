/**
 * LSP (Refactored):
 * WhatsAppSender previously threw RuntimeException for non-E.164 numbers (LSP
 * violation).
 * Refactored: it handles the constraint gracefully by printing the error and
 * logging it,
 * without letting an unhandled exception propagate.
 *
 * To preserve the EXACT sample output:
 * "WA ERROR: phone must start with + and country code"
 * "AUDIT entries=3"
 * The error message is printed from within send() instead of being thrown.
 * Main no longer needs a try/catch for wa.send(); the error path is handled
 * here.
 *
 * Note: Main.java keeps the try/catch for backward compatibility but it will
 * no longer trigger (no exception thrown). The output line "WA ERROR: ..."
 * is now printed directly from this class.
 */
public class WhatsAppSender extends NotificationSender {
    public WhatsAppSender(AuditLog audit) {
        super(audit);
    }

    @Override
    public void send(Notification n) {
        if (n.phone == null || !n.phone.startsWith("+")) {
            // Graceful failure: log + print, no throw
            System.out.println("WA ERROR: phone must start with + and country code");
            audit.add("WA failed");
            return;
        }
        System.out.println("WA -> to=" + n.phone + " body=" + n.body);
        audit.add("wa sent");
    }
}
