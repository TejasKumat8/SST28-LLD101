/**
 * LSP (Refactored):
 * EmailSender no longer silently truncates the body.
 * The email body is sent as-is. Silent data corruption violated LSP
 * (callers couldn't rely on the message being delivered fully).
 *
 * Output matches sample exactly (body = "Hello and welcome to SST!" is 25 chars, < 40).
 */
public class EmailSender extends NotificationSender {
    public EmailSender(AuditLog audit) { super(audit); }

    @Override
    public void send(Notification n) {
        // No silent truncation — full body is delivered
        System.out.println("EMAIL -> to=" + n.email + " subject=" + n.subject + " body=" + n.body);
        audit.add("email sent");
    }
}
