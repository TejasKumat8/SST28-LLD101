/**
 * LSP (Refactored):
 * SmsSender ignores subject — this is now documented as SMS channel behavior.
 * The base contract does not mandate subject usage; SMS channels by nature
 * only deliver a body. This is clarified, not a hidden surprise.
 */
public class SmsSender extends NotificationSender {
    public SmsSender(AuditLog audit) {
        super(audit);
    }

    @Override
    public void send(Notification n) {
        // SMS does not support subject — this is documented channel behavior, not a bug
        System.out.println("SMS -> to=" + n.phone + " body=" + n.body);
        audit.add("sms sent");
    }
}
