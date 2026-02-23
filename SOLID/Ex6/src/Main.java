/**
 * Main – updated slightly for Ex6 LSP refactor.
 *
 * WhatsAppSender no longer throws — it handles the failure gracefully
 * internally.
 * The try/catch is kept for backward compatibility but will not trigger.
 * The "WA ERROR: ..." line is now printed from within WhatsAppSender.send().
 *
 * Output remains identical to sample:
 * === Notification Demo ===
 * EMAIL -> to=riya@sst.edu subject=Welcome body=Hello and welcome to SST!
 * SMS -> to=9876543210 body=Hello and welcome to SST!
 * WA ERROR: phone must start with + and country code
 * AUDIT entries=3
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("=== Notification Demo ===");
        AuditLog audit = new AuditLog();

        Notification n = new Notification("Welcome", "Hello and welcome to SST!", "riya@sst.edu", "9876543210");

        NotificationSender email = new EmailSender(audit);
        NotificationSender sms = new SmsSender(audit);
        NotificationSender wa = new WhatsAppSender(audit);

        email.send(n);
        sms.send(n);
        wa.send(n); // No longer throws; handles failure gracefully (LSP compliant)

        System.out.println("AUDIT entries=" + audit.size());
    }
}
